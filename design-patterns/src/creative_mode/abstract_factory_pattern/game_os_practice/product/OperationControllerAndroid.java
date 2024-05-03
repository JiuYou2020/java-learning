package creative_mode.abstract_factory_pattern.game_os_practice.product;

/**
 * @author jiuyou2020
 * @description 电脑产品-苹果
 * @date 2024/4/23 下午10:42
 */
public class OperationControllerAndroid extends OperationController {
    @Override
    public void show() {
        System.out.println("Android系统下的操作控制器");
    }
}
