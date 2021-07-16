package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysPunchThumbsup;

/**
 * 打卡社区点赞记录Mapper接口
 *
 * @author xzj
 * @date 2021-07-15
 */
public interface SysPunchThumbsupMapper
{
    /**
     * 查询打卡社区点赞记录
     *
     * @param id 打卡社区点赞记录ID
     * @return 打卡社区点赞记录
     */
    public SysPunchThumbsup selectSysPunchThumbsupById(Long id);

    /**
     * 查询打卡社区点赞记录列表
     *
     * @param sysPunchThumbsup 打卡社区点赞记录
     * @return 打卡社区点赞记录集合
     */
    public List<SysPunchThumbsup> selectSysPunchThumbsupList(SysPunchThumbsup sysPunchThumbsup);

    /**
     * 新增打卡社区点赞记录
     *
     * @param sysPunchThumbsup 打卡社区点赞记录
     * @return 结果
     */
    public int insertSysPunchThumbsup(SysPunchThumbsup sysPunchThumbsup);

    /**
     * 修改打卡社区点赞记录
     *
     * @param sysPunchThumbsup 打卡社区点赞记录
     * @return 结果
     */
    public int updateSysPunchThumbsup(SysPunchThumbsup sysPunchThumbsup);

    /**
     * 删除打卡社区点赞记录
     *
     * @param id 打卡社区点赞记录ID
     * @return 结果
     */
    public int deleteSysPunchThumbsupById(Long id);

    /**
     * 批量删除打卡社区点赞记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPunchThumbsupByIds(Long[] ids);

    /**
     * 根据punchId和openid删除对应点赞记录
     * @return
     */
    public int deleteThumbsupByPunchIdAndOpenid(SysPunchThumbsup sysPunchThumbsup);

    /**
     * 根据punchId、openid查询点赞记录
     * @param sysPunchThumbsup
     * @return
     */
    SysPunchThumbsup getThumbsupByPunchIdAndOpenid(SysPunchThumbsup sysPunchThumbsup);
}