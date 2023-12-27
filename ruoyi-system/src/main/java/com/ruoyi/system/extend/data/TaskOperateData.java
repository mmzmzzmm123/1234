package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class TaskOperateData {

    private String userCode;

    private List<String> taskIds;

}
