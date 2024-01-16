package com.onethinker.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 标签对象 bk_label
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_label")
public class Label extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long sortId;

    /** 标签名称 */
    @Excel(name = "标签名称")
    private String labelName;

    /** 标签描述 */
    @Excel(name = "标签描述")
    private String labelDescription;


}
