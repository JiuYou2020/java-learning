package cn.learning.behavioral_mode.strategy_pattern.airplane_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class StrategyContext {
    private Plane plane;

    public StrategyContext(Plane plane) {
        this.plane = plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void takeOff() {
        plane.takeOff();
    }

    public void fly() {
        plane.fly();
    }
}
