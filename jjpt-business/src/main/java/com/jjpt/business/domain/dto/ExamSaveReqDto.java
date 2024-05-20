package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElExam;

import java.util.List;

/**
 * ExamSaveReqDto
 *
 * @author 卢亚峰
 * @version 2024/05/19 11:17
 **/
public class ExamSaveReqDto extends ElExam {

    private List<ElExamRepoExtDto> repoList;

    public List<ElExamRepoExtDto> getRepoList() {
        return repoList;
    }

    public void setRepoList(List<ElExamRepoExtDto> repoList) {
        this.repoList = repoList;
    }
}

