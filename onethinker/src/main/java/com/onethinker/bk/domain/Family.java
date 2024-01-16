package com.onethinker.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 家庭信息对象 bk_family
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_family")
public class Family extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 背景封面 */
    @Excel(name = "背景封面")
    private String bgCover;

    /** 男生头像 */
    @Excel(name = "男生头像")
    private String manCover;

    /** 女生头像 */
    @Excel(name = "女生头像")
    private String womanCover;

    /** 男生昵称 */
    @Excel(name = "男生昵称")
    private String manName;

    /** 女生昵称 */
    @Excel(name = "女生昵称")
    private String womanName;

    /** 计时 */
    @Excel(name = "计时")
    private String timing;

    /** 倒计时标题 */
    @Excel(name = "倒计时标题")
    private String countdownTitle;

    /** 倒计时时间 */
    @Excel(name = "倒计时时间")
    private String countdownTime;

    /** 是否启用[0:否，1:是] */
    @Excel(name = "是否启用[0:否，1:是]")
    private Integer status;

    /** 额外信息 */
    @Excel(name = "额外信息")
    private String familyInfo;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;


}
