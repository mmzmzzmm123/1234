package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultBill;
import com.ruoyi.psychology.request.PsyAdminBillReq;

import java.util.List;

public interface PsyConsultBillMapper extends BaseMapper<PsyConsultBill>
{

    List<PsyConsultBill> getList(PsyAdminBillReq req);

    PsyConsultBill getBillByBillDate(String billTime);

}
