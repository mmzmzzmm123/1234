package com.ruoyi.web.controller.tool;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.config.dymic.RedisConfigureRegistrar;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * swagger 用户测试方法
 * 
 * @author ruoyi
 */
@Api("redis动态配置管理")
@RestController
@Anonymous
@RequestMapping("/redis-conf")
public class RedisConfigureController {

	@ApiOperation("修改配置类值")
	@PostMapping("/change")
	public void change(@RequestBody ChangeDTO dto) {
		if (StringUtils.isEmpty(dto.getClassName()) || StringUtils.isEmpty(dto.getFieldName())
				|| StringUtils.isEmpty(dto.getVal())) {
			return;
		}
		RedisConfigureRegistrar.doChange(dto.getClassName(), dto.getFieldName(), dto.getVal());
	}

	@Data
	public static class ChangeDTO {
		/**
		 * 配置类 所在 的全类名
		 */
		@ApiModelProperty("配置类 所在 的全类名")
		private String className;

		/**
		 * 配置类所在的 字段名
		 */
		@ApiModelProperty("配置类所在的 字段名")
		private String fieldName;

		/**
		 * 要修改的值
		 */
		@ApiModelProperty("要修改的值")
		private String val;
	}

}
