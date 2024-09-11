package interfacetest;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/16
 */
public class ClassOne implements InterfaceOne {
    @Override
    public void methodOne() {
        System.out.println("ClassOne.methodOne: 实现接口，必须得实现它所有的方法定义");
    }

    @Override
    public void methodTwo() {
//        this.a = 2; // Cannot assign a value to final variable 'a'
        System.out.println("ClassOne.methodTwo" + this.a);
    }
}
