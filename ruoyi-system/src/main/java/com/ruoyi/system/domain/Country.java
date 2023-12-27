package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * ubpm国家字典表
 * @TableName t_country
 */
@TableName(value ="t_country")
@Data
public class Country implements Serializable {
    /**
     * 国家代码
     */
    @TableId(value = "country_code")
    private String countryCode;

    /**
     * 国家
     */
    @TableField(value = "country_name")
    private String countryName;

    /**
     * 手机区号
     */
    @TableField(value = "phone_area")
    private String phoneArea;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}