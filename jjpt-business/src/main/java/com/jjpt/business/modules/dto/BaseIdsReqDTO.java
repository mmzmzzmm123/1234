package com.jjpt.business.modules.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * 通用ID列表类操作，用于批量删除、修改状态等
 * @author bool 
 * @date 2019-08-01 19:07
 */
@Data
public class BaseIdsReqDTO extends BaseDTO {


    @JsonIgnore
    private String userId;


    private List<String> ids;
}
