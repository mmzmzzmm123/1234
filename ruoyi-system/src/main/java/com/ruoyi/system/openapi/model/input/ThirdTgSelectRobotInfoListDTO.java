package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class ThirdTgSelectRobotInfoListDTO {
	/**
	 * 机器人编号
	 */
	@ApiModelProperty(value = "机器人编号")
	private List<String> robotSerialNos;

}