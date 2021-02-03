package com.gox.system.service.impl;

import java.util.List;

import com.gox.common.utils.uuid.SnowflakesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.PropsMapper;
import com.gox.system.domain.form.Props;
import com.gox.system.service.IPropsService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class PropsServiceImpl implements IPropsService 
{
    @Autowired
    private PropsMapper propsMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Props selectPropsById(Long id)
    {
        return propsMapper.selectPropsById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param props 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Props> selectPropsList(Props props)
    {
        return propsMapper.selectPropsList(props);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param props 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProps(Props props)
    {
        Long proId= SnowflakesTools.WORKER.nextId();
        props.setId(proId);
        Props props1 =props.getProps();
        if (props1!=null){
            props1.setProId(proId);
            insertProps(props1);
        }
        return propsMapper.insertProps(props);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param props 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProps(Props props)
    {
        return propsMapper.updateProps(props);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deletePropsByIds(Long[] ids)
    {
        return propsMapper.deletePropsByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deletePropsById(Long id)
    {
        return propsMapper.deletePropsById(id);
    }
}
