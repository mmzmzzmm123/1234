package com.gox.system.domain.form;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormDesignerData{
	@JsonIgnore
	private Long id;
	@JsonProperty("formRef")
	private String formRef;

	@JsonProperty("gutter")
	private int gutter;

	@JsonProperty("size")
	private String size;

	@JsonProperty("formRules")
	private String formRules;

	@JsonProperty("labelPosition")
	private String labelPosition;

	@JsonProperty("formBtns")
	private boolean formBtns;

	@JsonProperty("labelWidth")
	private int labelWidth;

	@JsonProperty("disabled")
	private boolean disabled;

	@JsonProperty("formModel")
	private String formModel;

	@JsonProperty("fields")
	private List<FieldsItem> fields;

	@JsonProperty("span")
	private int span;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFormRef(String formRef) {
		this.formRef = formRef;
	}

	public void setGutter(int gutter) {
		this.gutter = gutter;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setFormRules(String formRules) {
		this.formRules = formRules;
	}

	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}

	public void setFormBtns(boolean formBtns) {
		this.formBtns = formBtns;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setFormModel(String formModel) {
		this.formModel = formModel;
	}

	public void setFields(List<FieldsItem> fields) {
		this.fields = fields;
	}

	public void setSpan(int span) {
		this.span = span;
	}

	public String getFormRef(){
		return formRef;
	}

	public int getGutter(){
		return gutter;
	}

	public String getSize(){
		return size;
	}

	public String getFormRules(){
		return formRules;
	}

	public String getLabelPosition(){
		return labelPosition;
	}

	public boolean isFormBtns(){
		return formBtns;
	}

	public int getLabelWidth(){
		return labelWidth;
	}

	public boolean isDisabled(){
		return disabled;
	}

	public String getFormModel(){
		return formModel;
	}

	public List<FieldsItem> getFields(){
		return fields;
	}

	public int getSpan(){
		return span;
	}
}