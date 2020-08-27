package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                            import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByPlanweekitemMapper;
import com.ruoyi.project.benyi.domain.ByPlanweekitem;
import com.ruoyi.project.benyi.service.IByPlanweekitemService;

/**
 * 周计划(家长和教育部门细化)Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-27
 */
@Service
public class ByPlanweekitemServiceImpl implements IByPlanweekitemService
{
    @Autowired
    private ByPlanweekitemMapper byPlanweekitemMapper;

    /**
     * 查询周计划(家长和教育部门细化)
     *
     * @param id 周计划(家长和教育部门细化)ID
     * @return 周计划(家长和教育部门细化)
     */
    @Override
    public ByPlanweekitem selectByPlanweekitemById(Long id)
    {
        return byPlanweekitemMapper.selectByPlanweekitemById(id);
    }

    /**
     * 查询周计划(家长和教育部门细化)列表
     *
     * @param byPlanweekitem 周计划(家长和教育部门细化)
     * @return 周计划(家长和教育部门细化)
     */
    @Override
    public List<ByPlanweekitem> selectByPlanweekitemList(ByPlanweekitem byPlanweekitem)
    {
        return byPlanweekitemMapper.selectByPlanweekitemList(byPlanweekitem);
    }

    /**
     * 新增周计划(家长和教育部门细化)
     *
     * @param byPlanweekitem 周计划(家长和教育部门细化)
     * @return 结果
     */
    @Override
    public int insertByPlanweekitem(ByPlanweekitem byPlanweekitem)
    {
                                                                                                                                                            byPlanweekitem.setCreateTime(DateUtils.getNowDate());
                                                                    return byPlanweekitemMapper.insertByPlanweekitem(byPlanweekitem);
    }

    /**
     * 修改周计划(家长和教育部门细化)
     *
     * @param byPlanweekitem 周计划(家长和教育部门细化)
     * @return 结果
     */
    @Override
    public int updateByPlanweekitem(ByPlanweekitem byPlanweekitem)
    {
                                                                                                                                                                                                    byPlanweekitem.setUpdateTime(DateUtils.getNowDate());
                            return byPlanweekitemMapper.updateByPlanweekitem(byPlanweekitem);
    }

    /**
     * 批量删除周计划(家长和教育部门细化)
     *
     * @param ids 需要删除的周计划(家长和教育部门细化)ID
     * @return 结果
     */
    @Override
    public int deleteByPlanweekitemByIds(Long[] ids)
    {
        return byPlanweekitemMapper.deleteByPlanweekitemByIds(ids);
    }

    /**
     * 删除周计划(家长和教育部门细化)信息
     *
     * @param id 周计划(家长和教育部门细化)ID
     * @return 结果
     */
    @Override
    public int deleteByPlanweekitemById(Long id)
    {
        return byPlanweekitemMapper.deleteByPlanweekitemById(id);
    }
}
