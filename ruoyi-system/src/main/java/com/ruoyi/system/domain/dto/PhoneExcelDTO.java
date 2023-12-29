package com.ruoyi.system.domain.dto;

import com.ruoyi.common.annotation.Excel;

import lombok.Data;

@Data
public class PhoneExcelDTO {
	@Excel(name = "手机号")
	private String phone;

}
