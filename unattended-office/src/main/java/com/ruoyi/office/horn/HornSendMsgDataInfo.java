package com.ruoyi.office.horn;

import lombok.Data;

@Data
public class HornSendMsgDataInfo {
    private String tts;
    private Integer inner;
    private Integer volume;
}
