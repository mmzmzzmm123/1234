package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.psychology.domain.PsyConsultingBannerConfig;
import com.ruoyi.psychology.service.IPsyConsultingBannerConfigService;
import com.ruoyi.psychology.vo.PsyConsultingBannerConfigVO;
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
@RequestMapping("/app/consultant/banner")
public class AppPsyBannerController extends BaseController
{
    @Resource
    private IPsyConsultingBannerConfigService psyConsultingBannerConfigService;

    /**
     * 查询banner列表
     */
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultingBannerConfigVO req)
    {
        startPage();
        List<PsyConsultingBannerConfig> list = psyConsultingBannerConfigService.getList(req);
        return getDataTable(list);
    }

}
