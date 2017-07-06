package com.kezhi.android.kezhiar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseFragment;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:9:53
 * Email: song511653502@gmail.com
 * Description: 探索
 */

public class ExploreFragment extends BaseFragment {

    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_explore;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void doBusiness(Context context) {

    }

    @Override
    public void viewClick(View v) {

    }
}
