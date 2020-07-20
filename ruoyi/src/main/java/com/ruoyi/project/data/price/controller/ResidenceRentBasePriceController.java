package com.ruoyi.project.data.price.controller;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import com.ruoyi.project.data.price.service.IArtificialResidenceRentPriceService;
import com.ruoyi.project.data.price.service.IComputeResidenceRentPriceService;
import com.ruoyi.project.data.price.service.IUltimateResidenceRentPriceService;
import com.ruoyi.project.system.domain.UploadFile;
import com.ruoyi.project.system.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/data/rent-price/residence/")
public class ResidenceRentBasePriceController extends BaseController {
    @Autowired
    private IComputeResidenceRentPriceService computeResidenceRentPriceService;
    @Autowired
    private IArtificialResidenceRentPriceService artificialResidenceRentPriceService;
    @Autowired
    private IUltimateResidenceRentPriceService ultimateResidenceRentBasePriceService;
    @Autowired
    private IUploadFileService fileService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/compute/list")
    public TableDataInfo computeList(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        computeResidenceRentBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        computeResidenceRentBasePrice.setPageSize(pageSize);

        int total = computeResidenceRentPriceService.selectPageCount(computeResidenceRentBasePrice);
        List<ComputeResidenceRentBasePrice> list =
                computeResidenceRentPriceService.selectPageList(computeResidenceRentBasePrice);
        list.forEach(x -> x.setYearMonth(computeResidenceRentBasePrice.getYearMonth()));
        return getDataTable(list, total);
    }

    /**
     * 查询 年月
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/compute/yearmonth")
    public AjaxResult computeYearMonthList() {
        List<VueSelectModel> list = computeResidenceRentPriceService.getYearMonth();
        return AjaxResult.success(list);
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
     * 导出 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "住宅租赁基价", businessType = BusinessType.EXPORT)
    @GetMapping("/compute/export")
    public AjaxResult export(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        int total = computeResidenceRentPriceService.selectPageCount(computeResidenceRentBasePrice);
        computeResidenceRentBasePrice.setPageIndex(0);
        computeResidenceRentBasePrice.setPageSize(total);
        List<ComputeResidenceRentBasePrice> list =
                computeResidenceRentPriceService.selectPageList(computeResidenceRentBasePrice);
        ExcelUtil<ComputeResidenceRentBasePrice> util = new ExcelUtil<>(ComputeResidenceRentBasePrice.class);
        return util.exportExcel(list, "住宅租赁基价初始化-" + computeResidenceRentBasePrice.getYearMonth());
    }


    /**
     * 查询 人工修正住宅租赁 表名
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/artificial/yearmonth")
    public AjaxResult artificialYearMonthList() {
        List<VueSelectModel> list = artificialResidenceRentPriceService.getYearMonth();
        return AjaxResult.success(list);
    }

    /**
     * 查询 人工修正住宅租赁
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/artificial/{yearMonth}/{id}")
    public AjaxResult artificialById(@PathVariable("yearMonth") Integer yearMonth, @PathVariable("id") String id) {
        ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice =
                artificialResidenceRentPriceService.selectById(yearMonth, id);
        return AjaxResult.success(artificialResidenceRentBasePrice);
    }

    /**
     * 修改 住宅租赁基价
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "人工修正住宅租赁基价", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/artificial")
    public AjaxResult artificialEdit(@RequestBody ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice) {
        return toAjax(artificialResidenceRentPriceService.update(artificialResidenceRentBasePrice));
    }

    /**
     * 查询 人工修正住宅租赁
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
     * 导出 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "人工修正住宅租赁基价", businessType = BusinessType.EXPORT)
    @GetMapping("/artificial/export")
    public AjaxResult artificialExport(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice) {
        int total = artificialResidenceRentPriceService.selectCount(artificialResidenceRentBasePrice);
        artificialResidenceRentBasePrice.setPageIndex(0);
        artificialResidenceRentBasePrice.setPageSize(total);
        List<ArtificialResidenceRentBasePrice> list =
                artificialResidenceRentPriceService.selectList(artificialResidenceRentBasePrice);
        ExcelUtil<ArtificialResidenceRentBasePrice> util = new ExcelUtil<>(ArtificialResidenceRentBasePrice.class);
        return util.exportExcel(list, "人工修正住宅租赁基价" + artificialResidenceRentBasePrice.getYearMonth());
    }

    /**
     * 导入 人工修正住宅租赁基价
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "住宅租赁基价", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/artificial/importData/{yearMonth}")
    public AjaxResult importData(@PathVariable("yearMonth") Integer yearMonth,
                                 MultipartFile file) throws Exception {
        ExcelUtil<ComputeResidenceRentBasePrice> util = new ExcelUtil<>(ComputeResidenceRentBasePrice.class);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件名称
        String fileName = FileUploadUtils.upload(filePath, file);
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFk(yearMonth.toString());
        uploadFile.setSaveFileName(fileName);
        uploadFile.setFileName(file.getOriginalFilename());
        uploadFile.setModuleName("人工修正住宅销售基价");
        uploadFile.setCreateBy(operName);
        fileService.insert(uploadFile);

        List<ComputeResidenceRentBasePrice> artificialResidenceSaleBasePrices = util.importExcel(file.getInputStream
                ());
        if (StringUtils.isNull(artificialResidenceSaleBasePrices) || artificialResidenceSaleBasePrices.size() == 0) {
            throw new CustomException("人工修正住宅销售基价不能为空！");
        }
        String message = artificialResidenceRentPriceService.batchImport(yearMonth, artificialResidenceSaleBasePrices);
        return AjaxResult.success(message);
    }

    /**
     * 同步作价数据
     *
     * @param yearMonth
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/artificial/importSync/{yearMonth}")
    public AjaxResult artificialImportBySync(@PathVariable Integer yearMonth) {
        artificialResidenceRentPriceService.importBySync(yearMonth);
        return AjaxResult.success();
    }


    /**
     * 查询 最终住宅租赁 表名
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/ultimate/yearmonth")
    public AjaxResult ultimateYearMonthList() {
        List<VueSelectModel> list = ultimateResidenceRentBasePriceService.getYearMonth();
        return AjaxResult.success(list);
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

        int total = ultimateResidenceRentBasePriceService.selectPageCount(ultimateResidenceRentBasePrice);
        List<UltimateResidenceRentBasePrice> list =
                ultimateResidenceRentBasePriceService.selectPageList(ultimateResidenceRentBasePrice);
        list.forEach(x -> x.setYearMonth(ultimateResidenceRentBasePrice.getYearMonth()));
        return getDataTable(list, total);
    }

    /**
     * 导出 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "审核住宅租赁基价", businessType = BusinessType.EXPORT)
    @GetMapping("/ultimate/export")
    public AjaxResult ultimateResidenceRentBasePriceExport(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        int total = ultimateResidenceRentBasePriceService.selectPageCount(ultimateResidenceRentBasePrice);
        ultimateResidenceRentBasePrice.setPageIndex(0);
        ultimateResidenceRentBasePrice.setPageSize(total);
        List<UltimateResidenceRentBasePrice> list =
                ultimateResidenceRentBasePriceService.selectPageList(ultimateResidenceRentBasePrice);
        ExcelUtil<UltimateResidenceRentBasePrice> util = new ExcelUtil<>(UltimateResidenceRentBasePrice.class);
        return util.exportExcel(list, "核准住宅租赁基价");
    }
}
