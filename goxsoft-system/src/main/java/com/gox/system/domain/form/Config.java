package com.gox.system.domain.form;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Config{
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long itemId;
	@JsonProperty("formId")
	private int formId;

	@JsonProperty("buttonText")
	private String buttonText;

	@JsonProperty("showTip")
	private boolean showTip;

	@JsonProperty("document")
	private String document;

	@JsonProperty("label")
	private String label;

	@JsonProperty("showLabel")
	private boolean showLabel;

	@JsonProperty("required")
	private boolean required;

	@JsonProperty("renderKey")
	private String renderKey;

	@JsonProperty("layout")
	private String layout;

	@JsonProperty("tagIcon")
	private String tagIcon;

	@JsonProperty("fileSize")
	private int fileSize;

	@JsonProperty("changeTag")
	private boolean changeTag;

	@JsonProperty("sizeUnit")
	private String sizeUnit;

	@JsonProperty("tag")
	private String tag;

	@JsonProperty("regList")
	private List<RegListItem> regList;

	@JsonProperty("span")
	private int span;

	@JsonProperty("defaultValue")
	private String defaultValue;

	@JsonProperty("border")
	private boolean border;

	@JsonProperty("optionType")
	private String optionType;

	@JsonProperty("method")
	private String method;

	@JsonProperty("dataType")
	private String dataType;

	@JsonProperty("url")
	private String url;

	@JsonProperty("dataKey")
	private String dataKey;

	@JsonProperty("labelWidth")
	private int labelWidth;

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

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public void setShowTip(boolean showTip) {
		this.showTip = showTip;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setRenderKey(String renderKey) {
		this.renderKey = renderKey;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setTagIcon(String tagIcon) {
		this.tagIcon = tagIcon;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public void setChangeTag(boolean changeTag) {
		this.changeTag = changeTag;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setRegList(List<RegListItem> regList) {
		this.regList = regList;
	}

	public void setSpan(int span) {
		this.span = span;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}

	public int getFormId(){
		return formId;
	}

	public String getButtonText(){
		return buttonText;
	}

	public boolean isShowTip(){
		return showTip;
	}

	public String getDocument(){
		return document;
	}

	public String getLabel(){
		return label;
	}

	public boolean isShowLabel(){
		return showLabel;
	}

	public boolean isRequired(){
		return required;
	}

	public String getRenderKey(){
		return renderKey;
	}

	public String getLayout(){
		return layout;
	}

	public String getTagIcon(){
		return tagIcon;
	}

	public int getFileSize(){
		return fileSize;
	}

	public boolean isChangeTag(){
		return changeTag;
	}

	public String getSizeUnit(){
		return sizeUnit;
	}

	public String getTag(){
		return tag;
	}

	public List<RegListItem> getRegList(){
		return regList;
	}

	public int getSpan(){
		return span;
	}

	public String getDefaultValue(){
		return defaultValue;
	}

	public boolean isBorder(){
		return border;
	}

	public String getOptionType(){
		return optionType;
	}

	public String getMethod(){
		return method;
	}

	public String getDataType(){
		return dataType;
	}

	public String getUrl(){
		return url;
	}

	public String getDataKey(){
		return dataKey;
	}

	public int getLabelWidth(){
		return labelWidth;
	}
}