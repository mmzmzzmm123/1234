package com.ruoyi.stu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.domain.StuMaterial;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class StuInfoMaterial extends BaseEntity {
    private Integer id ;
    private StuInfo stuInfo;
    private Integer flag ;
    private String url ;
    private List<StuMaterial> stuMaterialList;


    /** 创建者 */
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
