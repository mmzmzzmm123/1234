package com.ruoyi.gbxxgl.service;

import java.util.List;
import com.ruoyi.gbxxgl.domain.TsbzGbjbqk;

/**
 * 干部基本情况Service接口
 * 
 * @author ruoyi
 * @date 2020-12-02
 */
public interface ITsbzGbjbqkService 
{
    /**
     * 查询干部基本情况
     * 
     * @param id 干部基本情况ID
     * @return 干部基本情况
     */
    public TsbzGbjbqk selectTsbzGbjbqkById(Long id);

    /**
     * 查询干部基本情况列表
     * 
     * @param tsbzGbjbqk 干部基本情况
     * @return 干部基本情况集合
     */
    public List<TsbzGbjbqk> selectTsbzGbjbqkList(TsbzGbjbqk tsbzGbjbqk);

    /**
     * 新增干部基本情况
     * 
     * @param tsbzGbjbqk 干部基本情况
     * @return 结果
     */
    public int insertTsbzGbjbqk(TsbzGbjbqk tsbzGbjbqk);

    /**
     * 修改干部基本情况
     * 
     * @param tsbzGbjbqk 干部基本情况
     * @return 结果
     */
    public int updateTsbzGbjbqk(TsbzGbjbqk tsbzGbjbqk);

    /**
     * 批量删除干部基本情况
     * 
     * @param ids 需要删除的干部基本情况ID
     * @return 结果
     */
    public int deleteTsbzGbjbqkByIds(Long[] ids);

    /**
     * 删除干部基本情况信息
     * 
     * @param id 干部基本情况ID
     * @return 结果
     */
    public int deleteTsbzGbjbqkById(Long id);
}
