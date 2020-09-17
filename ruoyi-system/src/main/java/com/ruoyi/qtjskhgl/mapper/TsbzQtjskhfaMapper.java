package com.ruoyi.qtjskhgl.mapper;

import java.util.List;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhfa;

/**
 * 群体教师考核方案Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
public interface TsbzQtjskhfaMapper 
{
    /**
     * 查询群体教师考核方案
     * 
     * @param id 群体教师考核方案ID
     * @return 群体教师考核方案
     */
    public TsbzQtjskhfa selectTsbzQtjskhfaById(Long id);

    /**
     * 查询群体教师考核方案列表
     * 
     * @param tsbzQtjskhfa 群体教师考核方案
     * @return 群体教师考核方案集合
     */
    public List<TsbzQtjskhfa> selectTsbzQtjskhfaList(TsbzQtjskhfa tsbzQtjskhfa);

    /**
     * 新增群体教师考核方案
     * 
     * @param tsbzQtjskhfa 群体教师考核方案
     * @return 结果
     */
    public int insertTsbzQtjskhfa(TsbzQtjskhfa tsbzQtjskhfa);

    /**
     * 修改群体教师考核方案
     * 
     * @param tsbzQtjskhfa 群体教师考核方案
     * @return 结果
     */
    public int updateTsbzQtjskhfa(TsbzQtjskhfa tsbzQtjskhfa);

    /**
     * 删除群体教师考核方案
     * 
     * @param id 群体教师考核方案ID
     * @return 结果
     */
    public int deleteTsbzQtjskhfaById(Long id);

    /**
     * 批量删除群体教师考核方案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzQtjskhfaByIds(Long[] ids);
}
