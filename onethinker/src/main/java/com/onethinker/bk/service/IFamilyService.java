package com.onethinker.bk.service;

import com.onethinker.bk.domain.Family;

import java.util.List;

/**
 * 家庭信息Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IFamilyService {
    /**
     * 查询家庭信息
     *
     * @param id 家庭信息主键
     * @return 家庭信息
     */
    Family selectFamilyById(Long id);

    /**
     * 查询家庭信息列表
     *
     * @param family 家庭信息
     * @return 家庭信息集合
     */
    List<Family> selectFamilyList(Family family);

    /**
     * 新增家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    int insertFamily(Family family);

    /**
     * 修改家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    int updateFamily(Family family);

    /**
     * 批量删除家庭信息
     *
     * @param ids 需要删除的家庭信息主键集合
     * @return 结果
     */
    int deleteFamilyByIds(Long[] ids);

    /**
     * 删除家庭信息信息
     *
     * @param id 家庭信息主键
     * @return 结果
     */
    int deleteFamilyById(Long id);
}
