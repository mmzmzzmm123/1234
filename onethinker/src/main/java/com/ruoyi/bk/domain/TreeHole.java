package com.ruoyi.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 树洞对象 bk_tree_hole
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_tree_hole")
public class TreeHole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 留言 */
    @Excel(name = "留言")
    private String message;


}
