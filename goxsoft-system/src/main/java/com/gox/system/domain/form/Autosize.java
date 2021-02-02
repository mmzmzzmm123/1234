package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Autosize{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long itemId;
	@JsonProperty("minRows")
	private int minRows;

	@JsonProperty("maxRows")
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