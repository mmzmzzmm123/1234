package com.ruoyi.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 历史信息对象 bk_history_info
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_history_info")
public class HistoryInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** ip */
    @Excel(name = "ip")
    private String ip;

    /** 国家 */
    @Excel(name = "国家")
    private String nation;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 城市 */
    @Excel(name = "城市")
    private String city;


}
