package com.ruoyi.jxzxkhgl.mapper;

import java.util.List;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhsh;

/**
 * 考核审核过程Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-01
 */
public interface TsbzJxzxkhshMapper 
{
    /**
     * 查询考核审核过程
     * 
     * @param id 考核审核过程ID
     * @return 考核审核过程
     */
    public TsbzJxzxkhsh selectTsbzJxzxkhshById(Long id);

    /**
     * 查询考核审核过程列表
     * 
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 考核审核过程集合
     */
    public List<TsbzJxzxkhsh> selectTsbzJxzxkhshList(TsbzJxzxkhsh tsbzJxzxkhsh);

    /**
     * 新增考核审核过程
     * 
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    public int insertTsbzJxzxkhsh(TsbzJxzxkhsh tsbzJxzxkhsh);

    /**
     * 修改考核审核过程
     * 
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    public int updateTsbzJxzxkhsh(TsbzJxzxkhsh tsbzJxzxkhsh);

    /**
     * 删除考核审核过程
     * 
     * @param id 考核审核过程ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhshById(Long id);

    /**
     * 批量删除考核审核过程
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhshByIds(Long[] ids);
}
