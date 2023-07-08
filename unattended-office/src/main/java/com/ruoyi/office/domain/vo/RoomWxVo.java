package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomPrice;
import lombok.Data;

import java.util.List;

@Data
public class RoomWxVo extends TRoom {
    private List<TRoomPrice> priceList;

    private RoomAvailablePeriod period;

}
