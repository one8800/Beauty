package com.jianda.beauty.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.jianda.util.DeviceUtils;

/**
 * Created by zhangqun on 2017/10/25.
 * 自定义dialog
 */

public class MyDialog extends Dialog {

    private final View mView;
    private final int mX;
    private final int mY;
    private final int mWidth;
    private final int mHeight;

    public MyDialog(Context context, int themeResId, View view, int x, int y, int width, int height) {
        super(context, themeResId);
        mView = view;
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(mView);
        android.view.Window window = this.getWindow();
        DeviceUtils.Window.setHideNaivBar(window);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        attributes.gravity = Gravity.CENTER_HORIZONTAL;
        attributes.dimAmount = 0.5f;
        attributes.x = mX;
        attributes.y = mY;
        attributes.width = mWidth;
        attributes.height = mHeight;
        window.setAttributes(attributes);
    }
}
