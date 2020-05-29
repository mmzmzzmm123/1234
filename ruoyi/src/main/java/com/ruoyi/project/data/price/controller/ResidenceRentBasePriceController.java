package com.ruoyi.project.data.price.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import com.ruoyi.project.data.price.service.IArtificialResidenceRentPriceService;
import com.ruoyi.project.data.price.service.IComputeResidenceRentPriceService;
import com.ruoyi.project.data.price.service.IUltimateResidenceRentBasePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/data/rentprice/residence")
public class ResidenceRentBasePriceController extends BaseController {
    @Autowired
    private IComputeResidenceRentPriceService computeResidenceRentPriceService;
    @Autowired
    private IArtificialResidenceRentPriceService artificialResidenceRentPriceService;
    @Autowired
    private IUltimateResidenceRentBasePriceService ultimateResidenceRentBasePriceService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/compute/list")
    public TableDataInfo list(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        computeResidenceRentBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        computeResidenceRentBasePrice.setPageSize(pageSize);

        int total = computeResidenceRentPriceService.selectCount(computeResidenceRentBasePrice);
        List<ComputeResidenceRentBasePrice> list =
                computeResidenceRentPriceService.selectList(computeResidenceRentBasePrice);
        list.forEach(x -> x.setYearMonth(computeResidenceRentBasePrice.getYearMonth()));
        return getDataTable(list, total);
    }


    /**
     * 获取 住宅租赁基价详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/compute/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(computeResidenceRentPriceService.selectById(id));
    }

    /**
     * 修改 住宅租赁基价
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "住宅租赁基价", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/compute")
    public AjaxResult edit(@RequestBody ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        return toAjax(computeResidenceRentPriceService.update(computeResidenceRentBasePrice));
    }

    /**
     * 导出 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "办公基价", businessType = BusinessType.EXPORT)
    @GetMapping("/compute/export")
    public AjaxResult export(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        int total = computeResidenceRentPriceService.selectCount(computeResidenceRentBasePrice);
        computeResidenceRentBasePrice.setPageIndex(0);
        computeResidenceRentBasePrice.setPageSize(total);
        List<ComputeResidenceRentBasePrice> list =
                computeResidenceRentPriceService.selectList(computeResidenceRentBasePrice);
        ExcelUtil<ComputeResidenceRentBasePrice> util = new ExcelUtil<>(ComputeResidenceRentBasePrice.class);
        return util.exportExcel(list, "住宅租赁基价");
    }

    /**
     * 办公基价导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "办公基价", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/compute/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<ComputeResidenceRentBasePrice> util = new ExcelUtil<>(ComputeResidenceRentBasePrice.class);
        List<ComputeResidenceRentBasePrice> computeResidenceRentBasePrices = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = computeResidenceRentPriceService.batchImport(computeResidenceRentBasePrices, operName);
        return AjaxResult.success(message);
    }


    /**
     * 人工修正住宅租赁
     *
     * @param artificialResidenceRentBasePrice
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/artificial/list")
    public TableDataInfo artificialByList(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        artificialResidenceRentBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        artificialResidenceRentBasePrice.setPageSize(pageSize);

        int total = artificialResidenceRentPriceService.selectCount(artificialResidenceRentBasePrice);
        List<ArtificialResidenceRentBasePrice> list =
                artificialResidenceRentPriceService.selectList(artificialResidenceRentBasePrice);
        list.forEach(x -> x.setYearMonth(artificialResidenceRentBasePrice.getYearMonth()));
        return getDataTable(list, total);
    }


    /**
     * 查询 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/ultimate/list")
    public TableDataInfo ultimateResidenceRentBasePriceList(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        ultimateResidenceRentBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        ultimateResidenceRentBasePrice.setPageSize(pageSize);

        int total = ultimateResidenceRentBasePriceService.selectCount(ultimateResidenceRentBasePrice);
        List<UltimateResidenceRentBasePrice> list =
                ultimateResidenceRentBasePriceService.selectList(ultimateResidenceRentBasePrice);
        list.forEach(x -> x.setYearMonth(ultimateResidenceRentBasePrice.getYearMonth()));
        return getDataTable(list, total);
    }


    /**
     * 获取 住宅租赁基价详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/ultimate/{id}")
    public AjaxResult ultimateResidenceRentBasePriceGet(@PathVariable("id") Integer id) {
        return AjaxResult.success(ultimateResidenceRentBasePriceService.selectById(id));
    }

    /**
     * 修改 住宅租赁基价
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "住宅租赁基价", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/ultimate")
    public AjaxResult ultimateResidenceRentBasePriceEdit(@RequestBody UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        return toAjax(ultimateResidenceRentBasePriceService.update(ultimateResidenceRentBasePrice));
    }

    /**
     * 导出 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "审核住宅租赁基价", businessType = BusinessType.EXPORT)
    @GetMapping("/ultimate/export")
    public AjaxResult ultimateResidenceRentBasePriceExport(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        int total = ultimateResidenceRentBasePriceService.selectCount(ultimateResidenceRentBasePrice);
        ultimateResidenceRentBasePrice.setPageIndex(0);
        ultimateResidenceRentBasePrice.setPageSize(total);
        List<UltimateResidenceRentBasePrice> list =
                ultimateResidenceRentBasePriceService.selectList(ultimateResidenceRentBasePrice);
        ExcelUtil<UltimateResidenceRentBasePrice> util = new ExcelUtil<>(UltimateResidenceRentBasePrice.class);
        return util.exportExcel(list, "核准住宅租赁基价");
    }

}
