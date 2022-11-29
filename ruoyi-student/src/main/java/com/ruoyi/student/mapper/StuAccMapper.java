package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.StuAccVo;
import com.ruoyi.student.domain.StuInfo;

import java.util.List;

public interface StuAccMapper {

    List<StuAccVo> selectStuAccList(StuAccVo stuAccVo);

    List<StuAccVo> selectStuAccSumList(StuAccVo stuAccVo);

    List<StuAccVo> selectCampStuAccSumList(StuAccVo stuAccVo);

    StuAccVo selectStuAccSum(StuAccVo stuAccVo);
}
