package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TRoomOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoomAvailablePeriod {
    private Long roomId;
    private Date date;
    // date 当天不可用时间段
    private List<Integer> canNotUseList;
    // date+1 不可用时间段 跨天预约
    private List<Integer> canNotUseList2;

    List<TRoomOrder> orderList;

}
