package cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product;

/**
 * @author jiuyou2020
 * @description 电脑产品-华为
 * @date 2024/4/23 下午10:43
 */
public class OperationControllerIos extends OperationController {
    @Override
    public void show() {
        System.out.println("Ios系统下的操作控制器");
    }
}
