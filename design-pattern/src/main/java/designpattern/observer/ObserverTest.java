package designpattern.observer;

import designpattern.observer.impl.Secretary;
import designpattern.observer.impl.StockObserver;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/15
 */
public class ObserverTest {
    public static void main(String[] args) {
        Secretary secretary = new Secretary();

        StockObserver observer = new StockObserver("tom", secretary);
        StockObserver observer1 = new StockObserver("jack", secretary);

        secretary.attach(observer);
        secretary.attach(observer1);

        secretary.setAction("老板回来了");
        secretary.notifyObservers();
    }
}
