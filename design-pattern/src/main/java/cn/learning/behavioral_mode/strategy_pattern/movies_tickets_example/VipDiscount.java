package cn.learning.behavioral_mode.strategy_pattern.movies_tickets_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class VipDiscount extends Discount {
    @Override
    public double calculate(double price) {
        double res = price * 0.5;
        System.out.println("VIP票：" + res);
        return res;
    }
}
