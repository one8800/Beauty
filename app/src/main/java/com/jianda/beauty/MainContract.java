package com.jianda.beauty;

import android.content.Context;

/**
 * Created by zhangqun on 2018/3/5.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showNetStatus(boolean isAvailable);
    }

    interface Presenter extends BasePresenter {
        void registerBroadcast(Context context);

        void unregisterBroadcast(Context context);
    }
}
