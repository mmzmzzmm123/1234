package com.ruoyi.web.controller.source;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.PageDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.entity.SourceCalculationDirectCost;
import com.ruoyi.entity.SourceZeroBill;
import com.ruoyi.service.ISourceCalculationDirectCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 成本测算导入（直接费） 前端控制器
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Api(tags = "源数据---成本测算（直接费）")
@Controller
@RequestMapping("/sourceCalculationDirectCost")
public class SourceCalculationDirectCostController {

    @Autowired
    private ISourceCalculationDirectCostService sourceCalculationDirectCostService;
    @ApiOperation("导入")
    @PostMapping("/import")
    public R importCalculationDirectCost(@RequestPart("file") MultipartFile file) throws IOException {
        sourceCalculationDirectCostService.importData(file);
        return R.ok("导入成功。。。");
    }

    @ApiOperation("新增编辑")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody SourceZeroBill sourceZeroBill) {
        sourceCalculationDirectCostService.saveOrUpdateData(sourceZeroBill);
        return R.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable("id") Integer id) {
        sourceCalculationDirectCostService.removeById(id);
        return R.ok();
    }

    @ApiOperation("列表")
    @GetMapping("list")
    public R<Page<SourceCalculationDirectCost>> list(PageDto pageDto) {
        return R.ok(sourceCalculationDirectCostService.dataList(pageDto));
    }

}
