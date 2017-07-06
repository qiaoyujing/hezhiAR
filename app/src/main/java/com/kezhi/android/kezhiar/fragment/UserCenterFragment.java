package com.kezhi.android.kezhiar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.activity.AccountManageActivity;
import com.kezhi.android.kezhiar.activity.LoginActivity;
import com.kezhi.android.kezhiar.activity.UserCollectionActivity;
import com.kezhi.android.kezhiar.activity.VersionInfoActivity;
import com.kezhi.android.kezhiar.base.BaseFragment;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:9:12
 * Email: song511653502@gmail.com
 * Description: 个人中心
 */

public class UserCenterFragment extends BaseFragment {

    private TextView mTextViewLogin;
    private LinearLayout mLLUserCollection;
    private LinearLayout mLLUserUpload;
    private LinearLayout mLLAccountManager;
    private LinearLayout mLLClearCache;
    private LinearLayout mLLVersionInfo;

    public static UserCenterFragment newInstance() {
        UserCenterFragment fragment = new UserCenterFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_user_center;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTextViewLogin = $(view, R.id.tv_user_center_login_sign);
        mLLUserCollection = $(view, R.id.ll_user_center_user_collection);
        mLLUserUpload = $(view, R.id.ll_user_center_user_upload);
        mLLAccountManager = $(view, R.id.ll_user_center_account_manager);
        mLLClearCache = $(view, R.id.ll_user_center_clear_cache);
        mLLVersionInfo = $(view, R.id.ll_user_center_version_info);

        mTextViewLogin.setOnClickListener(this);
        mLLUserCollection.setOnClickListener(this);
        mLLUserUpload.setOnClickListener(this);
        mLLAccountManager.setOnClickListener(this);
        mLLClearCache.setOnClickListener(this);
        mLLVersionInfo.setOnClickListener(this);
    }

    @Override
    protected void doBusiness(Context context) {

    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.tv_user_center_login_sign:
                LoginActivity.activityStart(getActivity());
                break;
            case R.id.ll_user_center_user_collection:
                UserCollectionActivity.activityStart(getActivity());
                break;
            case R.id.ll_user_center_user_upload:
                break;
            case R.id.ll_user_center_account_manager:
                AccountManageActivity.activityStart(getActivity());
                break;
            case R.id.ll_user_center_clear_cache:
                break;
            case R.id.ll_user_center_version_info:
                VersionInfoActivity.activityStart(getActivity());
                break;
            default:
        }
    }
}
