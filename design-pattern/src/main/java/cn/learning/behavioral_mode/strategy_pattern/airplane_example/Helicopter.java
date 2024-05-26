package cn.learning.behavioral_mode.strategy_pattern.airplane_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Helicopter extends Plane{
    @Override
    public void takeOff() {
        System.out.println("直升机垂直起飞中...");
    }

    @Override
    public void fly() {
        System.out.println("直升机亚音速飞行中...");
    }
}
