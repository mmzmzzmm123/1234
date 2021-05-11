package com.ruoyi.jdgl.service;

import java.util.List;
import com.ruoyi.jdgl.domain.TsbzJdglzdjs;

/**
 * 基地管理制度建设Service接口
 * 
 * @author tsbz
 * @date 2021-05-11
 */
public interface ITsbzJdglzdjsService 
{
    /**
     * 查询基地管理制度建设
     * 
     * @param id 基地管理制度建设ID
     * @return 基地管理制度建设
     */
    public TsbzJdglzdjs selectTsbzJdglzdjsById(Long id);

    /**
     * 查询基地管理制度建设列表
     * 
     * @param tsbzJdglzdjs 基地管理制度建设
     * @return 基地管理制度建设集合
     */
    public List<TsbzJdglzdjs> selectTsbzJdglzdjsList(TsbzJdglzdjs tsbzJdglzdjs);

    /**
     * 新增基地管理制度建设
     * 
     * @param tsbzJdglzdjs 基地管理制度建设
     * @return 结果
     */
    public int insertTsbzJdglzdjs(TsbzJdglzdjs tsbzJdglzdjs);

    /**
     * 修改基地管理制度建设
     * 
     * @param tsbzJdglzdjs 基地管理制度建设
     * @return 结果
     */
    public int updateTsbzJdglzdjs(TsbzJdglzdjs tsbzJdglzdjs);

    /**
     * 批量删除基地管理制度建设
     * 
     * @param ids 需要删除的基地管理制度建设ID
     * @return 结果
     */
    public int deleteTsbzJdglzdjsByIds(Long[] ids);

    /**
     * 删除基地管理制度建设信息
     * 
     * @param id 基地管理制度建设ID
     * @return 结果
     */
    public int deleteTsbzJdglzdjsById(Long id);
}
