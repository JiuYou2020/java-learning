package creative_mode.factory_method_pattern.product_example;

import creative_mode.factory_method_pattern.product_example.factory.ComputerProductFactory;
import creative_mode.factory_method_pattern.product_example.factory.Factory;
import creative_mode.factory_method_pattern.product_example.factory.PhoneProductFactory;
import creative_mode.factory_method_pattern.product_example.product.Product;

/**
 * @author jiuyou2020
 * @description 客户端类
 * @date 2024/4/22 下午6:18
 */
public class Client {
    public static void main(String[] args) {
        //创建工厂
        Factory factory = new PhoneProductFactory();
        //创建产品
        Product product = factory.createProduct();
        //展示产品
        product.show();

        //创建工厂
        ComputerProductFactory productFactory = new ComputerProductFactory();
        //创建产品
        Product product1 = productFactory.createProduct();
        //展示产品
        product1.show();
    }
}
