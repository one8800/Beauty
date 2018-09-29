package com.jianda.beauty.design.creational.factory.abstraction;

import com.jianda.beauty.design.creational.factory.AsusComputer;
import com.jianda.beauty.design.creational.factory.Computer;
import com.jianda.beauty.design.creational.factory.DellComputer;
import com.jianda.beauty.design.creational.factory.HPComputer;
import com.jianda.beauty.design.creational.factory.HWPhone;
import com.jianda.beauty.design.creational.factory.Phone;
import com.jianda.beauty.design.creational.factory.SXPhone;
import com.jianda.beauty.design.creational.factory.XMPhone;

/**
 * B工厂
 */
public class BFactory extends AbstractFactory {
    @Override
    public Computer createHPComputer() {
        return new HPComputer();
    }

    @Override
    public Computer createAsusComputer() {
        return new AsusComputer();
    }

    @Override
    public Computer createDellComputer() {
        return new DellComputer();
    }

    @Override
    public Phone createXMPhone() {
        return new XMPhone();
    }

    @Override
    public Phone createHWPhone() {
        return new HWPhone();
    }

    @Override
    public Phone createSXPhone() {
        return new SXPhone();
    }
}
