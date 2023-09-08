package cn.learning.flowable.springboot.pojo;

import lombok.Data;
/**
 * 响应类
 * @Date
 */
@Data
public class ResponseBean {
  
    private Integer status;
    
    private String msg;
    
    private Object data;

    public static ResponseBean ok(String msg, Object data) {
        return new ResponseBean(200, msg, data);
    }


    public static ResponseBean ok(String msg) {
        return new ResponseBean(200, msg, null);
    }


    public static ResponseBean error(String msg, Object data) {
        return new ResponseBean(500, msg, data);
    }


    public static ResponseBean error(String msg) {
        return new ResponseBean(500, msg, null);
    }

    private ResponseBean() {
    }

    private ResponseBean(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}