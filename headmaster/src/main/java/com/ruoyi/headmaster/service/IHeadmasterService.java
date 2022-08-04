package com.ruoyi.headmaster.service;

import java.util.List;
import com.ruoyi.headmaster.domain.Headmaster;
import com.ruoyi.headmaster.domain.HeadmasterVo;

/**
 * 主任信息Service接口
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
public interface IHeadmasterService 
{
    /**
     * 查询主任信息
     * 
     * @param id 主任信息主键
     * @return 主任信息
     */
    public Headmaster selectHeadmasterById(Long id);

    /**
     * 查询主任信息列表
     * 
     * @param headmaster 主任信息
     * @return 主任信息集合
     */
    public List<HeadmasterVo> selectHeadmasterList(HeadmasterVo headmaster);

    /**
     * 新增主任信息
     * 
     * @param headmaster 主任信息
     * @return 结果
     */
    public int insertHeadmaster(Headmaster headmaster);

    /**
     * 修改主任信息
     * 
     * @param headmaster 主任信息
     * @return 结果
     */
    public int updateHeadmaster(Headmaster headmaster);

    /**
     * 批量删除主任信息
     * 
     * @param ids 需要删除的主任信息主键集合
     * @return 结果
     */
    public int deleteHeadmasterByIds(Long[] ids);

    /**
     * 删除主任信息信息
     * 
     * @param id 主任信息主键
     * @return 结果
     */
    public int deleteHeadmasterById(Long id);
}
