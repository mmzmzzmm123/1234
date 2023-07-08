package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TRoomOrder;
import lombok.Data;

@Data
public class RoomOrderWxVo extends TRoomOrder {

    private String storeId;
    private String storeName;
    private String roomName;
    private String storeAdress;
    private String phone;
    private String lat;
    private String lon;

}
