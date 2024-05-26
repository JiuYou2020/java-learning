package cn.learning.behavioral_mode.strategy_pattern.airplane_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Fighter extends Plane {
    @Override
    public void takeOff() {
        System.out.println("战斗机长距离起飞中...");
    }

    @Override
    public void fly() {
        System.out.println("战斗机超音速飞行中...");
    }
}
