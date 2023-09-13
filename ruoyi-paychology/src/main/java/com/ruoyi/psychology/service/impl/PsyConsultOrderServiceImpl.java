package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.constant.NewConstants;
import com.ruoyi.common.constant.PsyConstants;
import com.ruoyi.common.domain.PsyOrderLog;
import com.ruoyi.common.service.IPsyOrderLogService;
import com.ruoyi.common.utils.NewDateUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.vo.DateLimitUtilVO;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.domain.PsyConsultOrderItem;
import com.ruoyi.psychology.domain.PsyConsultOrderServe;
import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.dto.OrderDTO;
import com.ruoyi.psychology.dto.OrderListDTO;
import com.ruoyi.psychology.mapper.PsyConsultOrderMapper;
import com.ruoyi.psychology.request.PsyAdminOrderReq;
import com.ruoyi.psychology.request.PsyHxOrderReq;
import com.ruoyi.psychology.request.PsyRefOrderReq;
import com.ruoyi.psychology.service.*;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;
import com.ruoyi.psychology.vo.PsyConsultServeConfigVO;
import com.ruoyi.psychology.vo.PsyConsultVO;
import com.ruoyi.wechat.service.WechatService;
import com.ruoyi.wechat.vo.TemplateMessageItemVo;
import com.ruoyi.wechat.vo.TemplateMessageVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 咨询订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@Service
public class PsyConsultOrderServiceImpl implements IPsyConsultOrderService 
{

    @Resource
    private WechatService wechatService;

    @Resource
    private IPsyConsultService psyConsultService;

    @Resource
    private IPsyOrderLogService psyOrderLogService;

    @Resource
    private PsyConsultOrderMapper psyConsultOrderMapper;

    @Resource
    private IPsyConsultWorkService psyConsultWorkService;

    @Resource
    private IPsyConsultOrderServeService psyConsultOrderServeService;

    @Resource
    private IPsyConsultOrderItemService psyConsultOrderItemService;

    @Resource
    private IPsyConsultServeConfigService psyConsultServeConfigService;

    @Override
    public OrderDTO getOrderDetail(Long id) {
        OrderDTO detail = psyConsultOrderMapper.getOrderDetail(id);
        PsyConsultOrder order = new PsyConsultOrder();
        order.setPayStatus(detail.getPayStatus());
        order.setStatus(detail.getStatus());
        setNames(order);
        detail.setStatusName(order.getStatusName());
        detail.setPayStatusName(order.getPayStatusName());

        List<PsyConsultOrderItem> items = psyConsultOrderItemService.getList(id);

        detail.setServe(psyConsultOrderServeService.getOneByOrder(id, detail.getServeId()));
        detail.setItems(items);
        return detail;
    }

