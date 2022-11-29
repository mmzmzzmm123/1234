package com.ruoyi.student.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.student.domain.StuAccVo;
import com.ruoyi.student.mapper.StuAccMapper;
import com.ruoyi.student.service.IStuAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuAccServiceImpl implements IStuAccService {

    @Autowired
    private StuAccMapper stuAccMapper;

    @DataScope(deptAlias = "t")
    @Override
    public List<StuAccVo> selectStuAccList(StuAccVo stuAccVo) {
        return stuAccMapper.selectStuAccList(stuAccVo);
    }

    @Override
    @DataScope(deptAlias = "t")
    public List<StuAccVo> selectStuAccSumList(StuAccVo stuAccVo) {
        return stuAccMapper.selectStuAccSumList(stuAccVo);
    }

    @Override
    @DataScope(deptAlias = "t")
    public List<StuAccVo> selectCampStuAccSumList(StuAccVo stuAccVo) {
        return stuAccMapper.selectCampStuAccSumList(stuAccVo);
    }

    @Override
    public StuAccVo selectStuAccSum(StuAccVo stuAccVo) {
        return stuAccMapper.selectStuAccSum(stuAccVo);
    }
}
