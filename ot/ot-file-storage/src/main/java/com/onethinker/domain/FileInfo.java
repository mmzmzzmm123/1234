package com.onethinker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 文件信息对象 t_file
 *
 * @author yangyouqi
 * @date 2024-05-18
 */
@Data
@TableName("t_file")
public class FileInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文件Id
     */
    private String id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;

    /**
     * 文件指纹
     */
    @Excel(name = "文件指纹")
    private String fingerprint;

    /**
     * 文件大小(字节)
     */
    @Excel(name = "文件大小(字节)")
    private Long fileSize;

    /**
     * MIME文件类型
     */
    @Excel(name = "MIME文件类型")
    private String mimeType;

    /**
     * 文件扩展名
     */
    @Excel(name = "文件扩展名")
    private String extension;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String path;

    /**
     * 应用名称
     */
    @Excel(name = "应用名称")
    private String appName;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private String createUserId;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateUserId;

    /**
     * 创建人ip
     */
    @Excel(name = "创建人ip")
    private String creatorIpAddress;

    /**
     * 主机名称
     */
    @Excel(name = "主机名称")
    private String hostName;

    /**
     * 删除标识；1-删除, 0-可用
     */
    private Long delFlag;

    /**
     * 磁盘存储路径
     */
    @Excel(name = "磁盘存储路径")
    private String diskPath;

    /**
     * 检测的MIME文件类型
     */
    @Excel(name = "检测的MIME文件类型")
    private String detectMime;

    /**
     * 来源
     */
    @Excel(name = "来源")
    private String platform;


}
