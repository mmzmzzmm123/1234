package com.ruoyi.web.controller.source;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.PageDto;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.entity.SourceMixtureResolve;
import com.ruoyi.entity.SourceZeroBill;
import com.ruoyi.service.ISourceMixtureResolveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

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
public class SourceMixtureResolveController extends BaseController {

    @Autowired
    private ISourceMixtureResolveService sourceMixtureResolveService;

    @ApiOperation("导入")
    @PostMapping("/import")
    public AjaxResult importMultipartFile(MultipartFile file) throws IOException {
        sourceMixtureResolveService.importData(file);
        return success("导入成功。。。");
    }

    @ApiOperation("新增编辑")
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveOrUpdate(@RequestBody SourceMixtureResolve sourceMixtureResolve) {
        sourceMixtureResolveService.saveOrUpdateData(sourceMixtureResolve);
        return success();
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable("id") Integer id) {
        sourceMixtureResolveService.removeById(id);
        return success();
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<SourceMixtureResolve> list = sourceMixtureResolveService.dataList();
        return getDataTable(list);
    }
}
