package com.ruoyi.web.controller.stu;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class ShowDataController extends BaseController {
    @Autowired
    private IStuInfoMaterialService iStuInfoMaterialService;
    /**
     * 获取提交材料参数详细信息
     */
//    @PreAuthorize("@ss.hasPermi('stu:material:query')")
    @GetMapping(value = "/topData/{clsYear}")
    public AjaxResult topData(@PathVariable("clsYear") Integer clsYear)
    {
        return success(iStuInfoMaterialService.showTopData((clsYear-2002)+"级"));
    }
}
