package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByExperience;

/**
 * 入班体验申请Mapper接口
 *
 * @author tsbz
 * @date 2020-07-26
 */
public interface ByExperienceMapper {
    /**
     * 查询入班体验申请
     *
     * @param id 入班体验申请ID
     * @return 入班体验申请
     */
    public ByExperience selectByExperienceById(Long id);

    /**
     * 查询入班体验申请列表
     *
     * @param byExperience 入班体验申请
     * @return 入班体验申请集合
     */
    public List<ByExperience> selectByExperienceList(ByExperience byExperience);

    /**
     * 新增入班体验申请
     *
     * @param byExperience 入班体验申请
     * @return 结果
     */
    public int insertByExperience(ByExperience byExperience);

    /**
     * 修改入班体验申请
     *
     * @param byExperience 入班体验申请
     * @return 结果
     */
    public int updateByExperience(ByExperience byExperience);

    /**
     * 删除入班体验申请
     *
     * @param id 入班体验申请ID
     * @return 结果
     */
    public int deleteByExperienceById(Long id);

    /**
     * 批量删除入班体验申请
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByExperienceByIds(Long[] ids);
}
