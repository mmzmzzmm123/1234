package com.jjpt.business.modules.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 题库请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data

public class RepoDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    private String id;


    private String code;


    private String title;


    private String remark;


    private Date createTime;


    private Date updateTime;
    
}
