package cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product;

/**
 * @author jiuyou2020
 * @description 手机产品-华为
 * @date 2024/4/23 下午10:35
 */
public class InterfaceControllerIos extends InterfaceController {

    @Override
    public void show() {
        System.out.println("Ios系统下的界面控制器");
    }
}
