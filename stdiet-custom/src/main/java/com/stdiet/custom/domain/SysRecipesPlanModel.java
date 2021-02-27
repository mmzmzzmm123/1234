package com.stdiet.custom.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SysRecipesPlanModel {
    Long id;

    Long nutritionistId;

    Long nutriAssisId;

    Long planId;

    String remark;

    Date updateTime;

    Date createTime;

    String updateBy;

    String createBy;

    Long recipesId;
}
