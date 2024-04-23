package abstract_factory_pattern.game_os_practice.factory;

import abstract_factory_pattern.game_os_practice.product.InterfaceController;
import abstract_factory_pattern.game_os_practice.product.InterfaceControllerAndroid;
import abstract_factory_pattern.game_os_practice.product.OperationController;
import abstract_factory_pattern.game_os_practice.product.OperationControllerAndroid;

/**
 * @author jiuyou2020
 * @description 负责Android系列手机的游戏操作控制
 * @date 2024/4/23 下午10:52
 */
public class ProductFactoryAndroid extends Factory {
    @Override
    public InterfaceController createInterfaceController() {
        InterfaceControllerAndroid interfaceControllerAndroid = new InterfaceControllerAndroid();
        interfaceControllerAndroid.os = "Android";
        interfaceControllerAndroid.operation = "游戏界面控制";
        return interfaceControllerAndroid;
    }

    @Override
    public OperationController createOperationController() {
        OperationControllerAndroid operationControllerAndroid = new OperationControllerAndroid();
        operationControllerAndroid.os = "Android";
        operationControllerAndroid.operation = "游戏操作控制";
        return operationControllerAndroid;
    }
}
