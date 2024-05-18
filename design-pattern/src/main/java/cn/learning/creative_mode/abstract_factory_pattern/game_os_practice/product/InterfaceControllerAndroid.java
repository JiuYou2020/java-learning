package cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product;

/**
 * @author jiuyou2020
 * @description 手机产品-苹果
 * @date 2024/4/23 下午10:36
 */
public class InterfaceControllerAndroid extends InterfaceController {
    @Override
    public void show() {
        System.out.println("Android系统下的界面控制器");
    }
}
