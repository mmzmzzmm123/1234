package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.BySchoolcharge;

/**
 * 园所收费标准Service接口
 *
 * @author tsbz
 * @date 2020-12-10
 */
public interface IBySchoolchargeService {
    /**
     * 查询园所收费标准
     *
     * @param id 园所收费标准ID
     * @return 园所收费标准
     */
    public BySchoolcharge selectBySchoolchargeById(Long id);

    /**
     * 查询园所收费标准列表
     *
     * @param bySchoolcharge 园所收费标准
     * @return 园所收费标准集合
     */
    public List<BySchoolcharge> selectBySchoolchargeList(BySchoolcharge bySchoolcharge);

    /**
     * 查询幼儿收费列表
     *
     * @param bySchoolcharge 收费标准
     * @return 幼儿收费集合
     */
    public List<BySchoolcharge> selectByChildchargeList(BySchoolcharge bySchoolcharge);

    /**
     * 新增园所收费标准
     *
     * @param bySchoolcharge 园所收费标准
     * @return 结果
     */
    public int insertBySchoolcharge(BySchoolcharge bySchoolcharge);

    /**
     * 修改园所收费标准
     *
     * @param bySchoolcharge 园所收费标准
     * @return 结果
     */
    public int updateBySchoolcharge(BySchoolcharge bySchoolcharge);

    /**
     * 批量删除园所收费标准
     *
     * @param ids 需要删除的园所收费标准ID
     * @return 结果
     */
    public int deleteBySchoolchargeByIds(Long[] ids);

    /**
     * 删除园所收费标准信息
     *
     * @param id 园所收费标准ID
     * @return 结果
     */
    public int deleteBySchoolchargeById(Long id);
}
