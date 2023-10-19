package com.xinyu.idol.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.xinyu.idol.common.utils.sign.Md5Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 资源冗余大表
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
@TableName("t_content")
@ApiModel(value = "ContentEntity对象", description = "资源冗余大表")
public class ContentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String fileName;

    private String displayName;

    @ApiModelProperty("资源描述")
    private String description;

    @ApiModelProperty("资源目录")
    private String path;

    @ApiModelProperty("最小支持版本")
    private Double minimumCompatibility;

    @ApiModelProperty("最高支持版本")
    private Double maximumCompatibility;

    @ApiModelProperty("被取代资源pak_id")
    private Long replaceId;

    @ApiModelProperty("一级分类")
    private String classification1;

    @ApiModelProperty("二级分类")
    private String classification2;

    @ApiModelProperty("三级分类")
    private String classification3;

    @ApiModelProperty("四级分类")
    private String classification4;

    @ApiModelProperty("特殊标签,用来标记....")
    private String specialTag;

    @ApiModelProperty("类型")
    private String type;

    private String iconOsskey;

    private String pakOsskey;

    @ApiModelProperty("是否为测试资源,测试资源无法同步到下个环境")
    private Integer isTest;

    @ApiModelProperty("用户名id")
    private String ownerid;

    @ApiModelProperty("是否隐藏")
    private Integer isHide;

    @ApiModelProperty("被覆盖次数")
    private Integer replaceTimes;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer version;

    @ApiModelProperty("字段幂等键哈希")
    private String idemHash;

    @ApiModelProperty("资源下载时的sha1,客户端用来做更新比对校验")
    private String pakSha1;

    @ApiModelProperty("最近上传时间")
    private String currentUploadTime;

    @ApiModelProperty("pak_id资源的实际id")
    private Long guid;

    @ApiModelProperty("唯一标识,用来分配pakId的key")
    private String identifier;

    @ApiModelProperty("定制资源")
    private Integer custom;

    @ApiModelProperty("json文件的osskey")
    private String jsonOsskey;

    private String jsonSha1;

    private Integer pakFileSize;

    private Integer jsonFileSize;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getMinimumCompatibility() {
        return minimumCompatibility;
    }

    public void setMinimumCompatibility(Double minimumCompatibility) {
        this.minimumCompatibility = minimumCompatibility;
    }

    public Double getMaximumCompatibility() {
        return maximumCompatibility;
    }

    public void setMaximumCompatibility(Double maximumCompatibility) {
        this.maximumCompatibility = maximumCompatibility;
    }

    public Long getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Long replaceId) {
        this.replaceId = replaceId;
    }

    public String getClassification1() {
        return classification1;
    }

    public void setClassification1(String classification1) {
        this.classification1 = classification1;
    }

    public String getClassification2() {
        return classification2;
    }

    public void setClassification2(String classification2) {
        this.classification2 = classification2;
    }

    public String getClassification3() {
        return classification3;
    }

    public void setClassification3(String classification3) {
        this.classification3 = classification3;
    }

    public String getClassification4() {
        return classification4;
    }

    public void setClassification4(String classification4) {
        this.classification4 = classification4;
    }

    public String getSpecialTag() {
        return specialTag;
    }

    public void setSpecialTag(String specialTag) {
        this.specialTag = specialTag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIconOsskey() {
        return iconOsskey;
    }

    public void setIconOsskey(String iconOsskey) {
        this.iconOsskey = iconOsskey;
    }

    public String getPakOsskey() {
        return pakOsskey;
    }

    public void setPakOsskey(String pakOsskey) {
        this.pakOsskey = pakOsskey;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    public Integer getReplaceTimes() {
        return replaceTimes;
    }

    public void setReplaceTimes(Integer replaceTimes) {
        this.replaceTimes = replaceTimes;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getIdemHash() {
        return idemHash;
    }

    public void setIdemHash(String idemHash) {
        this.idemHash = idemHash;
    }

    public String getPakSha1() {
        return pakSha1;
    }

    public void setPakSha1(String pakSha1) {
        this.pakSha1 = pakSha1;
    }

    public String getCurrentUploadTime() {
        return currentUploadTime;
    }

    public void setCurrentUploadTime(String currentUploadTime) {
        this.currentUploadTime = currentUploadTime;
    }

    public Long getGuid() {
        return guid;
    }

    public void setGuid(Long guid) {
        this.guid = guid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getCustom() {
        return custom;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public String getJsonOsskey() {
        return jsonOsskey;
    }

    public void setJsonOsskey(String jsonOsskey) {
        this.jsonOsskey = jsonOsskey;
    }

    public String getJsonSha1() {
        return jsonSha1;
    }

    public void setJsonSha1(String jsonSha1) {
        this.jsonSha1 = jsonSha1;
    }

    public Integer getPakFileSize() {
        return pakFileSize;
    }

    public void setPakFileSize(Integer pakFileSize) {
        this.pakFileSize = pakFileSize;
    }

    public Integer getJsonFileSize() {
        return jsonFileSize;
    }

    public void setJsonFileSize(Integer jsonFileSize) {
        this.jsonFileSize = jsonFileSize;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ContentEntity{" +
            "id = " + id +
            ", fileName = " + fileName +
            ", displayName = " + displayName +
            ", description = " + description +
            ", path = " + path +
            ", minimumCompatibility = " + minimumCompatibility +
            ", maximumCompatibility = " + maximumCompatibility +
            ", replaceId = " + replaceId +
            ", classification1 = " + classification1 +
            ", classification2 = " + classification2 +
            ", classification3 = " + classification3 +
            ", classification4 = " + classification4 +
            ", specialTag = " + specialTag +
            ", type = " + type +
            ", iconOsskey = " + iconOsskey +
            ", pakOsskey = " + pakOsskey +
            ", isTest = " + isTest +
            ", ownerid = " + ownerid +
            ", isHide = " + isHide +
            ", replaceTimes = " + replaceTimes +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", version = " + version +
            ", idemHash = " + idemHash +
            ", pakSha1 = " + pakSha1 +
            ", currentUploadTime = " + currentUploadTime +
            ", guid = " + guid +
            ", identifier = " + identifier +
            ", custom = " + custom +
            ", jsonOsskey = " + jsonOsskey +
            ", jsonSha1 = " + jsonSha1 +
            ", pakFileSize = " + pakFileSize +
            ", jsonFileSize = " + jsonFileSize +
            ", sort = " + sort +
        "}";
    }

    /**
     * 生成标识用来做环境间状态对比
     */
    public void generateIdentifier(){
        String identifierStr=fileName+displayName+description+maximumCompatibility+minimumCompatibility
                +classification1+classification2+classification3+classification4+specialTag+minimumCompatibility+iconOsskey+pakOsskey+
                isTest+isHide+pakSha1+jsonOsskey+jsonSha1+sort+path;
        setIdentifier(Md5Utils.hash(identifierStr));
    }
}
