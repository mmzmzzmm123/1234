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
 * 子竞赛信息表
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sub_contest")
public class SubContest extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子竞赛ID
     */
      @TableId(value = "sub_contest_id", type = IdType.AUTO)
    private Long subContestId;

    /**
     * 竞赛ID
     */
    private Long contestId;

    /**
     * 竞赛名称
     */
    private String name;

    /**
     * 竞赛通知网址
     */
    private String url;

    /**
     * 附件上传地址
     */
    private String attachmentsUrl;

    /**
     * 年度
     */
    private Integer year;

    /**
     * 等级：0校赛、1省赛/区域赛、2国赛/国际赛
     */
    private String rank;

    /**
     * 竞赛日期
     */
    private LocalDateTime competitionDate;

    /**
     * 报名费用
     */
    private Integer fee;



}
