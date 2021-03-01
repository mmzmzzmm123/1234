package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 备用元数据对象 metadata_reserve
 *
 * @author gox
 * @date 2021-01-13
 */
public class MetadataReserve extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f1;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f2;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f3;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f4;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f5;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f6;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f7;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f8;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f9;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f10;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f11;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f12;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f13;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f14;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String f15;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF1() {
        return f1;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF2() {
        return f2;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF3() {
        return f3;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    public String getF4() {
        return f4;
    }

    public void setF5(String f5) {
        this.f5 = f5;
    }

    public String getF5() {
        return f5;
    }

    public void setF6(String f6) {
        this.f6 = f6;
    }

    public String getF6() {
        return f6;
    }

    public void setF7(String f7) {
        this.f7 = f7;
    }

    public String getF7() {
        return f7;
    }

    public void setF8(String f8) {
        this.f8 = f8;
    }

    public String getF8() {
        return f8;
    }

    public void setF9(String f9) {
        this.f9 = f9;
    }

    public String getF9() {
        return f9;
    }

    public void setF10(String f10) {
        this.f10 = f10;
    }

    public String getF10() {
        return f10;
    }

    public void setF11(String f11) {
        this.f11 = f11;
    }

    public String getF11() {
        return f11;
    }

    public void setF12(String f12) {
        this.f12 = f12;
    }

    public String getF12() {
        return f12;
    }

    public void setF13(String f13) {
        this.f13 = f13;
    }

    public String getF13() {
        return f13;
    }

    public void setF14(String f14) {
        this.f14 = f14;
    }

    public String getF14() {
        return f14;
    }

    public void setF15(String f15) {
        this.f15 = f15;
    }

    public String getF15() {
        return f15;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("f1", getF1())
                .append("f2", getF2())
                .append("f3", getF3())
                .append("f4", getF4())
                .append("f5", getF5())
                .append("f6", getF6())
                .append("f7", getF7())
                .append("f8", getF8())
                .append("f9", getF9())
                .append("f10", getF10())
                .append("f11", getF11())
                .append("f12", getF12())
                .append("f13", getF13())
                .append("f14", getF14())
                .append("f15", getF15())
                .toString();
    }
}
