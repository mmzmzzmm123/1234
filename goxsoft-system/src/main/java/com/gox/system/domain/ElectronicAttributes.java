package com.gox.system.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 电子文件信息对象 electronic_attributes
 * 
 * @author gox
 * @date 2020-12-29
 */
public class ElectronicAttributes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 格式信息 */
    @Excel(name = "格式信息")
    private String formatInformation;

    /** 计算机文件名 */
    @Excel(name = "计算机文件名")
    private String computerFileName;

    /** 计算机文件大小 */
    @Excel(name = "计算机文件大小")
    private String computerFileSize;

    /** 文档创建程序 */
    @Excel(name = "文档创建程序")
    private String documentCreatingApplication;

    /** 信息系统描述 */
    @Excel(name = "信息系统描述")
    private String informationSystemDescription;

    /** 数字化对象形态 */
    @Excel(name = "数字化对象形态")
    private String physicalRecordCharacteristics;

    /** 扫描分辨率 */
    @Excel(name = "扫描分辨率")
    private String scanningResolution;

    /** 扫描色彩模式 */
    @Excel(name = "扫描色彩模式")
    private String scanningColorModel;

    /** 图像压缩方案 */
    @Excel(name = "图像压缩方案")
    private String imageCompressionScheme;

    /** 图像压缩方案 */
    @Excel(name = "图像压缩方案")
    private String currentLocation;

    /** 脱机载体编号 */
    @Excel(name = "脱机载体编号")
    private String offlineMediumIdentifier;

    /** 脱机载体存址 */
    @Excel(name = "脱机载体存址")
    private String offlineMediumStorageLocation;

    /** 缩微号 */
    @Excel(name = "缩微号")
    private String microformIdentifier;

    /** $column.columnComment */
    @Excel(name = "缩微号")
    private Long metadataId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFormatInformation(String formatInformation) 
    {
        this.formatInformation = formatInformation;
    }

    public String getFormatInformation() 
    {
        return formatInformation;
    }
    public void setComputerFileName(String computerFileName) 
    {
        this.computerFileName = computerFileName;
    }

    public String getComputerFileName() 
    {
        return computerFileName;
    }
    public void setComputerFileSize(String computerFileSize) 
    {
        this.computerFileSize = computerFileSize;
    }

    public String getComputerFileSize() 
    {
        return computerFileSize;
    }
    public void setDocumentCreatingApplication(String documentCreatingApplication) 
    {
        this.documentCreatingApplication = documentCreatingApplication;
    }

    public String getDocumentCreatingApplication() 
    {
        return documentCreatingApplication;
    }
    public void setInformationSystemDescription(String informationSystemDescription) 
    {
        this.informationSystemDescription = informationSystemDescription;
    }

    public String getInformationSystemDescription() 
    {
        return informationSystemDescription;
    }
    public void setPhysicalRecordCharacteristics(String physicalRecordCharacteristics) 
    {
        this.physicalRecordCharacteristics = physicalRecordCharacteristics;
    }

    public String getPhysicalRecordCharacteristics() 
    {
        return physicalRecordCharacteristics;
    }
    public void setScanningResolution(String scanningResolution) 
    {
        this.scanningResolution = scanningResolution;
    }

    public String getScanningResolution() 
    {
        return scanningResolution;
    }
    public void setScanningColorModel(String scanningColorModel) 
    {
        this.scanningColorModel = scanningColorModel;
    }

    public String getScanningColorModel() 
    {
        return scanningColorModel;
    }
    public void setImageCompressionScheme(String imageCompressionScheme) 
    {
        this.imageCompressionScheme = imageCompressionScheme;
    }

    public String getImageCompressionScheme() 
    {
        return imageCompressionScheme;
    }
    public void setCurrentLocation(String currentLocation) 
    {
        this.currentLocation = currentLocation;
    }

    public String getCurrentLocation() 
    {
        return currentLocation;
    }
    public void setOfflineMediumIdentifier(String offlineMediumIdentifier) 
    {
        this.offlineMediumIdentifier = offlineMediumIdentifier;
    }

    public String getOfflineMediumIdentifier() 
    {
        return offlineMediumIdentifier;
    }
    public void setOfflineMediumStorageLocation(String offlineMediumStorageLocation) 
    {
        this.offlineMediumStorageLocation = offlineMediumStorageLocation;
    }

    public String getOfflineMediumStorageLocation() 
    {
        return offlineMediumStorageLocation;
    }
    public void setMicroformIdentifier(String microformIdentifier) 
    {
        this.microformIdentifier = microformIdentifier;
    }

    public String getMicroformIdentifier() 
    {
        return microformIdentifier;
    }
    public void setMetadataId(Long metadataId) 
    {
        this.metadataId = metadataId;
    }

    public Long getMetadataId() 
    {
        return metadataId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("formatInformation", getFormatInformation())
            .append("computerFileName", getComputerFileName())
            .append("computerFileSize", getComputerFileSize())
            .append("documentCreatingApplication", getDocumentCreatingApplication())
            .append("informationSystemDescription", getInformationSystemDescription())
            .append("physicalRecordCharacteristics", getPhysicalRecordCharacteristics())
            .append("scanningResolution", getScanningResolution())
            .append("scanningColorModel", getScanningColorModel())
            .append("imageCompressionScheme", getImageCompressionScheme())
            .append("currentLocation", getCurrentLocation())
            .append("offlineMediumIdentifier", getOfflineMediumIdentifier())
            .append("offlineMediumStorageLocation", getOfflineMediumStorageLocation())
            .append("microformIdentifier", getMicroformIdentifier())
            .append("metadataId", getMetadataId())
            .toString();
    }
}
