package com.ruoyi.autoee.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.autoee.mapper.ASchoolMapper;
import com.ruoyi.autoee.domain.ASchool;
import com.ruoyi.autoee.service.IASchoolService;

/**
 * 学校管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@Service
public class ASchoolServiceImpl implements IASchoolService 
{
    @Autowired
    private ASchoolMapper aSchoolMapper;

    /**
     * 查询学校管理
     * 
     * @param id 学校管理主键
     * @return 学校管理
     */
    @Override
    public ASchool selectASchoolById(Long id)
    {
        return aSchoolMapper.selectASchoolById(id);
    }

    /**
     * 查询学校管理列表
     * 
     * @param aSchool 学校管理
     * @return 学校管理
     */
    @Override
    public List<ASchool> selectASchoolList(ASchool aSchool)
    {
        return aSchoolMapper.selectASchoolList(aSchool);
    }

    /**
     * 新增学校管理
     * 
     * @param aSchool 学校管理
     * @return 结果
     */
    @Override
    public int insertASchool(ASchool aSchool)
    {
        aSchool.setCreateTime(DateUtils.getNowDate());
		aSchool.setUpdateTime(aSchool.getCreateTime());
        return aSchoolMapper.insertASchool(aSchool);
    }

    /**
     * 修改学校管理
     * 
     * @param aSchool 学校管理
     * @return 结果
     */
    @Override
    public int updateASchool(ASchool aSchool)
    {
        aSchool.setUpdateTime(DateUtils.getNowDate());
        return aSchoolMapper.updateASchool(aSchool);
    }

    /**
     * 批量删除学校管理
     * 
     * @param ids 需要删除的学校管理主键
     * @return 结果
     */
    @Override
    public int deleteASchoolByIds(Long[] ids)
    {
        return aSchoolMapper.deleteASchoolByIds(ids);
    }

    /**
     * 删除学校管理信息
     * 
     * @param id 学校管理主键
     * @return 结果
     */
    @Override
    public int deleteASchoolById(Long id)
    {
        return aSchoolMapper.deleteASchoolById(id);
    }
}
