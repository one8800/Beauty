package com.jianda.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jianda.beauty.R;
import com.jianda.beauty.BTApplication;

/**
 * Created by zk on 2017/6/30.
 */

public class ToastUtils {

    private static Toast toast = null ;

    public static void toastBarCenter(Context context, String content) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_bar, null); // 加载布局文件
        TextView textView = (TextView) view.findViewById(R.id.toast_text); // 得到textview
        textView.setText(content); // 设置文本
        if (toast == null){
            toast = new Toast(BTApplication.getApplicationCtx()); // 创建一个toast
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT); // 设置toast显示时间，整数值
        toast.setView(view); // 設置其显示的view,
        toast.show(); // 显示toast
    }

    public static void toastBarBottom(Context context, String content) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_bar, null); // 加载布局文件
        TextView textView = (TextView) view.findViewById(R.id.toast_text); // 得到textview
        textView.setText(content); // 设置文本
        if (toast == null){
            toast = new Toast(BTApplication.getApplicationCtx()); // 创建一个toast
        }
        toast.setGravity(Gravity.BOTTOM, 0, 70);
        toast.setDuration(Toast.LENGTH_SHORT); // 设置toast显示时间，整数值
        toast.setView(view); // 設置其显示的view,
        toast.show(); // 显示toast
    }

    public static void toastBarTop(Context context, String content) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_bar, null); // 加载布局文件
        TextView textView = (TextView) view.findViewById(R.id.toast_text); // 得到textview
        textView.setText(content); // 设置文本
        if (toast == null){
            toast = new Toast(BTApplication.getApplicationCtx()); // 创建一个toast
        }
        toast.setGravity(Gravity.TOP, 0, 150);
        toast.setDuration(Toast.LENGTH_SHORT); // 设置toast显示时间，整数值
        toast.setView(view); // 設置其显示的view,
        toast.show(); // 显示toast
    }



}
