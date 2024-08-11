package designpattern.strategy.impl;

import designpattern.strategy.ICalculateStrategy;

public class MediumCar implements ICalculateStrategy {
    @Override
    public double calculate(double distance) {
        if (distance > 0 && distance <= 5)
            return 6;
        if (distance > 5 && distance <= 7)
            return 9;
        return 12;
    }
}
