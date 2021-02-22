package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;

import java.io.Serializable;
import java.util.List;

public class Slot implements Serializable {
	@AutoId
	@JSONField(serialize = false)
	private Long id;
	@JSONField(serialize = false)
	private Long itemId;
	@JSONField(name = "listType")
	private boolean listType;

	@JSONField(name = "options")
	private List<OptionsItem> options;

	@JSONField(name = "prepend")
	private String prepend;

	@JSONField(name = "append")
	private String append;

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

	public void setListType(boolean listType) {
		this.listType = listType;
	}

	public void setOptions(List<OptionsItem> options) {
		this.options = options;
	}

	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}

	public void setAppend(String append) {
		this.append = append;
	}

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