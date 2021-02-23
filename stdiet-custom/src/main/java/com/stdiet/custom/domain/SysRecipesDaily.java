package com.stdiet.custom.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysRecipesDaily {
    private Long id;

    private Integer numDay;

    private Date date;

    private Long recipesId;

    private Long cusId;

    private Integer reviewStatus;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    private List<SysRecipesDailyDishes> dishes;

}
