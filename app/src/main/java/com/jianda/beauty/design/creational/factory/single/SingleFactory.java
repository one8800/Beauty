package com.jianda.beauty.design.creational.factory.single;

import com.jianda.beauty.design.creational.factory.AsusComputer;
import com.jianda.beauty.design.creational.factory.DellComputer;
import com.jianda.beauty.design.creational.factory.HPComputer;
import com.jianda.beauty.design.creational.factory.Computer;

/**
 * 简单工厂
 */
public class SingleFactory {

    public static final String COMPTURE_HP = "hp";
    public static final String COMPTURE_ASUS = "asus";
    public static final String COMPTURE_DELL = "dell";

    public static Computer createCompture(String comptureType) {

        switch (comptureType) {
            case COMPTURE_ASUS:
                return new AsusComputer();

            case COMPTURE_HP:
                return new HPComputer();

            case COMPTURE_DELL:
                return new DellComputer();

            default:
                return new DellComputer();
        }
    }
}
