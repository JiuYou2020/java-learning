package cn.learning.rabbitmq.cloud.error_handler.consumer.message;

/**
 * 示例 01 的 Message 消息
 */
public class Demo01Message {

    /**
     * 编号
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo01Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo01Message{" +
                "id=" + id +
                '}';
    }

}
