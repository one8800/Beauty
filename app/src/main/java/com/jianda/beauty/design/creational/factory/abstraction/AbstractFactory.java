package com.jianda.beauty.design.creational.factory.abstraction;

import com.jianda.beauty.design.creational.factory.Computer;
import com.jianda.beauty.design.creational.factory.Phone;

/**
 * 抽象工厂
 */
public abstract class AbstractFactory {

    public abstract Computer createHPComputer();

    public abstract Computer createAsusComputer();

    public abstract Computer createDellComputer();

    public abstract Phone createXMPhone();

    public abstract Phone createHWPhone();

    public abstract Phone createSXPhone();
}
