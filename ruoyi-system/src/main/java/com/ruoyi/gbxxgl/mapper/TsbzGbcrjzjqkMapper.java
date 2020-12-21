package com.ruoyi.gbxxgl.mapper;

import java.util.List;
import com.ruoyi.gbxxgl.domain.TsbzGbcrjzjqk;

/**
 * 干部出入境证件情况Mapper接口
 * 
 * @author ruoyi
 * @date 2020-12-21
 */
public interface TsbzGbcrjzjqkMapper 
{
    /**
     * 查询干部出入境证件情况
     * 
     * @param id 干部出入境证件情况ID
     * @return 干部出入境证件情况
     */
    public TsbzGbcrjzjqk selectTsbzGbcrjzjqkById(Long id);

    /**
     * 查询干部出入境证件情况列表
     * 
     * @param tsbzGbcrjzjqk 干部出入境证件情况
     * @return 干部出入境证件情况集合
     */
    public List<TsbzGbcrjzjqk> selectTsbzGbcrjzjqkList(TsbzGbcrjzjqk tsbzGbcrjzjqk);

    /**
     * 新增干部出入境证件情况
     * 
     * @param tsbzGbcrjzjqk 干部出入境证件情况
     * @return 结果
     */
    public int insertTsbzGbcrjzjqk(TsbzGbcrjzjqk tsbzGbcrjzjqk);

    /**
     * 修改干部出入境证件情况
     * 
     * @param tsbzGbcrjzjqk 干部出入境证件情况
     * @return 结果
     */
    public int updateTsbzGbcrjzjqk(TsbzGbcrjzjqk tsbzGbcrjzjqk);

    /**
     * 删除干部出入境证件情况
     * 
     * @param id 干部出入境证件情况ID
     * @return 结果
     */
    public int deleteTsbzGbcrjzjqkById(Long id);

    /**
     * 批量删除干部出入境证件情况
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzGbcrjzjqkByIds(Long[] ids);
}
