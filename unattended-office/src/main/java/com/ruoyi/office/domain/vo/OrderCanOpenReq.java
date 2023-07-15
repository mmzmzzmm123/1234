package com.ruoyi.office.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class OrderCanOpenReq extends BaseEntity {

    private Long storeId;
    private Long roomId;
    private String keyword;

}
