package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QzInviteMapper;
import com.ruoyi.system.domain.QzInvite;
import com.ruoyi.system.service.IQzInviteService;

/**
 * 邀请Service业务层处理
 * 
 * @author zlx
 * @date 2021-08-10
 */
@Service
public class QzInviteServiceImpl implements IQzInviteService 
{
    @Autowired
    private QzInviteMapper qzInviteMapper;

    /**
     * 查询邀请
     * 
     * @param id 邀请主键
     * @return 邀请
     */
    @Override
    public QzInvite selectQzInviteById(Long id)
    {
        return qzInviteMapper.selectQzInviteById(id);
    }

    /**
     * 查询邀请列表
     * 
     * @param qzInvite 邀请
     * @return 邀请
     */
    @Override
    public List<QzInvite> selectQzInviteList(QzInvite qzInvite)
    {
        return qzInviteMapper.selectQzInviteList(qzInvite);
    }

    /**
     * 新增邀请
     * 
     * @param qzInvite 邀请
     * @return 结果
     */
    @Override
    public int insertQzInvite(QzInvite qzInvite)
    {
        qzInvite.setCreateTime(DateUtils.getNowDate());
        return qzInviteMapper.insertQzInvite(qzInvite);
    }

    /**
     * 修改邀请
     * 
     * @param qzInvite 邀请
     * @return 结果
     */
    @Override
    public int updateQzInvite(QzInvite qzInvite)
    {
        qzInvite.setUpdateTime(DateUtils.getNowDate());
        return qzInviteMapper.updateQzInvite(qzInvite);
    }

    /**
     * 批量删除邀请
     * 
     * @param ids 需要删除的邀请主键
     * @return 结果
     */
    @Override
    public int deleteQzInviteByIds(Long[] ids)
    {
        return qzInviteMapper.deleteQzInviteByIds(ids);
    }

    /**
     * 删除邀请信息
     * 
     * @param id 邀请主键
     * @return 结果
     */
    @Override
    public int deleteQzInviteById(Long id)
    {
        return qzInviteMapper.deleteQzInviteById(id);
    }
}
