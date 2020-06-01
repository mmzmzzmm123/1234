package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByTrainVideoScoreMapper;
import com.ruoyi.project.benyi.domain.ByTrainVideoScore;
import com.ruoyi.project.benyi.service.IByTrainVideoScoreService;

/**
 * 培训视频评分Service业务层处理
 *
 * @author tsbz
 * @date 2020-06-01
 */
@Service
public class ByTrainVideoScoreServiceImpl implements IByTrainVideoScoreService
{
    @Autowired
    private ByTrainVideoScoreMapper byTrainVideoScoreMapper;

    /**
     * 查询培训视频评分
     *
     * @param id 培训视频评分ID
     * @return 培训视频评分
     */
    @Override
    public ByTrainVideoScore selectByTrainVideoScoreById(Long id)
    {
        return byTrainVideoScoreMapper.selectByTrainVideoScoreById(id);
    }

    /**
     * 查询培训视频评分列表
     *
     * @param byTrainVideoScore 培训视频评分
     * @return 培训视频评分
     */
    @Override
    public List<ByTrainVideoScore> selectByTrainVideoScoreList(ByTrainVideoScore byTrainVideoScore)
    {
        return byTrainVideoScoreMapper.selectByTrainVideoScoreList(byTrainVideoScore);
    }

    /**
     * 新增培训视频评分
     *
     * @param byTrainVideoScore 培训视频评分
     * @return 结果
     */
    @Override
    public int insertByTrainVideoScore(ByTrainVideoScore byTrainVideoScore)
    {
        return byTrainVideoScoreMapper.insertByTrainVideoScore(byTrainVideoScore);
    }

    /**
     * 修改培训视频评分
     *
     * @param byTrainVideoScore 培训视频评分
     * @return 结果
     */
    @Override
    public int updateByTrainVideoScore(ByTrainVideoScore byTrainVideoScore)
    {
        return byTrainVideoScoreMapper.updateByTrainVideoScore(byTrainVideoScore);
    }

    /**
     * 批量删除培训视频评分
     *
     * @param ids 需要删除的培训视频评分ID
     * @return 结果
     */
    @Override
    public int deleteByTrainVideoScoreByIds(Long[] ids)
    {
        return byTrainVideoScoreMapper.deleteByTrainVideoScoreByIds(ids);
    }

    /**
     * 删除培训视频评分信息
     *
     * @param id 培训视频评分ID
     * @return 结果
     */
    @Override
    public int deleteByTrainVideoScoreById(Long id)
    {
        return byTrainVideoScoreMapper.deleteByTrainVideoScoreById(id);
    }
}
