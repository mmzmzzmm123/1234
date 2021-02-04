package com.stdiet.custom.dto.response;

import com.stdiet.common.annotation.Excel;
import lombok.Data;

@Data
public class ExportFanStatisticsResponse {

    //序号
    @Excel(name = "序号")
    private Integer id;

    //销售姓名
    @Excel(name = "销售姓名")
    private String saleName;

    //账号
    @Excel(name = "账号")
    private String account;

    //微信号
    @Excel(name = "微信号")
    private String wxAccount;

    //进粉量、
    @Excel(name = "进粉量")
    private Integer fanNum;
}
