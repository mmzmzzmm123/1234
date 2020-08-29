package com.ruoyi.jxzxkhgl.service;

import java.util.List;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhfa;

/**
 * 见习之星考核方案Service接口
 * 
 * @author ruoyi
 * @date 2020-08-29
 */
public interface ITsbzJxzxkhfaService 
{
    /**
     * 查询见习之星考核方案
     * 
     * @param id 见习之星考核方案ID
     * @return 见习之星考核方案
     */
    public TsbzJxzxkhfa selectTsbzJxzxkhfaById(Long id);

    /**
     * 查询见习之星考核方案列表
     * 
     * @param tsbzJxzxkhfa 见习之星考核方案
     * @return 见习之星考核方案集合
     */
    public List<TsbzJxzxkhfa> selectTsbzJxzxkhfaList(TsbzJxzxkhfa tsbzJxzxkhfa);

    /**
     * 新增见习之星考核方案
     * 
     * @param tsbzJxzxkhfa 见习之星考核方案
     * @return 结果
     */
    public int insertTsbzJxzxkhfa(TsbzJxzxkhfa tsbzJxzxkhfa);

    /**
     * 修改见习之星考核方案
     * 
     * @param tsbzJxzxkhfa 见习之星考核方案
     * @return 结果
     */
    public int updateTsbzJxzxkhfa(TsbzJxzxkhfa tsbzJxzxkhfa);

    /**
     * 批量删除见习之星考核方案
     * 
     * @param ids 需要删除的见习之星考核方案ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhfaByIds(Long[] ids);

    /**
     * 删除见习之星考核方案信息
     * 
     * @param id 见习之星考核方案ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhfaById(Long id);
}
