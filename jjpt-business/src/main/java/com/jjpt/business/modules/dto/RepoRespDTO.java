package com.jjpt.business.modules.dto;


import lombok.Data;

/**
* <p>
* 题库请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data
public class RepoRespDTO extends RepoDTO {

    private static final long serialVersionUID = 2323232L;


    private Integer multiCount;


    private Integer radioCount;


    private Integer judgeCount;

}
