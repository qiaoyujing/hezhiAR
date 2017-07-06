package com.kezhi.android.kezhiar.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.SPUtil;

/**
 * Author: FishpondKing
 * Date: 2017/5/2:14:59
 * Email: song511653502@gmail.com
 * Description: 启动页
 */

public class SplashActivity extends BaseActivity {

    //最少停留在SplashScreen的时间（毫秒）
    private static final int DELAY_START_TIME = 2000;

    private ImageView mImageViewGuidePage;

    private Activity mActivity;
    private Boolean isFirstInstall;
    private int[] mGuidePageImages = new int[]{R.drawable.guide_page_1, R.drawable.guide_page_2,
            R.drawable.guide_page_3, R.drawable.guide_page_4, R.drawable.guide_page_5};
    private int mCurGuidePage;

    @Override
    protected void initVariables(Bundle params) {
        mActivity = SplashActivity.this;
        isFirstInstall = SPUtil.getBoolean("isFirstInstall", true);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mImageViewGuidePage = $(R.id.iv_splash_activity);
        mImageViewGuidePage.setOnClickListener(this);
    }

    @Override
    protected void doBusiness(Context context) throws InterruptedException {
        mImageViewGuidePage.setClickable(false);
        //设置最少停留在SplashScreen的时间
        Thread.currentThread().sleep(DELAY_START_TIME);
        //检查是否为第一次安装
        if (isFirstInstall) {
            mCurGuidePage = 0;
            mImageViewGuidePage.setImageResource(mGuidePageImages[mCurGuidePage]);
            mImageViewGuidePage.setClickable(true);
        } else {
            MainActivity.activityStart(mActivity);
            finish();
        }
    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_splash_activity:
                if (mCurGuidePage < mGuidePageImages.length - 1) {
                    mImageViewGuidePage.setImageResource(mGuidePageImages[++mCurGuidePage]);
                } else if (mCurGuidePage == mGuidePageImages.length - 1) {
                    SPUtil.put("isFirstInstall", false);
                    MainActivity.activityStart(mActivity);
                    finish();
                    //淡入淡出动画
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
                break;
            default:
        }
    }
}
