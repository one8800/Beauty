package com.jianda.beauty.design.creational.factory.method;

import com.jianda.beauty.design.creational.factory.AsusComputer;
import com.jianda.beauty.design.creational.factory.Computer;

/**
 * 华硕工厂
 */
public class AsusFactory extends BaseFactory {
    @Override
    public Computer createComputer() {
        return new AsusComputer();
    }
}
