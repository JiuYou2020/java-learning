package cn.learning.behavioral_mode.template_method_pattern.bank_interest_calculation_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class Account {
    public boolean validate(String account, String password) {
        System.out.println("账号：" + account);
        System.out.println("密码：" + password);
        // 模拟验证账户
        return "张无忌".equals(account) && "123456".equals(password);
    }

    public abstract void calculateInterest();

    public void display() {
        System.out.println("显示利息！");
    }

    public void handle(String account, String password) {
        if (!validate(account, password)) {
            System.out.println("账户或密码错误！");
            return;
        }
        calculateInterest();
        display();
    }
}
