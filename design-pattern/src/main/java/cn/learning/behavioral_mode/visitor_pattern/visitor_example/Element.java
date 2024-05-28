package cn.learning.behavioral_mode.visitor_pattern.visitor_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class Element {
    public abstract void accept(Visitor visitor);
}
