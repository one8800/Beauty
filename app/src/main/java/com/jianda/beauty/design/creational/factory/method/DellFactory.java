package com.jianda.beauty.design.creational.factory.method;

import com.jianda.beauty.design.creational.factory.Computer;
import com.jianda.beauty.design.creational.factory.DellComputer;

/**
 * 戴尔工厂
 */
public class DellFactory extends BaseFactory {
    @Override
    public Computer createComputer() {
        return new DellComputer();
    }
}
