package cn.learning.behavioral_mode.mediator_pattern.window_component_example;

/**
 * @author: jiuyou2020
 * @description: 具体中介者类
 */
public class ConcreteMediator extends Mediator {
    private Button addButton;
    private List list;
    private TextBox userNameTextBox;
    private ComboBox cb;

    @Override
    public void componentChanged(Component c) {
        //单击按钮
        if (c == addButton) {
            System.out.println("--单击增加按钮--");
            list.update();
            cb.update();
            userNameTextBox.update();
        }
        //从列表框选择客户
        else if (c == list) {
            System.out.println("--从列表框选择客户--");
            cb.select();
            userNameTextBox.setText();
        }
        //从组合框选择客户
        else if (c == cb) {
            System.out.println("--从组合框选择客户--");
            cb.select();
            userNameTextBox.setText();
        }
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public TextBox getUserNameTextBox() {
        return userNameTextBox;
    }

    public void setUserNameTextBox(TextBox userNameTextBox) {
        this.userNameTextBox = userNameTextBox;
    }

    public ComboBox getCb() {
        return cb;
    }

    public void setCb(ComboBox cb) {
        this.cb = cb;
    }

}
