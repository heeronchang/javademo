package designpattern.observer;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/15
 */
public abstract class Observer {
    protected String name;
    protected ISubject subject;

    public Observer(String name, ISubject ISubject) {
        this.name = name;
        this.subject = ISubject;
    }

    public abstract void update();
}
