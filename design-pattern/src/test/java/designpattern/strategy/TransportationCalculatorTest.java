package designpattern.strategy;

import designpattern.strategy.impl.LuxuryCar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportationCalculatorTest {

    TransportationCalculator calculator;
    @BeforeEach
    void setUp() {
        calculator = new TransportationCalculator();
    }

    @AfterEach
    void tearDown() {
        calculator = null;
    }

    @Test
    void setStrategy() {
        calculator.setStrategy(new LuxuryCar());
    }

    @Test
    void calculate() {
        setStrategy();
        System.out.println(calculator.calculate(1));
    }
}
