package com.jianda.beauty.design.behavior.observer;

import android.util.Log;

/**
 * 观察者具体实现类
 */
public class User implements Observer {
    private String TAG = "User";
    private String name;
    private String msg;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String msg) {
        this.msg = msg;
        read();
    }

    public void read() {
        Log.i(TAG, name + "收到新的消息:" + msg);
    }
}
