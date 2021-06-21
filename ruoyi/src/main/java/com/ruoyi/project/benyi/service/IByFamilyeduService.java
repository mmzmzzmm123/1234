package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.benyi.domain.ByFamilyedu;
import com.ruoyi.project.benyi.domain.ByMicrocourse;

/**
 * 家庭教育Service接口
 *
 * @author tsbz
 * @date 2021-06-20
 */
public interface IByFamilyeduService {
    /**
     * 查询家庭教育
     *
     * @param id 家庭教育ID
     * @return 家庭教育
     */
    public ByFamilyedu selectByFamilyeduById(Long id);

    /**
     * 查询家庭教育列表
     *
     * @param byFamilyedu 家庭教育
     * @return 家庭教育集合
     */
    public List<ByFamilyedu> selectByFamilyeduList(ByFamilyedu byFamilyedu);

    /**
     * 查询家庭教育列表
     *
     * @param byFamilyedu 家庭教育
     * @return 家庭教育集合
     */
    public List<ByFamilyedu> selectByFamilyeduListTree(ByFamilyedu byFamilyedu);

    /**
     * 构建前端所需要树结构
     *
     * @param byFamilyedus 部门列表
     * @return 树结构列表
     */
    public List<ByFamilyedu> buildFamilyeduDetailTree(List<ByFamilyedu> byFamilyedus);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byFamilyedus 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildFamilyeduTreeSelect(List<ByFamilyedu> byFamilyedus);


    /**
     * 新增家庭教育
     *
     * @param byFamilyedu 家庭教育
     * @return 结果
     */
    public int insertByFamilyedu(ByFamilyedu byFamilyedu);

    /**
     * 修改家庭教育
     *
     * @param byFamilyedu 家庭教育
     * @return 结果
     */
    public int updateByFamilyedu(ByFamilyedu byFamilyedu);

    /**
     * 批量删除家庭教育
     *
     * @param ids 需要删除的家庭教育ID
     * @return 结果
     */
    public int deleteByFamilyeduByIds(Long[] ids);

    /**
     * 删除家庭教育信息
     *
     * @param id 家庭教育ID
     * @return 结果
     */
    public int deleteByFamilyeduById(Long id);
}
