package com.renxin.psychology.dto;


import com.renxin.psychology.domain.PsyConsultOrderItem;

import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * 咨询子订单对象，咨询师APP用
 * 
 * @author Jack
 * @date 2024-05-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderItemDTO extends PsyConsultOrderItem {

    /** 客户id */
    private Integer userAvatar;
    /** 客户 */
    private String userNickName;
    private String consultantName;
    private String consultantAvatar;

}
