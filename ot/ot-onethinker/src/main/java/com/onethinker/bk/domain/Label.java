package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.util.StringUtils;

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

    /**
     * id
     */
    private Long id;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private Long sortId;

    /**
     * 标签名称
     */
    @Excel(name = "标签名称")
    private String labelName;

    /**
     * 标签描述
     */
    @Excel(name = "标签描述")
    private String labelDescription;

    public void existsParams() {
        if (!StringUtils.hasText(labelName) || !StringUtils.hasText(labelDescription) || sortId == null) {
            throw new RuntimeException("标签名称和标签描述和分类Id不能为空！");
        }
    }


}
