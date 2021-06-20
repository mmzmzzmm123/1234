package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByFamilyedu;

/**
 * 家庭教育Mapper接口
 *
 * @author tsbz
 * @date 2021-06-20
 */
public interface ByFamilyeduMapper {
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
     * 删除家庭教育
     *
     * @param id 家庭教育ID
     * @return 结果
     */
    public int deleteByFamilyeduById(Long id);

    /**
     * 批量删除家庭教育
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByFamilyeduByIds(Long[] ids);
}
