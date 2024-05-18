package cn.learning.creative_mode.abstract_factory_pattern.game_os_practice.product;

/**
 * @author jiuyou2020
 * @description 电脑产品基类，一个产品结构
 * @date 2024/4/23 下午10:40
 */
public abstract class OperationController {
    public String os;
    public String operation;

    public void operating() {
        System.out.println("操作系统：" + os + "，操作方式：" + operation + "，操作中");
    }

    public abstract void show();
}
