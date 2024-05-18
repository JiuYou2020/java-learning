package cn.learning.behavioral_mode.chain_of_responsibility_pattern.procurement_approval_example;


/**
 * @author: jiuyou2020
 * @description: 具体处理者
 */
public class VicePresident extends Approver {
    public VicePresident(String name) {
        super(name);
    }

    @Override
    public void handleRequest(PurchaseRequest request) {
        if (request.getAmount() < 100000) {
            System.out.println("副董事长" + name + "审批采购单：" + request.getNumber() + "金额：" + request.getAmount() + "元，采购目的：" + request.getPurpose());
        } else {
            successor.handleRequest(request);
        }
    }
}
