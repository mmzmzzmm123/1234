package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.bk.vo.CommentVO;
import com.onethinker.common.annotation.Excel;
import com.onethinker.common.constant.BkConstants;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;

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

    public void existsParams(String weiYanType) {
        if (BkConstants.WEIYAN_TYPE_FRIEND.equals(weiYanType)) {
            if (!StringUtils.hasText(content)) {
                throw new RuntimeException("微言不能为空！");
            }

            String existsContent = CommentVO.removeHtml(content);
            if (!StringUtils.hasText(existsContent)) {
                throw new RuntimeException("微言内容不合法！");
            }

        } else if (BkConstants.WEIYAN_TYPE_NEWS.equals(weiYanType)) {
            if (!StringUtils.hasText(content) || source == null) {
                throw new RuntimeException("信息不全！");
            }
            setIsPublic(Boolean.TRUE);
        } else {
            throw new RuntimeException("微言类型有误");
        }
        setType(weiYanType);

    }

}
