package com.ruoyi.office.horn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HornSendMsgData {

    @JsonProperty("cmd_type")
    private String cmdType;

    private HornSendMsgDataInfo info;
}
