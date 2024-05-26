package cn.learning.behavioral_mode.strategy_pattern.movies_tickets_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class Discount {
    public abstract double calculate(double price);
}
