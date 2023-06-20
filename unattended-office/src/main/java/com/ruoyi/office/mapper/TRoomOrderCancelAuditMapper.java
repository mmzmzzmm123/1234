package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TRoomOrderCancelAudit;
import com.ruoyi.office.domain.vo.RefundAuditVo;

/**
 * 预约取消审核Mapper接口
 *
 * @author ruoyi
 * @date 2023-06-15
 */
public interface TRoomOrderCancelAuditMapper extends BaseMapper<TRoomOrderCancelAudit> {
    /**
     * 查询预约取消审核
     *
     * @param id 预约取消审核主键
     * @return 预约取消审核
     */
    public TRoomOrderCancelAudit selectTRoomOrderCancelAuditById(Long id);

    /**
     * 查询预约取消审核列表
     *
     * @param tRoomOrderCancelAudit 预约取消审核
     * @return 预约取消审核集合
     */
    public List<TRoomOrderCancelAudit> selectTRoomOrderCancelAuditList(TRoomOrderCancelAudit tRoomOrderCancelAudit);

    /**
     * 新增预约取消审核
     *
     * @param tRoomOrderCancelAudit 预约取消审核
     * @return 结果
     */
    public int insertTRoomOrderCancelAudit(TRoomOrderCancelAudit tRoomOrderCancelAudit);

    /**
     * 修改预约取消审核
     *
     * @param tRoomOrderCancelAudit 预约取消审核
     * @return 结果
     */
    public int updateTRoomOrderCancelAudit(TRoomOrderCancelAudit tRoomOrderCancelAudit);

    /**
     * 删除预约取消审核
     *
     * @param id 预约取消审核主键
     * @return 结果
     */
    public int deleteTRoomOrderCancelAuditById(Long id);

    /**
     * 批量删除预约取消审核
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTRoomOrderCancelAuditByIds(Long[] ids);

    /**
     * 查询预约取消审核列表
     *
     * @param tRoomOrderCancelAudit 预约取消审核
     * @return 预约取消审核集合
     */
    public List<RefundAuditVo> selectCancelAuditInfoList(RefundAuditVo tRoomOrderCancelAudit);

    Long getDayMaxOrder(String prefix);
}
