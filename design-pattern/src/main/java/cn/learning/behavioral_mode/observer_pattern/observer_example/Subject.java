package cn.learning.behavioral_mode.observer_pattern.observer_example;

import java.util.ArrayList;

/**
 * @author: jiuyou2020
 * @description: 抽象主题
 */
public abstract class Subject {
    protected ArrayList<Observer> observers = new ArrayList<>();

    // 注册观察者
    public void attach(Observer observer) {
        observers.add(observer);
    }

    // 删除观察者
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    // 通知观察者
    public abstract void notifyObserver();
}
