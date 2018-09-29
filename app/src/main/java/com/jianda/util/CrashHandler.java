package com.jianda.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.jianda.beauty.BTApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
@SuppressLint({"StaticFieldLeak", "SimpleDateFormat"})
public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "LH/CrashHandler";
    private static CrashHandler instance;
    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
    // Toast 显示文案
    private String mTips;
    private Context mContext;
    private boolean mIsRestartApp;
    private Class mRestartActivity;

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private CrashHandler() {

    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     *
     * @return instance
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     * @param isRestartApp
     * @param restartActivity
     */
    public void init(Context context, boolean isRestartApp, Class restartActivity) {
        mContext = context;
        mIsRestartApp = isRestartApp;
        mRestartActivity = restartActivity;
        mTips = "很抱歉，程序出现异常，即将退出...";
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            if (mIsRestartApp) { // 如果需要重启
                restartApp();
            }
            // 结束应用
            ((BTApplication) mContext.getApplicationContext()).finishActivityList();
        }
    }

    @SuppressLint("WrongConstant")
    private void restartApp() {
        Intent intent = new Intent(mContext.getApplicationContext(), mRestartActivity);
        AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        //重启应用，得使用PendingIntent
        PendingIntent restartIntent = PendingIntent.getActivity(
                mContext.getApplicationContext(), 0, intent,
                Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上，包含6.0
            if (mAlarmManager != null) {
                mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                        restartIntent); //解决Android6.0省电机制带来的不准时问题
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Android4.4到Android6.0之间，包含4.4
            if (mAlarmManager != null) {
                mAlarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
            }
        } else {// 解决set()在api19上不准时问题
            if (mAlarmManager != null) {
                mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
            }
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext.getApplicationContext(), getTips(ex), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext.getApplicationContext());
        //保存Crash日志文件到指定目录
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    @SuppressLint("SdCardPath")
    private void saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String result = key + "=" + value + "\n";
            sb.append(result);
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb = sb.append(result);
        Log.e(TAG, sb.toString());
        try {
            String time = formatter.format(new Date());
            long timestamp = System.currentTimeMillis();
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/" + mContext.getPackageName() + "/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    private void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 获取异常信息文本提示语
     *
     * @param ex
     * @return
     */
    private String getTips(Throwable ex) {
        if (ex instanceof SecurityException) {
            if (ex.getMessage().contains("android.permission.CAMERA")) {
                mTips = "请授予应用相机权限，程序出现异常，即将退出.";
            } else if (ex.getMessage().contains("android.permission.RECORD_AUDIO")) {
                mTips = "请授予应用麦克风权限，程序出现异常，即将退出。";
            } else if (ex.getMessage().contains("android.permission.WRITE_EXTERNAL_STORAGE")) {
                mTips = "请授予应用存储权限，程序出现异常，即将退出。";
            } else if (ex.getMessage().contains("android.permission.READ_PHONE_STATE")) {
                mTips = "请授予应用电话权限，程序出现异常，即将退出。";
            } else if (ex.getMessage().contains("android.permission.ACCESS_COARSE_LOCATION") ||
                    ex.getMessage().contains("android.permission.ACCESS_FINE_LOCATION")) {
                mTips = "请授予应用位置信息权，很抱歉，程序出现异常，即将退出。";
            } else {
                mTips = "很抱歉，程序出现异常，即将退出，请检查应用权限设置。";
            }
        }
        return mTips;
    }
}
