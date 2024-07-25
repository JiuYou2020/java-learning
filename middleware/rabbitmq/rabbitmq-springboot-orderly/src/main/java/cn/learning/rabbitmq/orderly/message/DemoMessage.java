package cn.learning.rabbitmq.orderly.message;

import java.io.Serializable;

/**
 * - 定义了 `QUEUE_DEMO_10-` 的四个子 Queue 。
 * <p>
 * - 定义了统一的 Exchange 。
 * <p>
 * - 暂未定义 RoutingKey 的名字，我们会使用“队列编号”作为 RoutingKey ，然后路由消息到每个子 Queue 中。
 */
public class DemoMessage implements Serializable {

    public static final int QUEUE_COUNT = 4;
    public static final String EXCHANGE = "EXCHANGE_DEMO_";
    private static final String QUEUE_BASE = "QUEUE_DEMO-";
    public static final String QUEUE_0 = QUEUE_BASE + "0";
    public static final String QUEUE_1 = QUEUE_BASE + "1";
    public static final String QUEUE_2 = QUEUE_BASE + "2";
    public static final String QUEUE_3 = QUEUE_BASE + "3";
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
