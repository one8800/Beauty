package com.jianda.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zengkang on 2018/2/2.
 */

public class NetworkUtils {

    /**
     * 是否已有网络连接
     */
    public static boolean isConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo ethNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        boolean isConnected = (mobNetInfo != null && mobNetInfo.isConnected())
                || (wifiNetInfo != null && wifiNetInfo.isConnected())
                || (ethNetInfo != null && ethNetInfo.isConnected());
        if (isConnected) {
            return true;
        }
        return false;
    }

    /**
     * 是否wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }
}
