package com.ruoyi.project.data.basis.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.data.basis.domain.LianJiaCommunityDict;
import com.ruoyi.project.data.basis.service.ILianJiaCommunityDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 链家id和联城id对应关系
 * 小区名称和小区地址标准化对应关系
 * 楼栋名称和楼栋地址标准化对应关系
 */
@RestController
@RequestMapping("/data/adrdict/lianjia")
public class AdrDictController extends BaseController {
    @Autowired
    private ILianJiaCommunityDictService lianJiaCommunityDictService;

    /**
     * @param lianJiaCommunityDict
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(LianJiaCommunityDict lianJiaCommunityDict) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        lianJiaCommunityDict.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        lianJiaCommunityDict.setPageSize(pageSize);

        int total = lianJiaCommunityDictService.selectPageOfTotal(lianJiaCommunityDict);
        List<LianJiaCommunityDict> list =
                lianJiaCommunityDictService.selectPageOfList(lianJiaCommunityDict);
        return getDataTable(list, total);
    }

    /**
     * 修改 住宅租赁基价
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "链家小区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LianJiaCommunityDict lianJiaCommunityDict) {
        return toAjax(lianJiaCommunityDictService.update(lianJiaCommunityDict));
    }

    /**
     * 新增角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "链家小区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody LianJiaCommunityDict lianJiaCommunityDict) {
        return toAjax(lianJiaCommunityDictService.insert(lianJiaCommunityDict));
    }

    /**
     * 删除角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "链家小区", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id) {
        return toAjax(lianJiaCommunityDictService.delete(id));
    }

    /**
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Integer id) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, lianJiaCommunityDictService.selectById(id));
        return ajax;
    }
}
