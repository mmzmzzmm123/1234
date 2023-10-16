package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomPackage;
import com.ruoyi.office.domain.TRoomPrice;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoomWxVo extends TRoom {
    private List<TRoomPrice> priceList;

    private RoomAvailablePeriod period;

    private List<TRoomPackage> packageList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date estEndTime;
}
