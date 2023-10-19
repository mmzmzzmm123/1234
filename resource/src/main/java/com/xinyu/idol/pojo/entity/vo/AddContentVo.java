package com.xinyu.idol.pojo.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xinyu.idol.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class AddContentVo implements Serializable {
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
    private Byte isTest;

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

    //字段判空
    public void verifyFields(){
        if(StringUtils.isEmpty(this.getPath())){
            throw new RuntimeException("path字段非空");
        }
        if(StringUtils.isEmpty(this.pakOsskey)){
            throw new RuntimeException("pakOsskey字段非空");
        }

    }
}
