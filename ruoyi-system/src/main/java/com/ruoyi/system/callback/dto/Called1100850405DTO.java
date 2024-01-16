package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Called1100850405DTO {

    /**
     * 离散等级
     */
    private Integer level;

    /**
     * 申请离散条数
     */
    private Integer num;

    /**
     * 原始文案
     */
    @JSONField(name = "origin_content")
    private String  originContent;

    /**
     * 离散文案
     */
    @JSONField(name = "discrete_list")
    private List<String> discreteList;

}
