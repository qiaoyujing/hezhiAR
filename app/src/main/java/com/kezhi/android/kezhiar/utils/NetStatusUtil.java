package com.kezhi.android.kezhiar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author: FishpondKing
 * Date: 2017/4/30:16:43
 * Email: song511653502@gmail.com
 * Description: 网络相关工具类
 */

public final class NetStatusUtil {

    private NetStatusUtil() {
    }

    /**
     * Method:isNetConnection(Context context)
     * Description: 检查是否连接网络
     *
     * @Param context 上下文
     * @Return 是否连接网络
     * Author: FishpondKing
     * Date: 2017/4/30:16:46
     */

    public static boolean isNetConnection(Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }

    /**
     * Method: isWifiConnection(Context context)
     * Description: 检查是否连接WiFi
     *
     * @Param context 上下文
     * @Return 是否连接WiFi
     * Author: FishpondKing
     * Date: 2017/4/30:16:47
     */

    public static boolean isWifiConnection(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
