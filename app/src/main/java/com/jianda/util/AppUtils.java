package com.jianda.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用程序工具类
 */
public class AppUtils {

    private static final String TAG = "ZQ/AppUtils";

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否为系统应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isSystemApp(Context context, String packageName) {
        if (StringUtils.isEmptyText(packageName)) {
            return false;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
                return ai != null && (ai.flags & 1) != 0;
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 判断服务是否开启
     *
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        if (serviceName.equals("") || serviceName == null)
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService =
                (ArrayList<ActivityManager.RunningServiceInfo>) am.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            String s = runningService.get(i).service.getClassName().toString();
            if (s.equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取资源目录下的内容
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String getAssetsString(String fileName, Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            AssetManager am = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(am.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取当前Activity的名字
     *
     * @param context
     * @return
     */
    public static String getCurrentActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
        String className = runningTaskInfo.topActivity.getClassName();
        return className;
    }
}
