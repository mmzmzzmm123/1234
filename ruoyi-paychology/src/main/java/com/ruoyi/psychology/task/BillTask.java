package com.ruoyi.psychology.task;

import com.ruoyi.common.utils.NewDateUtil;
import com.ruoyi.psychology.service.IPsyConsultBillItemService;
import com.ruoyi.psychology.service.IPsyConsultBillService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component("consultBillTask")
public class BillTask {

    @Resource
    private IPsyConsultBillService psyConsultBillService;

    @Resource
    private IPsyConsultBillItemService psyConsultBillItemService;

    public void batchAdd()
    {
        psyConsultBillItemService.batchAdd();
    }

    public void create()
    {
        // 本月20号自动生成上个月账单
        Calendar c = Calendar.getInstance();
        SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-20");
        String billTime = startSdf.format(c.getTime());
        String startTime = NewDateUtil.getLastMonthStartDay();
        String endTime = NewDateUtil.getLastMonthEndDay();

        psyConsultBillService.save(billTime, startTime, endTime);
    }

}
