package abstract_factory_pattern.game_os_practice.product;

/**
 * @author jiuyou2020
 * @description 产品基类
 * @date 2024/4/23 下午10:33
 */
public abstract class InterfaceController {
    public String os;
    public String operation;

    /**
     * 公共部分
     */
    public void fixUi() {
        System.out.println("操作系统：" + os + "，操作方式：" + operation + "，修改UI中");
    }

    /**
     * 私有部分
     */
    public abstract void show();
}
