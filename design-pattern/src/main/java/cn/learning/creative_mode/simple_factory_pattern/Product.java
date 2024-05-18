package cn.learning.creative_mode.simple_factory_pattern;

/**
 * @author jiuyou2020
 * @description 基类：抽象类
 * @date 2024/4/22 上午10:57
 */
public abstract class Product {
    /**
     * 所有产品类的公共业务方法
     */
    public void methodSame() {
        System.out.println("所有产品类的公共业务方法");
    }

    /**
     * 声明抽象业务方法，由子类实现
     */
    public abstract void methodDiff();
}
