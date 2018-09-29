package com.jianda.beauty.design.behavior.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者具体实现类
 */
public class WechatSever implements Observable {

    private final List<Observer> list;
    private String msg;

    public WechatSever() {
        list = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer observer) {
        list.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (list != null) {
            list.remove(observer);
        }
    }

    @Override
    public void notifyObserver() {
        for (int i = 0; i < list.size(); i++) {
            Observer observer = list.get(i);
            observer.update(msg);
        }
    }

    public void sendMessage(String msg) {
        this.msg = msg;
        notifyObserver();
    }
}
