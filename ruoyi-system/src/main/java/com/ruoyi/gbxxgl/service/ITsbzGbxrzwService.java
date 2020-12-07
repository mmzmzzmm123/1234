package com.ruoyi.gbxxgl.service;

import java.util.List;
import com.ruoyi.gbxxgl.domain.TsbzGbxrzw;

/**
 * 干部任职情况-现任职务Service接口
 * 
 * @author ruoyi
 * @date 2020-12-07
 */
public interface ITsbzGbxrzwService 
{
    /**
     * 查询干部任职情况-现任职务
     * 
     * @param id 干部任职情况-现任职务ID
     * @return 干部任职情况-现任职务
     */
    public TsbzGbxrzw selectTsbzGbxrzwById(Long id);

    /**
     * 查询干部任职情况-现任职务列表
     * 
     * @param tsbzGbxrzw 干部任职情况-现任职务
     * @return 干部任职情况-现任职务集合
     */
    public List<TsbzGbxrzw> selectTsbzGbxrzwList(TsbzGbxrzw tsbzGbxrzw);

    /**
     * 新增干部任职情况-现任职务
     * 
     * @param tsbzGbxrzw 干部任职情况-现任职务
     * @return 结果
     */
    public int insertTsbzGbxrzw(TsbzGbxrzw tsbzGbxrzw);

    /**
     * 修改干部任职情况-现任职务
     * 
     * @param tsbzGbxrzw 干部任职情况-现任职务
     * @return 结果
     */
    public int updateTsbzGbxrzw(TsbzGbxrzw tsbzGbxrzw);

    /**
     * 批量删除干部任职情况-现任职务
     * 
     * @param ids 需要删除的干部任职情况-现任职务ID
     * @return 结果
     */
    public int deleteTsbzGbxrzwByIds(Long[] ids);

    /**
     * 删除干部任职情况-现任职务信息
     * 
     * @param id 干部任职情况-现任职务ID
     * @return 结果
     */
    public int deleteTsbzGbxrzwById(Long id);
}
