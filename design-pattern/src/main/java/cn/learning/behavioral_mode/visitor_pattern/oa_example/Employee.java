package cn.learning.behavioral_mode.visitor_pattern.oa_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class Employee {
    public abstract void accept(Department visitor);
}
