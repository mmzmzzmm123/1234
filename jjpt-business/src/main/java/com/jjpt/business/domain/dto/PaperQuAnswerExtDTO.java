package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElPaperQuAnswer;
import lombok.Data;

/**
* <p>
* 试卷考题备选答案请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 17:31
*/
@Data
public class PaperQuAnswerExtDTO extends ElPaperQuAnswer {

    private static final long serialVersionUID = 1L;


    private String image;


    private String content;

    
}
