package cn.learning.behavioral_mode.strategy_pattern.movies_tickets_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class StudentDiscount extends Discount {
    @Override
    public double calculate(double price) {
        double res = price * 0.8;
        System.out.println("学生票：" + res);
        return res;
    }
}
