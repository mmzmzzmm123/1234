package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.BySchoolchargeMapper;
import com.ruoyi.project.benyi.domain.BySchoolcharge;
import com.ruoyi.project.benyi.service.IBySchoolchargeService;

/**
 * 园所收费标准Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-10
 */
@Service
public class BySchoolchargeServiceImpl implements IBySchoolchargeService {
    @Autowired
    private BySchoolchargeMapper bySchoolchargeMapper;

    /**
     * 查询园所收费标准
     *
     * @param id 园所收费标准ID
     * @return 园所收费标准
     */
    @Override
    public BySchoolcharge selectBySchoolchargeById(Long id) {
        return bySchoolchargeMapper.selectBySchoolchargeById(id);
    }

    /**
     * 查询园所收费标准列表
     *
     * @param bySchoolcharge 园所收费标准
     * @return 园所收费标准
     */
    @Override
    @DataScope(deptAlias = "b")
    public List<BySchoolcharge> selectBySchoolchargeList(BySchoolcharge bySchoolcharge) {
        return bySchoolchargeMapper.selectBySchoolchargeList(bySchoolcharge);
    }

    /**
     * 新增园所收费标准
     *
     * @param bySchoolcharge 园所收费标准
     * @return 结果
     */
    @Override
    public int insertBySchoolcharge(BySchoolcharge bySchoolcharge) {
        bySchoolcharge.setCreateTime(DateUtils.getNowDate());
        return bySchoolchargeMapper.insertBySchoolcharge(bySchoolcharge);
    }

    /**
     * 修改园所收费标准
     *
     * @param bySchoolcharge 园所收费标准
     * @return 结果
     */
    @Override
    public int updateBySchoolcharge(BySchoolcharge bySchoolcharge) {
        return bySchoolchargeMapper.updateBySchoolcharge(bySchoolcharge);
    }

    /**
     * 批量删除园所收费标准
     *
     * @param ids 需要删除的园所收费标准ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolchargeByIds(Long[] ids) {
        return bySchoolchargeMapper.deleteBySchoolchargeByIds(ids);
    }

    /**
     * 删除园所收费标准信息
     *
     * @param id 园所收费标准ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolchargeById(Long id) {
        return bySchoolchargeMapper.deleteBySchoolchargeById(id);
    }
}
