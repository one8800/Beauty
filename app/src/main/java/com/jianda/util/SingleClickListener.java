package com.jianda.util;

import android.view.View;

import java.util.Calendar;

/**
 * 防止短时间多次点击按钮
 */

public abstract class SingleClickListener implements View.OnClickListener {

    private long lastClickTime;
    private long MIN_CLICK_DELAY_TIME = 1000;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onSingleClick(view);
        }
    }

    protected abstract void onSingleClick(View v);
}
