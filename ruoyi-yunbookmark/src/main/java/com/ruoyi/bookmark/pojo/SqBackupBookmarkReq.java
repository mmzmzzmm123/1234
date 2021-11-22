package com.ruoyi.bookmark.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 备份书签对象 sq_backup_bookmark
 *
 * @author ruoyi
 * @date 2021-11-22
 */
@Data
public class SqBackupBookmarkReq
{

    private Long id;

    /** 1 开启 0 未开启 */
    private Integer aliyun;

    /** 1 开启 0 未开启 */
    private Integer baiduyun;

    /** 1 开启 0 未开启 */
    private Integer weiyun;

    /** 备份周期 单位/天 */
    private Integer cycle;

    /** 百度token */
    private String baiduyunToken;

    /** 阿里token */
    private String aliyunToken;

    /** 微云token */
    private String weiyunToken;



}
