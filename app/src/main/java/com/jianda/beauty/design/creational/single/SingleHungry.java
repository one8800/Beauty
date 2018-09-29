package com.jianda.beauty.design.creational.single;

/**
 * 单列模式--饿汉模式
 */
public class SingleHungry {
    //此处定义类变量实例并直接实例化，在类加载的时候就完成了实例化并保存在类中
    private static SingleHungry instance = new SingleHungry();
    //定义无参构造器，用于单例实例
    private SingleHungry() {

    }
    //定义公开方法，返回已创建的单例
    public static SingleHungry getInstance() {
        return instance;
    }
}
