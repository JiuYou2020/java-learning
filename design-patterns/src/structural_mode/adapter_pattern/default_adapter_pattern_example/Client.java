package structural_mode.adapter_pattern.default_adapter_pattern_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        AbstractServiceClass abstractServiceClass = new ConcreteServiceClass();
        abstractServiceClass.serviceOperation1();
        abstractServiceClass.serviceOperation2();
        abstractServiceClass.serviceOperation3();
        abstractServiceClass.serviceOperation4();
        abstractServiceClass.serviceOperation5();
        abstractServiceClass.serviceOperation6();
        abstractServiceClass.serviceOperation7();
        abstractServiceClass.serviceOperation8();
        abstractServiceClass.serviceOperation9();
        abstractServiceClass.serviceOperation10();
        abstractServiceClass.serviceOperation11();
    }
}
