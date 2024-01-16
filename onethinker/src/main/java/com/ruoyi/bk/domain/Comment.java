package com.ruoyi.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 文章评论对象 bk_comment
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_comment")
public class Comment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 评论来源标识 */
    @Excel(name = "评论来源标识")
    private Long source;

    /** 评论来源类型 */
    @Excel(name = "评论来源类型")
    private String type;

    /** 父评论ID */
    @Excel(name = "父评论ID")
    private Long parentCommentId;

    /** 发表用户ID */
    @Excel(name = "发表用户ID")
    private Long userId;

    /** 楼层评论ID */
    @Excel(name = "楼层评论ID")
    private Long floorCommentId;

    /** 父发表用户名ID */
    @Excel(name = "父发表用户名ID")
    private Long parentUserId;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String commentContent;

    /** 评论额外信息 */
    @Excel(name = "评论额外信息")
    private String commentInfo;


}
