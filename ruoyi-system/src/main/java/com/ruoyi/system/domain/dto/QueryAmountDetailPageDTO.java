package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/26/026 9:31
 * @Description :
 */
@Data
public class QueryAmountDetailPageDTO extends PageBaseDTO {

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;

    private String merchantId;

    @ApiModelProperty("操作类型 1-划拨 2-消费 3-退款 4-扣款 5-回收")
    private Integer operationType;

    @ApiModelProperty("资金类型 1-支出 2-收入")
    private Integer amountType;


}
