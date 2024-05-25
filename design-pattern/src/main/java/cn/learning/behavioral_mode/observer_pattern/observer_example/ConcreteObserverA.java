package cn.learning.behavioral_mode.observer_pattern.observer_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ConcreteObserverA implements Observer {
    @Override
    public void update() {
        System.out.println("ConcreteObserverA update");
    }
}
