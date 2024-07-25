package cn.southtang.message;

import java.io.Serializable;

public class DemoMessage implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO"; // 正常队列
    public static final String DELAY_QUEUE = "DELAY_QUEUE_DEMO"; // 死信队列

    public static final String EXCHANGE = "EXCHANGE_DEMO";

    public static final String ROUTING_KEY = "ROUTING_KEY"; // 正常路由键
    public static final String DELAY_ROUTING_KEY = "DELAY_ROUTING_KEY"; // 死信路由键


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
