package com.ruoyi.headmaster.service.impl;

import java.util.List;

import com.ruoyi.headmaster.domain.HeadmasterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.headmaster.mapper.HeadmasterMapper;
import com.ruoyi.headmaster.domain.Headmaster;
import com.ruoyi.headmaster.service.IHeadmasterService;

/**
 * 主任信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
@Service
public class HeadmasterServiceImpl implements IHeadmasterService 
{
    @Autowired
    private HeadmasterMapper headmasterMapper;

    /**
     * 查询主任信息
     * 
     * @param id 主任信息主键
     * @return 主任信息
     */
    @Override
    public Headmaster selectHeadmasterById(Long id)
    {
        return headmasterMapper.selectHeadmasterById(id);
    }

    /**
     * 查询主任信息列表
     * 
     * @param headmaster 主任信息
     * @return 主任信息
     */
    @Override
    public List<HeadmasterVo> selectHeadmasterList(HeadmasterVo headmaster)
    {
        return headmasterMapper.selectHeadmasterList(headmaster);
    }

    /**
     * 新增主任信息
     * 
     * @param headmaster 主任信息
     * @return 结果
     */
    @Override
    public int insertHeadmaster(Headmaster headmaster)
    {
        return headmasterMapper.insertHeadmaster(headmaster);
    }

    /**
     * 修改主任信息
     * 
     * @param headmaster 主任信息
     * @return 结果
     */
    @Override
    public int updateHeadmaster(Headmaster headmaster)
    {
        return headmasterMapper.updateHeadmaster(headmaster);
    }

    /**
     * 批量删除主任信息
     * 
     * @param ids 需要删除的主任信息主键
     * @return 结果
     */
    @Override
    public int deleteHeadmasterByIds(Long[] ids)
    {
        return headmasterMapper.deleteHeadmasterByIds(ids);
    }

    /**
     * 删除主任信息信息
     * 
     * @param id 主任信息主键
     * @return 结果
     */
    @Override
    public int deleteHeadmasterById(Long id)
    {
        return headmasterMapper.deleteHeadmasterById(id);
    }
}
