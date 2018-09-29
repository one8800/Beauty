package com.jianda.beauty;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import com.jianda.beauty.home.MainActivity;
import com.jianda.network.HttpCacheInterceptor;
import com.jianda.network.HttpManager;
import com.jianda.network.HttpParamsInterceptor;
import com.jianda.util.CrashHandler;
import com.jianda.util.SPUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zengkang on 2018/2/2.
 */

public class BTApplication extends Application {

    private static BTApplication appInstance;
    private HttpCacheInterceptor mHttpCacheInterceptor;
    private HttpParamsInterceptor mHttpParamsInterceptor;
    private List<BaseActivity> mAllActivities = new LinkedList<>();
    private static int mainTid;

    public static BTApplication getApplicationCtx() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        SPUtils.getInstance().init(this);

        HttpManager.getInstance().initialize(getHttpParamsInterceptor(), getHttpCacheInterceptor(), this.getCacheDir());

        CrashHandler.getInstance().init(this, true, MainActivity.class);

        mainTid = Process.myTid();
    }

    /**
     * 获取主线程
     *
     * @return
     */
    public int getMainTid() {
        return mainTid;
    }

    /**
     * 退出应用
     */
    public void appKill() {
        finishActivityList();
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        this.mAllActivities.add(activity);
    }

    /**
     * 结束一个Activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        this.mAllActivities.remove(activity);
    }

    /**
     * 结束所有的Activity
     */
    public void finishActivityList() {
        for (Activity activity : mAllActivities) {
            if (!(activity instanceof MainActivity)) {
                activity.finish();
            }
        }
    }

    public HttpParamsInterceptor getHttpParamsInterceptor() {
        if (mHttpParamsInterceptor == null) {
            mHttpParamsInterceptor = new HttpParamsInterceptor.Builder().build();
        }
        return mHttpParamsInterceptor;
    }

    public HttpCacheInterceptor getHttpCacheInterceptor() {
        if (mHttpCacheInterceptor == null) {
            mHttpCacheInterceptor = new HttpCacheInterceptor(this);
        }
        return mHttpCacheInterceptor;
    }
}
