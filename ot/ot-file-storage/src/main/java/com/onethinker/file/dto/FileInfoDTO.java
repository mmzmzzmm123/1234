package com.onethinker.file.dto;

import com.onethinker.common.enums.FileRealtionStatusEnum;
import com.onethinker.common.enums.FileRelationTypeEnum;
import com.onethinker.file.domain.FileInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileInfoDTO extends FileInfo {
    /**
     * 访问域名
     */
    private String domain;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 关联类型
     */
    private FileRelationTypeEnum relationType;

    /**
     * 查询状态
     */
    private FileRealtionStatusEnum relationStatus;

    /**
     * 指定活动id
     */
    private String activityId;



    private List<String> ids;
}
