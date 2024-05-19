package com.onethinker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 文件关联信息对象 t_file_relation
 *
 * @author yangyouqi
 * @date 2024-05-18
 */
@Data
@TableName("t_file_relation")
public class FileRelation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 关联信息ID
     */
    private String id;

    /**
     * 租户ID
     */
    @Excel(name = "租户ID")
    private String tenantId;

    /**
     * 文件ID
     */
    @Excel(name = "文件ID")
    private String fileId;

    /**
     * 关联类型
     */
    @Excel(name = "关联类型")
    private String relationType;

    /**
     * 关联类型业务ID
     */
    @Excel(name = "关联类型业务ID")
    private String relationTarget;

    /**
     * 删除标识：1-删除, 0-正常可用
     */
    private int delFlag;

    /**
     * 更新人ID
     */
    @Excel(name = "更新人ID")
    private String updateUserId;

    /**
     * 创建人ID
     */
    @Excel(name = "创建人ID")
    private String createUserId;

    /**
     * 创建用户姓名
     */
    @Excel(name = "创建用户姓名")
    private String createUserName;

    /**
     * 更新用户姓名
     */
    @Excel(name = "更新用户姓名")
    private String updateUserName;


    public FileRelation() {
    }

    public FileRelation(FileInfo fileInfo, String relationType, String relationTarget) {
        this.tenantId = fileInfo.getTenantId();
        this.fileId = fileInfo.getId();
        this.delFlag = 0;
        this.relationType = relationType;
        this.relationTarget = relationTarget;
    }


}
