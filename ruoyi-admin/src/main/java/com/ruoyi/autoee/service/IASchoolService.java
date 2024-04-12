package com.ruoyi.autoee.service;

import java.util.List;
import com.ruoyi.autoee.domain.ASchool;

/**
 * 学校管理Service接口
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
public interface IASchoolService 
{
    /**
     * 查询学校管理
     * 
     * @param id 学校管理主键
     * @return 学校管理
     */
    public ASchool selectASchoolById(Long id);

    /**
     * 查询学校管理列表
     * 
     * @param aSchool 学校管理
     * @return 学校管理集合
     */
    public List<ASchool> selectASchoolList(ASchool aSchool);

    /**
     * 新增学校管理
     * 
     * @param aSchool 学校管理
     * @return 结果
     */
    public int insertASchool(ASchool aSchool);

    /**
     * 修改学校管理
     * 
     * @param aSchool 学校管理
     * @return 结果
     */
    public int updateASchool(ASchool aSchool);

    /**
     * 批量删除学校管理
     * 
     * @param ids 需要删除的学校管理主键集合
     * @return 结果
     */
    public int deleteASchoolByIds(Long[] ids);

    /**
     * 删除学校管理信息
     * 
     * @param id 学校管理主键
     * @return 结果
     */
    public int deleteASchoolById(Long id);
}
