package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByTrainVideo;

/**
 * 培训Service接口
 * 
 * @author tsbz
 * @date 2020-05-25
 */
public interface IByTrainVideoService 
{
    /**
     * 查询培训
     * 
     * @param id 培训ID
     * @return 培训
     */
    public ByTrainVideo selectByTrainVideoById(Long id);

    /**
     * 查询培训列表
     * 
     * @param byTrainVideo 培训
     * @return 培训集合
     */
    public List<ByTrainVideo> selectByTrainVideoList(ByTrainVideo byTrainVideo);

    /**
     * 新增培训
     * 
     * @param byTrainVideo 培训
     * @return 结果
     */
    public int insertByTrainVideo(ByTrainVideo byTrainVideo);

    /**
     * 修改培训
     * 
     * @param byTrainVideo 培训
     * @return 结果
     */
    public int updateByTrainVideo(ByTrainVideo byTrainVideo);

    /**
     * 批量删除培训
     * 
     * @param ids 需要删除的培训ID
     * @return 结果
     */
    public int deleteByTrainVideoByIds(Long[] ids);

    /**
     * 删除培训信息
     * 
     * @param id 培训ID
     * @return 结果
     */
    public int deleteByTrainVideoById(Long id);
}
