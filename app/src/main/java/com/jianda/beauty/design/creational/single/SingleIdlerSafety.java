package com.jianda.beauty.design.creational.single;

/**
 * 单列模式--懒汉模式--线程安全
 *
 * 优点:线程安全
 *
 * 缺点:并发其实是一种特殊情况，每次调用时都要同步一次,大多时候这个锁占用的额外资源都浪费了，
 * 这种打补丁方式写出来的结构效率很低。
 */

public class SingleIdlerSafety {

    private static SingleIdlerSafety instance;

    private SingleIdlerSafety() {

    }

    public static synchronized SingleIdlerSafety getInstance() {
        if (instance == null) {
            instance = new SingleIdlerSafety();
        }
        return instance;
    }
}
