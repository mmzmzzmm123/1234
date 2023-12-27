package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Srchecksheet;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-12-20
 */
public interface ISrchecksheetService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Srchecksheet selectSrchecksheetById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param srchecksheet 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Srchecksheet> selectSrchecksheetList(Srchecksheet srchecksheet);

    /**
     * 新增【请填写功能名称】
     * 
     * @param srchecksheet 【请填写功能名称】
     * @return 结果
     */
    public int insertSrchecksheet(Srchecksheet srchecksheet);

    /**
     * 修改【请填写功能名称】
     * 
     * @param srchecksheet 【请填写功能名称】
     * @return 结果
     */
    public int updateSrchecksheet(Srchecksheet srchecksheet);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteSrchecksheetByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSrchecksheetById(Long id);
}
