package com.jianda.beauty.design.creational.single;

/**
 * 单列模式--静态内部类
 *
 * 优点:不会在类加时而加载,而是在调用getInstance时才加载,达到了懒汉模式的效果,又是线程安全的
 */
public class SingleInterior {

    private SingleInterior() {

    }

    private static class SingleHolder {
        private static SingleInterior instance = new SingleInterior();
    }

    public static SingleInterior getInstance() {
        return SingleHolder.instance;
    }
}
