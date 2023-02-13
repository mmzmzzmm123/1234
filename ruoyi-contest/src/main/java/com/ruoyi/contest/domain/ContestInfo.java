package com.ruoyi.contest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 竞赛基础信息表
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_contest_info")
public class ContestInfo extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 竞赛ID
     */
      @TableId(value = "contest_id", type = IdType.AUTO)
    private Long contestId;

    /**
     * 竞赛名称
     */
    private String name;

    /**
     * 竞赛官网
     */
    private String url;

    /**
     * 竞赛简介
     */
    private String brief;

    /**
     * 在教育部认可的57项赛事内
     */
    private String inMinistry;

    /**
     * 在教育部认可的57项赛事中的编号
     */
    private Integer ministryNo;

    /**
     * 竞赛类别：0个人、1团体、2个人与团体
     */
    private String type;

    /**
     * 竞赛等级：A~E
     */
    private String grade;

    /**
     * 竞赛频次：0每年一届、1每两年一届
     */
    private String freq;

    /**
     * 省赛/区域赛费用
     */
    private Integer fee1;

    /**
     * 国赛/决赛费用
     */
    private Integer fee2;



}
