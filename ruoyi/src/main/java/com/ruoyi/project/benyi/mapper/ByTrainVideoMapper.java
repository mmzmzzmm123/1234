package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByTrainVideo;

/**
 * 培训Mapper接口
 * 
 * @author tsbz
 * @date 2020-05-25
 */
public interface ByTrainVideoMapper 
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
     * 删除培训
     * 
     * @param id 培训ID
     * @return 结果
     */
    public int deleteByTrainVideoById(Long id);

    /**
     * 批量删除培训
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByTrainVideoByIds(Long[] ids);
}
