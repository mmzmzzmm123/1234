package com.ruoyi.web.controller.source;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.entity.SourceCalculationMaterialCost;
import com.ruoyi.service.ISourceCalculationMaterialCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 成本测算---材料费 前端控制器
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Api(tags = "源数据---成本测算（材料费）")
@Controller
@RequestMapping("/sourceCalculationMaterialCost")
public class SourceCalculationMaterialCostController extends BaseController {
    @Autowired
    private ISourceCalculationMaterialCostService sourceCalculationMaterialCostService;

    @ApiOperation("导入")
    @PostMapping("/import")
    public AjaxResult importMultipartFile(MultipartFile file) throws IOException {
        sourceCalculationMaterialCostService.importData(file);
        return success("导入成功。。。");
    }

    @ApiOperation("新增编辑")
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveOrUpdate(@RequestBody SourceCalculationMaterialCost sourceCalculationMaterialCost) {
        sourceCalculationMaterialCostService.saveOrUpdateData(sourceCalculationMaterialCost);
        return success();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable("id") Integer id) {
        sourceCalculationMaterialCostService.removeById(id);
        return success();
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<SourceCalculationMaterialCost> list = sourceCalculationMaterialCostService.dataList();
        return getDataTable(list);
    }
}
