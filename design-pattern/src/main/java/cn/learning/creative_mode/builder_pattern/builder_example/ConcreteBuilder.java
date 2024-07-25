package cn.learning.creative_mode.builder_pattern.builder_example;

/**
 * @author jiuyou2020
 * @description 具体建造者
 * @date 2024/4/25 下午2:35
 */
public class ConcreteBuilder extends Builder {
    @Override
    public void buildPartA() {
        product.setPartA("partA");
    }

    @Override
    public void buildPartB() {
        product.setPartB("partB");
    }

    @Override
    public void buildPartC() {
        product.setPartC("partC");
    }
}
