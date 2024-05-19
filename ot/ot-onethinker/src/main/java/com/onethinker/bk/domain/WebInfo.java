package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 网站信息对象 bk_web_info
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_web_info")
public class WebInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 网站名称
     */
    @Excel(name = "网站名称")
    private String webName;

    /**
     * 网站信息
     */
    @Excel(name = "网站信息")
    private String webTitle;

    /**
     * 公告
     */
    @Excel(name = "公告")
    private String notices;

    /**
     * 页脚
     */
    @Excel(name = "页脚")
    private String footer;

    /**
     * 背景
     */
    @Excel(name = "背景")
    private String backgroundImage;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String avatar;

    /**
     * 随机头像
     */
    @Excel(name = "随机头像")
    private String randomAvatar;

    /**
     * 随机名称
     */
    @Excel(name = "随机名称")
    private String randomName;

    /**
     * 随机封面
     */
    @Excel(name = "随机封面")
    private String randomCover;

    /**
     * 看板娘消息
     */
    @Excel(name = "看板娘消息")
    private String waifuJson;

    /**
     * 是否启用[0:否，1:是]
     */
    @Excel(name = "是否启用[0:否，1:是]")
    private Integer status;


}
