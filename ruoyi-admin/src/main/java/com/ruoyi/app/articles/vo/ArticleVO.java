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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
