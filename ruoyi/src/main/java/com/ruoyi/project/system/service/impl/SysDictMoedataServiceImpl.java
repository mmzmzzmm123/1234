package com.ruoyi.project.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.SysDictMoedataMapper;
import com.ruoyi.project.system.domain.SysDictMoedata;
import com.ruoyi.project.system.service.ISysDictMoedataService;

/**
 * 多级字典管理Service业务层处理
 *
 * @author tsbz
 * @date 2020-05-28
 */
@Service
public class SysDictMoedataServiceImpl implements ISysDictMoedataService
{
    @Autowired
    private SysDictMoedataMapper sysDictMoedataMapper;

    /**
     * 查询多级字典管理
     *
     * @param id 多级字典管理ID
     * @return 多级字典管理
     */
    @Override
    public SysDictMoedata selectSysDictMoedataById(Long id)
    {
        return sysDictMoedataMapper.selectSysDictMoedataById(id);
    }

    /**
     * 查询多级字典管理列表
     *
     * @param sysDictMoedata 多级字典管理
     * @return 多级字典管理
     */
    @Override
    public List<SysDictMoedata> selectSysDictMoedataList(SysDictMoedata sysDictMoedata)
    {
        return sysDictMoedataMapper.selectSysDictMoedataList(sysDictMoedata);
    }

    /**
     * 新增多级字典管理
     *
     * @param sysDictMoedata 多级字典管理
     * @return 结果
     */
    @Override
    public int insertSysDictMoedata(SysDictMoedata sysDictMoedata)
    {
        sysDictMoedata.setCreateTime(DateUtils.getNowDate());
        return sysDictMoedataMapper.insertSysDictMoedata(sysDictMoedata);
    }

    /**
     * 修改多级字典管理
     *
     * @param sysDictMoedata 多级字典管理
     * @return 结果
     */
    @Override
    public int updateSysDictMoedata(SysDictMoedata sysDictMoedata)
    {
        return sysDictMoedataMapper.updateSysDictMoedata(sysDictMoedata);
    }

    /**
     * 批量删除多级字典管理
     *
     * @param ids 需要删除的多级字典管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDictMoedataByIds(Long[] ids)
    {
        return sysDictMoedataMapper.deleteSysDictMoedataByIds(ids);
    }

    /**
     * 删除多级字典管理信息
     *
     * @param id 多级字典管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDictMoedataById(Long id)
    {
        return sysDictMoedataMapper.deleteSysDictMoedataById(id);
    }
}
