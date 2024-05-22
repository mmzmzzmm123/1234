package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElPaper;
import com.jjpt.business.domain.ElPaperQu;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
public class ExamDetailRespDTO extends ElPaper {



    private List<ElPaperQu> radioList;


    private List<ElPaperQu> multiList;


    private List<ElPaperQu> judgeList;



    public Long getLeftSeconds(){

        // 结束时间
        Calendar cl = Calendar.getInstance();
        cl.setTime(this.getCreateTime());
        cl.add(Calendar.MINUTE, getTotalTime());

        return (cl.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

}
