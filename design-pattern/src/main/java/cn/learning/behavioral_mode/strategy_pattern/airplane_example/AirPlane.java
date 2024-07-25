package cn.learning.behavioral_mode.strategy_pattern.airplane_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class AirPlane extends Plane {
    @Override
    public void takeOff() {
        System.out.println("客机长距离起飞中...");
    }

    @Override
    public void fly() {
        System.out.println("客机亚音速飞行中...");
    }
}
