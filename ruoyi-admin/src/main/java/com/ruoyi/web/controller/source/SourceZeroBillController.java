package com.ruoyi.web.controller.source;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.entity.SourceZeroBill;
import com.ruoyi.req.SourcePageQeq;
import com.ruoyi.service.ISourceZeroBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 原数据---0号台账 前端控制器
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Api(tags = "源数据---0号台账")
@RestController
@RequestMapping("/source/zeroBill")
public class SourceZeroBillController extends BaseController {
    @Autowired
    private ISourceZeroBillService sourceZeroBillService;

    @ApiOperation("0号台账列表导入")
    @PostMapping("/import")
    public AjaxResult importZeroBill(@RequestPart("file") MultipartFile file) throws IOException {
        sourceZeroBillService.importData(file);
        return success("导入成功。。。");
    }

    @ApiOperation("0号台账新增编辑")
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveOrUpdate(@RequestBody SourceZeroBill sourceZeroBill) {
        sourceZeroBillService.saveOrUpdateData(sourceZeroBill);
        return success();
    }

    @ApiOperation("0号台账删除")
    @DeleteMapping("delete/{id}")
    public AjaxResult delete(@PathVariable("id") Integer id) {
        sourceZeroBillService.removeById(id);
        return success();
    }

    @ApiOperation("0号台账列表")
    @GetMapping("list")
    public TableDataInfo list(SourcePageQeq req) {
        startPage();
        List<SourceZeroBill> list = sourceZeroBillService.dataList(req);
        return getDataTable(list);
    }


}
