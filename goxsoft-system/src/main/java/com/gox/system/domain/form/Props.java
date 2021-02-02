package com.gox.system.domain.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Props{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long itemId;
	@JsonIgnore
	private Long proId;
	@JsonProperty("props")
	private Props props;

	@JsonProperty("children")
	private String children;

	@JsonProperty("multiple")
	private boolean multiple;

	@JsonProperty("label")
	private String label;

	@JsonProperty("value")
	private String value;

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

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

	public void setProps(Props props) {
		this.props = props;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Props getProps(){
		return props;
	}

	public String getChildren(){
		return children;
	}

	public boolean isMultiple(){
		return multiple;
	}

	public String getLabel(){
		return label;
	}

	public String getValue(){
		return value;
	}
}