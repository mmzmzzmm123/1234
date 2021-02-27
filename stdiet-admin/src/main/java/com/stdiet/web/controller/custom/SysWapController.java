package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.service.ISysRecipesService;
import com.stdiet.custom.service.ISysWapServices;
import com.stdiet.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wap")
public class SysWapController extends BaseController {
    @Autowired
    ISysWapServices iSysWapServices;

    @Autowired
    ISysRecipesService iSysRecipesService;

    @Autowired
    ISysDictTypeService iSysDictTypeService;

    /**
     * 客户食谱详情
     *
     * @param outId
     * @return
     */
    @GetMapping(value = "/recipes/plans/{outId}")
    public AjaxResult detail(@PathVariable String outId) {
        return AjaxResult.success(iSysWapServices.getRecipesPlanListInfo(outId));
    }

    /**
     * 获取用户信息
     * @param outId
     * @return
     */
    @GetMapping(value = "/healthyInfo/{outId}")
    public AjaxResult healthy(@PathVariable String outId) {
        return AjaxResult.success(iSysWapServices.getHealthyDataByOutId(outId));
    }

    /**
     * 获取某天食谱菜品
     * @param id
     * @return
     */
    @GetMapping(value = "/recipes/menu/{id}")
    public AjaxResult dayilyMenu(@PathVariable Long id) {
        return AjaxResult.success(iSysRecipesService.selectDishesByMenuId(id));
    }

    /**
     * 系统字典
     * @param dictType
     * @return
     */
    @GetMapping(value = "/dict/{dictType}")
    public AjaxResult sysDict(@PathVariable String dictType) {
        return AjaxResult.success(iSysDictTypeService.selectDictDataByType(dictType));
    }
}
