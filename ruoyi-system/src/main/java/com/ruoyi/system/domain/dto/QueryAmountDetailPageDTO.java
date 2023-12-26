package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
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
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    private String merchantId;

    private Integer operationType;


}
