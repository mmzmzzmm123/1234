package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/26/026 16:25
 * @Description :
 */
@Data
public class MerchantInfoVO {
    @ApiModelProperty("商家ID")
    private String merchantId;

    @ApiModelProperty("商家名称")
    private String merchantName;

    @ApiModelProperty("商家类型 0-普通 1-代理 2-运营")
    private Integer merchantType;

    @ApiModelProperty("上级商家ID")
    private String plMerchantId;

    @ApiModelProperty("商家创建时间")
    private Date createTime;

    @ApiModelProperty("账户总金额")
    private Long totalAmount;

    @ApiModelProperty("可用余额")
    private Long availableAmount;

    @ApiModelProperty("锁定金额")
    private Long lockAmount;
    

}
