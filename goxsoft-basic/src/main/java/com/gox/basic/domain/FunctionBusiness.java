package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 职能业务对象 function_business
 *
 * @author gox
 * @date 2020-12-29
 */
public class FunctionBusiness extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 业务类型
     */
    @Excel(name = "业务类型")
    private String businessType;

    /**
     * 业务名称
     */
    @Excel(name = "业务名称")
    private String businessName;

    /**
     * 业务开始时间
     */
    @Excel(name = "业务开始时间")
    private String businessBeginningDate;

    /**
     * 业务结束时间
     */
    @Excel(name = "业务结束时间")
    private String businessEndingDate;

    /**
     * 业务描述
     */
    @Excel(name = "业务描述")
    private String businessDescription;

    /**
     * $column.columnComment
     */
    @Excel(name = "业务描述")
    private Long imageId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessBeginningDate(String businessBeginningDate) {
        this.businessBeginningDate = businessBeginningDate;
    }

    public String getBusinessBeginningDate() {
        return businessBeginningDate;
    }

    public void setBusinessEndingDate(String businessEndingDate) {
        this.businessEndingDate = businessEndingDate;
    }

    public String getBusinessEndingDate() {
        return businessEndingDate;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("businessType", getBusinessType())
                .append("businessName", getBusinessName())
                .append("businessBeginningDate", getBusinessBeginningDate())
                .append("businessEndingDate", getBusinessEndingDate())
                .append("businessDescription", getBusinessDescription())
                .append("imageId", getImageId())
                .toString();
    }
}
