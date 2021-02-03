package com.gox.system.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gox.common.plugin.AutoId;

public class RegListItem{
	@AutoId
	@JSONField(serialize = false)
	private Long id;
	@JSONField(serialize = false)
	private Long configId;
	@JSONField(name = "pattern")
	private String pattern;

	@JSONField(name = "message")
	private String message;

	public String getPattern(){
		return pattern;
	}

	public String getMessage(){
		return message;
	}
}