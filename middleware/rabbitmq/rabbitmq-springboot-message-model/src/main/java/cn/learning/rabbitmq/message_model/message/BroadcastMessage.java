package cn.learning.rabbitmq.message_model.message;

import java.io.Serializable;

/**
 * 广播消费的消息示例
 */
public class BroadcastMessage implements Serializable {

    public static final String QUEUE = "QUEUE_BROADCASTING";

    public static final String EXCHANGE = "EXCHANGE_BROADCASTING";

    /**
     * 编号
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public BroadcastMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "BroadcastMessage{" +
                "id=" + id +
                '}';
    }

}
