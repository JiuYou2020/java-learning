package creative_mode.abstract_factory_pattern.game_os_practice.factory;

import creative_mode.abstract_factory_pattern.game_os_practice.product.InterfaceController;
import creative_mode.abstract_factory_pattern.game_os_practice.product.InterfaceControllerIos;
import creative_mode.abstract_factory_pattern.game_os_practice.product.OperationController;
import creative_mode.abstract_factory_pattern.game_os_practice.product.OperationControllerIos;

/**
 * @author jiuyou2020
 * @description 负责Ios系列手机的游戏操作控制
 * @date 2024/4/23 下午10:47
 */
public class ProductFactoryIos extends Factory {
    @Override
    public InterfaceController createInterfaceController() {
        InterfaceControllerIos interfaceControllerIos = new InterfaceControllerIos();
        interfaceControllerIos.os = "Ios";
        interfaceControllerIos.operation = "游戏界面控制";
        return interfaceControllerIos;
    }

    @Override
    public OperationController createOperationController() {
        OperationControllerIos operationControllerIos = new OperationControllerIos();
        operationControllerIos.os = "Ios";
        operationControllerIos.operation = "游戏操作控制";
        return operationControllerIos;
    }
}
