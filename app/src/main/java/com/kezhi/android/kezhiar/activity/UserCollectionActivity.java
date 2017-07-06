package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:20:37
 * Email: song511653502@gmail.com
 * Description: 我的收藏
 */

public class UserCollectionActivity extends BaseActivity {

    private Toolbar mToolbar;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, UserCollectionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_user_collection;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mToolbar = $(R.id.tb_user_collection);
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
