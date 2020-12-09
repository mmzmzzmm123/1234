package com.ruoyi.gbxxgl.service;

import java.util.List;
import com.ruoyi.gbxxgl.domain.TsbzGbcrjqk;

/**
 * 干部出入境情况Service接口
 * 
 * @author ruoyi
 * @date 2020-12-08
 */
public interface ITsbzGbcrjqkService 
{
    /**
     * 查询干部出入境情况
     * 
     * @param id 干部出入境情况ID
     * @return 干部出入境情况
     */
    public TsbzGbcrjqk selectTsbzGbcrjqkById(Long id);

    /**
     * 查询干部出入境情况列表
     * 
     * @param tsbzGbcrjqk 干部出入境情况
     * @return 干部出入境情况集合
     */
    public List<TsbzGbcrjqk> selectTsbzGbcrjqkList(TsbzGbcrjqk tsbzGbcrjqk);

    /**
     * 新增干部出入境情况
     * 
     * @param tsbzGbcrjqk 干部出入境情况
     * @return 结果
     */
    public int insertTsbzGbcrjqk(TsbzGbcrjqk tsbzGbcrjqk);

    /**
     * 修改干部出入境情况
     * 
     * @param tsbzGbcrjqk 干部出入境情况
     * @return 结果
     */
    public int updateTsbzGbcrjqk(TsbzGbcrjqk tsbzGbcrjqk);

    /**
     * 批量删除干部出入境情况
     * 
     * @param ids 需要删除的干部出入境情况ID
     * @return 结果
     */
    public int deleteTsbzGbcrjqkByIds(Long[] ids);

    /**
     * 删除干部出入境情况信息
     * 
     * @param id 干部出入境情况ID
     * @return 结果
     */
    public int deleteTsbzGbcrjqkById(Long id);
}
