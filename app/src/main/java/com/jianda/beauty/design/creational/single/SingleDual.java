package com.jianda.beauty.design.creational.single;

/**
 * 单列模式--双重校验
 */
public class SingleDual {

    private static SingleDual instance;

    private SingleDual() {

    }

    public static SingleDual getInstance() {
        if (instance == null) {
            synchronized (SingleDual.class) {
                if (instance == null) {
                    instance = new SingleDual();
                }
            }
        }
        return instance;
    }
}
