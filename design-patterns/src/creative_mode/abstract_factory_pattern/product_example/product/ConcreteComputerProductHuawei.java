package abstract_factory_pattern.product_example.product;

/**
 * @author jiuyou2020
 * @description 电脑产品-华为
 * @date 2024/4/23 下午10:43
 */
public class ConcreteComputerProductHuawei extends ComputerProduct {
    @Override
    public void show() {
        System.out.println("产品：" + bland + "，类型：" + type + "展示中~");
    }
}
