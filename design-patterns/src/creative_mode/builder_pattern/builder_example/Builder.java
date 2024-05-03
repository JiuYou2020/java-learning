package builder_pattern.builder_example;

/**
 * @author jiuyou2020
 * @description 抽象建造者
 * @date 2024/4/25 下午2:34
 */
public abstract class Builder {
    protected Product product = new Product();

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    public Product getResult() {
        return product;
    }
}
