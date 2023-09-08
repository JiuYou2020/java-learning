package cn.learning.flowable.springboot.pojo;

import lombok.Data;

/**
 * 请假条审批
 *
 * @Date 2023/9/8
 */
@Data
public class VacationApproveVo {

    private String taskId;

    private Boolean approve;

    private String name;
}