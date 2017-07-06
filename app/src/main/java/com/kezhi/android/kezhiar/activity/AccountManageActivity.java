package com.kezhi.android.kezhiar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.base.BaseActivity;
import com.kezhi.android.kezhiar.utils.SharedPreferencesHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: FishpondKing
 * Date: 2017/5/1:20:36
 * Email: song511653502@gmail.com
 * Description: 账户管理
 */

public class AccountManageActivity extends BaseActivity {

    private Toolbar mToolbar;
    private CircleImageView mCircleImageView;
    private TextView mTextViewUserName;
    private LinearLayout mLinearLayoutHead;
    private LinearLayout mLinearLayoutNickname;
    private LinearLayout mLinearLayoutPwd;
    private LinearLayout mLinearLayoutPhone;
    private LinearLayout mLinearLayoutMail;
    private Button mButton;
   // private UMShareAPI mShareAPI = null;


    public static void activityStart(Context context) {
        Intent intent = new Intent(context, AccountManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables(Bundle params) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_account_manager;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mToolbar = $(R.id.tb_account_manager);
        mLinearLayoutNickname = $(R.id.ll_change_nickname);
        mLinearLayoutHead = $(R.id.ll_change_head);
        mLinearLayoutPwd = $(R.id.ll_change_pwd);
        mLinearLayoutPhone = $(R.id.ll_change_phone);
        mLinearLayoutMail = $(R.id.ll_change_mail);
        mButton = $(R.id.btn_exit);
        mCircleImageView = $(R.id.civ_account_manager_user_head);
        mTextViewUserName = $(R.id.tv_change_nickname);

        mLinearLayoutNickname.setOnClickListener(this);
        mLinearLayoutHead.setOnClickListener(this);
        mLinearLayoutPwd.setOnClickListener(this);
        mLinearLayoutPhone.setOnClickListener(this);
        mLinearLayoutMail.setOnClickListener(this);
        mButton.setOnClickListener(this);

    }

    @Override
    protected void doBusiness(Context context) {
        //配置ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferencesHelper.init(this);

        String username=(String) SharedPreferencesHelper.getInstance().getData("name","王小二");
        String iconurl=(String) SharedPreferencesHelper.getInstance().getData("iconurl","");
        Glide
                .with(this)
                .load(iconurl)
                .placeholder(R.drawable.ic_head)
                .into(mCircleImageView);

        mTextViewUserName.setText(username);


    }

    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_change_head:

                break;
            case R.id.ll_change_nickname:
                ChangeNicknameActivity.activityStart(this);
                break;
            case R.id.ll_change_pwd:
                ChangePwdActivity.activityStart(this);
                break;
            case R.id.ll_change_phone:
                ChangePhoneActivity.activityStart(this);
                break;
            case R.id.ll_change_mail:
                ChangeMailActivity.activityStart(this);
                break;
            case R.id.btn_exit:
                //取消第三方授权
                UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.QQ, authListener);
                SharedPreferencesHelper.getInstance().clearData();
                showToast("已退出");
                finish();
                break;
        }

    }
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

}
