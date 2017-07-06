package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.SharedPreferencesHelper;

/**
 * Created by songtao on 2017/5/6.
 */

public class ChangeMailActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView mTextViewPreviousMail, mTextViewSendCode;
    private EditText mEditTextNewMail;
    private EditText mEditTextCode;
    private Button mButton;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, ChangeMailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_change_mail;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mToolbar = $(R.id.tb_change_mail);
        mTextViewPreviousMail = $(R.id.tv_previous_mail);
        mTextViewSendCode = $(R.id.tv_change_mail_send_code);
        mEditTextNewMail = $(R.id.et_new_mail);
        mEditTextCode = $(R.id.et_security_code);
        mButton = $(R.id.btn_change_mail);

        mTextViewSendCode.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void doBusiness(Context context) throws InterruptedException {
        //配置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //初始化
        SharedPreferencesHelper.init(this);
        mTextViewPreviousMail.setText((String) SharedPreferencesHelper.getInstance().getData("mail", "你还没有绑定邮箱"));
    }

    @Override
    public void viewClick(View v) {
        String newMail = mEditTextNewMail.getText().toString().trim();
        switch (v.getId()) {
            case R.id.tv_change_mail_send_code:
                showToast("sorry~ 暂时还不能绑定邮箱");
                break;
            case R.id.btn_change_mail:
                SharedPreferencesHelper.getInstance().saveData("mail", newMail);
                showToast("修改成功");
                finish();
                break;
        }

    }


    //配置toobar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
