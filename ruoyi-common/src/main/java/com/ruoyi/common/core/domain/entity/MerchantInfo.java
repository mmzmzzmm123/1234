package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商家信息表
 * @TableName t_merchant_info
 */
@TableName(value ="t_merchant_info")
@Data
public class MerchantInfo implements Serializable {
    /**
     * 商家ID
     */
    @TableId
    private String merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商家类型 0-普通 1-代理 2-运营
     */
    private Integer merchantType;

    /**
     * 上级商家ID
     */
    private String plMerchantId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}