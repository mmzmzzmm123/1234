package com.ruoyi.psychology.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.psychology.domain.PsyConsultBillItem;
import com.ruoyi.psychology.dto.BillItemDTO;
import com.ruoyi.psychology.mapper.PsyConsultBillItemMapper;
import com.ruoyi.psychology.request.PsyAdminBillReq;
import com.ruoyi.psychology.service.IPsyConsultBillItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;

/**
 * 咨询核销记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-22
 */
@Service
public class PsyConsultBillItemServiceImpl extends ServiceImpl<PsyConsultBillItemMapper, PsyConsultBillItem> implements IPsyConsultBillItemService
{

    @Resource
    private PsyConsultBillItemMapper mapper;

    @Override
    public List<PsyConsultBillItem> getItemListForDetail(PsyAdminBillReq req) {
        return mapper.getItemListForDetail(req);
    }

    @Override
    public BigDecimal getBillItemSum(PsyAdminBillReq req) {
        return mapper.getBillItemSum(req);
    }

    @Override
    public List<BillItemDTO> getItemList(PsyAdminBillReq req) {
        return mapper.getItemList(req);
    }

    @Override
    public List<PsyConsultBillItem> getBillItems(PsyAdminBillReq req) {
        return mapper.getBillItems(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd() {
        List<PsyConsultBillItem> orderItems = mapper.getOrderItems();
        if (CollectionUtils.isNotEmpty(orderItems)) {
            // 以orderId 分组 计数
            Map<Long, List<PsyConsultBillItem>> collect = orderItems.stream().collect(groupingBy(PsyConsultBillItem::getOrderId));
            collect.forEach((key, value) -> {
                // 修复 咨询次数 咨询次数问题
                // 序号 = 已核销-当前数+1
                int i = value.get(0).getOrderNum() - value.get(0).getNum() - value.size() + 1;
                AtomicInteger num = new AtomicInteger(i);
                value.forEach(it -> {
                    it.setBuyNum(num.get());
                    it.setBuyNumStr(StrUtil.format("第{}次", num.get()));
                    num.getAndIncrement();
                    it.setPrice(it.getOrderTotal().divide(new BigDecimal(it.getOrderNum()), 2, BigDecimal.ROUND_UP));
                    it.setBrokerage(it.getPrice().multiply(it.getRatio().divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP)));
                });
            });

            this.saveOrUpdateBatch(orderItems);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<PsyConsultBillItem> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            this.updateBatchById(list);
        }
    }

}
