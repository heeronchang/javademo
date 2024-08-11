package designpattern.strategy.impl;

import designpattern.strategy.ICalculateStrategy;

public class GeneralCar implements ICalculateStrategy {
    @Override
    public double calculate(double distance) {
        if (distance > 0 && distance <= 5)
            return 5;
        if (distance > 5 && distance <= 7)
            return 7;
        return 10;
    }
}
