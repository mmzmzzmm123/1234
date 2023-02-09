package com.ruoyi.contest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2022-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tbl_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;

    private String deptName;


}
