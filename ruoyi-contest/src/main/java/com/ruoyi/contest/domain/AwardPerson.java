package com.ruoyi.contest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 参赛人员信息表
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_award_person")
public class AwardPerson extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参赛人员信息ID
     */
      @TableId(value = "award_person_id", type = IdType.AUTO)
    private Long awardPersonId;

    /**
     * 获奖ID
     */
    private Long awardId;

    /**
     * 参赛人员ID
     */
    private Long personId;

    /**
     * 参赛人员类型：0教师、1学生
     */
    private String personType;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 贡献度值：1~100的整数，默认根据同类人员的数量求平均
     */
    private Integer conDegree;

    /**
     * 该人员在竞赛过程的工作内容
     */
    private String workContent;



}
