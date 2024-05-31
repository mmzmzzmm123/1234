package com.onethinker.onethinker.domain;

import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 文件信息对象 sys_file_info
 *
 * @author yangyouqi
 * @date 2023-10-25
 */
@Data
public class SysFileInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final Integer SOURCE_TYPE_LOCALHOST = 1;
    public static final Integer SOURCE_TYPE_SERVICE = 2;
    public static final Integer SOURCE_TYPE_TX = 3;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;

    /**
     * 服务器文件id
     */
    @Excel(name = "服务器文件id")
    private String serverFileId;

    /**
     * 文件后缀名
     */
    @Excel(name = "文件后缀名")
    private String suffixName;

    /**
     * 文件大小，单位：Byte
     */
    @Excel(name = "文件大小，单位：Byte")
    private Long size;

    /**
     * 上传人
     */
    @Excel(name = "上传人")
    private Long createUserId;

    /**
     * 上传来源 1: 服务器上传 2:本地上传 3:腾讯云服务
     */
    @Excel(name = "上传来源 1: 服务器上传 2:本地上传 3:腾讯云服务")
    private Integer source;

    /**
     * 文件访问地址
     */
    @Excel(name = "文件访问地址")
    private String fileUrl;

    /**
     * 文件类型 1：图片 2：视频
     */
    @Excel(name = "文件类型 1：图片 2：视频")
    private Integer fileType;
}
