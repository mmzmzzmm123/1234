package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.service.ISysRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class SysRecipesController extends BaseController {

    @Autowired
    private ISysRecipesService sysRecipesService;

    @GetMapping(value = "/{resipesId}")
    public AjaxResult getInfo(@PathVariable("resipesId") Long resipesId) {
        return AjaxResult.success(sysRecipesService.selectSysRecipesByRecipesId(resipesId));
    }
}
