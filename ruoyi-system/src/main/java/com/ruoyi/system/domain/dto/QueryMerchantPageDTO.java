package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 14:55
 * @Description :
 */
@Data
public class QueryMerchantPageDTO extends PageBaseDTO {

    /**
     *
     */
    @ApiModelProperty("0-普通 1-代理 2-运营")
    private Integer merchantType;

    /**
     * 父级商家ID
     */
    @ApiModelProperty(hidden = true)
    private String plMerchantId;

    @ApiModelProperty("商家ID")
    private String merchantId;

    @ApiModelProperty("商家名称")
    private String merchantName;


    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}

