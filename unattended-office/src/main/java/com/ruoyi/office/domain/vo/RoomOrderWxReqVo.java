package com.ruoyi.office.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.office.domain.TRoomOrder;
import lombok.Data;

@Data
public class RoomOrderWxReqVo  extends BaseEntity {

    private Long storeId;
    private Long roomId;
    private String keyword;

}
