package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PickerOptions{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long itemId;
	@JsonProperty("selectableRange")
	private String selectableRange;

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

	public void setSelectableRange(String selectableRange) {
		this.selectableRange = selectableRange;
	}

	public String getSelectableRange(){
		return selectableRange;
	}
}