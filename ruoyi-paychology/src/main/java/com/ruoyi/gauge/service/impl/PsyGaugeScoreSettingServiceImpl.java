package com.ruoyi.gauge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gauge.mapper.PsyGaugeScoreSettingMapper;
import com.ruoyi.gauge.domain.PsyGaugeScoreSetting;
import com.ruoyi.gauge.service.IPsyGaugeScoreSettingService;

/**
 * 心理测评普通设置Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Service
public class PsyGaugeScoreSettingServiceImpl implements IPsyGaugeScoreSettingService 
{
    @Autowired
    private PsyGaugeScoreSettingMapper psyGaugeScoreSettingMapper;

    /**
     * 查询心理测评普通设置
     * 
     * @param id 心理测评普通设置主键
     * @return 心理测评普通设置
     */
    @Override
    public PsyGaugeScoreSetting selectPsyGaugeScoreSettingById(Integer id)
    {
        return psyGaugeScoreSettingMapper.selectPsyGaugeScoreSettingById(id);
    }

    /**
     * 查询心理测评普通设置列表
     * 
     * @param psyGaugeScoreSetting 心理测评普通设置
     * @return 心理测评普通设置
     */
    @Override
    public List<PsyGaugeScoreSetting> selectPsyGaugeScoreSettingList(PsyGaugeScoreSetting psyGaugeScoreSetting)
    {
        return psyGaugeScoreSettingMapper.selectPsyGaugeScoreSettingList(psyGaugeScoreSetting);
    }

    @Override
    public List<PsyGaugeScoreSetting> selectPsyGaugeScoreSettingListByIds(Integer[] ids) {
      return psyGaugeScoreSettingMapper.selectPsyGaugeScoreSettingListByIds(ids);
    }

    /**
     * 新增心理测评普通设置
     * 
     * @param psyGaugeScoreSetting 心理测评普通设置
     * @return 结果
     */
    @Override
    public PsyGaugeScoreSetting insertPsyGaugeScoreSetting(PsyGaugeScoreSetting psyGaugeScoreSetting)
    {
         psyGaugeScoreSettingMapper.insertPsyGaugeScoreSetting(psyGaugeScoreSetting);

        return psyGaugeScoreSetting;
    }

    /**
     * 修改心理测评普通设置
     * 
     * @param psyGaugeScoreSetting 心理测评普通设置
     * @return 结果
     */
    @Override
    public int updatePsyGaugeScoreSetting(PsyGaugeScoreSetting psyGaugeScoreSetting)
    {
        return psyGaugeScoreSettingMapper.updatePsyGaugeScoreSetting(psyGaugeScoreSetting);
    }

    /**
     * 批量删除心理测评普通设置
     * 
     * @param ids 需要删除的心理测评普通设置主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeScoreSettingByIds(Integer[] ids)
    {
        return psyGaugeScoreSettingMapper.deletePsyGaugeScoreSettingByIds(ids);
    }

    /**
     * 删除心理测评普通设置信息
     * 
     * @param id 心理测评普通设置主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeScoreSettingById(Integer id)
    {
        return psyGaugeScoreSettingMapper.deletePsyGaugeScoreSettingById(id);
    }
}
