package com.ruoyi.project.system.mapper;

import java.util.List;
import com.ruoyi.project.system.domain.BySchool;

/**
 * 幼儿园机构Mapper接口
 * 
 * @author tsbz
 * @date 2020-04-08
 */
public interface BySchoolMapper 
{
    /**
     * 查询幼儿园机构
     * 
     * @param id 幼儿园机构ID
     * @return 幼儿园机构
     */
    public BySchool selectBySchoolById(Long id);

    /**
     * 查询幼儿园机构列表
     * 
     * @param bySchool 幼儿园机构
     * @return 幼儿园机构集合
     */
    public List<BySchool> selectBySchoolList(BySchool bySchool);

    /**
     * 新增幼儿园机构
     * 
     * @param bySchool 幼儿园机构
     * @return 结果
     */
    public int insertBySchool(BySchool bySchool);

    /**
     * 修改幼儿园机构
     * 
     * @param bySchool 幼儿园机构
     * @return 结果
     */
    public int updateBySchool(BySchool bySchool);

    /**
     * 删除幼儿园机构
     * 
     * @param id 幼儿园机构ID
     * @return 结果
     */
    public int deleteBySchoolById(Long id);

    /**
     * 批量删除幼儿园机构
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBySchoolByIds(Long[] ids);
}
