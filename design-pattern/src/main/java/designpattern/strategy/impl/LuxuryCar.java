package designpattern.strategy.impl;

import designpattern.strategy.ICalculateStrategy;

public class LuxuryCar implements ICalculateStrategy {
    @Override
    public double calculate(double distance) {
        if (distance > 0 && distance <= 5)
            return 8;
        if (distance > 5 && distance <= 7)
            return 13;
        if (distance > 7 && distance <= 10)
            return 15;
        return 13;
    }
}
