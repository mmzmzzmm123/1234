package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysRecipesPlanListInfo {

    private Long id;

    private Long recipesId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Integer startNumDay;

    private Integer endNumDay;

    private List<SysRecipesPlan> menus;

    private Integer sendFlag;

    private Integer subscribed;

    private Integer status;

}
