package cn.jiuyou2020;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: jiuyou2020
 * @description:
 */
@ConfigurationProperties("rpc")
public class RpcProperties {
    public int port = 9099;

    //实际前缀是rpc.client.type
    public String type = "json";

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
