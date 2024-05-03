package creative_mode.factory_method_pattern.product_example.factory;

import creative_mode.factory_method_pattern.product_example.product.ComputerProduct;
import creative_mode.factory_method_pattern.product_example.product.Product;

/**
 * @author jiuyou2020
 * @description 对应Computer的工厂类
 * @date 2024/4/22 下午6:10
 */
public class ComputerProductFactory implements Factory {
    @Override
    public Product createProduct() {
        //还可以进行一些其他操作，例如初始化等
        return new ComputerProduct();
    }
}
