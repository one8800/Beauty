package com.jianda.beauty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianda.util.DeviceUtils;

/**
 * Created by zhangqun on 2018/3/2.
 */

public class BaseActivity extends AppCompatActivity implements MainContract.View {

    private TextView tv_title;
    private LinearLayout ll_base_bg;
    private ImageView iv_back;
    private ImageView iv_menu;
    private TextView tv_menu;
    private LinearLayout ll_status_bar;
    private RelativeLayout rl_title_bar;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        BTApplication.getApplicationCtx().addActivity(this);

        init();
    }

    @Override
    public void setContentView(int view) {
        View.inflate(this, view, ll_base_bg);
        onContentChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.unregisterBroadcast(this);
    }

    private void init() {
        DeviceUtils.Window.setScreenFullImmerse(getWindow());
        getSupportActionBar().hide();
        mainPresenter = new MainPresenter(this);
        mainPresenter.registerBroadcast(this);

        initView();

        int barHeight = DeviceUtils.Window.getStatusBarHeight(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//定义一个LayoutParams
        lp.setMargins(0, barHeight, 0, 0);
        ll_status_bar.setLayoutParams(lp);
    }

    private void initView() {
        ll_base_bg = findViewById(R.id.ll_base_bg);
        ll_status_bar = findViewById(R.id.ll_status_bar);
        rl_title_bar = findViewById(R.id.rl_title_bar);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_menu = findViewById(R.id.tv_menu);
        iv_menu = findViewById(R.id.iv_menu);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackClick(view);
            }
        });
        tv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuTextClick(view);
            }
        });
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuIconClick(view);
            }
        });
    }

    protected void onBackClick(View view) {

    }

    protected void onMenuTextClick(View view) {

    }

    protected void onMenuIconClick(View view) {

    }

    protected void setWindowBg(int icon) {
        if (ll_base_bg != null) {
            ll_base_bg.setBackgroundResource(icon);
        }
    }

    protected void setTitleBarVisibility(int visibility) {
        if (rl_title_bar != null) {
            rl_title_bar.setVisibility(visibility);
        }
    }

    protected void setTitleBarBg(int icon) {
        if (rl_title_bar != null) {
            rl_title_bar.setBackgroundResource(icon);
        }
    }

    protected void setBackIcon(int icon) {
        if (iv_back != null) {
            iv_back.setBackgroundResource(icon);
        }
    }

    protected void setBackIconVisibility(int visibility) {
        if (iv_back != null) {
            iv_back.setVisibility(visibility);
        }
    }

    protected void setTitleTextVisibility(int visibility) {
        if (tv_title != null) {
            tv_title.setText(visibility);
        }
    }

    protected void setTitleText(String text, int color) {
        if (tv_title != null) {
            tv_title.setText(text);
            tv_title.setTextColor(getResources().getColor(color));
        }
    }

    protected void setMenuTextVisibility(int visibility) {
        if (tv_menu != null) {
            tv_menu.setVisibility(visibility);
        }
    }

    protected void setMenuText(String text, int color) {
        if (tv_menu != null) {
            tv_menu.setText(text);
            tv_menu.setTextColor(getResources().getColor(color));
        }
    }

    protected void setMenuIconVisibility(int visibility) {
        if (iv_menu != null) {
            iv_menu.setVisibility(visibility);
        }
    }

    protected void setMenuIcon(int icon) {
        if (iv_menu != null) {
            iv_menu.setBackgroundResource(icon);
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void showNetStatus(boolean isAvailable) {

    }
}
