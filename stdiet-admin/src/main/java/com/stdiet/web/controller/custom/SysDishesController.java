package com.stdiet.web.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.domain.SysDishesIngredient;
import com.stdiet.custom.service.ISysDishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品Controller
 *
 * @author wonder
 * @date 2020-12-28
 */
@RestController
@RequestMapping("/custom/dishes")
public class SysDishesController extends BaseController {
    @Autowired
    private ISysDishesService sysDishesService;

    /**
     * 查询菜品列表
     */
    @PreAuthorize("@ss.hasPermi('custom:dishes:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDishes sysDishes) {
        startPage();
        List<SysDishes> list = sysDishesService.selectSysDishesList(sysDishes);
        for (SysDishes dishes : list) {
            List<SysDishesIngredient> ingredients = sysDishesService.selectSysIngreditentsById(dishes.getId());
            if (StringUtils.isNull(ingredients)) {
                dishes.setIgdList(new ArrayList<>());
            } else {
                dishes.setIgdList(ingredients);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出菜品列表
     */
    @PreAuthorize("@ss.hasPermi('custom:dishes:export')")
    @Log(title = "菜品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysDishes sysDishes) {
        List<SysDishes> list = sysDishesService.selectSysDishesList(sysDishes);
        ExcelUtil<SysDishes> util = new ExcelUtil<SysDishes>(SysDishes.class);
        return util.exportExcel(list, "dishes");
    }

    /**
     * 获取菜品详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:dishes:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysDishesService.selectSysDishesById(id));
    }

    /**
     * 查看菜品可选餐类
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/menuTypes/{id}")
    public AjaxResult getMenuTypes(@PathVariable("id") Long id) {
        JSONObject object = new JSONObject();
        object.put("type", sysDishesService.getDishesMenuTypeById(id));
        return AjaxResult.success(object);
    }

    /**
     * 新增菜品
     */
    @PreAuthorize("@ss.hasPermi('custom:dishes:add')")
    @Log(title = "菜品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDishes sysDishes) {
        if(sysDishes.getDishClass() != null && sysDishes.getDishClass().length == 2){
            sysDishes.setBigClass(sysDishes.getDishClass()[0]);
            sysDishes.setSmallClass(sysDishes.getDishClass()[1]);
        }
        return toAjax(sysDishesService.insertSysDishes(sysDishes));
    }

    /**
     * 修改菜品
     */
    @PreAuthorize("@ss.hasPermi('custom:dishes:edit')")
    @Log(title = "菜品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDishes sysDishes) {
        if(sysDishes.getDishClass() != null && sysDishes.getDishClass().length == 2){
            sysDishes.setBigClass(sysDishes.getDishClass()[0]);
            sysDishes.setSmallClass(sysDishes.getDishClass()[1]);
        }
        return toAjax(sysDishesService.updateSysDishes(sysDishes));
    }

    /**
     * 删除菜品
     */
    @PreAuthorize("@ss.hasPermi('custom:dishes:remove')")
    @Log(title = "菜品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(sysDishesService.deleteSysDishesById(id));
    }
}