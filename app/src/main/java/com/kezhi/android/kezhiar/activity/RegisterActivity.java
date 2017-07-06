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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.CheckBoxUtil;
import com.kezhi.android.kezhiar.utils.RegexUtils;
import com.kezhi.android.kezhiar.utils.SharedPreferencesHelper;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by songtao on 2017/5/3.
 */

public class RegisterActivity extends BaseActivity {
    private Toolbar mToolbar;
    private CheckBox mCheckBox1,mCheckBox2;
    private EditText mEditTextPmail, mEditTextCode, mEditTextPwd, mEditTextConfirm;
    private Button mButtonRegister, mButtonGetCode;
    private TextView mTextView;

    private static final String APPKEY = "1d4caa72af77c";
    private static final String APPSECRET = "b1a9a62b3e455d785825c5da995a53fd";
    private String countryCode = "86";
    private int i = 60;//倒计时


    public static void activityStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mToolbar = $(R.id.tb_register);
        mEditTextPmail = $(R.id.et_register_pmail);
        mEditTextCode = $(R.id.et_security_code);
        mEditTextPwd = $(R.id.et_register_pwd);
        mEditTextConfirm = $(R.id.et_confirm_pwd);
        mTextView = $(R.id.tv_to_login);
        mButtonGetCode = $(R.id.btn_get_code);
        mButtonRegister = $(R.id.btn_register);
        mCheckBox1 = $(R.id.cb_register_pwd);
        mCheckBox2 = $(R.id.cb_register_confirm);


        mTextView.setOnClickListener(this);
        mButtonGetCode.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
    }

    @Override
    protected void doBusiness(Context context) {
        //配置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initSMS();

        //设置密码输入框
        CheckBoxUtil.initCheckBox(mEditTextPwd,mCheckBox1);
        CheckBoxUtil.initCheckBox(mEditTextConfirm,mCheckBox1);


        //注册按钮不可点击
        mButtonRegister.setEnabled(false);

        mEditTextPmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus) {
                    if (mEditTextPmail.getText().toString().trim().length()< 7) {
                        showToast("请输入正确的手机号或邮箱地址");
                    }
                }
            }
        });

        //当验证码输入框失去焦点，判断验证码的正确性
        //如果正确，锁定手机号，并且让注册按钮可点击
        mEditTextCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String code = mEditTextCode.getText().toString().trim();
                    if (!RegexUtils.isEmpty(mEditTextPmail.getText().toString().trim())) {
                        return;
                    }
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(getApplicationContext(), "验证码不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SMSSDK.submitVerificationCode(countryCode, mEditTextPmail.getText().toString().trim(), code);
                }
            }
        });

        //密码输入框监听事件
        mEditTextPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus) {
                    if (mEditTextPwd.getText().toString().trim().length()< 6) {
                        showToast("密码至少由6位数字或字母组成");
                    }
                }
            }
        });
        //初始化SharedPreferenceHelper
        SharedPreferencesHelper.init(getApplicationContext());


    }

    @Override
    public void viewClick(View v) {
        String pmail = mEditTextPmail.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_get_code:
                if (RegexUtils.isEmail(pmail)) {
                    showToast("暂时不能使用邮箱");
                    Log.i("test", "doClick:邮箱 ");
                } else {
                    if (!RegexUtils.isEmpty(pmail)) return;
                    SMSSDK.getVerificationCode(countryCode, pmail);
                    mButtonGetCode.setEnabled(false);
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
                }

                break;

            case R.id.btn_register:
                doRegister();
                break;

            case R.id.tv_to_login:
                LoginActivity.activityStart(this);
                finish();
                break;
        }

    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                mButtonGetCode.setText(i + " s");
            } else if (msg.what == -2) {
                mButtonGetCode.setText("重新发送");
                mButtonGetCode.setEnabled(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("asd", "event=" + event + "  result=" + result + "  ---> result=-1 success , result=0 error");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回RegisterActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功,调用注册接口，之后直接登录
                        //当号码来自短信注册页面时调用登录注册接口
                        //当号码来自绑定页面时调用绑定手机号码接口
                        mEditTextCode.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "短信验证成功",
                                Toast.LENGTH_SHORT).show();
                        mButtonRegister.setEnabled(true);

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
                            Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    };

    public void initSMS() {
        SMSSDK.initSDK(RegisterActivity.this, APPKEY, APPSECRET);
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


    public void doRegister() {
        String password = mEditTextPwd.getText().toString().trim();
        String phone = mEditTextPmail.getText().toString().trim();
        String confirm = mEditTextConfirm.getText().toString().trim();

        if (RegexUtils.isUserpassword(password) && (password.equals(confirm))) {
            showToast("注册成功，正在跳转页面...");
            savePersonInfo(phone,password);
            LoginActivity.activityStart(this);
            finish();
        } else {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
        }
    }

        //用sharePreference把用户信息保存在本地，文件名是 user+手机号
    public void savePersonInfo(String phone,String password){
        SharedPreferencesHelper.getInstance().saveData("phone",phone);
        SharedPreferencesHelper.getInstance().saveData("name","王小二");//默认名字
        SharedPreferencesHelper.getInstance().saveData("password",password);
    }


    //配置toobar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
