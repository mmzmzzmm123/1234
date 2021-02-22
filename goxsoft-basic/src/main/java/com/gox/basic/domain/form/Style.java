package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;

import java.io.Serializable;

public class Style implements Serializable {
	@AutoId
	@JSONField(serialize = false)
	private Long id;
	@JSONField(serialize = false)
	private Long itemId;



	@JSONField(name = "width")
	private String width;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	public String getWidth(){
		return width;
	}
}