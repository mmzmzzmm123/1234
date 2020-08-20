package com.ruoyi.jxjs.mapper;

import java.util.List;
import com.ruoyi.jxjs.domain.TsbzJxzxpxfa;

/**
 * 见习之星评选方案Mapper接口
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public interface TsbzJxzxpxfaMapper 
{
    /**
     * 查询见习之星评选方案
     * 
     * @param id 见习之星评选方案ID
     * @return 见习之星评选方案
     */
    public TsbzJxzxpxfa selectTsbzJxzxpxfaById(Long id);

    /**
     * 查询见习之星评选方案列表
     * 
     * @param tsbzJxzxpxfa 见习之星评选方案
     * @return 见习之星评选方案集合
     */
    public List<TsbzJxzxpxfa> selectTsbzJxzxpxfaList(TsbzJxzxpxfa tsbzJxzxpxfa);

    /**
     * 新增见习之星评选方案
     * 
     * @param tsbzJxzxpxfa 见习之星评选方案
     * @return 结果
     */
    public int insertTsbzJxzxpxfa(TsbzJxzxpxfa tsbzJxzxpxfa);

    /**
     * 修改见习之星评选方案
     * 
     * @param tsbzJxzxpxfa 见习之星评选方案
     * @return 结果
     */
    public int updateTsbzJxzxpxfa(TsbzJxzxpxfa tsbzJxzxpxfa);

    /**
     * 删除见习之星评选方案
     * 
     * @param id 见习之星评选方案ID
     * @return 结果
     */
    public int deleteTsbzJxzxpxfaById(Long id);

    /**
     * 批量删除见习之星评选方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxpxfaByIds(Long[] ids);
}
