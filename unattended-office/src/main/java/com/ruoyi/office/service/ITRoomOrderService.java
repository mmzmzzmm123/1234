package com.ruoyi.office.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.vo.*;

/**
 * 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）Service接口
 *
 * @author ruoyi
 * @date 2023-05-29
 */
public interface ITRoomOrderService {
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
     * 批量删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param ids 需要删除的房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）主键集合
     * @return 结果
     */
    public int deleteTRoomOrderByIds(Long[] ids);

    /**
     * 删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）信息
     *
     * @param id 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）主键
     * @return 结果
     */
    public int deleteTRoomOrderById(Long id);

    BigDecimal getPeriodPrice(GetRoomPriceVo vo);

    PrepayResp prepay(PrepayReq vo, Long userId);

    boolean checkCoupon(PrepayReq vo, Long userId);

    void wxnotify(String orderNo, String openId, int centTotal, String s);

    WxPayOrderQueryV3Result finish(PrepayResp vo, Long id);

    RoomAvailablePeriod getAvailablePeriod(RoomAvailablePeriod vo);

    TRoomOrder continueOrder(Long userId);

    List<RoomOrderWxVo> getWxRoomOrder(RoomOrderWxReqVo tRoomOrder);

    void scanOrder();

    PrepayResp reOrder(ReorderPayReq orderId, long wxUserId);

    List<RoomOrderWxVo> getOrderCanOpen(OrderCanOpenReq wxUserId);

    void scanToPayOrder();

    PrepayResp packPrepay(PackPrepayReq order, long wxUserId);
}
