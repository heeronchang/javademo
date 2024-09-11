package interfacetest;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/16
 */
public interface InterfaceOne {
    int a = 0; // 这个属性默认是final类型的
    void methodOne();
    void methodTwo();

    default void methodThree() {
        System.out.println("InterfaceOne.methodThree");
    }
}
