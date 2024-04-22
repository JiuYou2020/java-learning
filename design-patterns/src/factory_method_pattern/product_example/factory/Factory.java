package factory_method_pattern.product_example.factory;


import factory_method_pattern.product_example.product.Product;

/**
 * @author jiuyou2020
 * @description 工厂接口，具体工厂类需要实现/继承该类
 * @date 2024/4/22 下午6:08
 */
public interface Factory {
    Product createProduct();
}
