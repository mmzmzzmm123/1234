package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByExperienceMapper;
import com.ruoyi.project.benyi.domain.ByExperience;
import com.ruoyi.project.benyi.service.IByExperienceService;

/**
 * 入班体验申请Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-26
 */
@Service
public class ByExperienceServiceImpl implements IByExperienceService {
    @Autowired
    private ByExperienceMapper byExperienceMapper;

    /**
     * 查询入班体验申请
     *
     * @param id 入班体验申请ID
     * @return 入班体验申请
     */
    @Override
    public ByExperience selectByExperienceById(Long id) {
        return byExperienceMapper.selectByExperienceById(id);
    }

    /**
     * 查询入班体验申请列表
     *
     * @param byExperience 入班体验申请
     * @return 入班体验申请
     */
    @Override
    public List<ByExperience> selectByExperienceList(ByExperience byExperience) {
        return byExperienceMapper.selectByExperienceList(byExperience);
    }

    /**
     * 新增入班体验申请
     *
     * @param byExperience 入班体验申请
     * @return 结果
     */
    @Override
    public int insertByExperience(ByExperience byExperience) {
        byExperience.setCreateTime(DateUtils.getNowDate());
        return byExperienceMapper.insertByExperience(byExperience);
    }

    /**
     * 修改入班体验申请
     *
     * @param byExperience 入班体验申请
     * @return 结果
     */
    @Override
    public int updateByExperience(ByExperience byExperience) {
        return byExperienceMapper.updateByExperience(byExperience);
    }

    /**
     * 批量删除入班体验申请
     *
     * @param ids 需要删除的入班体验申请ID
     * @return 结果
     */
    @Override
    public int deleteByExperienceByIds(Long[] ids) {
        return byExperienceMapper.deleteByExperienceByIds(ids);
    }

    /**
     * 删除入班体验申请信息
     *
     * @param id 入班体验申请ID
     * @return 结果
     */
    @Override
    public int deleteByExperienceById(Long id) {
        return byExperienceMapper.deleteByExperienceById(id);
    }
}
