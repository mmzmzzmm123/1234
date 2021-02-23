package com.stdiet.custom.domain;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 菜品对象 sys_dishes
 *
 * @author wonder
 * @date 2020-12-28
 */
@Data
public class SysDishes {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 菜品名称
     */
    @Excel(name = "菜品名称")
    private String name;

    /**
     * 菜品类型
     */
    @Excel(name = "菜品类型")
    private String type;

    /**
     * 做法
     */
    @Excel(name = "做法")
    private String methods;

    private Integer isMain;

    private String reviewStatus;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    private List<SysDishesIngredient> igdList;

}