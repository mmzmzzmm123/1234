package com.ruoyi.xypsgl.service;

import java.util.List;
import com.ruoyi.xypsgl.domain.TsbzXypsfa;

/**
 * 学员评审方案Service接口
 * 
 * @author tsbz
 * @date 2021-04-22
 */
public interface ITsbzXypsfaService 
{
    /**
     * 查询学员评审方案
     * 
     * @param id 学员评审方案ID
     * @return 学员评审方案
     */
    public TsbzXypsfa selectTsbzXypsfaById(Long id);

    /**
     * 查询学员评审方案列表
     * 
     * @param tsbzXypsfa 学员评审方案
     * @return 学员评审方案集合
     */
    public List<TsbzXypsfa> selectTsbzXypsfaList(TsbzXypsfa tsbzXypsfa);

    /**
     * 新增学员评审方案
     * 
     * @param tsbzXypsfa 学员评审方案
     * @return 结果
     */
    public int insertTsbzXypsfa(TsbzXypsfa tsbzXypsfa);

    /**
     * 修改学员评审方案
     * 
     * @param tsbzXypsfa 学员评审方案
     * @return 结果
     */
    public int updateTsbzXypsfa(TsbzXypsfa tsbzXypsfa);

    /**
     * 批量删除学员评审方案
     * 
     * @param ids 需要删除的学员评审方案ID
     * @return 结果
     */
    public int deleteTsbzXypsfaByIds(Long[] ids);

    /**
     * 删除学员评审方案信息
     * 
     * @param id 学员评审方案ID
     * @return 结果
     */
    public int deleteTsbzXypsfaById(Long id);
}
