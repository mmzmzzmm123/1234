package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台礼物管理对象 bus_platform_gift
 *
 * @author Lam
 * @date 2023-09-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-平台礼物管理对象")
public class PlatformGift extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 封面 */
    @Excel(name = "封面")
    @ApiModelProperty("封面")
    private String cover;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态", dictType = "sys_show_hide")
    @ApiModelProperty("状态")
    private String state;

    /** 视频地址 */
    @Excel(name = "视频地址")
    @ApiModelProperty("视频地址")
    private String videoUrl;

    /** 视频秒数 */
    @Excel(name = "视频秒数")
    @ApiModelProperty("视频秒数")
    private Long videoSeconds;

    /** 价格 */
    @Excel(name = "价格")
    @ApiModelProperty("价格")
    private BigDecimal price;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortNum;


}
