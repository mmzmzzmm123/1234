package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 微言对象 bk_wei_yan
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_wei_yan")
public class WeiYan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 微言
     */
    public static final String WEIYAN_TYPE_FRIEND = "friend";

    public static final String WEIYAN_TYPE_NEWS = "news";

    /**
     * id
     */
    private Long id;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 点赞数
     */
    @Excel(name = "点赞数")
    private Long likeCount;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 来源标识
     */
    @Excel(name = "来源标识")
    private Long source;

    /**
     * 是否公开[0:仅自己可见，1:所有人可见]
     */
    @Excel(name = "是否公开[0:仅自己可见，1:所有人可见]")
    private Boolean isPublic;


}
