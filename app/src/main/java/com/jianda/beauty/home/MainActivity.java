package com.jianda.beauty.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jianda.beauty.BaseActivity;
import com.jianda.beauty.R;
import com.jianda.beauty.design.behavior.observer.Observer;
import com.jianda.beauty.design.behavior.observer.User;
import com.jianda.beauty.design.behavior.observer.WechatSever;
import com.jianda.util.SPUtils;
import com.jianda.util.ToastUtils;

/**
 * Created by zhangqun on 2018/3/1.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //测试创建型设计模式
        testDesignCreational();
        //测试结构型设计模式
        testDesignStructural();
        //测试行为型设计模式
        testDesignBehavior();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showNetStatus(boolean isAvailable) {
        if (!isAvailable) {
            ToastUtils.toastBarCenter(this, "网络已断开");
        }
    }

    private void testDesignBehavior() {
        testObserver();
    }

    private void testObserver() {
        //创建被观察这对象
        WechatSever sever = new WechatSever();
        //创建观察者对象
        Observer zhangqun = new User("张群");
        Observer liuyong = new User("刘勇");
        Observer longhai = new User("龙海");
        //注册观察者
        sever.registerObserver(zhangqun);
        sever.registerObserver(liuyong);
        sever.registerObserver(longhai);
        //发送消息
        sever.sendMessage("微信版本要更新了");
    }

    private void testDesignCreational() {

    }

    private void testDesignStructural() {

    }
}
