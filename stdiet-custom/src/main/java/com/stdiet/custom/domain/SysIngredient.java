package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 食材对象 sys_ingredient
 *
 * @author wonder
 * @date 2020-12-15
 */
@Data
public class SysIngredient {
    /**
     * id
     */
    private Long id;

    /**
     * 食材名称
     */
    @Excel(name = "食材名称")
    private String name;

    /**
     * 食材类别
     */
    @Excel(name = "食材类别")
    private String type;

    /**
     * 蛋白质比例
     */
    @Excel(name = "蛋白质比例")
    private BigDecimal proteinRatio;

    /**
     * 脂肪比例
     */
    @Excel(name = "脂肪比例")
    private BigDecimal fatRatio;

    /**
     * 碳水比例
     */
    @Excel(name = "碳水比例")
    private BigDecimal carbonRatio;

    /**
     * 地域
     */
    @Excel(name = "地域")
    private String area;

    /**
     * 忌口
     */
    @Excel(name = "忌口人群")
    private String notRec;

    /**
     * 推荐
     */
    @Excel(name = "推荐人群")
    private String rec;

    /**
     * 审核状态
     */
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


    private Long[] recIds;

    private Long[] notRecIds;

    /**
     * 食材信息
     */
    private String info;

    private List<SysIngredentFile> imgList;

}