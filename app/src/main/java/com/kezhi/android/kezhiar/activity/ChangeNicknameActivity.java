package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.SharedPreferencesHelper;

/**
 * Created by songtao on 2017/5/4.
 */

public class ChangeNicknameActivity extends BaseActivity {

    private Toolbar mToolbarNickname;
    private TextView mTextViewSave;
    private EditText mEditTextNewName;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, ChangeNicknameActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_change_nickname;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mToolbarNickname = $(R.id.tb_change_nickname);
        mTextViewSave = $(R.id.tv_save);
        mEditTextNewName = $(R.id.et_new_nickname);

        mTextViewSave.setOnClickListener(this);
    }

    @Override
    protected void doBusiness(Context context) {
        //配置ToolBar
        setSupportActionBar(mToolbarNickname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.tv_save:
                String newNickname = mEditTextNewName.getText().toString().trim();
                SharedPreferencesHelper.getInstance().saveData("name",newNickname);
                showToast("已保存");
                finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==android.R.id.home){
           finish();
       }
        return super.onOptionsItemSelected(item);
    }
}
