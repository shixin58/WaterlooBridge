package com.roy.devil.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bride.baselib.BaseFragment;
import com.bride.baselib.ResUtils;
import com.roy.devil.R;

import androidx.annotation.Nullable;

/**
 * <p>Created by shixin on 2018/4/22.
 */
public class RankingListFragment extends BaseFragment {
    private static final String TAG = RankingListFragment.class.getSimpleName();
    public static final String KEY_CAR_LEVEL = "key_car_level";
    /** 紧凑型 */
    public static int CAR_LEVEL_JINCOUXINGCHE = 3;
    /** SUV */
    public static int CAR_LEVEL_SUV = 7;
    /** 新能源 */
    public static final int MODEL_LEVEL_NEW_ENERGY = 0;
    /** 微型车 */
    public static int CAR_LEVEL_WEIXINGCHE = 1;
    /** 小型车 */
    public static int CAR_LEVEL_XIAOXINGCHE = 2;
    /** 中型车 */
    public static int CAR_LEVEL_ZHONGXINGCHE = 4;
    /** 中大型车 */
    public static int CAR_LEVEL_ZHONGDAXINGCHE = 5;
    /** MPV */
    public static int CAR_LEVEL_MPV = 8;
    public static final int[] CAR_LEVELS = {CAR_LEVEL_JINCOUXINGCHE, CAR_LEVEL_SUV, MODEL_LEVEL_NEW_ENERGY, CAR_LEVEL_WEIXINGCHE,
            CAR_LEVEL_XIAOXINGCHE, CAR_LEVEL_ZHONGXINGCHE, CAR_LEVEL_ZHONGDAXINGCHE, CAR_LEVEL_MPV};
    // 自动按key排序
    public static SparseArray<String> CAR_LEVEL_NAMES = new SparseArray<>();
    static {
         CAR_LEVEL_NAMES.append(CAR_LEVEL_JINCOUXINGCHE, ResUtils.getString(R.string.car_level_jincouxingche));
         CAR_LEVEL_NAMES.append(CAR_LEVEL_SUV, ResUtils.getString(R.string.car_level_suv));
         CAR_LEVEL_NAMES.append(MODEL_LEVEL_NEW_ENERGY, ResUtils.getString(R.string.car_level_new_energy));
         CAR_LEVEL_NAMES.append(CAR_LEVEL_WEIXINGCHE, ResUtils.getString(R.string.car_level_minicar));
         CAR_LEVEL_NAMES.append(CAR_LEVEL_XIAOXINGCHE, ResUtils.getString(R.string.car_level_xiaoxingche));
         CAR_LEVEL_NAMES.append(CAR_LEVEL_ZHONGXINGCHE, ResUtils.getString(R.string.car_level_midsize_car));
         CAR_LEVEL_NAMES.append(CAR_LEVEL_ZHONGDAXINGCHE, ResUtils.getString(R.string.car_level_zhongdaxingche));
         CAR_LEVEL_NAMES.append(CAR_LEVEL_MPV, ResUtils.getString(R.string.car_level_mpv));
    }

    private int mCarLevel;

    public static RankingListFragment newInstance(int carLevel) {
        RankingListFragment fragment = new RankingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CAR_LEVEL, carLevel);
        fragment.setArguments(bundle);
        Log.i(TAG, "new RankingListFragment() "+carLevel);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCarLevel = getArguments().getInt(KEY_CAR_LEVEL, CAR_LEVEL_JINCOUXINGCHE);
        Log.i(TAG, "onAttach - "+mCarLevel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate - "+mCarLevel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView - "+mCarLevel);
        return inflater.inflate(R.layout.fragment_ranking_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated - "+mCarLevel);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated - "+mCarLevel);

        String carLevelName = CAR_LEVEL_NAMES.get(mCarLevel);
        TextView tvCarLevel = view.findViewById(R.id.tv_car_level);
        tvCarLevel.setText(carLevelName);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart - "+mCarLevel);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume - "+mCarLevel);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause - "+mCarLevel);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop - "+mCarLevel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView - "+mCarLevel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy - "+mCarLevel);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach - "+mCarLevel);
    }
}
