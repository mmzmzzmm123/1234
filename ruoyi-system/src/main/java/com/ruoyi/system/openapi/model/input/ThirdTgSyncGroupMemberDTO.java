package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTgSyncGroupMemberDTO {


    @ApiModelProperty("群编号")
    private List<String> chatroomSerialNos;

}
