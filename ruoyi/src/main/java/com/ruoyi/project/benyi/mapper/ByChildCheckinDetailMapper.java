package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByChildCheckinDetail;

/**
 * 幼儿考勤Mapper接口
 *
 * @author tsbz
 * @date 2020-07-21
 */
public interface ByChildCheckinDetailMapper {
    /**
     * 查询幼儿考勤
     *
     * @param id 幼儿考勤ID
     * @return 幼儿考勤
     */
    public ByChildCheckinDetail selectByChildCheckinDetailById(Long id);

    /**
     * 查询幼儿考勤列表
     *
     * @param byChildCheckinDetail 幼儿考勤
     * @return 幼儿考勤集合
     */
    public List<ByChildCheckinDetail> selectByChildCheckinDetailList(ByChildCheckinDetail byChildCheckinDetail);

    /**
     * 新增幼儿考勤
     *
     * @param byChildCheckinDetail 幼儿考勤
     * @return 结果
     */
    public int insertByChildCheckinDetail(ByChildCheckinDetail byChildCheckinDetail);

    /**
     * 修改幼儿考勤
     *
     * @param byChildCheckinDetail 幼儿考勤
     * @return 结果
     */
    public int updateByChildCheckinDetail(ByChildCheckinDetail byChildCheckinDetail);

    /**
     * 删除幼儿考勤
     *
     * @param id 幼儿考勤ID
     * @return 结果
     */
    public int deleteByChildCheckinDetailById(Long id);

    /**
     * 批量删除幼儿考勤
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByChildCheckinDetailByIds(Long[] ids);
}
