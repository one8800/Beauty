package com.jianda.util;

import android.util.Log;

/**
 * Created by lh on 2018/1/10.
 */

public class HtLog {

    private HtLog() {
        throw new UnsupportedOperationException("LogUtils cannot be instantiated");
    }

    public static boolean isDebug = true;

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

}
