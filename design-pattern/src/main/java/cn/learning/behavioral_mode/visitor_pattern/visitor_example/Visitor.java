package cn.learning.behavioral_mode.visitor_pattern.visitor_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class Visitor {
    public abstract void visitConcreteElementA(ConcreteElementA concreteElementA);
    public abstract void visitConcreteElementB(ConcreteElementB concreteElementB);
}
