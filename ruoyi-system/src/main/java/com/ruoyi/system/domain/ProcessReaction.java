package com.ruoyi.system.domain;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/2
 * Time: 22:21
 */
@Data
public class ProcessReaction {

    private Long processReactionId;

    private Long reactionCode;

    private Integer sort;

    private String processName;

    private String childProcessName;

    private Integer childProcessSort;

    private String precondition;

    private String param;

    private String endif;
}
