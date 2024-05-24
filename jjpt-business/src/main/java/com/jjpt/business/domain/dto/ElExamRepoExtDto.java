package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElExamRepo;
import lombok.Data;

/**
 * ElExamRepoExtDto
 *
 * @author 卢亚峰
 * @version 2024/05/19 10:55
 **/

public class ElExamRepoExtDto extends ElExamRepo {

    private static final long serialVersionUID = 1L;



    private Integer totalRadio;


    private Integer totalMulti;


    private Integer totalJudge;

    public ElExamRepoExtDto(Integer totalRadio, Integer totalMulti, Integer totalJudge) {
        this.totalRadio = totalRadio;
        this.totalMulti = totalMulti;
        this.totalJudge = totalJudge;
    }



    public ElExamRepoExtDto() {

    }



    public Integer getTotalRadio() {
        return totalRadio;
    }

    public void setTotalRadio(Integer totalRadio) {
        this.totalRadio = totalRadio;
    }

    public Integer getTotalMulti() {
        return totalMulti;
    }

    public void setTotalMulti(Integer totalMulti) {
        this.totalMulti = totalMulti;
    }

    public Integer getTotalJudge() {
        return totalJudge;
    }

    public void setTotalJudge(Integer totalJudge) {
        this.totalJudge = totalJudge;
    }


}

