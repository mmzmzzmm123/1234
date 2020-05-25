package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByTrainVideoMapper;
import com.ruoyi.project.benyi.domain.ByTrainVideo;
import com.ruoyi.project.benyi.service.IByTrainVideoService;

/**
 * 培训Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-25
 */
@Service
public class ByTrainVideoServiceImpl implements IByTrainVideoService 
{
    @Autowired
    private ByTrainVideoMapper byTrainVideoMapper;

    /**
     * 查询培训
     * 
     * @param id 培训ID
     * @return 培训
     */
    @Override
    public ByTrainVideo selectByTrainVideoById(Long id)
    {
        return byTrainVideoMapper.selectByTrainVideoById(id);
    }

    /**
     * 查询培训列表
     * 
     * @param byTrainVideo 培训
     * @return 培训
     */
    @Override
    public List<ByTrainVideo> selectByTrainVideoList(ByTrainVideo byTrainVideo)
    {
        return byTrainVideoMapper.selectByTrainVideoList(byTrainVideo);
    }

    /**
     * 新增培训
     * 
     * @param byTrainVideo 培训
     * @return 结果
     */
    @Override
    public int insertByTrainVideo(ByTrainVideo byTrainVideo)
    {
        return byTrainVideoMapper.insertByTrainVideo(byTrainVideo);
    }

    /**
     * 修改培训
     * 
     * @param byTrainVideo 培训
     * @return 结果
     */
    @Override
    public int updateByTrainVideo(ByTrainVideo byTrainVideo)
    {
        return byTrainVideoMapper.updateByTrainVideo(byTrainVideo);
    }

    /**
     * 批量删除培训
     * 
     * @param ids 需要删除的培训ID
     * @return 结果
     */
    @Override
    public int deleteByTrainVideoByIds(Long[] ids)
    {
        return byTrainVideoMapper.deleteByTrainVideoByIds(ids);
    }

    /**
     * 删除培训信息
     * 
     * @param id 培训ID
     * @return 结果
     */
    @Override
    public int deleteByTrainVideoById(Long id)
    {
        return byTrainVideoMapper.deleteByTrainVideoById(id);
    }
}
