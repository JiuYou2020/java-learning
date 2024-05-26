package cn.learning.behavioral_mode.strategy_pattern.airplane_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Harrier extends Plane {
    @Override
    public void takeOff() {
        System.out.println("鹞式战斗机垂直起飞中...");
    }

    @Override
    public void fly() {
        System.out.println("鹞式战斗机超音速飞行中...");
    }
}
