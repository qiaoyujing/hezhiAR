package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kezhi.android.kezhiar.base.BaseActivity;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:20:38
 * Email: song511653502@gmail.com
 * Description: 我的上传
 */

public class UserUploadActivity extends BaseActivity {

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, UserUploadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return 0;
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
