package com.gox.basic.service;

import com.gox.basic.domain.form.Props;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface IPropsService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Props selectPropsById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param props 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Props> selectPropsList(Props props);

    /**
     * 新增【请填写功能名称】
     * 
     * @param props 【请填写功能名称】
     * @return 结果
     */
    public int insertProps(Props props);

    /**
     * 修改【请填写功能名称】
     * 
     * @param props 【请填写功能名称】
     * @return 结果
     */
    public int updateProps(Props props);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deletePropsByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePropsById(Long id);
}
