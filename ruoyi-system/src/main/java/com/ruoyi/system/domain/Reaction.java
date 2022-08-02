package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/2
 * Time: 21:21
 */
@Data
public class Reaction extends BaseEntity{

    private Long reactionId;

    private String reactionName;

    private String reactionCode;
}
