package cn.learning.behavioral_mode.state_pattern.state_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class AccountState {
    protected Account account;


    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public abstract void computeInterest();
}
