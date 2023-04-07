package com.ruoyi.course.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class LabelVO extends BaseEntity {

    /** ID */
    private Integer id;

    /** 课程编号 */
    @Excel(name = "课程ID")
    private Integer courseId;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String courseName;

    /** 课程标签 */
    @Excel(name = "课程标签")
    private Integer courseLabel;
}
