package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.vo.PrepayReq;
import com.ruoyi.office.domain.vo.RoomOrderWxReqVo;
import com.ruoyi.office.domain.vo.RoomOrderWxVo;

/**
 * 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-29
 */
public interface TRoomOrderMapper extends BaseMapper<TRoomOrder> {
    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param id 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）主键
     * @return 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     */
    public TRoomOrder selectTRoomOrderById(Long id);

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     *
     * @param tRoomOrder 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     * @return 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）集合
     */
    public List<TRoomOrder> selectTRoomOrderList(TRoomOrder tRoomOrder);

    /**
     * 新增房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param tRoomOrder 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     * @return 结果
     */
    public int insertTRoomOrder(TRoomOrder tRoomOrder);

    /**
     * 修改房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param tRoomOrder 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     * @return 结果
     */
    public int updateTRoomOrder(TRoomOrder tRoomOrder);

    /**
     * 删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param id 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）主键
     * @return 结果
     */
    public int deleteTRoomOrderById(Long id);

    /**
     * 批量删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTRoomOrderByIds(Long[] ids);

    List<TRoomOrder> selectConflictRoomPeriod(PrepayReq tRoomOrder);

    Long getHourMaxOrder(String nowHour);

    List<RoomOrderWxVo> getWxRoomOrder(RoomOrderWxReqVo tRoomOrder);
}
