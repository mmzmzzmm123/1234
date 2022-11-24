package com.ruoyi.student.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.PieVoMapper;
import com.ruoyi.student.domain.PieVo;
import com.ruoyi.student.service.IPieVoService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-23
 */
@Service
public class PieVoServiceImpl implements IPieVoService 
{
    @Autowired
    private PieVoMapper pieVoMapper;




    @Override
    public Map<String, BigDecimal> selectPieVoAcc()
    {
        return pieVoMapper.selectPieVoAcc();
    }

    @Override
    public Map<String, BigDecimal> selectPieVoResidence() {
        return pieVoMapper.selectPieVoResidence();
    }

    @Override
    public Map<String, BigDecimal> selectPieVoNoBackSchool() {
        return pieVoMapper.selectPieVoNoBackSchool();
    }

    @Override
    public Map<String, BigDecimal> selectPieVoControl() {
        return pieVoMapper.selectPieVoControl();
    }

    @Override
    public Map<String, BigDecimal> selectPieVoPlaceToSchool() {
        return pieVoMapper.selectPieVoPlaceToSchool();
    }


}
