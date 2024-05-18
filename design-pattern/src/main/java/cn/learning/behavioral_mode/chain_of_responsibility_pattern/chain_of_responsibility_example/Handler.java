package cn.learning.behavioral_mode.chain_of_responsibility_pattern.chain_of_responsibility_example;

/**
 * @author: jiuyou2020
 * @description: 抽象处理者
 */
public abstract class Handler {
    protected Handler successor;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(int request);
}
