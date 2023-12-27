package com.ruoyi.system.extend.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class GetPrivateMessageTaskPageData extends UtTouchQueryPage {

    /**
     * 任务创建开始时间
     */
    private LocalDateTime starTime;

    /**
     * 任务创建结束时间
     */
    private LocalDateTime endTime;

    /**
     * 任务执行状态
     */
    private Integer executeStatus;

    /**
     * 批量私信任务Id列表
     */
    private List<String> privateMessageTaskIds = new ArrayList<>();

    /**
     * 名称
     */
    private String name;

    /**
     * 用户编码
     */
    private String userCode;

}
