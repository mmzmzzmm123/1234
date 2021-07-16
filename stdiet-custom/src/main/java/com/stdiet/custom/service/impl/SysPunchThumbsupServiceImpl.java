package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysPunchThumbsupMapper;
import com.stdiet.custom.domain.SysPunchThumbsup;
import com.stdiet.custom.service.ISysPunchThumbsupService;

/**
 * 打卡社区点赞记录Service业务层处理
 *
 * @author xzj
 * @date 2021-07-15
 */
@Service
public class SysPunchThumbsupServiceImpl implements ISysPunchThumbsupService
{
    @Autowired
    private SysPunchThumbsupMapper sysPunchThumbsupMapper;

    /**
     * 查询打卡社区点赞记录
     *
     * @param id 打卡社区点赞记录ID
     * @return 打卡社区点赞记录
     */
    @Override
    public SysPunchThumbsup selectSysPunchThumbsupById(Long id)
    {
        return sysPunchThumbsupMapper.selectSysPunchThumbsupById(id);
    }

    /**
     * 查询打卡社区点赞记录列表
     *
     * @param sysPunchThumbsup 打卡社区点赞记录
     * @return 打卡社区点赞记录
     */
    @Override
    public List<SysPunchThumbsup> selectSysPunchThumbsupList(SysPunchThumbsup sysPunchThumbsup)
    {
        return sysPunchThumbsupMapper.selectSysPunchThumbsupList(sysPunchThumbsup);
    }

    /**
     * 新增打卡社区点赞记录
     *
     * @param sysPunchThumbsup 打卡社区点赞记录
     * @return 结果
     */
    @Override
    public int insertSysPunchThumbsup(SysPunchThumbsup sysPunchThumbsup)
    {
        sysPunchThumbsup.setCreateTime(DateUtils.getNowDate());
        return sysPunchThumbsupMapper.insertSysPunchThumbsup(sysPunchThumbsup);
    }

    /**
     * 修改打卡社区点赞记录
     *
     * @param sysPunchThumbsup 打卡社区点赞记录
     * @return 结果
     */
    @Override
    public int updateSysPunchThumbsup(SysPunchThumbsup sysPunchThumbsup)
    {
        sysPunchThumbsup.setUpdateTime(DateUtils.getNowDate());
        return sysPunchThumbsupMapper.updateSysPunchThumbsup(sysPunchThumbsup);
    }

    /**
     * 批量删除打卡社区点赞记录
     *
     * @param ids 需要删除的打卡社区点赞记录ID
     * @return 结果
     */
    @Override
    public int deleteSysPunchThumbsupByIds(Long[] ids)
    {
        return sysPunchThumbsupMapper.deleteSysPunchThumbsupByIds(ids);
    }

    /**
     * 删除打卡社区点赞记录信息
     *
     * @param id 打卡社区点赞记录ID
     * @return 结果
     */
    @Override
    public int deleteSysPunchThumbsupById(Long id)
    {
        return sysPunchThumbsupMapper.deleteSysPunchThumbsupById(id);
    }

    /**
     * 根据punchId和openid删除对应点赞记录
     * @return
     */
    @Override
    public int deleteThumbsupByPunchIdAndOpenid(SysPunchThumbsup sysPunchThumbsup){
        return sysPunchThumbsupMapper.deleteThumbsupByPunchIdAndOpenid(sysPunchThumbsup);
    }

    /**
     * 根据punchId、openid查询点赞记录
     * @param sysPunchThumbsup
     * @return
     */
    @Override
    public SysPunchThumbsup getThumbsupByPunchIdAndOpenid(SysPunchThumbsup sysPunchThumbsup){
        return sysPunchThumbsupMapper.getThumbsupByPunchIdAndOpenid(sysPunchThumbsup);
    }
}