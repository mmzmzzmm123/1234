package com.ruoyi.zcrpsgl.mapper;

import java.util.List;
import com.ruoyi.zcrpsgl.domain.TsbzZcrzjzmd;

/**
 * 主持人报名专家组名单Mapper接口
 * 
 * @author tsbz
 * @date 2021-04-21
 */
public interface TsbzZcrzjzmdMapper 
{
    /**
     * 查询主持人报名专家组名单
     * 
     * @param id 主持人报名专家组名单ID
     * @return 主持人报名专家组名单
     */
    public TsbzZcrzjzmd selectTsbzZcrzjzmdById(Long id);

    /**
     * 查询主持人报名专家组名单列表
     * 
     * @param tsbzZcrzjzmd 主持人报名专家组名单
     * @return 主持人报名专家组名单集合
     */
    public List<TsbzZcrzjzmd> selectTsbzZcrzjzmdList(TsbzZcrzjzmd tsbzZcrzjzmd);

    /**
     * 新增主持人报名专家组名单
     * 
     * @param tsbzZcrzjzmd 主持人报名专家组名单
     * @return 结果
     */
    public int insertTsbzZcrzjzmd(TsbzZcrzjzmd tsbzZcrzjzmd);

    /**
     * 修改主持人报名专家组名单
     * 
     * @param tsbzZcrzjzmd 主持人报名专家组名单
     * @return 结果
     */
    public int updateTsbzZcrzjzmd(TsbzZcrzjzmd tsbzZcrzjzmd);

    /**
     * 删除主持人报名专家组名单
     * 
     * @param id 主持人报名专家组名单ID
     * @return 结果
     */
    public int deleteTsbzZcrzjzmdById(Long id);

    /**
     * 批量删除主持人报名专家组名单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzZcrzjzmdByIds(Long[] ids);
}
