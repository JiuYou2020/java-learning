package cn.learning.rabbitmq.error_handler.message;

import java.io.Serializable;

public class DemoMessage implements Serializable {

    public static final String QUEUE = "QUEUE_DEM";

    public static final String EXCHANGE = "EXCHANGE_DEMO";

    public static final String ROUTING_KEY = "ROUTING_KEY";

    /**
     * 编号
     */
    private Integer id;

    public DemoMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DemoMessage{" +
                "id=" + id +
                '}';
    }

}
