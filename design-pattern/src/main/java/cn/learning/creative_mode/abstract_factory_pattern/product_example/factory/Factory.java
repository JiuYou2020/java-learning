package cn.learning.creative_mode.abstract_factory_pattern.product_example.factory;

import cn.learning.creative_mode.abstract_factory_pattern.product_example.product.ComputerProduct;
import cn.learning.creative_mode.abstract_factory_pattern.product_example.product.PhoneProduct;

/**
 * @author jiuyou2020
 * @description 产品基类
 * @date 2024/4/23 下午10:45
 */
public abstract class Factory {
    public abstract PhoneProduct createPhoneProduct();

    public abstract ComputerProduct createComputerProduct();
}
