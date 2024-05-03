package creative_mode.simple_factory_pattern;

/**
 * @author jiuyou2020
 * @description 产品类A
 * @date 2024/4/22 上午11:00
 */
public class ConcreteProductA extends Product {
    @Override
    public void methodDiff() {
        System.out.println("产品A的特有业务方法");
    }
}
