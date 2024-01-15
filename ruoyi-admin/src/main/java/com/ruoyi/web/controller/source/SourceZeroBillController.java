package com.ruoyi.web.controller.source;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.PageDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.entity.SourceZeroBill;
import com.ruoyi.service.ISourceZeroBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
public class SourceZeroBillController {
    @Autowired
    private ISourceZeroBillService sourceZeroBillService;

    @ApiOperation("0号台账列表导入")
    @PostMapping("/import")
    public R importZeroBill(@RequestPart("file") MultipartFile file) throws IOException {
        sourceZeroBillService.importData(file);
        return R.ok("导入成功。。。");
    }

    @ApiOperation("0号台账新增编辑")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody SourceZeroBill sourceZeroBill) {
        sourceZeroBillService.saveOrUpdateData(sourceZeroBill);
        return R.ok();
    }

    @ApiOperation("0号台账删除")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable("id") Integer id) {
        sourceZeroBillService.removeById(id);
        return R.ok();
    }

    @ApiOperation("0号台账列表")
    @GetMapping("list")
    public R<Page<SourceZeroBill>> list(PageDto pageDto) {
        return R.ok(sourceZeroBillService.dataList(pageDto));
    }

}
