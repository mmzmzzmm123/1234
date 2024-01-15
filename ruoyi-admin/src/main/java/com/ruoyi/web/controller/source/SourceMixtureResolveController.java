package com.ruoyi.web.controller.source;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.PageDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.entity.SourceMixtureResolve;
import com.ruoyi.service.ISourceMixtureResolveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 原数据 --- 混合料分解 前端控制器
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Api(tags = "源数据---混合料分解")
@Controller
@RequestMapping("/source/mixtureResolve")
public class SourceMixtureResolveController {

    @Autowired
    private ISourceMixtureResolveService sourceMixtureResolveService;

    @ApiOperation("导入")
    @PostMapping("/import")
    public R importMultipartFile(MultipartFile file) throws IOException {
        sourceMixtureResolveService.importData(file);
        return R.ok("导入成功。。。");
    }

    @ApiOperation("新增编辑")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody SourceMixtureResolve sourceMixtureResolve) {
        sourceMixtureResolveService.saveOrUpdateData(sourceMixtureResolve);
        return R.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id) {
        sourceMixtureResolveService.removeById(id);
        return R.ok();
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    public R<Page<SourceMixtureResolve>> list(@RequestBody PageDto pageDto) {
        return R.ok(sourceMixtureResolveService.dataList(pageDto));
    }
}
