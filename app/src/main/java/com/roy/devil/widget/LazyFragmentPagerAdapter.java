package com.roy.devil.widget;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public abstract class LazyFragmentPagerAdapter extends LazyPagerAdapter<Fragment> {
	private static final String TAG = LazyFragmentPagerAdapter.class.getSimpleName();

	private final FragmentManager mFragmentManager;
	private FragmentTransaction mCurTransaction = null;

	public LazyFragmentPagerAdapter(FragmentManager fm) {
		mFragmentManager = fm;
	}

	@Override
	public void startUpdate(@NonNull ViewGroup container) {
		if (container.getId() == View.NO_ID) {
			throw new IllegalStateException("ViewPager with adapter " + this
					+ " requires a view id");
		}
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		Log.i(TAG, "instantiateItem "+position);
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		String name = makeFragmentName(container.getId(), getItemId(position));
		Fragment fragment = mFragmentManager.findFragmentByTag(name);
		if (fragment != null) {
			if (!(fragment instanceof Deferrable)) {
				mCurTransaction.attach(fragment);
			}
		} else {
			fragment = getItem(container, position);
			if (fragment instanceof Deferrable) {
				// 懒加载第1步
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
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		Log.i(TAG, "destroyItem "+position);
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		if (object instanceof Deferrable) {
			// 懒加载第3步
			mLazyItems.remove(position);
		} else {
			mCurTransaction.detach((Fragment) object);
		}
	}

	@Override
	public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		Log.i(TAG, "setPrimaryItem "+position);
		Fragment fragment = (Fragment)object;
		if (fragment != getCurrentItem()) {
			if (getCurrentItem() != null) {
				getCurrentItem().setMenuVisibility(false);
				getCurrentItem().setUserVisibleHint(false);
			}
			fragment.setMenuVisibility(true);
			fragment.setUserVisibleHint(true);
		}
		super.setPrimaryItem(container, position, object);
	}

    @Override
	public Fragment addLazyItem(ViewGroup container, int position) {
		Fragment fragment = mLazyItems.get(position);
		if (fragment == null)
			return null;
		long itemId = getItemId(position);
		String name = makeFragmentName(container.getId(), itemId);
		if (mFragmentManager.findFragmentByTag(name) == null) {
			Log.i(TAG, "addLazyItem "+position);
			if (mCurTransaction == null) {
				mCurTransaction = mFragmentManager.beginTransaction();
			}
			// 懒加载第2步
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