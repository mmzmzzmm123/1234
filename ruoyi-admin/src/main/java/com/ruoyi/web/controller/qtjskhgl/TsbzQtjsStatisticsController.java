package com.ruoyi.web.controller.qtjskhgl;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.qtjs.domain.TsbzQtjspxjg;
import com.ruoyi.qtjs.service.ITsbzQtjspxjgService;
import com.ruoyi.web.controller.common.SchoolCommonController;
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
@RequestMapping("/qtjskhgl/qtjsstatistics")
public class TsbzQtjsStatisticsController extends BaseController {
    @Autowired
    private ITsbzQtjspxjgService tsbzQtjspxjgService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询见习之星名单列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjsstatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjspxjg tsbzQtjspxjg) {

        //首先判断是否为学校用户
        String xxId = schoolCommonController.deptIdToXxId();
        if (!schoolCommonController.isStringEmpty(xxId)) {
            tsbzQtjspxjg.setDeptid(xxId);
        }

        System.out.println("nf:" + tsbzQtjspxjg.getNf());
        System.out.println("faid:" + tsbzQtjspxjg.getFaid());
        System.out.println("name:" + tsbzQtjspxjg.getJsxm());
        startPage();
        List<TsbzQtjspxjg> list = tsbzQtjspxjgService.selectTsbzQtjspxjgKhjdList(tsbzQtjspxjg);
        return getDataTable(list);
    }
}
