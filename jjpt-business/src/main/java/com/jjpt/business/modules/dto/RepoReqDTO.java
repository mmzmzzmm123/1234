package com.jjpt.business.modules.dto;


import lombok.Data;

import java.util.List;

/**
* <p>
* 题库请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data

public class RepoReqDTO extends RepoDTO {

    private static final long serialVersionUID = 1L;


    private List<String> excludes;


    private String title;

}
