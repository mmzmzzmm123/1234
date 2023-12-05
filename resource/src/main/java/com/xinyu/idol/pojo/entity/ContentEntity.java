package com.xinyu.idol.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.xinyu.idol.common.utils.sign.Md5Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 资源冗余大表
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
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


    private String osskeyList;


    private String osskeyListSha1;

    private String generalTag;



    /**
     * 生成标识用来做环境间状态对比
     */
    public void generateIdentifier(){
        String identifierStr=fileName+displayName+description+maximumCompatibility+minimumCompatibility
                +classification1+classification2+classification3+classification4+specialTag+minimumCompatibility+iconOsskey+pakOsskey+
                isTest+isHide+pakSha1+jsonOsskey+jsonSha1+sort+path+osskeyList+generalTag;
       this.identifier= Md5Utils.hash(identifierStr);
    }
}
