package simple_factory_pattern;

/**
 * @author jiuyou2020
 * @description 客户端类，在此处，我们每次修改产品类时，都需要修改客户端代码中工厂类的参数，违反了开闭原则，我们可以将静态工厂方法的参数放在配置文件中，这样就不需要修改客户端代码了
 * @date 2024/4/22 上午11:29
 */
public class Client {
    public static void main(String[] args) {
        Product productA = ProductFactory.createProduct(ProductFactory.PRODUCT_A);
        productA.methodSame();
        productA.methodDiff();

        Product productB = ProductFactory.createProduct(ProductFactory.PRODUCT_B);
        productB.methodSame();
        productB.methodDiff();
    }
}
