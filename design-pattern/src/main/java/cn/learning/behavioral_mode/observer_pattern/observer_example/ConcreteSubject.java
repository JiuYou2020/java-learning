package cn.learning.behavioral_mode.observer_pattern.observer_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ConcreteSubject extends Subject {
    // 具体主题的状态
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    }
