package com.kezhi.android.kezhiar.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Author: FishpondKing
 * Date: 2017/4/30:16:55
 * Email: song511653502@gmail.com
 * Description: App相关工具
 */

public final class AppUtil {

    private AppUtil() {
    }

    /**
     * Method: getPackageName(Context context)
     * Description: 获取当前程序包名
     * @Param context 上下文
     * @Return 程序包名
     * Author: FishpondKing
     * Date: 2017/4/30:16:56
     */
    
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * Method: getVersionName(Context context)
     * Description: 获取当前版本信息
     * @Param context 上下文
     * @Return 版本信息
     * Author: FishpondKing
     * Date: 2017/4/30:16:56
     */
    
    public static String getVersionName(Context context) {
        String versionName = null;
        String pkName = context.getPackageName();
        try {
            versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * Method: getVersionCode(Context context)
     * Description: 获取当前版本号
     * @Param context 上下文
     * @Return 版本号
     * Author: FishpondKing
     * Date: 2017/4/30:16:56
     */
    
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        String pkName = context.getPackageName();
        try {
            versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }
}
