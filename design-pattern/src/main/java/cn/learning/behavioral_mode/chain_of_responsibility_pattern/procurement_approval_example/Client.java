package cn.learning.behavioral_mode.chain_of_responsibility_pattern.procurement_approval_example;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Approver wjzhang, gyang, jguo, meeting, congress;
        wjzhang = new Director("张无忌");
        gyang = new VicePresident("杨过");
        jguo = new President("郭靖");
        meeting = new Congress("董事会");

        wjzhang.setSuccessor(gyang);
        gyang.setSuccessor(jguo);
        jguo.setSuccessor(meeting);

        PurchaseRequest request1 = new PurchaseRequest(45000, 10001, "购买倚天剑");
        wjzhang.handleRequest(request1);

        PurchaseRequest request2 = new PurchaseRequest(60000, 10002, "购买屠龙刀");
        wjzhang.handleRequest(request2);

        PurchaseRequest request3 = new PurchaseRequest(160000, 10003, "购买九阳真经");
        wjzhang.handleRequest(request3);

        PurchaseRequest request4 = new PurchaseRequest(800000, 10004, "购买葵花宝典");
        wjzhang.handleRequest(request4);

        PurchaseRequest request5 = new PurchaseRequest(1600000, 10005, "购买九阴真经");
        wjzhang.handleRequest(request5);
    }
}
