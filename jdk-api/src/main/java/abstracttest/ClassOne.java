package abstracttest;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/16
 */
public class ClassOne extends AbstractOne {

    final Object obj = new Object();
    @Override
    public void methodOne() {
        System.out.println("ClassOne methodOne: 继承抽象类，必须实现它的抽象方法");
    }

    @Override
    public void methodTwo() {
        super.methodThree();
    }

    @Override
    public void methodThree() {
        System.out.println(3*0.1);
        System.out.println(3*(0.1*10)/10);
    }

    public void methodFour() {

    }

    public void methodFour(String name) {

    }

    public void methodFour(int age) {

    }

    public void methodFour(String name,int age) {}

    public void methodFour(int age,String name) {}

//    public String methodFour(String name) {
//        return null;
//    }

    public static void main(String[] args) {
        ClassOne classOne = new ClassOne();
        classOne.methodThree();
    }
}
