package creative_mode.abstract_factory_pattern.product_example;

import creative_mode.abstract_factory_pattern.product_example.factory.Factory;
import creative_mode.abstract_factory_pattern.product_example.factory.ProductFactoryHuawei;
import creative_mode.abstract_factory_pattern.product_example.product.ComputerProduct;
import creative_mode.abstract_factory_pattern.product_example.product.PhoneProduct;

/**
 * @author jiuyou2020
 * @description 客户端
 * @date 2024/4/23 下午10:55
 */
public class Client {
    public static void main(String[] args) {
        // 创建工厂
        Factory factory = new ProductFactoryHuawei();
        // 创建手机产品
        PhoneProduct phoneProduct = factory.createPhoneProduct();
        phoneProduct.show();
        // 创建电脑产品
        ComputerProduct computerProduct = factory.createComputerProduct();
        computerProduct.show();
    }
}
