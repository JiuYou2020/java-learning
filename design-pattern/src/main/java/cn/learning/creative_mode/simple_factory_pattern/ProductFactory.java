package cn.learning.creative_mode.simple_factory_pattern;

/**
 * @author jiuyou2020
 * @description 产品工厂类
 * @date 2024/4/22 上午11:27
 */
public class ProductFactory {
    public static final String PRODUCT_A = "A";
    public static final String PRODUCT_B = "B";
    public static Product createProduct(String type) {
        Product product=null;
        if (PRODUCT_A.equals(type)) {
            product = new ConcreteProductA();
        } else if (PRODUCT_B.equals(type)) {
            product = new ConcreteProductB();
        }
        //在这里还可以对产品类进行一些初始化操作
        return product;
    }
}
