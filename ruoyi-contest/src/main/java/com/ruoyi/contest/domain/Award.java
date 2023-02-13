package com.ruoyi.contest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 获奖登记表
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_award")
public class Award extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 获奖ID
     */
      @TableId(value = "award_id", type = IdType.AUTO)
    private Long awardId;

    /**
     * 竞赛ID
     */
    private Long contestId;

    /**
     * 子竞赛ID
     */
    private Long subContestId;

    /**
     * 获奖日期
     */
    private LocalDateTime awardDate;

    /**
     * 获奖等级：0特等奖、1一等奖、2二等奖、3三等奖、4优秀奖
     */
    private String awardGrade;

    /**
     * 支撑材料上传地址
     */
    private String attachmentUrl;




}
