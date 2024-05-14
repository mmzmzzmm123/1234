package com.jjpt.business.modules.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * <p>
 * 主键通用请求类，用于根据ID查询
 * </p>
 *
 * @author 聪明笨狗
 * @since 2019-04-20 12:15
 */
@Data
public class BaseIdReqDTO extends BaseDTO {



    private String id;

    @JsonIgnore
    private String userId;

}
