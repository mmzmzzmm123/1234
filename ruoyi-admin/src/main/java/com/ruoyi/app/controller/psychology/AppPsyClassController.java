package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.psychology.domain.PsyConsultingClass;
import com.ruoyi.psychology.service.IPsyConsultingClassService;
import com.ruoyi.psychology.vo.PsyConsultingClassVO;
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
@RequestMapping("/app/consultant/class")
public class AppPsyClassController extends BaseController
{
    @Resource
    private IPsyConsultingClassService psyConsultingClassService;

    /**
     * 查询class列表
     */
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultingClassVO req)
    {
        startPage();
        List<PsyConsultingClass> list = psyConsultingClassService.getList(req);
        return getDataTable(list);
    }


}
