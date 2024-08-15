package designpattern.observer.impl;

import designpattern.observer.Observer;
import designpattern.observer.ISubject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/15
 */
public class Secretary implements ISubject {
    private List<Observer> observers = new ArrayList<>();
    private String action;

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getAction() {
        return action;
    }
}
