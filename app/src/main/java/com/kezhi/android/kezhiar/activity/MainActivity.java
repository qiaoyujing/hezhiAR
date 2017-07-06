package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.fragment.ClassicalCaseFragment;
import com.kezhi.android.kezhiar.fragment.UserCenterFragment;

public class MainActivity extends BaseActivity {

    private Fragment mClassicalCaseFragment;
    private Fragment mUserCenterFragment;
    private LinearLayout mLLClassicalCase;
    private ImageView mImageViewClassicalCase;
    private TextView mTextViewClassicalCase;
    private ImageView mImageViewAR;
    private LinearLayout mLLUserCenter;
    private ImageView mImageViewUserCenter;
    private TextView mTextViewUserCenter;

    private Resources mResources;
    private FragmentManager mFragmentManager;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {
        mResources = getResources();
        mClassicalCaseFragment = ClassicalCaseFragment.newInstance();
        mUserCenterFragment = UserCenterFragment.newInstance();
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mLLClassicalCase = $(R.id.ll_main_activity_classical_case);
        mImageViewClassicalCase = $(R.id.iv_main_activity_classical_case);
        mTextViewClassicalCase = $(R.id.tv_main_activity_classical_case);
        mImageViewAR = $(R.id.iv_main_activity_ar);
        mLLUserCenter = $(R.id.ll_main_activity_user_center);
        mImageViewUserCenter = $(R.id.iv_main_activity_user_center);
        mTextViewUserCenter = $(R.id.tv_main_activity_user_center);

        mFragmentManager = getSupportFragmentManager();

        mLLClassicalCase.setOnClickListener(this);
        mLLUserCenter.setOnClickListener(this);
    }

    @Override
    protected void doBusiness(Context context) {
        //默认打开MainActivity时选择“经典案例”选项卡
        mFragmentManager.beginTransaction()
                .add(R.id.fl_main_activity_fragment_container, mClassicalCaseFragment)
                .add(R.id.fl_main_activity_fragment_container, mUserCenterFragment)
                .hide(mUserCenterFragment)
                .commit();
        selectClassicalCaseTab();
    }

    //选择“经典案例"选项卡
    private void selectClassicalCaseTab(){
        mImageViewClassicalCase.setEnabled(true);
        mTextViewClassicalCase.setTextColor(mResources.getColor(R.color.green));
        mImageViewUserCenter.setEnabled(false);
        mTextViewUserCenter.setTextColor(mResources.getColor(R.color.grey));
    }

    //选择“个人中心”选项卡
    private void selectUserCenterTab(){
        mImageViewClassicalCase.setEnabled(false);
        mTextViewClassicalCase.setTextColor(mResources.getColor(R.color.grey));
        mImageViewUserCenter.setEnabled(true);
        mTextViewUserCenter.setTextColor(mResources.getColor(R.color.green));
    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.ll_main_activity_classical_case:
                mFragmentManager.beginTransaction()
                        .show(mClassicalCaseFragment)
                        .hide(mUserCenterFragment)
                        .commit();
                selectClassicalCaseTab();
                break;
            case R.id.iv_main_activity_ar:
                break;
            case R.id.ll_main_activity_user_center:
                mFragmentManager.beginTransaction()
                        .show(mUserCenterFragment)
                        .hide(mClassicalCaseFragment)
                        .commit();
                selectUserCenterTab();
                break;
            default:
        }
    }

}
