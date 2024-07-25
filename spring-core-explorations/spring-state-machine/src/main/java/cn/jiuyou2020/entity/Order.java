package cn.jiuyou2020.entity;

import cn.jiuyou2020.constant.OrderStatus;

public class Order {
    private int id;
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "订单号：" + id + ", 订单状态：" + status;
    }
}

