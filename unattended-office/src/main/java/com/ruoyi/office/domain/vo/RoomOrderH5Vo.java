package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.TStorePromotion;
import lombok.Data;

@Data
public class RoomOrderH5Vo extends TRoomOrder {
    private String storeName;
    private String roomName;
    private String payTypeName;


}
