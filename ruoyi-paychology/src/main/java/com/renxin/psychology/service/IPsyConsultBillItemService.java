package com.renxin.psychology.service;


import com.renxin.psychology.domain.PsyConsultBillItem;
import com.renxin.psychology.dto.BillItemDTO;
import com.renxin.psychology.request.PsyAdminBillReq;

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
