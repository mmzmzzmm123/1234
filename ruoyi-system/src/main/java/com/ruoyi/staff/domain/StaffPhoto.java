package com.ruoyi.staff.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工相册对象 bus_staff_photo
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工相册对象")
public class StaffPhoto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String imgUrl;

    /** 是否解锁 */
    @Excel(name = "是否解锁")
    @ApiModelProperty("是否解锁")
    private String ifUnlock;

    /** 解锁金额 */
    @Excel(name = "解锁金额")
    @ApiModelProperty("解锁金额")
    private BigDecimal unlockAmount;

    /** 状态（-1审核不通过，0正常，1审核中） */
    @Excel(name = "状态", readConverterExp = "-=1审核不通过，0正常，1审核中")
    @ApiModelProperty("状态")
    private String state;

    /** 不通过原因 */
    @Excel(name = "不通过原因")
    @ApiModelProperty("不通过原因")
    private String noPassReason;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortNum;


}
