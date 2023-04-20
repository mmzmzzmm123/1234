package com.boot.common.core.domain.model;

import lombok.Data;

/**
 * 用户注册对象
 * 
 * @author boot
 */
@Data
public class WXRegisterBody extends LoginBody
{
	private String openId;
	private String unionId;
}
