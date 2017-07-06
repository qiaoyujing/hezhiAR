package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.CheckBoxUtil;
import com.kezhi.android.kezhiar.utils.SharedPreferencesHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:20:36
 * Email: song511653502@gmail.com
 * Description: 登录注册
 */

public class LoginActivity extends BaseActivity {

    private Toolbar mToolbar;

    private UMShareAPI mShareAPI = null;
    private CheckBox mCheckBox;

    private EditText mEditTextPmail, mEditTextPwd;
    private TextView findPwdTextView, registerTextView;
    private Button loginButton;
    private ImageButton sinaButton, qqButton, wechatButton;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mToolbar = $(R.id.tb_login);
        mEditTextPmail = $(R.id.et_login_pmail);
        mEditTextPwd = $(R.id.et_login_pwd);
        findPwdTextView = $(R.id.tv_find_pwd);
        registerTextView = $(R.id.tv_register);
        loginButton = $(R.id.btn_login);
        sinaButton = $(R.id.btn_sina_login);
        qqButton = $(R.id.btn_qq_login);
        wechatButton = $(R.id.btn_wechat_login);
        mCheckBox = $(R.id.cb_display_pwd);

        findPwdTextView.setOnClickListener(this);
        registerTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        sinaButton.setOnClickListener(this);
        qqButton.setOnClickListener(this);
        wechatButton.setOnClickListener(this);

    }

    @Override
    protected void doBusiness(Context context) {
        //配置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //初始化SharedPreference
        SharedPreferencesHelper.init(getApplicationContext());

        //初始化EditText,记住密码
        CheckBoxUtil.initCheckBox(mEditTextPwd,mCheckBox);
        String savedPhone = (String) SharedPreferencesHelper.getInstance().getData("phone", "");
        String savedPassword = (String) SharedPreferencesHelper.getInstance().getData("password", "");
        mEditTextPmail.setText(savedPhone);
        mEditTextPwd.setText(savedPassword);

        //获取友盟ShareAPI
        mShareAPI = UMShareAPI.get(this);


    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                String phone = mEditTextPmail.getText().toString().trim();
                String password = mEditTextPwd.getText().toString().trim();
                String savedPhone = (String) SharedPreferencesHelper.getInstance().getData("phone", "");
                String savedPassword = (String) SharedPreferencesHelper.getInstance().getData("password", "");

                if (password.equals(savedPassword) && (phone.equals(savedPhone))) {
                    MainActivity.activityStart(this);
                    finish();
                } else {
                    showToast("用户名或密码不正确");
                    Log.i("test", "phone:"+phone);
                    Log.i("test", "savephone:"+savedPhone);
                    Log.i("test", "password:"+password);
                    Log.i("test", "savedpassword:"+savedPassword);
                }
                break;
            case R.id.tv_find_pwd:

                break;
            case R.id.tv_register:
                RegisterActivity.activityStart(this);
                break;
            case R.id.btn_qq_login:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.btn_sina_login:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.btn_wechat_login:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            showToast("请稍等...");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "授权成功，正在跳转页面，请稍等...", Toast.LENGTH_SHORT).show();
            String name = data.get("name");
            String iconurl = data.get("iconurl");
            SharedPreferencesHelper.getInstance().saveData("name",name);
            SharedPreferencesHelper.getInstance().saveData("iconurl",iconurl);
            MainActivity.activityStart(LoginActivity.this);
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

       @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
