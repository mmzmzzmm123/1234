package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;

import java.io.Serializable;
import java.util.List;


public class Config implements Serializable {
    @AutoId
    @JSONField(serialize = false)
    private Long id;
    @JSONField(serialize = false)
    private Long itemId;
    @JSONField(name = "formId")
    private int formId;

    @JSONField(name = "buttonText")
    private String buttonText;

    @JSONField(name = "showTip")
    private boolean showTip;

    @JSONField(name = "document")
    private String document;

    @JSONField(name = "label")
    private String label;

    @JSONField(name = "showLabel")
    private boolean showLabel;

    @JSONField(name = "required")
    private boolean required;

    @JSONField(name = "renderKey")
    private String renderKey;

    @JSONField(name = "layout")
    private String layout;

    @JSONField(name = "tagIcon")
    private String tagIcon;

    @JSONField(name = "fileSize")
    private int fileSize;

    @JSONField(name = "changeTag")
    private boolean changeTag;

    @JSONField(name = "sizeUnit")
    private String sizeUnit;

    @JSONField(name = "tag")
    private String tag;

    @JSONField(name = "regList")
    private List<RegListItem> regList;

    @JSONField(name = "span")
    private int span;

    @JSONField(name = "defaultValue")
    private String defaultValue;

    @JSONField(name = "border")
    private boolean border;

    @JSONField(name = "optionType")
    private String optionType;

    @JSONField(name = "method")
    private String method;

    @JSONField(name = "dataType")
    private String dataType;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "dataKey")
    private String dataKey;

    @JSONField(name = "labelWidth")
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

    public int getFormId() {
        return formId;
    }

    public String getButtonText() {
        return buttonText;
    }

    public boolean isShowTip() {
        return showTip;
    }

    public String getDocument() {
        return document;
    }

    public String getLabel() {
        return label;
    }

    public boolean isShowLabel() {
        return showLabel;
    }

    public boolean isRequired() {
        return required;
    }

    public String getRenderKey() {
        return renderKey;
    }

    public String getLayout() {
        return layout;
    }

    public String getTagIcon() {
        return tagIcon;
    }

    public int getFileSize() {
        return fileSize;
    }

    public boolean isChangeTag() {
        return changeTag;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public String getTag() {
        return tag;
    }

    public List<RegListItem> getRegList() {
        return regList;
    }

    public int getSpan() {
        return span;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isBorder() {
        return border;
    }

    public String getOptionType() {
        return optionType;
    }

    public String getMethod() {
        return method;
    }

    public String getDataType() {
        return dataType;
    }

    public String getUrl() {
        return url;
    }

    public String getDataKey() {
        return dataKey;
    }

    public int getLabelWidth() {
        return labelWidth;
    }
}