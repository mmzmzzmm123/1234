package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByPlanweekMapper;
import com.ruoyi.project.benyi.domain.ByPlanweek;
import com.ruoyi.project.benyi.service.IByPlanweekService;

/**
 * 周计划(家长和教育部门)Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-24
 */
@Service
public class ByPlanweekServiceImpl implements IByPlanweekService {
    @Autowired
    private ByPlanweekMapper byPlanweekMapper;

    /**
     * 查询周计划(家长和教育部门)
     *
     * @param id 周计划(家长和教育部门)ID
     * @return 周计划(家长和教育部门)
     */
    @Override
    public ByPlanweek selectByPlanweekById(Long id) {
        return byPlanweekMapper.selectByPlanweekById(id);
    }

    /**
     * 查询周计划(家长和教育部门)列表
     *
     * @param byPlanweek 周计划(家长和教育部门)
     * @return 周计划(家长和教育部门)
     */
    @Override
    public List<ByPlanweek> selectByPlanweekList(ByPlanweek byPlanweek) {
        return byPlanweekMapper.selectByPlanweekList(byPlanweek);
    }

    /**
     * 新增周计划(家长和教育部门)
     *
     * @param byPlanweek 周计划(家长和教育部门)
     * @return 结果
     */
    @Override
    public int insertByPlanweek(ByPlanweek byPlanweek) {
        byPlanweek.setCreateTime(DateUtils.getNowDate());
        return byPlanweekMapper.insertByPlanweek(byPlanweek);
    }

    /**
     * 修改周计划(家长和教育部门)
     *
     * @param byPlanweek 周计划(家长和教育部门)
     * @return 结果
     */
    @Override
    public int updateByPlanweek(ByPlanweek byPlanweek) {
        return byPlanweekMapper.updateByPlanweek(byPlanweek);
    }

    /**
     * 批量删除周计划(家长和教育部门)
     *
     * @param ids 需要删除的周计划(家长和教育部门)ID
     * @return 结果
     */
    @Override
    public int deleteByPlanweekByIds(Long[] ids) {
        return byPlanweekMapper.deleteByPlanweekByIds(ids);
    }

    /**
     * 删除周计划(家长和教育部门)信息
     *
     * @param id 周计划(家长和教育部门)ID
     * @return 结果
     */
    @Override
    public int deleteByPlanweekById(Long id) {
        return byPlanweekMapper.deleteByPlanweekById(id);
    }
}
