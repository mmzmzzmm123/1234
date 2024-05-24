package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElQu;
import com.jjpt.business.domain.ElQuAnswer;
import lombok.Data;

import java.util.List;

/**
 * EIQuDto
 *
 * @author 卢亚峰
 * @version 2024/05/17 20:04
 **/
@Data
public class ElQuDto extends ElQu {

    //答案
    private List<ElQuAnswer> answerList;
    //题库列表
    private List<String> repoIds;
}

