package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文本内容管理对象 bus_platform_text_content
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-文本内容管理对象")
public class PlatformTextContent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 类型 */
    @Excel(name = "类型", dictType = "text_type")
    @ApiModelProperty("类型")
    private String type;

    /** 文本内容 */
    @Excel(name = "文本内容")
    @ApiModelProperty("文本内容")
    private String content;


}
