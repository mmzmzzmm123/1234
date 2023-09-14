package com.ruoyi.staff.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工礼物记录对象 staff_gift_record
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工礼物记录对象")
public class StaffGiftRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 员工标识 */
    @Excel(name = "员工标识")
    @ApiModelProperty("员工标识")
    private Long staffUserId;

    /** 礼物标识 */
    @Excel(name = "礼物标识")
    @ApiModelProperty("礼物标识")
    private Long giftIf;

    /** 礼物数量 */
    @Excel(name = "礼物数量")
    @ApiModelProperty("礼物数量")
    private Long giftNum;


}
