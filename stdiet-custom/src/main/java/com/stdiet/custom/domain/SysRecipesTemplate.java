package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysRecipesTemplate {
    Long id;

    String name;

    Long nutritionistId;

    String nutritionist;

    Long nutriAssisId;

    String nutriAssis;

    Long planId;

    String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    String updateBy;

    String createBy;

    Long recipesId;

    Integer reviewStatus;
}
