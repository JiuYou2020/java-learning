package cn.learning.rabbitmq.batch_sending.message;

import java.io.Serializable;

public class DemoMessage implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_05";

    public static final String EXCHANGE = "EXCHANGE_DEMO_05";

    public static final String ROUTING_KEY = "ROUTING_KEY_05";

    /**
     * 编号
     */
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DemoMessage{" +
                "id=" + id +
                '}';
    }

}
