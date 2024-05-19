package com.onethinker.bk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.constant.BkConstants;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Data
public class ResourcePathVO {

    public static final String SAVE_RESOURCE_PATH = "1";
    public static final String SAVE_FRIEND = "2";
    public static final String UPDATE_RESOURCE_PATH = "3";
    public static final String SAVE_FUNNY = "4";
    public static final String SAVE_LOVE_PHOTO = "5";

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类
     */
    private String classify;

    /**
     * 封面
     */
    private String cover;

    /**
     * 链接
     */
    private String url;

    /**
     * 资源类型
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用[0:否，1:是]
     */
    private Boolean status;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public void existsParams(String saveType) {
        if (SAVE_RESOURCE_PATH.equals(saveType)) {
            if (!StringUtils.hasText(title) || !StringUtils.hasText(type)) {
                throw new RuntimeException("标题和资源类型不能为空！");
            }
        } else if (SAVE_FRIEND.equals(saveType)) {
            if (!StringUtils.hasText(title) || !StringUtils.hasText(cover) || !StringUtils.hasText(url) || !StringUtils.hasText(introduction)) {
                throw new RuntimeException("信息不全！");
            }
            this.setType(BkConstants.RESOURCE_PATH_TYPE_FRIEND);
            this.setClassify(BkConstants.DEFAULT_FRIEND);
            this.setStatus(Boolean.FALSE);
            this.setRemark(SecurityUtils.getUserId().toString());
        } else if (UPDATE_RESOURCE_PATH.equals(saveType)) {
            if (!StringUtils.hasText(title) || !StringUtils.hasText(type)) {
                throw new RuntimeException("标题和资源类型不能为空！");
            }
            if (id == null) {
                throw new RuntimeException("Id不能为空！");
            }

        } else if (SAVE_FUNNY.equals(saveType)) {
            if (!StringUtils.hasText(classify) || !StringUtils.hasText(cover) || !StringUtils.hasText(url) || !StringUtils.hasText(title)) {
                throw new RuntimeException("信息不全！");
            }
            setType(BkConstants.RESOURCE_PATH_TYPE_FUNNY);
            setStatus(Boolean.FALSE);
        } else if (SAVE_LOVE_PHOTO.equals(saveType)) {
            if (!StringUtils.hasText(classify) || !StringUtils.hasText(cover) || !StringUtils.hasText(title)) {
                throw new RuntimeException("信息不全！");
            }
            setType(BkConstants.RESOURCE_PATH_TYPE_LOVE_PHOTO);
            setStatus(Boolean.FALSE);
        }

        if (BkConstants.RESOURCE_PATH_TYPE_LOVE_PHOTO.equals(type)) {
            this.setRemark(SecurityUtils.getUserId().toString());
        }

    }
}
