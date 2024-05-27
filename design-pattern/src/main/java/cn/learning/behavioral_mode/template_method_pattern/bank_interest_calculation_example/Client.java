package cn.learning.behavioral_mode.template_method_pattern.bank_interest_calculation_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Account account = new CurrentAccount();
        account.handle("张无忌", "123456");
        System.out.println("---------------");
        account = new SavingAccount();
        account.handle("小龙女", "123456");
    }
}
