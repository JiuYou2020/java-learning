package creative_mode.factory_method_pattern.product_example.product;

/**
 * @author jiuyou2020
 * @description 产品基类，具体产品需要实现它
 * @date 2024/4/22 下午6:11
 */
public abstract class Product {
    public abstract void show();

    public void showProduct() {
        System.out.println("Product");
    }
}
