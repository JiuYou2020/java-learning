package cn.learning.behavioral_mode.visitor_pattern.visitor_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.addElement(new ConcreteElementA());
        objectStructure.addElement(new ConcreteElementB());

        ConcreteVisitorA concreteVisitor1 = new ConcreteVisitorA();
        ConcreteVisitorB concreteVisitor2 = new ConcreteVisitorB();

        objectStructure.accept(concreteVisitor1);
        System.out.println("====================================");
        objectStructure.accept(concreteVisitor2);
    }
}
