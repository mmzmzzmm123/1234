package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElUserExam;
import lombok.Data;

/**
* <p>
* 考试记录数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2020-09-21 15:13
*/
@Data
public class UserExamRespDTO extends ElUserExam {

    private static final long serialVersionUID = 1L;


    private String title;

    private String realName;
    
}
