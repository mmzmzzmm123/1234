package com.stdiet.custom.dto.request;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class SalaryRequest extends BaseEntity {

    //姓名
    @Excel(name = "姓名")
    private String name;

    //薪资部门ID
    @Excel(name = "岗位",prompt = "营养师、营养师助理、销售主管、销售、商务、策划运营、策划助理、人事、IT")
    private String salaryPostName;

    //薪资
    @Excel(name = "薪资",prompt="保留两位小数")
    private BigDecimal salary;

    //是否全勤
    @Excel(name = "是否全勤",prompt="是、否")
    private String fullWork;
}
