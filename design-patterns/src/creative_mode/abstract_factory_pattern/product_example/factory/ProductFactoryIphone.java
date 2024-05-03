package abstract_factory_pattern.product_example.factory;

import abstract_factory_pattern.product_example.product.ComputerProduct;
import abstract_factory_pattern.product_example.product.ConcreteComputerProductIphone;
import abstract_factory_pattern.product_example.product.ConcretePhoneProductIphone;
import abstract_factory_pattern.product_example.product.PhoneProduct;

/**
 * @author jiuyou2020
 * @description 苹果产品生产工厂
 * @date 2024/4/23 下午10:52
 */
public class ProductFactoryIphone extends Factory {
    @Override
    public PhoneProduct createPhoneProduct() {
        ConcretePhoneProductIphone phone = new ConcretePhoneProductIphone();
        phone.bland = "苹果";
        phone.type = "手机";
        return phone;
    }

    @Override
    public ComputerProduct createComputerProduct() {
        ConcreteComputerProductIphone computer = new ConcreteComputerProductIphone();
        computer.bland = "苹果";
        computer.type = "电脑";
        return computer;
    }
}
