package com.renxin.common.task;

import com.renxin.quartz.domain.SysJobLog;
import com.renxin.quartz.service.ISysJobLogService;
import com.renxin.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("jobLogTask")
public class JobLogTask {

    @Resource
    private ISysConfigService configService;

    @Resource
    private ISysJobLogService jobLogService;

    public void clearCancelLog()
    {
        String val = configService.selectConfigByKey("job.log.clearDay");
        int dayNum = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : 30;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -dayNum);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date time = calendar.getTime();

        SysJobLog req = new SysJobLog();
        Map<String, Object> params = new HashMap<>();
        params.put("endTime", sdf.format(time));
        req.setParams(params);

        List<SysJobLog> sysJobLogs = jobLogService.selectJobLogList(req);
        if (CollectionUtils.isNotEmpty(sysJobLogs)) {
            List<Long> longs = sysJobLogs.stream().map(SysJobLog::getJobLogId).collect(Collectors.toList());
            Long[] toArray = longs.toArray(new Long[0]);
            jobLogService.deleteJobLogByIds(toArray);
            log.info("定时删除job日志");
        }
    }
}
