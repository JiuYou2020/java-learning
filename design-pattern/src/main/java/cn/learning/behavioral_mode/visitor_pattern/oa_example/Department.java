package cn.learning.behavioral_mode.visitor_pattern.oa_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class Department {
    public abstract void visit(FullTimeEmployee fullTimeEmployee);

    public abstract void visit(PartTimeEmployee partTimeEmployee);
}
