package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 资源信息对象 bk_resource
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_resource")
public class BkResource extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 资源类型 */
    @Excel(name = "资源类型")
    private String type;

    /** 资源路径 */
    @Excel(name = "资源路径")
    private String path;

    /** 资源内容的大小，单位：字节 */
    @Excel(name = "资源内容的大小，单位：字节")
    private Long size;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String originalName;

    /** 资源的 MIME 类型 */
    @Excel(name = "资源的 MIME 类型")
    private String mimeType;

    /** 是否启用[0:否，1:是] */
    @Excel(name = "是否启用[0:否，1:是]")
    private Integer status;

    /** 存储平台 */
    @Excel(name = "存储平台")
    private String storeType;


}
