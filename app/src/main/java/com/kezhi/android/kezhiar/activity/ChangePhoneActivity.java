package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.RegexUtils;
import com.kezhi.android.kezhiar.utils.SharedPreferencesHelper;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by songtao on 2017/5/4.
 */

public class ChangePhoneActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView mTextViewPreviousPhone, mTextViewSendCode;
    private EditText mEditTextNewPhone;
    private EditText mEditTextCode;
    private Button mButton;

    private static final String APPKEY = "1d4caa72af77c";
    private static final String APPSECRET = "b1a9a62b3e455d785825c5da995a53fd";
    private String countryCode = "86";
    private int i = 60;//倒计时


    public static void activityStart(Context context) {
        Intent intent = new Intent(context, ChangePhoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mToolbar = $(R.id.tb_change_phone);
        mTextViewPreviousPhone = $(R.id.tv_previous_phone);
        mTextViewSendCode = $(R.id.tv_send_code);
        mEditTextNewPhone = $(R.id.et_new_phone);
        mEditTextCode = $(R.id.et_security_code);
        mButton = $(R.id.btn_change_phone);

        mTextViewSendCode.setOnClickListener(this);
        mButton.setOnClickListener(this);

    }

    @Override
    protected void doBusiness(Context context) {
        //配置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //初始化
        SharedPreferencesHelper.init(this);
        initSMS();

        //显示已绑定的手机号
        String previousPhone = (String) SharedPreferencesHelper.getInstance().getData("phone", "18888888888");
        mTextViewPreviousPhone.setText(previousPhone);
    }

    @Override
    public void viewClick(View v) {
        String phone = mEditTextNewPhone.getText().toString().trim();
        switch (v.getId()) {
            case R.id.tv_send_code:
                if (!RegexUtils.isEmpty(phone)) return;
                if (RegexUtils.isMobileSimple(phone)) {
                    SMSSDK.getVerificationCode(countryCode, phone);
                    mTextViewSendCode.setEnabled(false);
                    mTextViewSendCode.setTextColor(getResources().getColor(R.color.grey));
                    //开始倒计时
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-1);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(-2);
                        }
                    }).start();
                }else showToast("请输入正确的手机号");
                break;
            case R.id.btn_change_phone:
                String code = mEditTextCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplicationContext(), "验证码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode(countryCode, phone, code);
                break;
        }

    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                mTextViewSendCode.setTextColor(getResources().getColor(R.color.grey));
                mTextViewSendCode.setText(i + " s");
            } else if (msg.what == -2) {
                mTextViewSendCode.setTextColor(getResources().getColor(R.color.green));
                mTextViewSendCode.setText("重新发送");
                mTextViewSendCode.setEnabled(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("asd", "event=" + event + "  result=" + result + "  ---> result=-1 success , result=0 error");
                if (result == SMSSDK.RESULT_COMPLETE) {

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功,调用注册接口，之后直接登录
                        //当号码来自短信注册页面时调用登录注册接口
                        //当号码来自绑定页面时调用绑定手机号码接口
                        mEditTextCode.setEnabled(false);
                        showToast("修改成功");
                        SharedPreferencesHelper.getInstance().saveData("phone",mEditTextNewPhone.getText().toString().trim());
                        finish();

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Log.e("asd", "des: " + des);
                            showToast(des);
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    };

    private void initSMS() {
        SMSSDK.initSDK(ChangePhoneActivity.this, APPKEY, APPSECRET);
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
