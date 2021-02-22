package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;

import java.io.Serializable;

public class OptionsItem implements Serializable {
	@AutoId
	@JSONField(serialize = false)
	private Long id;
	@JSONField(serialize = false)
	private Long itemId;
	@JSONField(serialize = false)
	private Long slotId;
	@JSONField(name = "label")
	private String label;

	@JSONField(name = "value")
	private String value;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSlotId() {
		return slotId;
	}

	public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public String getLabel(){
		return label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}