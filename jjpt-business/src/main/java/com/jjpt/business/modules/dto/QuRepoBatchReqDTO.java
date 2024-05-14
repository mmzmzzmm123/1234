package com.jjpt.business.modules.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* <p>
* 问题题目请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data

public class QuRepoBatchReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    private List<String> quIds;


    private List<String> repoIds;


    private Boolean remove;
    
}
