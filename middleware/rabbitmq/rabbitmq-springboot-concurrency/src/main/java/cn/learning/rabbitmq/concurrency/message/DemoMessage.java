package cn.learning.rabbitmq.concurrency.message;

import java.io.Serializable;

public class DemoMessage implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_";

    public static final String EXCHANGE = "EXCHANGE_DEMO_";

    public static final String ROUTING_KEY = "ROUTING_KEY_";

    /**
     * 编号
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public DemoMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "DemoMessage{" +
                "id=" + id +
                '}';
    }

}
