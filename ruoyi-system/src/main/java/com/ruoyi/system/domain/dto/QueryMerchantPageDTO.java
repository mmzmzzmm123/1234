package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 14:55
 * @Description :
 */
@Data
public class QueryMerchantPageDTO extends PageBaseDTO {

    /**
     * 0-普通 1-代理 2-运营
     */
    @Max(value = 2)
    @Min(value = 0)
    private Integer merchantType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 父级商家ID
     */
    private String plMerchantId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}

