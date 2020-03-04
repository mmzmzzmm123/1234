package com.ruoyi.project.mr.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mr.base.mapper.DataIdiomMapper;
import com.ruoyi.project.mr.base.domain.DataIdiom;
import com.ruoyi.project.mr.base.service.IDataIdiomService;

/**
 * 成语存放Service业务层处理
 *
 * @author mr
 * @date 2020-03-03
 */
@Service
public class DataIdiomServiceImpl implements IDataIdiomService
{
    @Autowired
    private DataIdiomMapper dataIdiomMapper;

    /**
     * 查询成语存放
     *
     * @param id 成语存放ID
     * @return 成语存放
     */
    @Override
    public DataIdiom selectDataIdiomById(Long id)
    {
        return dataIdiomMapper.selectDataIdiomById(id);
    }

    /**
     * 查询成语存放
     *
     * @param name 成语存放Name
     * @return 成语存放
     */
    @Override
    public DataIdiom selectDataIdiomByName(String name)
    {
        return dataIdiomMapper.selectDataIdiomByName(name);
    }

    /**
     * 查询成语存放列表
     *
     * @param dataIdiom 成语存放
     * @return 成语存放
     */
    @Override
    public List<DataIdiom> selectDataIdiomList(DataIdiom dataIdiom)
    {
        return dataIdiomMapper.selectDataIdiomList(dataIdiom);
    }

    /**
     * 新增成语存放
     *
     * @param dataIdiom 成语存放
     * @return 结果
     */
    @Override
    public int insertDataIdiom(DataIdiom dataIdiom)
    {
        dataIdiom.setCreateTime(DateUtils.getNowDate());
        return dataIdiomMapper.insertDataIdiom(dataIdiom);
    }

    /**
     * 修改成语存放
     *
     * @param dataIdiom 成语存放
     * @return 结果
     */
    @Override
    public int updateDataIdiom(DataIdiom dataIdiom)
    {
        dataIdiom.setUpdateTime(DateUtils.getNowDate());
        return dataIdiomMapper.updateDataIdiom(dataIdiom);
    }

    /**
     * 批量删除成语存放
     *
     * @param ids 需要删除的成语存放ID
     * @return 结果
     */
    @Override
    public int deleteDataIdiomByIds(Long[] ids)
    {
        return dataIdiomMapper.deleteDataIdiomByIds(ids);
    }

    /**
     * 删除成语存放信息
     *
     * @param id 成语存放ID
     * @return 结果
     */
    @Override
    public int deleteDataIdiomById(Long id)
    {
        return dataIdiomMapper.deleteDataIdiomById(id);
    }
}
