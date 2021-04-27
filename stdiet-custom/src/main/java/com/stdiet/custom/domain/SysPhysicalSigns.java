package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import lombok.Data;

/**
 * 体征对象 sys_physical_signs
 *
 * @author wonder
 * @date 2020-12-23
 */
@Data
public class SysPhysicalSigns {
    /**
     * id
     */
    private Long id;

    /**
     * 体征名称
     */
    @Excel(name = "体征名称")
    private String name;

    /**
     * 体征类别
     */
    private Long typeId;

    @Excel(name = "体征类别")
    private String typeName;

    private String remark;

}