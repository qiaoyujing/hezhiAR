package com.kezhi.android.kezhiar.base;

import android.app.Application;

import com.kezhi.android.kezhiar.R;
import com.kezhi.android.kezhiar.utils.SPUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Author: FishpondKing
 * Date: 2017/4/29:19:40
 * Email: song511653502@gmail.com
 * Description: Application基类
 */

public class BaseApplication extends Application{

    public static String APP_NAME;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_NAME = getResources().getString(R.string.app_name);

        Config.DEBUG = true;
        UMShareAPI.get(this);
        //初始化Bugly
        //CrashReport.initCrashReport(getApplicationContext(), "89c787f177", false);

        //初始化SPUtil
        SPUtil.init(this);
    }
    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("1106081136", "1WV5Q6M7JPOqf2Eo");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }
}
