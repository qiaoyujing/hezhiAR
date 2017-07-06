package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.CheckBoxUtil;

/**
 * Created by songtao on 2017/5/4.
 */

public class ChangePwdActivity extends BaseActivity {
    private Toolbar mToolbar;
    private EditText mEditTextPreviousPwd, mEditTextNewPwd, mEditTextConfirmPwd;
    private Button mButton;
    private CheckBox mCheckBox1, mCheckBox2, mCheckBox3;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, ChangePwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mToolbar = $(R.id.tb_change_pwd);
        mEditTextPreviousPwd = $(R.id.et_previous_pwd);
        mEditTextNewPwd = $(R.id.et_new_pwd);
        mEditTextConfirmPwd = $(R.id.et_confirm_new_pwd);
        mButton = $(R.id.btn_change_pwd);
        mCheckBox1 = $(R.id.cb_change_pwd1);
        mCheckBox2 = $(R.id.cb_change_pwd2);
        mCheckBox3 = $(R.id.cb_change_pwd3);

        mButton.setOnClickListener(this);
    }

    @Override
    protected void doBusiness(Context context) {
        //配置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //初始化EditText控件
        CheckBoxUtil.initCheckBox(mEditTextPreviousPwd,mCheckBox1);
        CheckBoxUtil.initCheckBox(mEditTextNewPwd,mCheckBox2);
        CheckBoxUtil.initCheckBox(mEditTextConfirmPwd,mCheckBox3);
    }



    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_pwd:
                showToast("修改成功");
                finish();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
