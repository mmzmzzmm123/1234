package com.gox.system.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数字签名对象 digital_signature
 * 
 * @author gox
 * @date 2020-12-29
 */
public class DigitalSignature extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String signatureFormatDescription;

    /** 签名格式描述 */
    @Excel(name = "签名格式描述")
    private String signatureDate;

    /** 签名者 */
    @Excel(name = "签名者")
    private String signer;

    /** 签名 */
    @Excel(name = "签名")
    private String signature;

    /** 证书 */
    @Excel(name = "证书")
    private String certificate;

    /** 证书引证 */
    @Excel(name = "证书引证")
    private String certificateReference;

    /** 签名算法 */
    @Excel(name = "签名算法")
    private String signatureAlgorithm;

    /** 签名格式描述 */
    @Excel(name = "签名格式描述")
    private Long metadataId;

    /** $column.columnComment */
    @Excel(name = "签名格式描述")
    private Long eleId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSignatureFormatDescription(String signatureFormatDescription) 
    {
        this.signatureFormatDescription = signatureFormatDescription;
    }

    public String getSignatureFormatDescription() 
    {
        return signatureFormatDescription;
    }
    public void setSignatureDate(String signatureDate) 
    {
        this.signatureDate = signatureDate;
    }

    public String getSignatureDate() 
    {
        return signatureDate;
    }
    public void setSigner(String signer) 
    {
        this.signer = signer;
    }

    public String getSigner() 
    {
        return signer;
    }
    public void setSignature(String signature) 
    {
        this.signature = signature;
    }

    public String getSignature() 
    {
        return signature;
    }
    public void setCertificate(String certificate) 
    {
        this.certificate = certificate;
    }

    public String getCertificate() 
    {
        return certificate;
    }
    public void setCertificateReference(String certificateReference) 
    {
        this.certificateReference = certificateReference;
    }

    public String getCertificateReference() 
    {
        return certificateReference;
    }
    public void setSignatureAlgorithm(String signatureAlgorithm) 
    {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getSignatureAlgorithm() 
    {
        return signatureAlgorithm;
    }
    public void setMetadataId(Long metadataId) 
    {
        this.metadataId = metadataId;
    }

    public Long getMetadataId() 
    {
        return metadataId;
    }
    public void setEleId(Long eleId) 
    {
        this.eleId = eleId;
    }

    public Long getEleId() 
    {
        return eleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("signatureFormatDescription", getSignatureFormatDescription())
            .append("signatureDate", getSignatureDate())
            .append("signer", getSigner())
            .append("signature", getSignature())
            .append("certificate", getCertificate())
            .append("certificateReference", getCertificateReference())
            .append("signatureAlgorithm", getSignatureAlgorithm())
            .append("metadataId", getMetadataId())
            .append("eleId", getEleId())
            .toString();
    }
}
