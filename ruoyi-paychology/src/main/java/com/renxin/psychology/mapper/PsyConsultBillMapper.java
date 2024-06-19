package com.renxin.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsultBill;
import com.renxin.psychology.request.PsyAdminBillReq;

import java.util.List;

public interface PsyConsultBillMapper extends BaseMapper<PsyConsultBill>
{

    List<PsyConsultBill> getList(PsyAdminBillReq req);

    PsyConsultBill getBillByBillDate(String billTime);

}