    @Override
    public OrderDTO getOrderDetailByNo(String orderNo) {
        LambdaQueryWrapper<PsyConsultOrder> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultOrder::getOrderNo, orderNo);
        PsyConsultOrder order = psyConsultOrderMapper.selectOne(wp);
        return getOrderDetail(order.getId());
    }

    @Override
    public boolean checkNewByServe(Long orderId, Long serveId, Integer userId) {
        Integer bound;
        if (orderId == null) {
            PsyConsultServeConfigVO one = psyConsultServeConfigService.getOne(serveId);
            bound = one.getBound();
        } else {
            PsyConsultOrderServe oneByOrder = psyConsultOrderServeService.getOneByOrder(orderId, serveId);
            bound = oneByOrder.getBound();
        }

        if (ConsultConstant.CONSULT_LIMIT.equals(bound)) {
            PsyConsultOrderVO req = new PsyConsultOrderVO();
            req.setUserId(userId);
            req.setPayStatus(ConsultConstant.PAY_STATUE_PAID);
            List<OrderListDTO> orderList = getOrderList(req);
            return orderList != null && orderList.size() > 0;
        }

        return false;
    }

    @Override
    public PsyConsultOrder getOrderById(Long id) {
        return psyConsultOrderMapper.selectById(id);
    }

    @Override
    public PsyConsultOrderVO getOne(Long id) {
        return BeanUtil.toBean(psyConsultOrderMapper.selectById(id), PsyConsultOrderVO.class);
    }

    @Override
    public String getOpenId(Long cId) {
        PsyConsultVO one = psyConsultService.getOne(cId);
        return one.getOpenId();
    }

    @Override
    public List<PsyConsultOrder> getList(PsyAdminOrderReq req) {
        req.setDelFlag("0");
        if (StringUtils.isNotBlank(req.getDateLimit())) {
            DateLimitUtilVO dateLimit = NewDateUtil.getDateLimit(req.getDateLimit());
            req.setStartTime(dateLimit.getStartTime());
            req.setEndTime(dateLimit.getEndTime());
        }

        List<PsyConsultOrder> list = psyConsultOrderMapper.getList(req);
        list.forEach(this::setNames);

        return list;
    }

    @Override
    public List<PsyConsultOrder> getListForNotice(String last) {
        LambdaQueryWrapper<PsyConsultOrder> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultOrder::getDelFlag, "0");
        wp.eq(PsyConsultOrder::getPayStatus, "2");
        wp.last(last);
        return psyConsultOrderMapper.selectList(wp);
    }

    private void setNames(PsyConsultOrder entity) {
        switch (entity.getStatus()) {
            case ConsultConstant.CONSULT_ORDER_STATUE_CREATED:
                entity.setStatusName("待付款");
                break;
            case ConsultConstant.CONSULT_ORDER_STATUE_PENDING:
                entity.setStatusName("进行中");
                break;
            case ConsultConstant.CONSULT_ORDER_STATUE_FINISHED:
                entity.setStatusName("已完成");
                break;
            case ConsultConstant.CONSULT_ORDER_STATUE_CANCELED:
                entity.setStatusName("已取消");
                break;
        }

        switch (entity.getPayStatus()) {
            case ConsultConstant.PAY_STATUE_PENDING:
                entity.setPayStatusName("未支付");
                break;
            case ConsultConstant.PAY_STATUE_PAID:
                entity.setPayStatusName("支付成功");
                break;
            case ConsultConstant.PAY_STATUE_REFUNDING:
                entity.setPayStatusName("退款中");
                break;
            case ConsultConstant.PAY_STATUE_PART_REFUNDING:
                entity.setPayStatusName("部分退");
                break;
            case ConsultConstant.PAY_STATUE_REFUN:
                entity.setPayStatusName("全单退");
                break;
            case ConsultConstant.PAY_STATUE_REFUN_FAIL:
                entity.setPayStatusName("退款失败");
                break;
        }
    }

    @Override
    public List<PsyConsultOrder> getCancelList(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -num);
        Date time = calendar.getTime();
        LambdaQueryWrapper<PsyConsultOrder> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultOrder::getStatus, 0);
        wp.le(PsyConsultOrder::getUpdateTime, time);
        wp.eq(PsyConsultOrder::getDelFlag, "0");

        return psyConsultOrderMapper.selectList(wp);
    }

    @Override
    public List<OrderListDTO> getOrderList(PsyConsultOrderVO req) {
        req.setDelFlag("0");
        return psyConsultOrderMapper.getOrderList(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String hx(PsyHxOrderReq req) {
        int now = req.getTimes().size();
        if (now == 0) {
            return "核销时间不能为空";
        }

        PsyConsultOrderVO order = getOne(req.getId());
        List<PsyConsultOrderItem> items = psyConsultOrderItemService.getList(req.getId()).stream().filter(a -> ConsultConstant.ONSULT_ORDER_ITEM_CREATED.equals(a.getStatus())).collect(Collectors.toList());
        List<PsyConsultOrderItem> list = new ArrayList<>();

        int max = order.getNum() + order.getBuyNum();
        if (now > max) {
            return "核销次数超过最大值";
        }

        order.setOrderTime("");
        order.setNum(Math.max(order.getNum() - now, 0));
        order.setBuyNum(Math.min(order.getBuyNum() + now, max));
        if (max == order.getBuyNum()) {
            order.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_FINISHED);
        }

        // 存在子单据情况下
        int num = 0;
        if (CollectionUtils.isNotEmpty(items)) {
            num = items.size();
            for (int i = 0; i < num; i++) {
                PsyConsultOrderItem orderItem = items.get(i);
                orderItem.setStatus(ConsultConstant.ONSULT_ORDER_ITEM_FINISHED);
                orderItem.setRealTime(req.getTimes().get(i));
                orderItem.setUpdateBy(SecurityUtils.getUsername());
                list.add(orderItem);
            }

            // 更新下次预约时间
            if (num > now) {
                PsyConsultOrderItem orderItem = items.get(now);
                order.setOrderTime(StrUtil.format("{} {}~{}", orderItem.getDay(), orderItem.getTimeStart(), orderItem.getTimeEnd()));
            }
        }

        Calendar calendar = Calendar.getInstance();
        if (max > num) {
            for (int i = num; i < now; i++) {
                Date date = NewDateUtil.strToDate(req.getTimes().get(i), NewConstants.DATE_FORMAT_HHMM);
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                PsyConsultOrderItem orderItem = new PsyConsultOrderItem();
                // "2023-07-26 16:09:50"
                orderItem.setOrderId(req.getId());
                orderItem.setWorkId(0L);
                orderItem.setTime(hour);
                orderItem.setWeek(NewDateUtil.getWeekOfDate(calendar));
                orderItem.setDay(req.getTimes().get(i).substring(0, 10));
                orderItem.setTimeStart(hour + ":00");
                orderItem.setTimeEnd(hour + ":50");
                orderItem.setStatus(ConsultConstant.ONSULT_ORDER_ITEM_FINISHED);
                orderItem.setRealTime(req.getTimes().get(i));
                orderItem.setCreateBy(SecurityUtils.getUsername());
                orderItem.setUpdateBy(SecurityUtils.getUsername());
                list.add(orderItem);
            }
        }
        doLog(order.getOrderNo(), PsyConstants.ORDER_LOG_HX, SecurityUtils.getUsername(), StrUtil.format(PsyConstants.ORDER_LOG_MESSAGE_HX, list.size()));
        if (max == order.getBuyNum()) {
            // 订单完成
            doLog(order.getOrderNo(), PsyConstants.ORDER_LOG_FINISHED, SecurityUtils.getUsername(), PsyConstants.ORDER_LOG_MESSAGE_FINISHED);
        }

        return update(order) > 0 && psyConsultOrderItemService.updateBatch(list) ? "ok" : "核销失败";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String modifyRef(PsyRefOrderReq req) {
        PsyConsultOrderVO order = getOne(req.getOrderId());
        if (!ConsultConstant.CONSULT_ORDER_STATUE_PENDING.equals(order.getStatus())) {
            return "订单状态异常";
        }

        PsyConsultVO consult = psyConsultService.getOne(req.getConsultId());
        if ("1".equals(consult.getStatus())) {
            return "咨询师状态异常";
        }

        // 原订单处理
        // 新订单处理






//        order.setPay(req.getPay());
//        order.setMemo1(req.getMemo1());
//
//        doLog(order.getOrderNo(), PsyConstants.ORDER_LOG_EDIT_PRICE, SecurityUtils.getUsername(), StrUtil.format(PsyConstants.ORDER_LOG_MESSAGE_EDIT_PRICE,  order.getAmount(), order.getPay()));
        return  "功能开发中...";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String modifyPrice(PsyConsultOrderVO req) {
        PsyConsultOrderVO order = getOne(req.getId());
        if (!ConsultConstant.CONSULT_ORDER_STATUE_CREATED.equals(order.getStatus())) {
            return "订单状态异常";
        }

        order.setPay(req.getPay());
        order.setMemo1(req.getMemo1());

        doLog(order.getOrderNo(), PsyConstants.ORDER_LOG_EDIT_PRICE, SecurityUtils.getUsername(), StrUtil.format(PsyConstants.ORDER_LOG_MESSAGE_EDIT_PRICE,  order.getAmount(), order.getPay()));

        return update(order) > 0 ? "ok" : "修改失败";
    }

    private void doLog(String oid, String type, String createBy, String msg)  {
        PsyOrderLog log = new PsyOrderLog();
        log.setOid(oid);
        log.setOrderType(PsyConstants.ORDER_CONSULT);
        log.setChangeType(type);
        log.setCreateBy(createBy);
        log.setChangeMessage(msg);
        psyOrderLogService.insertPsyOrderLog(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(PsyConsultOrder order, String createBy) {
        order.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_CANCELED);
        List<PsyConsultOrderItem> items = psyConsultOrderItemService.getList(order.getId());

        // 释放库存
        if (CollectionUtils.isNotEmpty(items)) {
            PsyConsultOrderItem orderItem = items.get(0);
            psyConsultWorkService.handleWork(orderItem.getWorkId(), order.getConsultId(), orderItem.getTime(), 2);
        }
        // 咨询人数减1
        // psyConsultService.updateNum(order.getConsultId(), -1);
        doLog(order.getOrderNo(), PsyConstants.ORDER_LOG_CANCEL, createBy, PsyConstants.ORDER_LOG_MESSAGE_CANCEL);
        psyConsultOrderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doConsult(Long id, Long workId, Integer time) {
        PsyConsultOrderVO order = getOne(id);
        if (order.getNum() == 0) {
            return -1;
        }

        PsyConsultOrderItem item = psyConsultOrderItemService.getOneByOrderId(id);
        if (item != null) {
            return -1;
        }

        order.setTime(time);
        order.setWorkId(workId);
        handleItem(order);

        if (StringUtils.isNotBlank(order.getOrderTime())) {
            // 消息推送
            sendPublicMsg(order);
        }

        // 核销完成,才算完成
//        if (order.getNum() == 0) {
//            order.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_FINISHED);
//        }
        // 更新预约数量
        return update(order);
    }

    private void handleItem(PsyConsultOrderVO req) {
        PsyConsultWork work = psyConsultWorkService.handleWork(req.getWorkId(), req.getConsultId(), req.getTime(), 1);
        // 插入子订单
        PsyConsultOrderItem orderItem = new PsyConsultOrderItem();
        orderItem.setOrderId(req.getId());
        orderItem.setStatus(ConsultConstant.ONSULT_ORDER_ITEM_CREATED);
        orderItem.setWorkId(req.getWorkId());
        orderItem.setDay(work.getDay());
        orderItem.setWeek(work.getWeek());
        orderItem.setTime(req.getTime());
        orderItem.setTimeStart(req.getTime() + ":00");
        orderItem.setTimeEnd(req.getTime() + ":50");

        req.setOrderTime(StrUtil.format("{} {}~{}", work.getDay(), orderItem.getTimeStart(), orderItem.getTimeEnd()));
        psyConsultOrderItemService.add(orderItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultOrderVO req) {
        PsyConsultServeConfigVO serve = psyConsultServeConfigService.getOne(req.getServeId());
        PsyConsultVO consult = psyConsultService.getOne(req.getConsultId());

        // 新增服务
        psyConsultOrderServeService.add(serve, req.getId());

        req.setConsultName(consult.getNickName());
        req.setServeName(serve.getName());
        req.setDelFlag("0");
        req.setNum(serve.getNum());
        req.setBuyNum(0);
        req.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_CREATED);
        req.setPayStatus(ConsultConstant.PAY_STATUE_PENDING);

        if (req.getWorkId() > 0 && req.getTime() > 0) {
            handleItem(req);
        }

        doLog(req.getOrderNo(), PsyConstants.ORDER_LOG_CREATE, req.getNickName(), PsyConstants.ORDER_LOG_MESSAGE_CREATE);
        return psyConsultOrderMapper.insert(BeanUtil.toBean(req, PsyConsultOrder.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePayOrder(PsyConsultOrderVO req) {
        PsyConsultOrderVO orderVO = getOne(req.getId());
        List<PsyConsultOrderItem> items = psyConsultOrderItemService.getList(req.getId());

        // 释放库存
        if (CollectionUtils.isNotEmpty(items)) {
            PsyConsultOrderItem orderItem = items.get(0);
            psyConsultWorkService.handleWork(orderItem.getWorkId(), orderVO.getConsultId(), orderItem.getTime(), 2);
            psyConsultOrderItemService.del(orderItem.getId());
            req.setOrderTime("");
        }

        // 加库存
        if (req.getWorkId() != null && req.getWorkId() > 0) {
            handleItem(req);
        }

        // 更新订单
        update(req);
    }

    // 核销后才更新数量
    private void updateNum(PsyConsultOrderVO req) {
        req.setNum(Math.max(req.getNum() - 1, 0));
        req.setBuyNum(Math.max(req.getBuyNum() + 1, 0));
    }

    public Boolean sendPublicMsg(PsyConsultOrderVO psyOrder) {
        TemplateMessageVo msg = new TemplateMessageVo();
        msg.setTemplate_id(PsyConstants.CONSULT_TEMPLATE_ID);

        HashMap<String, TemplateMessageItemVo> hashMap = new HashMap<>();
        hashMap.put("thing1", new TemplateMessageItemVo(psyOrder.getNickName()));
        hashMap.put("thing2", new TemplateMessageItemVo(psyOrder.getConsultName()));
        hashMap.put("thing3", new TemplateMessageItemVo(psyOrder.getServeName()));
        hashMap.put("time4", new TemplateMessageItemVo(StringUtils.isNotBlank(psyOrder.getOrderTime()) ? psyOrder.getOrderTime() : NewDateUtil.dateToStr(psyOrder.getUpdateTime(), NewConstants.DATE_FORMAT_HHMM)));
        hashMap.put("thing5", new TemplateMessageItemVo(psyOrder.getOrderNo()));
        msg.setData(hashMap);
        msg.setTouser(getOpenId(psyOrder.getConsultId()));
        return wechatService.sendPublicMsg(msg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wechatPayNotify(PsyConsultOrderVO req) {
        req.setPayTime(new Date());
//        if (StringUtils.isNotBlank(req.getOrderTime())) {
//
//        }
        // 消息推送
        sendPublicMsg(req);
//        {"errcode":47003,"errmsg":"argument invalid! data.thing5.value invalid rid: 64d4912a-5e4f2ce4-13323c1a"}
        // 增加预约人数 支付成功后+1
        psyConsultService.updateNum(req.getConsultId(), 1);
        // 增加服务销量
        psyConsultServeConfigService.updateNum(req.getServeId());
        doLog(req.getOrderNo(), PsyConstants.ORDER_LOG_PAY_SUCCESS, req.getNickName(), PsyConstants.ORDER_LOG_MESSAGE_PAY_SUCCESS);

        update(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultOrderVO req) {
        return psyConsultOrderMapper.updateById(BeanUtil.toBean(req, PsyConsultOrder.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remark(PsyConsultOrderVO req) {
        doLog(req.getOrderNo(), PsyConstants.ORDER_LOG_REMARK, SecurityUtils.getUsername(), req.getRemark());
        return update(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultOrderMapper.tombstonedByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultOrderMapper.deleteById(id);
    }
}
