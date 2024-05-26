package cn.learning.behavioral_mode.strategy_pattern.movies_tickets_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ChildrenDiscount extends Discount {
    @Override
    public double calculate(double price) {
        double res = price - 10;
        System.out.println("儿童票：" + res);
        return res;
    }
}
