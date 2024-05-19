package com.onethinker.onethinker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 轮播图对象 t_banner
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
@Data
@TableName("t_banner")
public class Banner extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为不可见，1为可见
     */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Long enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 图片地址
     */
    @Excel(name = "图片地址")
    private String imageUrl;

    /**
     * 跳转地址
     */
    @Excel(name = "跳转地址")
    private String url;


}
