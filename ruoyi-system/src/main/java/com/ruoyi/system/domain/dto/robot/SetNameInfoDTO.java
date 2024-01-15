package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("SetNameInfoDTO")
@Data
public class SetNameInfoDTO {
    private String firstName;
    private String lastName;
    private String briefIntro;

}
