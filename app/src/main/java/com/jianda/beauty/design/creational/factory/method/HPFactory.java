package com.jianda.beauty.design.creational.factory.method;

import com.jianda.beauty.design.creational.factory.Computer;
import com.jianda.beauty.design.creational.factory.HPComputer;

/**
 * 惠普工厂
 */
public class HPFactory extends BaseFactory {
    @Override
    public Computer createComputer() {
        return new HPComputer();
    }
}
