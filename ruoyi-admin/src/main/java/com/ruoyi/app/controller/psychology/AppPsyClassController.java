package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.psychology.domain.PsyConsultClass;
import com.ruoyi.psychology.service.IPsyConsultClassService;
import com.ruoyi.psychology.vo.PsyConsultClassVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 心理咨询师Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/consult/class")
public class AppPsyClassController extends BaseController
{
    @Resource
    private IPsyConsultClassService PsyConsultClassService;

    /**
     * 查询class列表
     */
    @GetMapping("/list")
    @RateLimiter(count = 50, limitType = LimitType.IP)
    public TableDataInfo list(PsyConsultClassVO req)
    {
        startPage();
        List<PsyConsultClass> list = PsyConsultClassService.getList(req);
        return getDataTable(list);
    }


}
