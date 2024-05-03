package builder_pattern.builder_example;

/**
 * @author jiuyou2020
 * @description 指挥者：隔离客户端与创建过程，控制buildx方法是否被调用以及调用次序
 * @date 2024/4/25 下午2:39
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product build() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}
