package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TRoomOrderCancel;

/**
 * 预约取消设置Mapper接口
 *
 * @author ruoyi
 * @date 2023-06-15
 */
public interface TRoomOrderCancelMapper extends BaseMapper<TRoomOrderCancel> {
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
     * 删除预约取消设置
     *
     * @param id 预约取消设置主键
     * @return 结果
     */
    public int deleteTRoomOrderCancelById(Long id);

    /**
     * 批量删除预约取消设置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTRoomOrderCancelByIds(Long[] ids);
}
