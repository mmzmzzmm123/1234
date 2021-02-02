package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Style{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long itemId;

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

	@JsonProperty("width")
	private String width;

	public String getWidth(){
		return width;
	}
}