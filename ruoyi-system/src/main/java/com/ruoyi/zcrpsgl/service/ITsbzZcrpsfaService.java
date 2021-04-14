package com.ruoyi.zcrpsgl.service;

import com.ruoyi.zcrpsgl.domain.TsbzZcrpsfa;

import java.util.List;

/**
 * 评审方案(主持人评审管理-评审方案)Service接口
 * 
 * @author tsbz
 * @date 2021-04-14
 */
public interface ITsbzZcrpsfaService 
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
     * 批量删除评审方案(主持人评审管理-评审方案)
     * 
     * @param ids 需要删除的评审方案(主持人评审管理-评审方案)ID
     * @return 结果
     */
    public int deleteTsbzZcrpsfaByIds(Long[] ids);

    /**
     * 删除评审方案(主持人评审管理-评审方案)信息
     * 
     * @param id 评审方案(主持人评审管理-评审方案)ID
     * @return 结果
     */
    public int deleteTsbzZcrpsfaById(Long id);


    /**
     * 启用评审方案(主持人评审管理-评审方案)信息
     *
     * @param id 评审方案(主持人评审管理-评审方案)ID
     * @return 结果
     */
    public int openstatus(Long id);

    /**
     * 停止评审方案(主持人评审管理-评审方案)信息
     *
     * @param id 评审方案(主持人评审管理-评审方案)ID
     * @return 结果
     */
    public int stopstatus(Long id);

}
