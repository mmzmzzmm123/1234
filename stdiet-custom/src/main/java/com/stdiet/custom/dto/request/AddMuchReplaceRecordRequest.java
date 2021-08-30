package com.stdiet.custom.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class AddMuchReplaceRecordRequest {

    //客户ID
    private Long customerId;

    @Excel(name = "客户姓名")
    private String customerName;

    @Excel(name="手机号")
    private String phone;

    /** 更换的营养师ID */
    private Long nutritionistId;

    /** 更换的售后ID */
    private Long afterSaleId;

    /** 更换的售后ID */
    private Long nutriAssisId;

    //营养师
    @Excel(name = "营养师")
    private String nutritionist;

    @Excel(name = "营养师助理")
    private String nutriAssis;

    // 售后
    @Excel(name = "售后")
    private String afterSale;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;
}
