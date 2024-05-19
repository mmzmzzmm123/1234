package com.onethinker.bk.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.onethinker.bk.enums.CommentTypeEnum;
import com.ruoyi.common.core.domain.AjaxResult;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * @author yyq
 */
@Data
public class CommentVO {

    private Integer id;

    @NotNull(message = "评论来源标识不能为空")
    private Long source;

    /**
     * 评论来源类型
     */
    @NotBlank(message = "评论来源类型不能为空")
    private String type;

    /**
     * 层主的parentCommentId是0，回复的parentCommentId是层主的id
     */
    private Long parentCommentId;

    /**
     * 层主的parentUserId是null，回复的parentUserId是被回复的userId
     */
    private Long parentUserId;

    private Long userId;

    private Integer likeCount;

    @NotBlank(message = "评论内容不能为空")
    private String commentContent;

    private String commentInfo;

    /**
     * 子评论必须传评论楼层ID
     */
    private Long floorCommentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 需要查询封装
    private Page childComments;

    private String parentUsername;

    private String username;

    private String avatar;

    public void existsPrams() {
        String content = removeHtml(commentContent);
        if (!StringUtils.hasText(content)) {
            throw new RuntimeException("评论内容不合法！");
        }
        commentContent = content;

        if (CommentTypeEnum.getSysConfigKeyEnumByCode(type) == null) {
            throw new RuntimeException("评论来源类型不存在！");
        }
    }

    public static String removeHtml(String content) {
        return content.replace("<", "《").replace(">", "》");
    }
}
