package cn.learning.rabbitmq.springboot.quickstart.message;

import java.io.Serializable;

/**
 * Topic Exchange
 */
public class Demo02Message implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_02";

    public static final String EXCHANGE = "EXCHANGE_DEMO_02";

    public static final String ROUTING_KEY = "#.jiu.you";

    /**
     * 编号
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo02Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo02Message{" +
                "id=" + id +
                '}';
    }

}
