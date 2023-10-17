package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class RoomEquipeOpenReq {

    private Long roomId;
    private String equipType;//门禁	door    电灯	light    空调	aircondition

}

