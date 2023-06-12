package com.ruoyi.office.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户套餐购买记录对象 t_wx_user_package
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@Data
public class TWxUserPackage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 套餐名称
     */
    @Excel(name = "套餐名称")
    private String packageName;

    /**
     * 实付金额
     */
    @Excel(name = "实付金额")
    private BigDecimal payAmount;

    /**
     * 赠送金额
     */
    @Excel(name = "赠送金额")
    private BigDecimal giftAmont;

    /**
     * 套餐id
     */
    @Excel(name = "套餐id")
    private Long packId;

    /**
     * 套餐商户
     */
    @Excel(name = "套餐商户")
    private Long merchant;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    private Long orderNo;

}
