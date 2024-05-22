package com.onethinker.bx.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 佰兴-客户信息表对象 bx_customer_info
 *
 * @author yangyouqi
 * @date 2024-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bx_customer_info")
public class BxCustomerInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 简称
     */
    @Excel(name = "简称")
    private String shortName;

    /**
     * 客户类别
     */
    @Excel(name = "客户类别")
    private String customerType;

    /**
     * 负责人
     */
    @Excel(name = "负责人")
    private String manager;

    /**
     * 业务员
     */
    @Excel(name = "业务员")
    private String salesperson;

    /**
     * 产品类别
     */
    @Excel(name = "产品类别")
    private String productType;

    /**
     * 所属区域
     */
    @Excel(name = "所属区域")
    private String region;

    /**
     * 送货方式
     */
    @Excel(name = "送货方式")
    private String deliveryType;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactPerson;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String mobile;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String deliveryAddress;

    /**
     * 注意事项
     */
    @Excel(name = "注意事项")
    private String notes;

    /**
     * 科室
     */
    @Excel(name = "科室")
    private String department;

}
