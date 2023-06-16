package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TRoomOrderCancel;

/**
 * 预约取消设置Service接口
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public interface ITRoomOrderCancelService 
{
    /**
     * 查询预约取消设置
     * 
     * @param id 预约取消设置主键
     * @return 预约取消设置
     */
    public TRoomOrderCancel selectTRoomOrderCancelById(Long id);

    /**
     * 查询预约取消设置列表
     * 
     * @param tRoomOrderCancel 预约取消设置
     * @return 预约取消设置集合
     */
    public List<TRoomOrderCancel> selectTRoomOrderCancelList(TRoomOrderCancel tRoomOrderCancel);

    /**
     * 新增预约取消设置
     * 
     * @param tRoomOrderCancel 预约取消设置
     * @return 结果
     */
    public int insertTRoomOrderCancel(TRoomOrderCancel tRoomOrderCancel);

    /**
     * 修改预约取消设置
     * 
     * @param tRoomOrderCancel 预约取消设置
     * @return 结果
     */
    public int updateTRoomOrderCancel(TRoomOrderCancel tRoomOrderCancel);

    /**
     * 批量删除预约取消设置
     * 
     * @param ids 需要删除的预约取消设置主键集合
     * @return 结果
     */
    public int deleteTRoomOrderCancelByIds(Long[] ids);

    /**
     * 删除预约取消设置信息
     * 
     * @param id 预约取消设置主键
     * @return 结果
     */
    public int deleteTRoomOrderCancelById(Long id);
}
