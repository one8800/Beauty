package com.jianda.beauty.design.behavior.observer;

/**
 * 定义被观察者接口
 */
public interface Observable {

    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();
}
