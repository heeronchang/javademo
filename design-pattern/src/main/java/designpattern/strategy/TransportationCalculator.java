package designpattern.strategy;

import designpattern.strategy.impl.GeneralCar;

/**
 * 交通工具计费操作Context策略的类
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */
public class TransportationCalculator {
    /**
     * 计费策略类，默认策略为普通车
     */
    private ICalculateStrategy strategy = new GeneralCar();

    /**
     * 设置计费策略
     * @param strategy 计费策略
     */
    public void setStrategy(ICalculateStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 计算费用
     * @param distance 距离
     * @return 费用
     */
    public double calculate(double distance) {
        return strategy.calculate(distance);
    }
}
