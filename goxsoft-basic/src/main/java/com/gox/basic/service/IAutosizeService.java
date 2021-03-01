package com.gox.basic.service;

import com.gox.basic.domain.form.Autosize;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IAutosizeService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Autosize selectAutosizeById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param autosize 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Autosize> selectAutosizeList(Autosize autosize);

    /**
     * 新增【请填写功能名称】
     *
     * @param autosize 【请填写功能名称】
     * @return 结果
     */
    public int insertAutosize(Autosize autosize);

    /**
     * 修改【请填写功能名称】
     *
     * @param autosize 【请填写功能名称】
     * @return 结果
     */
    public int updateAutosize(Autosize autosize);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteAutosizeByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteAutosizeById(Long id);
}
