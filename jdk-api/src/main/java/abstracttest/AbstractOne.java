package abstracttest;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/16
 */
public abstract class AbstractOne {
    private short a;
    protected String b;
    public int c;
    public abstract void methodOne();
    public abstract void methodTwo();

    public void methodThree(){
        System.out.println("methodThree");
    }
}
