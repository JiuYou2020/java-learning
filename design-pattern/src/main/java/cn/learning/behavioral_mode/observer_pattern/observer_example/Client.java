package cn.learning.behavioral_mode.observer_pattern.observer_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        ConcreteObserverA observerA = new ConcreteObserverA();
        ConcreteObserverB observerB = new ConcreteObserverB();
        Subject subject = new ConcreteSubject();
        subject.attach(observerA);
        subject.attach(observerB);
        subject.notifyObserver();
        System.out.println("------------");
        subject.detach(observerA);
        subject.notifyObserver();
    }
}
