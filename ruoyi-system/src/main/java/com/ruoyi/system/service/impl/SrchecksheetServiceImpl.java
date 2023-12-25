package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SrchecksheetMapper;
import com.ruoyi.system.domain.Srchecksheet;
import com.ruoyi.system.service.ISrchecksheetService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-20
 */
@Service
public class SrchecksheetServiceImpl implements ISrchecksheetService 
{
    @Autowired
    private SrchecksheetMapper srchecksheetMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Srchecksheet selectSrchecksheetById(Long id)
    {
        Srchecksheet srchecksheet = srchecksheetMapper.selectSrchecksheetById(id);
        System.out.println("值是"+srchecksheet);
        return srchecksheetMapper.selectSrchecksheetById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param srchecksheet 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Srchecksheet> selectSrchecksheetList(Srchecksheet srchecksheet)
    {
        return srchecksheetMapper.selectSrchecksheetList(srchecksheet);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param srchecksheet 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSrchecksheet(Srchecksheet srchecksheet)
    {
        return srchecksheetMapper.insertSrchecksheet(srchecksheet);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param srchecksheet 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSrchecksheet(Srchecksheet srchecksheet)
    {
        return srchecksheetMapper.updateSrchecksheet(srchecksheet);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSrchecksheetByIds(Long[] ids)
    {
        return srchecksheetMapper.deleteSrchecksheetByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSrchecksheetById(Long id)
    {
        return srchecksheetMapper.deleteSrchecksheetById(id);
    }
}
