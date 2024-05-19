package com.onethinker.bk.vo;

import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.onethinker.bk.domain.Label;
import com.onethinker.bk.domain.Sort;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ArticleVO {

    private Long id;

    private Long userId;

    /**
     * 查询为空时，随机选择
     */
    private String articleCover;

    @NotBlank(message = "文章标题不能为空")
    private String articleTitle;

    @NotBlank(message = "文章内容不能为空")
    private String articleContent;

    private Integer viewCount;

    private Integer likeCount;

    private Boolean commentStatus;

    private Boolean recommendStatus;

    private String videoUrl;

    private String password;

    private String tips;

    private Boolean viewStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateBy;

    @NotNull(message = "文章分类不能为空")
    private Long sortId;

    @NotNull(message = "文章标签不能为空")
    private Long labelId;

    /**
     * 需要查询封装
     */
    private Integer commentCount;

    private String username;

    private Sort sort;

    private Label label;

    public void existsParams() {
        if (viewStatus != null && !viewStatus && !StringUtils.hasText(password)) {
            throw new RuntimeException("请设置文章密码！");
        }
        userId = SecurityUtils.getUserId();
        createTime = DateUtils.getNowDate();
    }
}
