package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Slot{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long itemId;
	@JsonProperty("list-type")
	private boolean listType;

	@JsonProperty("options")
	private List<OptionsItem> options;

	@JsonProperty("prepend")
	private String prepend;

	@JsonProperty("append")
	private String append;

	public boolean isListType(){
		return listType;
	}

	public List<OptionsItem> getOptions(){
		return options;
	}

	public String getPrepend(){
		return prepend;
	}

	public String getAppend(){
		return append;
	}
}