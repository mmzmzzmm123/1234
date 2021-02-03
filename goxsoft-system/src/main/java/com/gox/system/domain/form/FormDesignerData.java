package com.gox.system.domain.form;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Objects;
import com.gox.common.plugin.AutoId;

public class FormDesignerData{
	@AutoId
	@JSONField(serialize = false)
	private Long id;
	@JSONField(name = "formRef")
	private String formRef;
	@JSONField(name = "gutter")
	private int gutter;

	@JSONField(name = "size")
	private String size;

	@JSONField(name = "formRules")
	private String formRules;

	@JSONField(name = "labelPosition")
	private String labelPosition;

	@JSONField(name = "formBtns")
	private boolean formBtns;

	@JSONField(name = "labelWidth")
	private int labelWidth;

	@JSONField(name = "disabled")
	private boolean disabled;

	@JSONField(name = "formModel")
	private String formModel;

	@JSONField(name = "fields")
	private List<FieldsItem> fields;

	@JSONField(name = "span")
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FormDesignerData)) return false;
		FormDesignerData that = (FormDesignerData) o;
		return getGutter() == that.getGutter() &&
				isFormBtns() == that.isFormBtns() &&
				getLabelWidth() == that.getLabelWidth() &&
				isDisabled() == that.isDisabled() &&
				getSpan() == that.getSpan() &&
				Objects.equal(getId(), that.getId()) &&
				Objects.equal(getFormRef(), that.getFormRef()) &&
				Objects.equal(getSize(), that.getSize()) &&
				Objects.equal(getFormRules(), that.getFormRules()) &&
				Objects.equal(getLabelPosition(), that.getLabelPosition()) &&
				Objects.equal(getFormModel(), that.getFormModel()) &&
				Objects.equal(getFields(), that.getFields());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId(), getFormRef(), getGutter(), getSize(), getFormRules(), getLabelPosition(), isFormBtns(), getLabelWidth(), isDisabled(), getFormModel(), getFields(), getSpan());
	}
}