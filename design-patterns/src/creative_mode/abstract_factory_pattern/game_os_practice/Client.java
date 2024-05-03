package abstract_factory_pattern.game_os_practice;

import abstract_factory_pattern.game_os_practice.factory.Factory;
import abstract_factory_pattern.game_os_practice.factory.ProductFactoryIos;
import abstract_factory_pattern.game_os_practice.product.InterfaceController;
import abstract_factory_pattern.game_os_practice.product.OperationController;

/**
 * @author jiuyou2020
 * @description 客户端
 * @date 2024/4/23 下午10:55
 */
public class Client {
    public static void main(String[] args) {
        Factory factory = new ProductFactoryIos();
        InterfaceController interfaceController = factory.createInterfaceController();
        OperationController operationController = factory.createOperationController();
        interfaceController.fixUi();
        interfaceController.show();
        operationController.operating();
        operationController.show();
    }
}
