package creative_mode.builder_pattern.builder_example;

/**
 * @author jiuyou2020
 * @description 客户端类
 * @date 2024/4/25 下午2:43
 */
public class Client {
    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.build();
        System.out.println(product.getPartA());
        System.out.println(product.getPartB());
        System.out.println(product.getPartC());
    }
}
