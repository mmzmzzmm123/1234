package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomOrderCancelMapper;
import com.ruoyi.office.domain.TRoomOrderCancel;
import com.ruoyi.office.service.ITRoomOrderCancelService;

/**
 * 预约取消设置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@Service
public class TRoomOrderCancelServiceImpl extends ServiceImpl<TRoomOrderCancelMapper, TRoomOrderCancel> implements ITRoomOrderCancelService
{
    @Autowired
    private TRoomOrderCancelMapper tRoomOrderCancelMapper;

    /**
     * 查询预约取消设置
     * 
     * @param id 预约取消设置主键
     * @return 预约取消设置
     */
    @Override
    public TRoomOrderCancel selectTRoomOrderCancelById(Long id)
    {
        return tRoomOrderCancelMapper.selectTRoomOrderCancelById(id);
    }

    /**
     * 查询预约取消设置列表
     * 
     * @param tRoomOrderCancel 预约取消设置
     * @return 预约取消设置
     */
    @Override
    public List<TRoomOrderCancel> selectTRoomOrderCancelList(TRoomOrderCancel tRoomOrderCancel)
    {
        return tRoomOrderCancelMapper.selectTRoomOrderCancelList(tRoomOrderCancel);
    }

    /**
     * 新增预约取消设置
     * 
     * @param tRoomOrderCancel 预约取消设置
     * @return 结果
     */
    @Override
    public int insertTRoomOrderCancel(TRoomOrderCancel tRoomOrderCancel)
    {
        tRoomOrderCancel.setCreateTime(DateUtils.getNowDate());
        return tRoomOrderCancelMapper.insertTRoomOrderCancel(tRoomOrderCancel);
    }

    /**
     * 修改预约取消设置
     * 
     * @param tRoomOrderCancel 预约取消设置
     * @return 结果
     */
    @Override
    public int updateTRoomOrderCancel(TRoomOrderCancel tRoomOrderCancel)
    {
        tRoomOrderCancel.setUpdateTime(DateUtils.getNowDate());
        return tRoomOrderCancelMapper.updateTRoomOrderCancel(tRoomOrderCancel);
    }

    /**
     * 批量删除预约取消设置
     * 
     * @param ids 需要删除的预约取消设置主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderCancelByIds(Long[] ids)
    {
        return tRoomOrderCancelMapper.deleteTRoomOrderCancelByIds(ids);
    }

    /**
     * 删除预约取消设置信息
     * 
     * @param id 预约取消设置主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderCancelById(Long id)
    {
        return tRoomOrderCancelMapper.deleteTRoomOrderCancelById(id);
    }
}
