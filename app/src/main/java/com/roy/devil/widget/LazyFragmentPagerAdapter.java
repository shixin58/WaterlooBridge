package com.roy.devil.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public abstract class LazyFragmentPagerAdapter extends LazyPagerAdapter<Fragment> {

	private final FragmentManager mFragmentManager;
	private FragmentTransaction mCurTransaction = null;

	public LazyFragmentPagerAdapter(FragmentManager fm) {
		mFragmentManager = fm;
	}

	@Override
	public void startUpdate(ViewGroup container) {
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Log.i("PagerAdapter", "instantiateItem "+position);
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		final long itemId = getItemId(position);

		// Do we already have this fragment?
		String name = makeFragmentName(container.getId(), itemId);
		Fragment fragment = mFragmentManager.findFragmentByTag(name);
		if (fragment != null) {
			// 无效
			mCurTransaction.attach(fragment);
		} else {
			fragment = getItem(container, position);
			if (fragment instanceof Deferrable) {
				// 1
				mLazyItems.put(position, fragment);
			} else {
				mCurTransaction.add(container.getId(), fragment, name);
			}
		}
		if (fragment != getCurrentItem()) {
			fragment.setMenuVisibility(false);
			fragment.setUserVisibleHint(false);
		}

		return fragment;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Log.i("PagerAdapter", "destroyItem "+position);
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		final long itemId = getItemId(position);
		String name = makeFragmentName(container.getId(), itemId);
		if (mFragmentManager.findFragmentByTag(name) == null) {
			// 无效
			mCurTransaction.detach((Fragment) object);
		} else {
			// 3
            mLazyItems.remove(position);
		}
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		Log.i("PagerAdapter", "setPrimaryItem "+position);
		Fragment fragment = (Fragment)object;
		if (fragment != getCurrentItem()) {
			if (getCurrentItem() != null) {
				getCurrentItem().setMenuVisibility(false);
				getCurrentItem().setUserVisibleHint(false);
			}
			if (fragment != null) {
				fragment.setMenuVisibility(true);
				fragment.setUserVisibleHint(true);
			}
		}
		super.setPrimaryItem(container, position, object);
	}

    @Override
	public Fragment addLazyItem(ViewGroup container, int position) {
		Fragment fragment = mLazyItems.get(position);
		if (fragment == null)
			return null;
		final long itemId = getItemId(position);
		String name = makeFragmentName(container.getId(), itemId);
		if (mFragmentManager.findFragmentByTag(name) == null) {
			Log.i("PagerAdapter", "addLazyItem "+position);
			if (mCurTransaction == null) {
				mCurTransaction = mFragmentManager.beginTransaction();
			}
			// 2
			mCurTransaction.add(container.getId(), fragment, name);
		}
        return fragment;
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		if (mCurTransaction != null) {
			mCurTransaction.commitAllowingStateLoss();
			mCurTransaction = null;
			mFragmentManager.executePendingTransactions();
		}
	}

    @Override
	public boolean isViewFromObject(View view, Object object) {
		return ((Fragment) object).getView() == view;
	}

	public long getItemId(int position) {
		return position;
	}

	private static String makeFragmentName(int viewId, long id) {
		return "android:switcher:" + viewId + ":" + id;
	}

    /**
     * mark the fragment can be added lazily
     */
    public interface Deferrable {
	}
}