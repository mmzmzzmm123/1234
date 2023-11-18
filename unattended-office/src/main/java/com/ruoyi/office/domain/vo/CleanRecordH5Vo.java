package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TRoomCleanRecord;
import com.ruoyi.office.domain.TRoomOrder;
import lombok.Data;

@Data
public class CleanRecordH5Vo extends TRoomCleanRecord {
    private String storeName;
    private String roomName;
    private Long duration;
    private String roomStatus;
    private Long storeId;
    private String cleaner;
}
