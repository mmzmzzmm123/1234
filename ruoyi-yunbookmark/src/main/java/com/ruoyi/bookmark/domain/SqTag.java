package com.ruoyi.bookmark.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 书签_标签对象 sq_tag
 *
 * @author ruoyi
 * @date 2020-09-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sq_tag")
public class SqTag
{
    private static final long serialVersionUID = 1L;
    /** 书签标签id */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    /** 标签名字 */
    @Excel(name = "标签名字")
    @Column(name = "name")
    private String name;

    /** 引用数量 */
    @Excel(name = "引用数量")
    @Column(name = "icount")
    private Integer icount;

    /** 谁增加的该标签 */
    @Excel(name = "谁增加的该标签")
    @Column(name = "user_Id")
    private Long userId;

    /** S系统标签,P书签标签,B便签书签 */
    @Excel(name = "S系统标签,P书签标签,B便签书签")
    @Column(name = "tag_type")
    private String tagType;

    /** 链接 */
    @Excel(name = "链接")
    @Column(name = "url")
    private String url;

    /** 状态 */
    @Excel(name = "状态")
    @Column(name = "status")
    private Integer status;


    /** 标签的字体颜色 */
    @Excel(name = "标签的字体颜色")
    @Column(name = "is_fontColor")
    private String isFontColor;

    /** 标签的背景颜色 */
    @Excel(name = "标签的背景颜色")
    @Column(name = "is_bgColor")
    private String isBgColor;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;


    public  SqTag(Long userId){
        this.userId = userId;
    }
}
