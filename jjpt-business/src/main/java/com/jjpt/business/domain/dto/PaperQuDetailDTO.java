package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElPaperQu;

import lombok.Data;

import java.util.List;

/**
* <p>
* 试卷考题请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 17:31
*/
@Data

public class PaperQuDetailDTO extends ElPaperQu {

    private static final long serialVersionUID = 1L;

    private String image;

    private String content;

    List<PaperQuAnswerExtDTO> answerList;
}
