package com.ruoyi.common.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 电路信息对象 sdhcircuit
 *
 * @author ruoyi
 * @date 2023-12-23
 */
public class SdhcircuitA extends BaseEntity {

    /**
     * 编号
     */
    private Long id;
    private List<Sdhcircuit>   sdhcircuitList;

}
