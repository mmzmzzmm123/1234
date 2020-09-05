package com.ruoyi.web.controller.jxzxkhgl;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.jxjs.domain.TsbzJxzxmd;
import com.ruoyi.jxjs.service.ITsbzJxzxmdService;
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
@RequestMapping("/jxzxkhgl/statistics")
public class TsbzJxzxStatisticsController extends BaseController {
    @Autowired
    private ITsbzJxzxmdService tsbzJxzxmdService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询见习之星名单列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxmd tsbzJxzxmd) {

        //首先判断是否为学校用户
        String jdxId = schoolCommonController.deptIdToJdxId();
        if (!schoolCommonController.isStringEmpty(jdxId)) {
            tsbzJxzxmd.setJdxid(jdxId);
        }

        System.out.println("nf:" + tsbzJxzxmd.getNf());
        System.out.println("faid:" + tsbzJxzxmd.getFaid());
        System.out.println("name:" + tsbzJxzxmd.getName());
        startPage();
        List<TsbzJxzxmd> list = tsbzJxzxmdService.selectTsbzJxzxmdKhjdList(tsbzJxzxmd);
        return getDataTable(list);
    }

}
