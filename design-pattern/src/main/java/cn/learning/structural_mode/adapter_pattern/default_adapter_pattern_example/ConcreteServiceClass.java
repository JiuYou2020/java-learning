package cn.learning.structural_mode.adapter_pattern.default_adapter_pattern_example;

/**
 * @author: jiuyou2020
 * @description: 具体业务类
 */
public class ConcreteServiceClass extends AbstractServiceClass {
    @Override
    public void serviceOperation1() {
        System.out.println("ConcreteServiceClass serviceOperation1");
    }

    @Override
    public int serviceOperation2() {
        System.out.println("ConcreteServiceClass serviceOperation2");
        return 0;
    }

    @Override
    public String serviceOperation3() {
        System.out.println("ConcreteServiceClass serviceOperation3");
        return "";
    }

    @Override
    public void serviceOperation4() {
        System.out.println("ConcreteServiceClass serviceOperation4");
    }

    @Override
    public int serviceOperation5() {
        System.out.println("ConcreteServiceClass serviceOperation5");
        return 0;
    }

    @Override
    public String serviceOperation6() {
        System.out.println("ConcreteServiceClass serviceOperation6");
        return "";
    }

    @Override
    public void serviceOperation7() {
        System.out.println("ConcreteServiceClass serviceOperation7");
    }

    @Override
    public int serviceOperation8() {
        System.out.println("ConcreteServiceClass serviceOperation8");
        return 0;
    }

    @Override
    public String serviceOperation9() {
        System.out.println("ConcreteServiceClass serviceOperation9");
        return "";
    }

    @Override
    public void serviceOperation10() {
        System.out.println("ConcreteServiceClass serviceOperation10");
    }

    @Override
    public int serviceOperation11() {
        System.out.println("ConcreteServiceClass serviceOperation11");
        return 0;
    }
}
