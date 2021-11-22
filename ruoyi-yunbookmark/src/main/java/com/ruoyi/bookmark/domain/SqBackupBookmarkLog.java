package com.ruoyi.bookmark.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 备份记录表对象 sq_backup_bookmark_log
 *
 * @author wanghao
 * @date 2021-11-22
 */
@Table(name="Sq_backup_bookmark_log")
@Data
@ToString
public class SqBackupBookmarkLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "JDBC")//返回自增长主键
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    private Long userId;

    /** 文件的名称 */
    @Column(name = "name")
    private String name;

    /** 远程的下载url */
    @Column(name = "down_url")
    private String downUrl;

    /** 备份形式 1.百度云 2.微云 3.阿里云 */
    @Column(name = "type")
    private Integer type;

    /** 备份结果:1.成功 2.token异常 3.空间不足 4.请求失败 */
    @Column(name = "result")
    private String result;


}
