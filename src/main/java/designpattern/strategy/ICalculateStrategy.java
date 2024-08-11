package designpattern.strategy;

/**
 * 需求：对交通工具计算车费，一般轿车，中等轿车，豪华轿车。
 */
public interface ICalculateStrategy {
    /**
     * 按距离计算车费
     * @param distance 距离
     * @return 车费
     */
    double calculate(double distance);
}
