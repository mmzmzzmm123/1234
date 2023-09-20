package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TWxUserCleaner;

/**
 * 保洁员信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-09-20
 */
public interface TWxUserCleanerMapper extends BaseMapper<TWxUserCleaner> {
    /**
     * 查询保洁员信息
     *
     * @param id 保洁员信息主键
     * @return 保洁员信息
     */
    public TWxUserCleaner selectTWxUserCleanerById(Long id);

    /**
     * 查询保洁员信息列表
     *
     * @param tWxUserCleaner 保洁员信息
     * @return 保洁员信息集合
     */
    public List<TWxUserCleaner> selectTWxUserCleanerList(TWxUserCleaner tWxUserCleaner);

    /**
     * 新增保洁员信息
     *
     * @param tWxUserCleaner 保洁员信息
     * @return 结果
     */
    public int insertTWxUserCleaner(TWxUserCleaner tWxUserCleaner);

    /**
     * 修改保洁员信息
     *
     * @param tWxUserCleaner 保洁员信息
     * @return 结果
     */
    public int updateTWxUserCleaner(TWxUserCleaner tWxUserCleaner);

    /**
     * 删除保洁员信息
     *
     * @param id 保洁员信息主键
     * @return 结果
     */
    public int deleteTWxUserCleanerById(Long id);

    /**
     * 批量删除保洁员信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTWxUserCleanerByIds(Long[] ids);
}
