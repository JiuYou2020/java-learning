package cn.learning.behavioral_mode.mediator_pattern.window_component_example;

/**
 * @author: jiuyou2020
 * @description: 组合框类
 */
public class ComboBox extends Component {
    @Override
    public void update() {
        System.out.println("组合框增加一项：张无忌");
    }

    public void select() {
        System.out.println("组合框选中项：小龙女");
    }
}
