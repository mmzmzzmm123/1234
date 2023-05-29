package com.ruoyi.stu.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class StuClsCountData extends BaseEntity {
    private String stuCls;
    private String materialName;
    private Integer num;
}
