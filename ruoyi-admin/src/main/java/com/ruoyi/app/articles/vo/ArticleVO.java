package com.ruoyi.app.articles.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ArticleVO {
    private Long id;

    /** 文章表体 */
    @ApiModelProperty(name = "文章标题")

    private String title;

    /** 文章内容 */
    @ApiModelProperty(name = "文章内容")
    private String content;

    /** 配图 */
    @ApiModelProperty(name = "配图")
    private String image;

    /** 发帖人id */
    @ApiModelProperty(name = "发帖人id")
    private Long userId;

    /** 发帖地区id */
    @ApiModelProperty(name = "发帖地区id")
    private String regionId;

    /** 发帖地区编码 */
    @ApiModelProperty(name = "发帖地区编码")
    private String regionCode;
}
