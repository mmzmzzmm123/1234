package com.ruoyi.common.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qinjin
 * @date 2023年06月15日11:48:53
 */
@Getter
@Setter
@ToString
public class BaseValObj implements Serializable {

    private Long id;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;
}
