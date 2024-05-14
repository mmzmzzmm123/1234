package com.jjpt.business.modules.dto;


import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 候选答案请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data
public class QuAnswerDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    private String id;


    private String quId;


    private Boolean isRight;


    private String image;


    private String content;


    private String analysis;
    
}
