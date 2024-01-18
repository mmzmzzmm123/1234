package com.ruoyi.system.domain.vo.robot;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("SetNameResourceVO")
@Data
public class SetNameResourceVO {
    @Excel(name = "名字")
    private String firstName;
    @Excel(name = "姓氏")
    private String lastName;
    @Excel(name = "个人简介")
    private String briefIntro;
}
