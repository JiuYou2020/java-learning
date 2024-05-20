package cn.learning.behavioral_mode.mediator_pattern.window_component_example;

/**
 * @author: jiuyou2020
 * @description: 文本框类
 */
public class TextBox extends Component {
    @Override
    public void update() {
        System.out.println("客户信息增加成功后文本框清空");
    }

    public void setText() {
        System.out.println("文本框显示：小龙女");
    }
}
