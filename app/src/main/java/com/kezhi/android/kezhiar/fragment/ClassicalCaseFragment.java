package com.kezhi.android.kezhiar.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.adapter.ClassicalClassPagerAdapter;
import com.kezhi.android.kezhiar.base.BaseFragment;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:9:12
 * Email: song511653502@gmail.com
 * Description: 经典案例
 */

public class ClassicalCaseFragment extends BaseFragment {

    private TextView mTextViewKeZhiSite;
    private FragmentManager mFragmentManager;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mImageViewAllChannel;

    private ClassicalClassPagerAdapter mPagerAdapter;

    public static ClassicalCaseFragment newInstance() {
        ClassicalCaseFragment fragment = new ClassicalCaseFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_classical_case;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTextViewKeZhiSite = $(view, R.id.tv_classical_case_kezhi_site);
        mViewPager = $(view, R.id.vp_classical_case);
        mTabLayout = $(view, R.id.tl_classical_case);
        mImageViewAllChannel = $(view, R.id.iv_classical_case_all_channel);

        mFragmentManager = getChildFragmentManager();
        mPagerAdapter = new ClassicalClassPagerAdapter(getActivity(), mFragmentManager);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTextViewKeZhiSite.setOnClickListener(this);
        mImageViewAllChannel.setOnClickListener(this);

    }

    @Override
    protected void doBusiness(Context context) {

    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.tv_classical_case_kezhi_site:
                Uri uri = Uri.parse("https://www.baidu.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.iv_classical_case_all_channel:
                break;
            default:
        }
    }
}
