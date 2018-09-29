package com.jianda.beauty.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jianda.beauty.R;
import com.jianda.util.DeviceUtils;


/**
 * Created by zhangqun on 2017/11/24.
 * 自定义dialog
 */

public class DialogPrompt extends Dialog {

    private Context mContext;
    private LeftListener leftListener;
    private RightListener rightListener;
    private TextView tv_current_location;

    public DialogPrompt(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_prompt, null);
        tv_current_location = contentView.findViewById(R.id.tv_current_location);
        TextView tv_left = contentView.findViewById(R.id.tv_left);
        TextView tv_right = contentView.findViewById(R.id.tv_right);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftListener.leftClick(view);
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightListener.rightClick(view);
            }
        });
        setContentView(contentView);
        setSize(mContext.getResources().getDimensionPixelSize(R.dimen.home_dialog_sign_in_width),
                mContext.getResources().getDimensionPixelSize(R.dimen.home_dialog_sign_in_height),
                0, 0
        );
    }

    private void setSize(int width, int height, int x, int y) {
        android.view.Window window = this.getWindow();
        DeviceUtils.Window.setHideNaivBar(window);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        attributes.gravity = Gravity.CENTER_HORIZONTAL;
        attributes.dimAmount = 0.5f;
        attributes.x = x;
        attributes.y = y;
        attributes.width = width;
        attributes.height = height;
        window.setAttributes(attributes);
    }

    public void setTitle(String title) {
        tv_current_location.setText(title);
    }

    public void setRightListener(RightListener rightListener) {
        this.rightListener = rightListener;
    }

    public void setLeftListener(LeftListener leftListener) {
        this.leftListener = leftListener;
    }

    public interface LeftListener {
        void leftClick(View view);
    }

    public interface RightListener {
        void rightClick(View view);
    }

}