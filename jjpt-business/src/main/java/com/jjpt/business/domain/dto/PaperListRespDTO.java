package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElPaper;

import lombok.Data;

import java.awt.print.Paper;

/**
* <p>
* 试卷请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 17:31
*/
@Data
public class PaperListRespDTO extends ElPaper {

    private static final long serialVersionUID = 1L;


    private String realName;

    
}
