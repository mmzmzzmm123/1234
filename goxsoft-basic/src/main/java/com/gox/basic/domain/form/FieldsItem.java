package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;

import java.io.Serializable;
import java.util.List;

public class FieldsItem implements Serializable {
    @AutoId
    @JSONField(serialize = false)
    private Long id;
    @JSONField(serialize = false)
    private Long formId;
    @JSONField(name = "listType")
    private String listType;

    @JSONField(name = "__config__")
    private Config config;

    @JSONField(name = "autoUpload")
    private boolean autoUpload;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "multiple")
    private boolean multiple;

    @JSONField(name = "__vModel__")
    private String vModel;

    @JSONField(name = "action")
    private String action;

    @JSONField(name = "disabled")
    private boolean disabled;

    @JSONField(name = "__slot__")
    private Slot slot;

    @JSONField(name = "accept")
    private String accept;

    @JSONField(name = "colorFormat")
    private String colorFormat;

    @JSONField(name = "size")
    private String size;

    @JSONField(name = "showAlpha")
    private boolean showAlpha;

    @JSONField(name = "showText")
    private boolean showText;

    @JSONField(name = "showScore")
    private boolean showScore;

    @JSONField(name = "max")
    private int max;

    @JSONField(name = "allowHalf")
    private boolean allowHalf;

    @JSONField(name = "style")
    private Style style;

    @JSONField(name = "clearable")
    private boolean clearable;

    @JSONField(name = "rangeSeparator")
    private String rangeSeparator;

    @JSONField(name = "readonly")
    private boolean readonly;

    @JSONField(name = "startPlaceholder")
    private String startPlaceholder;

    @JSONField(name = "format")
    private String format;

    @JSONField(name = "valueFormat")
    private String valueFormat;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "endPlaceholder")
    private String endPlaceholder;

    @JSONField(name = "placeholder")
    private String placeholder;

    @JSONField(name = "isRange")
    private boolean isRange;

    @JSONField(name = "pickerOptions")
    private PickerOptions pickerOptions;

    @JSONField(name = "min")
    private int min;

    @JSONField(name = "showStops")
    private boolean showStops;

    @JSONField(name = "range")
    private boolean range;

    @JSONField(name = "step")
    private int step;

    @JSONField(name = "activeText")
    private String activeText;

    @JSONField(name = "activeColor")
    private String activeColor;

    @JSONField(name = "inactiveText")
    private String inactiveText;

    @JSONField(name = "activeValue")
    private boolean activeValue;

    @JSONField(name = "inactiveValue")
    private boolean inactiveValue;

    @JSONField(name = "inactiveColor")
    private String inactiveColor;

    @JSONField(name = "filterable")
    private boolean filterable;

    @JSONField(name = "showAllLevels")
    private boolean showAllLevels;

    @JSONField(name = "options")
    private List<OptionsItem> options;

    @JSONField(name = "separator")
    private String separator;

    @JSONField(name = "props")
    private Props props;

    @JSONField(name = "stepStrictly")
    private boolean stepStrictly;

    @JSONField(name = "precision")
    private int precision;

    @JSONField(name = "controlsPosition")
    private String controlsPosition;

    @JSONField(name = "showWordLimit")
    private boolean showWordLimit;

    @JSONField(name = "prefixIcon")
    private String prefixIcon;

    @JSONField(name = "suffixIcon")
    private String suffixIcon;

    @JSONField(name = "showPassword")
    private boolean showPassword;

    @JSONField(name = "autosize")
    private Autosize autosize;

    @JSONField(name = "maxlength")
    private String maxlength;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public void setvModel(String vModel) {
        this.vModel = vModel;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public void setColorFormat(String colorFormat) {
        this.colorFormat = colorFormat;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setShowAlpha(boolean showAlpha) {
        this.showAlpha = showAlpha;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public void setShowScore(boolean showScore) {
        this.showScore = showScore;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setAllowHalf(boolean allowHalf) {
        this.allowHalf = allowHalf;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setClearable(boolean clearable) {
        this.clearable = clearable;
    }

    public void setRangeSeparator(String rangeSeparator) {
        this.rangeSeparator = rangeSeparator;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public void setStartPlaceholder(String startPlaceholder) {
        this.startPlaceholder = startPlaceholder;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setValueFormat(String valueFormat) {
        this.valueFormat = valueFormat;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEndPlaceholder(String endPlaceholder) {
        this.endPlaceholder = endPlaceholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setRange(boolean range) {
        isRange = range;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setActiveText(String activeText) {
        this.activeText = activeText;
    }

    public void setActiveColor(String activeColor) {
        this.activeColor = activeColor;
    }

    public void setInactiveText(String inactiveText) {
        this.inactiveText = inactiveText;
    }

    public void setActiveValue(boolean activeValue) {
        this.activeValue = activeValue;
    }

    public void setInactiveValue(boolean inactiveValue) {
        this.inactiveValue = inactiveValue;
    }

    public void setInactiveColor(String inactiveColor) {
        this.inactiveColor = inactiveColor;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public void setShowAllLevels(boolean showAllLevels) {
        this.showAllLevels = showAllLevels;
    }

    public void setOptions(List<OptionsItem> options) {
        this.options = options;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setProps(Props props) {
        this.props = props;
    }

    public void setStepStrictly(boolean stepStrictly) {
        this.stepStrictly = stepStrictly;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setControlsPosition(String controlsPosition) {
        this.controlsPosition = controlsPosition;
    }

    public void setShowWordLimit(boolean showWordLimit) {
        this.showWordLimit = showWordLimit;
    }

    public void setPrefixIcon(String prefixIcon) {
        this.prefixIcon = prefixIcon;
    }

    public void setSuffixIcon(String suffixIcon) {
        this.suffixIcon = suffixIcon;
    }

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
    }

    public void setAutosize(Autosize autosize) {
        this.autosize = autosize;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public void setPickerOptions(PickerOptions pickerOptions) {
        this.pickerOptions = pickerOptions;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setShowStops(boolean showStops) {
        this.showStops = showStops;
    }

    public String getListType() {
        return listType;
    }

    public Config getConfig() {
        return config;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public String getName() {
        return name;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public String getVModel() {
        return vModel;
    }

    public String getAction() {
        return action;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public Slot getSlot() {
        return slot;
    }

    public String getAccept() {
        return accept;
    }

    public String getColorFormat() {
        return colorFormat;
    }

    public String getSize() {
        return size;
    }

    public boolean isShowAlpha() {
        return showAlpha;
    }

    public boolean isShowText() {
        return showText;
    }

    public boolean isShowScore() {
        return showScore;
    }

    public int getMax() {
        return max;
    }

    public boolean isAllowHalf() {
        return allowHalf;
    }

    public Style getStyle() {
        return style;
    }

    public boolean isClearable() {
        return clearable;
    }

    public String getRangeSeparator() {
        return rangeSeparator;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public String getStartPlaceholder() {
        return startPlaceholder;
    }

    public String getFormat() {
        return format;
    }

    public String getValueFormat() {
        return valueFormat;
    }

    public String getType() {
        return type;
    }

    public String getEndPlaceholder() {
        return endPlaceholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public boolean isIsRange() {
        return isRange;
    }

    public PickerOptions getPickerOptions() {
        return pickerOptions;
    }

    public int getMin() {
        return min;
    }

    public boolean isShowStops() {
        return showStops;
    }

    public boolean isRange() {
        return range;
    }

    public int getStep() {
        return step;
    }

    public String getActiveText() {
        return activeText;
    }

    public String getActiveColor() {
        return activeColor;
    }

    public String getInactiveText() {
        return inactiveText;
    }

    public boolean isActiveValue() {
        return activeValue;
    }

    public boolean isInactiveValue() {
        return inactiveValue;
    }

    public String getInactiveColor() {
        return inactiveColor;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public boolean isShowAllLevels() {
        return showAllLevels;
    }

    public List<OptionsItem> getOptions() {
        return options;
    }

    public String getSeparator() {
        return separator;
    }

    public Props getProps() {
        return props;
    }

    public boolean isStepStrictly() {
        return stepStrictly;
    }

    public int getPrecision() {
        return precision;
    }

    public String getControlsPosition() {
        return controlsPosition;
    }

    public boolean isShowWordLimit() {
        return showWordLimit;
    }

    public String getPrefixIcon() {
        return prefixIcon;
    }

    public String getSuffixIcon() {
        return suffixIcon;
    }

    public boolean isShowPassword() {
        return showPassword;
    }

    public Autosize getAutosize() {
        return autosize;
    }

    public String getMaxlength() {
        return maxlength;
    }
}