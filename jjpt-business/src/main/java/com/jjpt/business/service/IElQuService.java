package com.jjpt.business.service;

import com.jjpt.business.domain.ElQu;
import com.jjpt.business.domain.dto.ElQuDto;

import java.util.List;


/**
 * 试题管理Service接口
 * 
 * @author 卢亚峰
 * @date 2024-05-17
 */
public interface IElQuService 
{
    /**
     * 查询试题管理
     * 
     * @param id 试题管理主键
     * @return 试题管理
     */
    public ElQu selectElQuById(String id);

    /**
     * 查询试题管理列表
     * 
     * @param elQu 试题管理
     * @return 试题管理集合
     */
    public List<ElQu> selectElQuList(ElQu elQu);

    /**
     * 新增试题管理
     * 
     * @param elQu 试题管理
     * @return 结果
     */
    public int insertElQu(ElQuDto elQu);

    /**
     * 修改试题管理
     * 
     * @param elQu 试题管理
     * @return 结果
     */
    public int updateElQu(ElQu elQu);

    /**
     * 批量删除试题管理
     * 
     * @param ids 需要删除的试题管理主键集合
     * @return 结果
     */
    public int deleteElQuByIds(String[] ids);

    /**
     * 删除试题管理信息
     * 
     * @param id 试题管理主键
     * @return 结果
     */
    public int deleteElQuById(String id);
}
