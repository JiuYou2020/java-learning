package creative_mode.abstract_factory_pattern.product_example.product;

/**
 * @author jiuyou2020
 * @description 手机产品-苹果
 * @date 2024/4/23 下午10:36
 */
public class ConcretePhoneProductIphone extends PhoneProduct {

    @Override
    public void show() {
        System.out.println("产品：" + bland + "，类型：" + type + "展示中~");
    }
}
