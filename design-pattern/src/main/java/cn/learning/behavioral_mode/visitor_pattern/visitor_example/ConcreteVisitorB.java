package cn.learning.behavioral_mode.visitor_pattern.visitor_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ConcreteVisitorB extends Visitor {
    @Override
    public void visitConcreteElementA(ConcreteElementA concreteElementA) {
        System.out.println("访问者B访问元素A，进行了A的操作");
        concreteElementA.operationA();
    }

    @Override
    public void visitConcreteElementB(ConcreteElementB concreteElementB) {
        System.out.println("访问者B访问元素B，进行了B的操作");
        concreteElementB.operationB();
    }
}
