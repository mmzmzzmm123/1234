package com.ruoyi.student.service;

import com.ruoyi.student.domain.StuAccVo;

import java.util.List;

public interface IStuAccService {

    List<StuAccVo> selectStuAccList(StuAccVo stuAccVo);

    List<StuAccVo> selectStuAccSumList(StuAccVo stuAccVo);

    List<StuAccVo> selectCampStuAccSumList(StuAccVo stuAccVo);

    StuAccVo selectStuAccSum(StuAccVo stuAccVo);
}
