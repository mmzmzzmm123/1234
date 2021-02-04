package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;

public class OptionsItem{
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
	private int value;

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

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel(){
		return label;
	}

	public int getValue(){
		return value;
	}
}