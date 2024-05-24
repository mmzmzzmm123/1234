package com.jjpt.business.domain.dto;


import com.jjpt.business.domain.ElPaper;
import lombok.Data;

import java.util.List;

@Data
public class ExamResultRespDTO extends ElPaper {


    private List<PaperQuDetailDTO> quList;

    private String userId_dictText;

}
