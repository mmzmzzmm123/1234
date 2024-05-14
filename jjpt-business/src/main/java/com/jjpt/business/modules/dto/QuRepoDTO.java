package com.jjpt.business.modules.dto;


import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 试题题库请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data
public class QuRepoDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;


    private String quId;


    private String repoId;


    private Integer quType;


    private Integer sort;

}