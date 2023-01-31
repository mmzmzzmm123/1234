package com.ruoyi.gauge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 咨询服务对象 psy_consulting_service
 * 
 * @author ruoyi
 * @date 2023-01-31
 */
public class PsyConsultingService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 咨询师id */
    @Excel(name = "咨询师id")
    private Long consultantId;

    /** 咨询服务id */
    @Excel(name = "咨询服务id")
    private Long serviceId;

    /** 咨询类型0不限 1面对面 2视频 3语音 */
    @Excel(name = "咨询类型0不限 1面对面 2视频 3语音")
    private Integer type;

    /** 价格 */
    @Excel(name = "价格")
    private String price;

    /** 描述 */
    @Excel(name = "描述")
    private String describe;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setServiceId(Long serviceId) 
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId() 
    {
        return serviceId;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setPrice(String price) 
    {
        this.price = price;
    }

    public String getPrice() 
    {
        return price;
    }
    public void setDescribe(String describe) 
    {
        this.describe = describe;
    }

    public String getDescribe() 
    {
        return describe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("consultantId", getConsultantId())
            .append("serviceId", getServiceId())
            .append("type", getType())
            .append("price", getPrice())
            .append("describe", getDescribe())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
