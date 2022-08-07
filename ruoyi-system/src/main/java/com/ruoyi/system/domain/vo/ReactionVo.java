package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.*;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/2
 * Time: 22:05
 */
@Data
public class ReactionVo {

    private Long reactionId;

    private String reactionName;

    private String reactionCode;

    private String createBy;

    private String createTime;

    private String remark;

    private List<WorkReactionDetail> workReactionDetails;

    private WorkFeeding workFeeding;

    private WorkHeating workHeating;

    private WorkConcentrate workConcentrate;

    private WorkExtraction workExtraction;

    private WorkGel workGel;
}
