package cn.learning.creative_mode.abstract_factory_pattern.product_example.factory;

import cn.learning.creative_mode.abstract_factory_pattern.product_example.product.ComputerProduct;
import cn.learning.creative_mode.abstract_factory_pattern.product_example.product.ConcreteComputerProductHuawei;
import cn.learning.creative_mode.abstract_factory_pattern.product_example.product.ConcretePhoneProductHuawei;
import cn.learning.creative_mode.abstract_factory_pattern.product_example.product.PhoneProduct;

/**
 * @author jiuyou2020
 * @description 华为产品生产工厂
 * @date 2024/4/23 下午10:47
 */
public class ProductFactoryHuawei extends Factory {
    @Override
    public PhoneProduct createPhoneProduct() {
        ConcretePhoneProductHuawei phone = new ConcretePhoneProductHuawei();
        phone.bland = "华为";
        phone.type = "手机";
        return phone;
    }

    @Override
    public ComputerProduct createComputerProduct() {
        ConcreteComputerProductHuawei computer = new ConcreteComputerProductHuawei();
        computer.bland = "华为";
        computer.type = "电脑";
        return computer;
    }
}
