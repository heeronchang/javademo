package designpattern.observer.impl;

import designpattern.observer.ISubject;
import designpattern.observer.Observer;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/15
 */
public class StockObserver extends Observer {
    public StockObserver(String name, ISubject ISubject) {
        super(name, ISubject);
    }

    @Override
    public void update() {
        System.out.println(subject.getAction() + ", " + name + ":好的,知道了");
    }
}
