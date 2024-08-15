package designpattern.observer;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/15
 */
public interface ISubject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
    void setAction(String action);
    String getAction();
}
