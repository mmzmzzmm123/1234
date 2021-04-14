package com.ruoyi.zcrpsgl.mapper;

import com.ruoyi.zcrpsgl.domain.TsbzZcrpsfa;

import java.util.List;

/**
 * 评审方案(主持人评审管理-评审方案)Mapper接口
 * 
 * @author tsbz
 * @date 2021-04-14
 */
public interface TsbzZcrpsfaMapper 
{
    /**
     * 查询评审方案(主持人评审管理-评审方案)
     * 
     * @param id 评审方案(主持人评审管理-评审方案)ID
     * @return 评审方案(主持人评审管理-评审方案)
     */
    public TsbzZcrpsfa selectTsbzZcrpsfaById(Long id);

    /**
     * 查询评审方案(主持人评审管理-评审方案)列表
     * 
     * @param tsbzZcrpsfa 评审方案(主持人评审管理-评审方案)
     * @return 评审方案(主持人评审管理-评审方案)集合
     */
    public List<TsbzZcrpsfa> selectTsbzZcrpsfaList(TsbzZcrpsfa tsbzZcrpsfa);

    /**
     * 新增评审方案(主持人评审管理-评审方案)
     * 
     * @param tsbzZcrpsfa 评审方案(主持人评审管理-评审方案)
     * @return 结果
     */
    public int insertTsbzZcrpsfa(TsbzZcrpsfa tsbzZcrpsfa);

    /**
     * 修改评审方案(主持人评审管理-评审方案)
     * 
     * @param tsbzZcrpsfa 评审方案(主持人评审管理-评审方案)
     * @return 结果
     */
    public int updateTsbzZcrpsfa(TsbzZcrpsfa tsbzZcrpsfa);

    /**
     * 删除评审方案(主持人评审管理-评审方案)
     * 
     * @param id 评审方案(主持人评审管理-评审方案)ID
     * @return 结果
     */
    public int deleteTsbzZcrpsfaById(Long id);

    /**
     * 批量删除评审方案(主持人评审管理-评审方案)
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzZcrpsfaByIds(Long[] ids);
}
