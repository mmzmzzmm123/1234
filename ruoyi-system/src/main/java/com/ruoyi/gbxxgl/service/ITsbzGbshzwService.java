package com.ruoyi.gbxxgl.service;

import java.util.List;
import com.ruoyi.gbxxgl.domain.TsbzGbshzw;

/**
 * 干部任职情况-社会职务Service接口
 * 
 * @author ruoyi
 * @date 2020-12-08
 */
public interface ITsbzGbshzwService 
{
    /**
     * 查询干部任职情况-社会职务
     * 
     * @param id 干部任职情况-社会职务ID
     * @return 干部任职情况-社会职务
     */
    public TsbzGbshzw selectTsbzGbshzwById(Long id);

    /**
     * 查询干部任职情况-社会职务列表
     * 
     * @param tsbzGbshzw 干部任职情况-社会职务
     * @return 干部任职情况-社会职务集合
     */
    public List<TsbzGbshzw> selectTsbzGbshzwList(TsbzGbshzw tsbzGbshzw);

    /**
     * 新增干部任职情况-社会职务
     * 
     * @param tsbzGbshzw 干部任职情况-社会职务
     * @return 结果
     */
    public int insertTsbzGbshzw(TsbzGbshzw tsbzGbshzw);

    /**
     * 修改干部任职情况-社会职务
     * 
     * @param tsbzGbshzw 干部任职情况-社会职务
     * @return 结果
     */
    public int updateTsbzGbshzw(TsbzGbshzw tsbzGbshzw);

    /**
     * 批量删除干部任职情况-社会职务
     * 
     * @param ids 需要删除的干部任职情况-社会职务ID
     * @return 结果
     */
    public int deleteTsbzGbshzwByIds(Long[] ids);

    /**
     * 删除干部任职情况-社会职务信息
     * 
     * @param id 干部任职情况-社会职务ID
     * @return 结果
     */
    public int deleteTsbzGbshzwById(Long id);
}
