package cn.learning.structural_mode.appearance_pattern.appearance_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.methodA();
        facade.methodB();
    }
}
