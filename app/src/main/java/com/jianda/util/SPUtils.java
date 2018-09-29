package com.jianda.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by LongHai on 17-4-11.
 */

public class SPUtils {

    private static SPUtils instance;
    private SharedPreferences mSharedPreferences = null;

    private SPUtils() {

    }

    public static SPUtils getInstance() {
        if (instance == null) {
            synchronized (SPUtils.class) {
                if (instance == null) {
                    instance = new SPUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
         mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putValue(String key, Object value) {
        if (getInstance().mSharedPreferences == null) {
            throw new IllegalArgumentException("Must call init in application");
        }
        SharedPreferences.Editor editor = getInstance().mSharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.commit();
    }

    public static void putValue(Map<String, Object> maps) {
        if (getInstance().mSharedPreferences == null || maps == null) {
            throw new IllegalArgumentException("Must call init in application");
        }
        if (maps.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editor = getInstance().mSharedPreferences.edit();
        Iterator iter = maps.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else {
                editor.putString(key, value.toString());
            }
        }
        editor.apply();
    }

    public static Object getValue(String key, Object defaultKey) {
        if (getInstance().mSharedPreferences == null) {
            throw new IllegalArgumentException("Must call init in application");
        }
        if (defaultKey instanceof String) {
            return getInstance().mSharedPreferences.getString(key, (String) defaultKey);
        } else if (defaultKey instanceof Integer) {
            return getInstance().mSharedPreferences.getInt(key, (Integer) defaultKey);
        } else if (defaultKey instanceof Boolean) {
            return getInstance().mSharedPreferences.getBoolean(key, (Boolean) defaultKey);
        } else if (defaultKey instanceof Float) {
            return getInstance().mSharedPreferences.getFloat(key, (Float) defaultKey);
        } else if (defaultKey instanceof Long) {
            return getInstance().mSharedPreferences.getLong(key, (Long) defaultKey);
        }
        return null;
    }

    public static boolean contains(String key) {
        if (getInstance().mSharedPreferences == null) {
            throw new IllegalArgumentException("Must call init in application");
        }
        return getInstance().mSharedPreferences.contains(key);
    }

    public static void remove(String key) {
        if (getInstance().mSharedPreferences == null) {
            throw new IllegalArgumentException("Must call init in application");
        }
        getInstance().mSharedPreferences.edit().remove(key).apply();
    }

    public static void clear() {
        if (getInstance().mSharedPreferences == null) {
            throw new IllegalArgumentException("Must call init in application");
        }
        getInstance().mSharedPreferences.edit().clear().apply();
    }

}
