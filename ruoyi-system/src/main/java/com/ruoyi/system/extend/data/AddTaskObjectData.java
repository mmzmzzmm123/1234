package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class AddTaskObjectData {

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 任务Id
     */
    private String taskId;

    /**
     * 手机号/用户名
     */
    private List<String> contentList;

    /**
     * 导入类型（0手机、1用户号）
     */
    private Integer importType;
}
