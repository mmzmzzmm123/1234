package com.xinyu.idol.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class PageContentResp implements Serializable {

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

    private Integer isHide;

    private String generalTag;

    private Integer sort;

    private String devDiff;

    private String testDiff;

    private String preDiff;

    private String onlineDiff;

    private ClassificationRowResp classification1Row;

    private ClassificationRowResp classification2Row;

    private ClassificationRowResp classification3Row;

    private ClassificationRowResp classification4Row;

    private String osskeyList;


    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class  ClassificationRowResp implements Serializable{

        private String id;

        private String name;

        private Integer level;

//        private Long parentId;
//
//        private Integer orderNum;
//
//        private String status;
//
//        private String englishName;


   }
}
