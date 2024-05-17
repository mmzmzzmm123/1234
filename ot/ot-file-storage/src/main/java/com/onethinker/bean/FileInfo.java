package com.onethinker.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author yangyouqi
 * @date 2024/5/15
 */
@Data
@Accessors(chain = true)
public class FileInfo implements Serializable {
    /**
     * 文件id
     */
    private String id;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件指纹
     */
    private String fingerprint;
    /**
     * 文件大小(字节)
     */
    private long fileSize;
    /**
     * MIME文件类型
     */
    private String mimeType;

    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件扩展名
     */
    private String extension;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人ip
     */
    private String creatorIpAddresses;
    /**
     * 主机名称
     */
    private String hostName;

    /**
     * 磁盘存储路径
     */
    private String diskPath;

    private Map<String, Object> attr;

    private static final long serialVersionUID = 1L;

}
