package com.ruoyi.bookmark.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 书签管理对象 sq_bookmark   不存在的字段注解 @Transient
 *
 */
@Data
public class SqBookmarkReq
{
    private Long bookmarkId;

    private Long userid;

    /** 书签标题 */
    private String title;

    /** 书签地址 */
    private String url;

    /** 官网地址 */
    private String urls;

    /** 书签描述 */
    private String description;

    /** 图片 */
    private String image;

    /** 标签 */
    private String label;

    /** 分类ID */
    private Long menuId;

    /** 点赞数 */
    private Long zcount;

    /** 0 未删除 1表示删除 */
    private Integer idelete;

    /** 0公开显示 1隐藏显示 2好友显示 3稍后再看 */
    private Integer start;

    /** 0非星标 1星标 */
    private Integer bookmarkStar;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**tag名称串**/
    private String tagNameAll;

    /**是否稍后读 0不是 1是**/
    private Integer seeYouLater;

    private Long parentId;

    /** 书签的类型 星标 稍后看 最新书签 **/
    private String type;

    /** 排序的方式 **/
    private String sort;

    /** 搜索 **/
    private String sousuo;

    /** 标签ID **/
    private Long tagId;



    private List<Map<String,Object>> sqTags;
    public SqBookmarkReq(){
    }

    public SqBookmarkReq(String url, Long userid){
        this.url = url;
        this.userid = userid;
    }

}
