package com.ruoyi.psychology.service;


import com.ruoyi.psychology.domain.PsyConsultBillItem;
import com.ruoyi.psychology.dto.BillItemDTO;
import com.ruoyi.psychology.request.PsyAdminBillReq;

import java.math.BigDecimal;
import java.util.List;

public interface IPsyConsultBillItemService {

    void batchAdd();

    BigDecimal getBillItemSum(PsyAdminBillReq req);

    void batchUpdate(List<PsyConsultBillItem> list);

    /**
     * 查询咨询订单核销记录
     */
    List<BillItemDTO> getItemList(PsyAdminBillReq req);

    List<PsyConsultBillItem> getItemListForDetail(PsyAdminBillReq req);

    List<PsyConsultBillItem> getBillItems(PsyAdminBillReq req);

}
