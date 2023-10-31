package com.ruoyi.onethinker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 活动类型对象 t_activity_type
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Data
public class ActivityType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 表主键 */
    private Long id;

    /** 是否可见，0为不可见，1为可见 */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Long enabled;

    /** 活动类型名 */
    @Excel(name = "活动类型名")
    private String name;


}
