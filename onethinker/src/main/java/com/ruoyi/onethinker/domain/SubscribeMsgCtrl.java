package com.ruoyi.onethinker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 订阅消息控制对象 t_subscribe_msg_ctrl
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
@Data
@TableName("t_subscribe_msg_ctrl")
public class SubscribeMsgCtrl extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0不可用，1为可用
     */
    @Excel(name = "是否可见，0不可用，1为可用")
    private Long enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 小程序模版id
     */
    @Excel(name = "小程序模版id")
    private String templateId;

    /**
     * 模版消息跳转链接
     */
    @Excel(name = "模版消息跳转链接")
    private String page;

    /**
     * 跳转小程序类型 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    @Excel(name = "跳转小程序类型 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版")
    private String miniprogramState;

    /**
     * 模板内容，不填则下发空模板,模版格式化遵循{key:{value:"xxxx"}}格式
     */
    @Excel(name = "模板内容，不填则下发空模板,模版格式化遵循{key:{value:\"xxxx\"}}格式")
    private String data;


}
