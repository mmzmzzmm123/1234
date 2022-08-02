package com.ruoyi.system.domain;

import lombok.Data;

/**
 * @author wjp
 * @date 2022/8/2 13:52
 * @description
 */
@Data
public class Process {

    private Long processId;

    private String processName;

    private String childProcessName;

    private String childProcessSort;

    private String precondition;

    private String param;

    private String endif;
}
