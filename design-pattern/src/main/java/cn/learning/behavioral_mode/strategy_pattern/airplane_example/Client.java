package cn.learning.behavioral_mode.strategy_pattern.airplane_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        StrategyContext strategyContext = new StrategyContext(new AirPlane());
        strategyContext.takeOff();
        strategyContext.fly();
        System.out.println("===================================");
        strategyContext.setPlane(new Helicopter());
        strategyContext.takeOff();
        strategyContext.fly();

        System.out.println("===================================");
        strategyContext.setPlane(new Fighter());
        strategyContext.takeOff();
        strategyContext.fly();

        System.out.println("===================================");
        strategyContext.setPlane(new Harrier());
        strategyContext.takeOff();
        strategyContext.fly();
    }
}
