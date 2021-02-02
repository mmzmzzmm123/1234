package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OptionsItem{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long itemId;
	@JsonIgnore
	private Long optId;
	@JsonProperty("label")
	private String label;

	@JsonProperty("value")
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

	public Long getOptId() {
		return optId;
	}

	public void setOptId(Long optId) {
		this.optId = optId;
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