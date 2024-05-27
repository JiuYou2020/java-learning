package cn.learning.behavioral_mode.template_method_pattern.bank_interest_calculation_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class SavingAccount extends Account {

    @Override
    public void calculateInterest() {
        System.out.println("按定期利率计算利息！");
    }
}
