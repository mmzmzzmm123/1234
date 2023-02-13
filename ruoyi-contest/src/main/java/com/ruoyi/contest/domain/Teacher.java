package com.ruoyi.contest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 教师信息表
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_teacher")
public class Teacher extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师ID
     */
      @TableId(value = "teacher_id", type = IdType.AUTO)
    private Long teacherId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别：0男、1女
     */
    private String gender;

    /**
     * 职称：0教授、1副教授、2讲师、3助教、4高级实验师
     */
    private String professional;

    /**
     * 职务：0院长、1书记、2副院长、3副书记、4教师、5行政人员
     */
    private String post;

    /**
     * 研究方向
     */
    private String research;

    /**
     * 简介
     */
    private String brief;



}
