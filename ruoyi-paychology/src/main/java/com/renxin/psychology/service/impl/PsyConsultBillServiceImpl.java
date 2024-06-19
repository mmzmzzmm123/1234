package com.renxin.psychology.service.impl;

import com.renxin.common.utils.IDhelper;
import com.renxin.psychology.domain.PsyConsultBill;
import com.renxin.psychology.domain.PsyConsultBillItem;
import com.renxin.psychology.mapper.PsyConsultBillMapper;
import com.renxin.psychology.request.PsyAdminBillReq;
import com.renxin.psychology.service.IPsyConsultBillItemService;
import com.renxin.psychology.service.IPsyConsultBillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询核销记录Service业务层处理
 *
 * @author renxin
 * @date 2023-11-22
 */
@Service
public class PsyConsultBillServiceImpl implements IPsyConsultBillService
{

    @Resource
    private PsyConsultBillMapper mapper;

    @Resource
    private IPsyConsultBillItemService itemService;

    @Override
    public List<PsyConsultBill> getList(PsyAdminBillReq req) {
        return mapper.getList(req);
    }

    @Override
    public void edit(PsyConsultBill entity) {
        mapper.updateById(entity);
    }

    /**
     *
     * @param billTime  账单日期
     * @param startTime 核销时间
     * @param endTime   核销时间
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String billTime, String startTime, String endTime) {

        // 获取上个月账单
        PsyConsultBill bill = mapper.getBillByBillDate(billTime);

        // 如果上个月账单为空，则创建新的账单
        if (bill == null) {
            bill = new PsyConsultBill();
            bill.setId(IDhelper.getNextId());
            bill.setBillTime(billTime);
            mapper.insert(bill);
        }
        Long billId = bill.getId();

        // 更新核销单明细
        PsyAdminBillReq req = new PsyAdminBillReq();
        req.setStartTime(startTime);
        req.setEndTime(endTime);
        List<PsyConsultBillItem> billItems = itemService.getBillItems(req);
        billItems.forEach(it -> {
            it.setBillId(billId);
            it.setBillTime(billTime);
        });

        // 批量更新核销单明细
        itemService.batchUpdate(billItems);
    }


}
