package cn.learning.flowable.springboot.pojo;

import lombok.Data;

/**
 * 请假条申请
 *
 * @Date
 */
@Data
public class VacationRequestVo {

    private String name;

    private Integer days;

    private String reason;
}