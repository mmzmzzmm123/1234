package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public Double getTotalAmount() {
        if (null == totalAmount) {
            return 0d;
        }
        return BigDecimal.valueOf(totalAmount).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public Double getAvailableAmount() {
        if (null == availableAmount) {
            return 0d;
        }
        return BigDecimal.valueOf(availableAmount).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public Double getLockAmount() {
        if (null == lockAmount) {
            return 0d;
        }
        return BigDecimal.valueOf(lockAmount).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue();
    }
}
