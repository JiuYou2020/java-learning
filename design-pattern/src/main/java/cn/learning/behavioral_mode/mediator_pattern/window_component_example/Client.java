package cn.learning.behavioral_mode.mediator_pattern.window_component_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        //定义中介者对象
        ConcreteMediator mediator = new ConcreteMediator();
        //定义同事对象
        List list = new List();
        Button button = new Button();
        ComboBox comboBox = new ComboBox();
        TextBox textBox = new TextBox();
        //设置同事对象
        list.setMediator(mediator);
        button.setMediator(mediator);
        comboBox.setMediator(mediator);
        textBox.setMediator(mediator);
        //设置中介者对象
        mediator.setList(list);
        mediator.setAddButton(button);
        mediator.setCb(comboBox);
        mediator.setUserNameTextBox(textBox);
        //模拟用户交互
        button.changed();
        System.out.println("-----------------------------");
        list.changed();
    }
}
