package com.jjpt.business.modules.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 问题题目请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data
public class QuDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;


    private Integer quType;


    private Integer level;


    private String image;


    private String content;



    private Date createTime;


    private Date updateTime;


    private String remark;


    private String analysis;
    
}
