package com.gox.system.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gox.common.plugin.AutoId;

public class Props{
	@AutoId
	@JSONField(serialize = false)
	private Long id;
	@JSONField(serialize = false)
	private Long itemId;
	@JSONField(serialize = false)
	private Long proId;
	@JSONField(name = "props")
	private Props props;

	@JSONField(name = "children")
	private String children;

	@JSONField(name = "multiple")
	private boolean multiple;

	@JSONField(name = "label")
	private String label;

	@JSONField(name = "value")
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