package com.ruoyi.psychology.dto;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.domain.PsyConsultPartnerItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * 咨询师入驻申请对象 psy_consult_partner
 * 
 * @author ruoyi
 * @date 2023-11-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PartnerDTO extends PsyConsultPartner implements Serializable
{
    private static final long serialVersionUID = -1705227712564097237L;

    private List<PsyConsultPartnerItem> items;

    private List<String> langList;

    /** 证件照 */
    private String cardImg;
    private List<String> cardImgs;

    /** 0-草稿,1-审核中,2-审核通过-已开通账号,3-审核通过-未开通账号,4-审核驳回 */
    private String status;

    /** 0-未缴费,1-已缴费 */
    private String pay;

}
