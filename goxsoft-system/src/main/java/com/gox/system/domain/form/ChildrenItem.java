package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChildrenItem{

	@JsonProperty("id")
	private int id;

	@JsonProperty("label")
	private String label;

	@JsonProperty("value")
	private int value;

	public int getId(){
		return id;
	}

	public String getLabel(){
		return label;
	}

	public int getValue(){
		return value;
	}
}