package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:20:38
 * Email: song511653502@gmail.com
 * Description: 版本信息
 */

public class VersionInfoActivity extends BaseActivity {

    private Toolbar mToolbar;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, VersionInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_version_info;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mToolbar = $(R.id.tb_version_info);
    }

    @Override
    protected void doBusiness(Context context) {
        //配置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void viewClick(View v) {

    }


}
