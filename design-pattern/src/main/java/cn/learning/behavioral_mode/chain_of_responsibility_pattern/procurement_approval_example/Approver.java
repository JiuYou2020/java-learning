package cn.learning.behavioral_mode.chain_of_responsibility_pattern.procurement_approval_example;

/**
 * @author: jiuyou2020
 * @description: 抽象处理者
 */
public abstract class Approver {
    protected Approver successor;
    protected String name;      // 处理者姓名

    public Approver(String name) {
        this.name = name;
    }

    public void setSuccessor(Approver successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(PurchaseRequest request);
}
