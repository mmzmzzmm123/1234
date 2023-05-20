package com.ruoyi.stu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class BiyeForm extends BaseEntity {
    private Integer id ;
    private Integer stuId;
    private Integer materialId;
    private Integer flag ;
    private String url ;
    private String stuName ;
    private String stuCls;
    private String stuClsYear;
    private String materialName ;
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
