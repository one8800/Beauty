package com.jianda.beauty.design.creational.single;

/**
 * 单列模式--懒汉模式--线程不安全
 * 缺点:线程不安全 存在多个调用者同时访问 并同时构造了多个对象 浪费内存
 */
public class SingleIdler {
    //定义一个私有变量来存放单例，私有的目的是指外部无法直接获取这个变量，只能用提供的公共方法来获取
    private static SingleIdler instance;
    //定义私有构造器，表示只能在类内部使用，亦指单例的实例只能在单例类内部创建
    private SingleIdler() {

    }
    //定义一个公共的公开的方法来返回该类的实例，由于是懒汉式，
    // 需要在第一次使用时生成实例
    public static SingleIdler getInstance() {
        if (instance == null) {
            instance = new SingleIdler();
        }
        return instance;
    }
}
