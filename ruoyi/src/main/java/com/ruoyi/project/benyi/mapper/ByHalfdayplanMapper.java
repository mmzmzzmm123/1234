package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByHalfdayplan;

/**
 * 入园半日体验计划Mapper接口
 *
 * @author tsbz
 * @date 2020-07-30
 */
public interface ByHalfdayplanMapper {
    /**
     * 查询入园半日体验计划
     *
     * @param id 入园半日体验计划ID
     * @return 入园半日体验计划
     */
    public ByHalfdayplan selectByHalfdayplanById(String id);

    /**
     * 查询入园半日体验计划列表
     *
     * @param byHalfdayplan 入园半日体验计划
     * @return 入园半日体验计划集合
     */
    public List<ByHalfdayplan> selectByHalfdayplanList(ByHalfdayplan byHalfdayplan);

    /**
     * 新增入园半日体验计划
     *
     * @param byHalfdayplan 入园半日体验计划
     * @return 结果
     */
    public int insertByHalfdayplan(ByHalfdayplan byHalfdayplan);

    /**
     * 修改入园半日体验计划
     *
     * @param byHalfdayplan 入园半日体验计划
     * @return 结果
     */
    public int updateByHalfdayplan(ByHalfdayplan byHalfdayplan);

    /**
     * 删除入园半日体验计划
     *
     * @param id 入园半日体验计划ID
     * @return 结果
     */
    public int deleteByHalfdayplanById(String id);

    /**
     * 批量删除入园半日体验计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByHalfdayplanByIds(String[] ids);
}