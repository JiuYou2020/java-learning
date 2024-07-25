package cn.learning.behavioral_mode.observer_pattern.observer_example;

/**
 * @author: jiuyou2020
 * @description: 有两种方式去实现观察者模式，一种是推模型，一种是拉模型
 * 推模型：主题对象主动向观察者推送主题的详细信息，不管观察者是否需要，推送的信息通常是主题对象的全部或部分数据
 * 拉模型：主题对象在通知观察者的时候，只传递少量信息，如果观察者需要更具体的信息，由观察者主动到主题对象中获取，相当于是观察者从主题对象中拉数据
 * 下面是一个推模型的实现
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
