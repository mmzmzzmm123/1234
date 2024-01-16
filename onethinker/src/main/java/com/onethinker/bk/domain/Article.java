package com.onethinker.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 文章对象 bk_article
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_article")
public class Article extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long sortId;

    /** 标签ID */
    @Excel(name = "标签ID")
    private Long labelId;

    /** 封面 */
    @Excel(name = "封面")
    private String articleCover;

    /** 博文标题 */
    @Excel(name = "博文标题")
    private String articleTitle;

    /** 博文内容 */
    @Excel(name = "博文内容")
    private String articleContent;

    /** 视频链接 */
    @Excel(name = "视频链接")
    private String videoUrl;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long viewCount;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 是否可见[0:否，1:是] */
    @Excel(name = "是否可见[0:否，1:是]")
    private Integer viewStatus;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 提示 */
    @Excel(name = "提示")
    private String tips;

    /** 是否推荐[0:否，1:是] */
    @Excel(name = "是否推荐[0:否，1:是]")
    private Integer recommendStatus;

    /** 是否启用评论[0:否，1:是] */
    @Excel(name = "是否启用评论[0:否，1:是]")
    private Integer commentStatus;

    /** 是否启用[0:未删除，1:已删除] */
    @Excel(name = "是否启用[0:未删除，1:已删除]")
    private Integer deleted;


}
