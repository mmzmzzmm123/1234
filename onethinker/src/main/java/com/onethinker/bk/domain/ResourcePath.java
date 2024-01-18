package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 资源路径对象 bk_resource_path
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_resource_path")
public class ResourcePath extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 分类
     */
    @Excel(name = "分类")
    private String classify;

    /**
     * 封面
     */
    @Excel(name = "封面")
    private String cover;

    /**
     * 链接
     */
    @Excel(name = "链接")
    private String url;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String introduction;

    /**
     * 资源类型
     */
    @Excel(name = "资源类型")
    private String type;

    /**
     * 是否启用[0:否，1:是]
     */
    @Excel(name = "是否启用[0:否，1:是]")
    private Boolean status;


}
