package com.jianda.beauty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by zhangqun on 2018/3/5.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;
    private NetworkReceiver networkReceiver;

    public MainPresenter(MainContract.View iMainView) {
        mMainView = checkNotNull(iMainView);
        mMainView.setPresenter(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    /**
     * 注册广播
     * @param context
     */
    @Override
    public void registerBroadcast(Context context) {
        networkReceiver = new NetworkReceiver();
        IntentFilter filterNetWork = new IntentFilter();
        filterNetWork.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filterNetWork);
    }

    /**
     * 取消注册广播
     * @param context
     */
    @Override
    public void unregisterBroadcast(Context context) {
        if (networkReceiver != null) {
            context.unregisterReceiver(networkReceiver);
            networkReceiver = null;
        }
    }

    /**
     * 网络状态广播接收者
     */
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isAvailable = false;
            String action = intent.getAction();
            if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm == null) {
                    return;
                }
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    isAvailable = true;
                }
                mMainView.showNetStatus(isAvailable);
            }
        }
    }
}
