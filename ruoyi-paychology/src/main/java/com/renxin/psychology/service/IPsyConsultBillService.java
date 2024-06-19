package com.renxin.psychology.service;

import com.renxin.psychology.domain.PsyConsultBill;
import com.renxin.psychology.request.PsyAdminBillReq;

import java.util.List;

public interface IPsyConsultBillService {

    /**
     *
     * @param billTime  账单日期
     * @param startTime 核销时间
     * @param endTime   核销时间
     */
    void save(String billTime, String startTime, String endTime);

    void edit(PsyConsultBill entity);

    List<PsyConsultBill> getList(PsyAdminBillReq req);

}
