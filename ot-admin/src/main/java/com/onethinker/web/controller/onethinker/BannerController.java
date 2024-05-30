package com.onethinker.web.controller.onethinker;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.onethinker.domain.Banner;
import com.onethinker.onethinker.service.IBannerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onethinker.common.annotation.Log;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.enums.BusinessType;

import com.onethinker.common.utils.poi.ExcelUtil;
import com.onethinker.common.core.page.TableDataInfo;

/**
 * 平台轮播图
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/onethinker/banner")
public class BannerController extends BaseController {
    @Autowired
    private IBannerService bannerService;

    /**
     * 查询轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(Banner banner) {
        startPage();
        List<Banner> list = bannerService.selectBannerList(banner);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:banner:export')")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/export")
    public void export(HttpServletResponse response, Banner banner) {
        List<Banner> list = bannerService.selectBannerList(banner);
        ExcelUtil<Banner> util = new ExcelUtil<>(Banner.class);
        util.exportExcel(response, list, "轮播图数据");
    }

    /**
     * 获取轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:banner:query')")
    @GetMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bannerService.selectBannerById(id));
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('onethinker:banner:add')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Banner banner) {
        return toAjax(bannerService.insertBanner(banner));
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('onethinker:banner:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Banner banner) {
        return toAjax(bannerService.updateBanner(banner));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('onethinker:banner:remove')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
    @DeleteMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bannerService.deleteBannerByIds(ids));
    }
}
