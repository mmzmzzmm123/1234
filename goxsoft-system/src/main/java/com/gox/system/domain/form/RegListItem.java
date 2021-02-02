package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegListItem{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long configId;
	@JsonProperty("pattern")
	private String pattern;

	@JsonProperty("message")
	private String message;

	public String getPattern(){
		return pattern;
	}

	public String getMessage(){
		return message;
	}
}