package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;


public class Autosize{
	@AutoId
	@JSONField(serialize = false)
	private Long id;
	@JSONField(serialize = false)
	private Long itemId;
	@JSONField(name = "minRows")
	private int minRows;

	@JSONField(name = "maxRows")
	private int maxRows;

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

	public void setMinRows(int minRows) {
		this.minRows = minRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getMinRows(){
		return minRows;
	}

	public int getMaxRows(){
		return maxRows;
	}
}