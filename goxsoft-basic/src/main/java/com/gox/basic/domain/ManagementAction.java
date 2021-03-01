package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 管理活动对象 management_action
 *
 * @author gox
 * @date 2020-12-29
 */
public class ManagementAction extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long actionIdentifier;

    /**
     * 照片ID
     */
    @Excel(name = "照片ID")
    private Long imageId;

    /**
     * 管理行为
     */
    @Excel(name = "管理行为")
    private String managementActivity;

    /**
     * 管理时间
     */
    @Excel(name = "管理时间")
    private String actionTime;

    /**
     * 关联实体标识符
     */
    @Excel(name = "关联实体标识符")
    private Long relatedEntitiesIdentifier;

    /**
     * 管理活动描述
     */
    @Excel(name = "管理活动描述")
    private String managementActionDescription;

    public void setActionIdentifier(Long actionIdentifier) {
        this.actionIdentifier = actionIdentifier;
    }

    public Long getActionIdentifier() {
        return actionIdentifier;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setManagementActivity(String managementActivity) {
        this.managementActivity = managementActivity;
    }

    public String getManagementActivity() {
        return managementActivity;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setRelatedEntitiesIdentifier(Long relatedEntitiesIdentifier) {
        this.relatedEntitiesIdentifier = relatedEntitiesIdentifier;
    }

    public Long getRelatedEntitiesIdentifier() {
        return relatedEntitiesIdentifier;
    }

    public void setManagementActionDescription(String managementActionDescription) {
        this.managementActionDescription = managementActionDescription;
    }

    public String getManagementActionDescription() {
        return managementActionDescription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("actionIdentifier", getActionIdentifier())
                .append("imageId", getImageId())
                .append("managementActivity", getManagementActivity())
                .append("actionTime", getActionTime())
                .append("relatedEntitiesIdentifier", getRelatedEntitiesIdentifier())
                .append("managementActionDescription", getManagementActionDescription())
                .toString();
    }
}
