package com.ruoyi.web.controller.jxzxkhgl;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.jxjs.domain.TsbzJxzxmd;
import com.ruoyi.jxjs.service.ITsbzJxzxmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 见习之星考核方案Controller
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxzxkhgl/statistics")
public class TsbzJxzxStatisticsController extends BaseController {
    @Autowired
    private ITsbzJxzxmdService tsbzJxzxmdService;

    /**
     * 查询见习之星名单列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxmd tsbzJxzxmd) {
        System.out.println("nf:" + tsbzJxzxmd.getNf());
        System.out.println("faid:" + tsbzJxzxmd.getFaid());
        startPage();
        List<TsbzJxzxmd> list = tsbzJxzxmdService.selectTsbzJxzxmdKhjdList(tsbzJxzxmd);
        return getDataTable(list);
    }

}
