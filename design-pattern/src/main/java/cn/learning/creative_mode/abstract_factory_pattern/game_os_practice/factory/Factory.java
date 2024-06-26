package cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.factory;

import cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product.InterfaceController;
import cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product.OperationController;
import cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product.InterfaceController;
import cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product.OperationController;

/**
 * @author jiuyou2020
 * @description 工厂基类，对拓展操作系统开放，对修改操作系统关闭
 * @date 2024/4/23 下午10:45
 */
public abstract class Factory {
    public abstract InterfaceController createInterfaceController();

    public abstract OperationController createOperationController();
}
