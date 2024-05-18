package cn.learning.behavioral_mode.chain_of_responsibility_pattern.procurement_approval_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Congress extends Approver {

    public Congress(String name) {
        super(name);
    }

    @Override
    public void handleRequest(PurchaseRequest request) {
        if (request.getAmount() < 1000000) {
            System.out.println("董事会审批采购单：" + request.getNumber() + "金额：" + request.getAmount() + "元，采购目的：" + request.getPurpose());
        } else {
            System.out.println("采购单：" + request.getNumber() + "金额：" + request.getAmount() + "元，采购目的：" + request.getPurpose() + "，需要召开董事会讨论！");
        }
    }
}
