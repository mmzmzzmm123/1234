package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 元数据定义规则对象 metadata_rule
 *
 * @author gox
 * @date 2021-01-05
 */
public class MetadataRule extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private String num;

    /**
     * 中文名称
     */
    @Excel(name = "中文名称")
    private String nameCn;

    /**
     * 英文名称
     */
    @Excel(name = "英文名称")
    private String name;

    /**
     * 定义
     */
    @Excel(name = "定义")
    private String definition;

    /**
     * 目的
     */
    @Excel(name = "目的")
    private String purpose;

    /**
     * 约束性
     */
    @Excel(name = "约束性")
    private String constraintion;

    /**
     * 条件
     */
    @Excel(name = "条件")
    private String conditions;

    /**
     * 相关正则
     */
    @Excel(name = "相关正则")
    private String regex;

    /**
     * 可重复性
     */
    @Excel(name = "可重复性")
    private String repeatability;

    /**
     * 元素类型
     */
    @Excel(name = "元素类型")
    private String elementType;

    /**
     * 数据类型
     */
    @Excel(name = "数据类型")
    private String dataType;

    /**
     * 编码修饰体系
     */
    @Excel(name = "编码修饰体系")
    private String encode;

    /**
     * 值域
     */
    @Excel(name = "值域")
    private String valueRange;

    /**
     * 缺省值
     */
    @Excel(name = "缺省值")
    private String defaultValue;

    /**
     * 子元素
     */
    @Excel(name = "子元素")
    private String subelement;

    /**
     * 信息来源
     */
    @Excel(name = "信息来源")
    private String sourcesOfInformation;

    /**
     * 相关元素
     */
    @Excel(name = "相关元素")
    private String coherentElement;

    /**
     * 注释
     */
    @Excel(name = "注释")
    private String comments;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 长度
     */
    @Excel(name = "长度")
    private Integer len;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setConstraintion(String constraintion) {
        this.constraintion = constraintion;
    }

    public String getConstraintion() {
        return constraintion;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getConditions() {
        return conditions;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRepeatability(String repeatability) {
        this.repeatability = repeatability;
    }

    public String getRepeatability() {
        return repeatability;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementType() {
        return elementType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getEncode() {
        return encode;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
    }

    public String getValueRange() {
        return valueRange;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setSubelement(String subelement) {
        this.subelement = subelement;
    }

    public String getSubelement() {
        return subelement;
    }

    public void setSourcesOfInformation(String sourcesOfInformation) {
        this.sourcesOfInformation = sourcesOfInformation;
    }

    public String getSourcesOfInformation() {
        return sourcesOfInformation;
    }

    public void setCoherentElement(String coherentElement) {
        this.coherentElement = coherentElement;
    }

    public String getCoherentElement() {
        return coherentElement;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public Integer getLen() {
        return len;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("num", getNum())
                .append("nameCn", getNameCn())
                .append("name", getName())
                .append("definition", getDefinition())
                .append("purpose", getPurpose())
                .append("constraintion", getConstraintion())
                .append("conditions", getConditions())
                .append("regex", getRegex())
                .append("repeatability", getRepeatability())
                .append("elementType", getElementType())
                .append("dataType", getDataType())
                .append("encode", getEncode())
                .append("valueRange", getValueRange())
                .append("defaultValue", getDefaultValue())
                .append("subelement", getSubelement())
                .append("sourcesOfInformation", getSourcesOfInformation())
                .append("coherentElement", getCoherentElement())
                .append("comments", getComments())
                .append("type", getType())
                .append("len", getLen())
                .toString();
    }
}
