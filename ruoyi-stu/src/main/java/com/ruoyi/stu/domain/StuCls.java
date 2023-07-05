package com.ruoyi.stu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 班级年级参数对象 stu_cls
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuCls extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long clsId;

    /** 班级 */
    @Excel(name = "班级")
    private String clsName;

    /** 年级 */
    @Excel(name = "年级")
    private String clsYear;
    /**
     * 班级人数
     */
    private int stuNum;
    /*
    已完成提交材料数量
     */
    private int mNum;

    /**
     * tea
     * @return
     */
    private Teacher teacher;

    /**
     *
     * status
     * @return
     */
    private int clsType;

}
