package cn.learning.behavioral_mode.visitor_pattern.visitor_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ConcreteElementA extends Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementA(this);
    }

    public void operationA() {
        System.out.println("ConcreteElementA operationA");
    }
}
