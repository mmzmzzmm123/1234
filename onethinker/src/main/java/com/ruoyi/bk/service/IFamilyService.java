package com.ruoyi.bk.service;

import java.util.List;
import com.ruoyi.bk.domain.Family;

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
    public Family selectFamilyById(Long id);

    /**
     * 查询家庭信息列表
     *
     * @param family 家庭信息
     * @return 家庭信息集合
     */
    public List<Family> selectFamilyList(Family family);

    /**
     * 新增家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    public int insertFamily(Family family);

    /**
     * 修改家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    public int updateFamily(Family family);

    /**
     * 批量删除家庭信息
     *
     * @param ids 需要删除的家庭信息主键集合
     * @return 结果
     */
    public int deleteFamilyByIds(Long[] ids);

    /**
     * 删除家庭信息信息
     *
     * @param id 家庭信息主键
     * @return 结果
     */
    public int deleteFamilyById(Long id);
}
