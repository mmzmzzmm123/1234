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
import com.ruoyi.project.data.price.domain.ArtificialResidenceSaleBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;
import com.ruoyi.project.data.price.service.IArtificialResidenceRentPriceService;
import com.ruoyi.project.data.price.service.IArtificialResidenceSalePriceService;
import com.ruoyi.project.data.price.service.IOriginalResidenceSalePriceService;
import com.ruoyi.project.data.price.service.IUltimateResidenceRentBasePriceService;
import com.ruoyi.project.system.domain.UploadFile;
import com.ruoyi.project.system.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/data/price/residence/sale")
public class ResidenceSaleBasePriceController extends BaseController {

    @Autowired
    private IOriginalResidenceSalePriceService computeResidenceSalePriceService;
    @Autowired
    private IArtificialResidenceSalePriceService artificialResidenceSalePriceService;
    @Autowired
    private IUltimateResidenceRentBasePriceService ultimateResidenceRentBasePriceService;
    @Autowired
    private IUploadFileService fileService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/compute/list")
    public TableDataInfo computeList(ComputeResidenceSaleBasePrice computeResidenceSaleBasePrice) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        computeResidenceSaleBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        computeResidenceSaleBasePrice.setPageSize(pageSize);

        int total = computeResidenceSalePriceService.selectCount(computeResidenceSaleBasePrice);
        List<ComputeResidenceSaleBasePrice> list =
                computeResidenceSalePriceService.selectList(computeResidenceSaleBasePrice);
        list.forEach(x -> x.setYearMonth(computeResidenceSaleBasePrice.getYearMonth()));
        return getDataTable(list, total);
    }

    /**
     * 查询 年月
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/compute/yearmonth")
    public AjaxResult computeYearMonthList() {
        List<VueSelectModel> list = computeResidenceSalePriceService.getYearMonth();

        return AjaxResult.success(list);
    }

    /**
     * 导出 住宅销售基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "住宅销售基价", businessType = BusinessType.EXPORT)
    @GetMapping("/compute/export")
    public AjaxResult computeExport(ComputeResidenceSaleBasePrice
                                            computeResidenceSaleBasePrice) {
        int total = computeResidenceSalePriceService.selectCount(computeResidenceSaleBasePrice);
        computeResidenceSaleBasePrice.setPageIndex(0);
        computeResidenceSaleBasePrice.setPageSize(total);
        List<ComputeResidenceSaleBasePrice> list =
                computeResidenceSalePriceService.selectList(computeResidenceSaleBasePrice);
        ExcelUtil<ComputeResidenceSaleBasePrice> util = new ExcelUtil<>(ComputeResidenceSaleBasePrice.class);
        return util.exportExcel(list, "住宅销售基价" + computeResidenceSaleBasePrice.getYearMonth());
    }

    /**
     * 查询 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:artificial:list')")
    @GetMapping("/artificial/list")
    public TableDataInfo artificialList(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        artificialResidenceSaleBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        artificialResidenceSaleBasePrice.setPageSize(pageSize);

        int total = artificialResidenceSalePriceService.selectCount(artificialResidenceSaleBasePrice);
        List<ArtificialResidenceSaleBasePrice> list =
                artificialResidenceSalePriceService.selectList(artificialResidenceSaleBasePrice);
        list.forEach(x -> x.setYearMonth(artificialResidenceSaleBasePrice.getYearMonth()));
        return getDataTable(list, total);
    }

    /**
     * 查询 年月
     */
    @PreAuthorize("@ss.hasPermi('system:artificial:list')")
    @GetMapping("/artificial/yearmonth")
    public AjaxResult artificialYearMonthList() {
        List<VueSelectModel> list = artificialResidenceSalePriceService.getYearMonth();

        return AjaxResult.success(list);
    }

    /**
     * 导出 住宅销售基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:artificial:export')")
    @Log(title = "住宅销售基价", businessType = BusinessType.EXPORT)
    @GetMapping("/artificial/export")
    public AjaxResult artificialExport(ArtificialResidenceSaleBasePrice
                                               artificialResidenceSaleBasePrice) {
        int total = artificialResidenceSalePriceService.selectCount(artificialResidenceSaleBasePrice);
        artificialResidenceSaleBasePrice.setPageIndex(0);
        artificialResidenceSaleBasePrice.setPageSize(total);
        List<ArtificialResidenceSaleBasePrice> list =
                artificialResidenceSalePriceService.selectList(artificialResidenceSaleBasePrice);
        ExcelUtil<ArtificialResidenceSaleBasePrice> util = new ExcelUtil<>(ArtificialResidenceSaleBasePrice.class);
        return util.exportExcel(list, "人工修正住宅销售基价" + artificialResidenceSaleBasePrice.getYearMonth());
    }

    /**
     * 获取 人工修正基价详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/artificial/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(artificialResidenceSalePriceService.selectById(id));
    }

    /**
     * 修改 住宅租赁基价
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "住宅售价基价", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/artificial")
    public AjaxResult edit(@RequestBody ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice) {
        return toAjax(artificialResidenceSalePriceService.updateEntity(artificialResidenceSaleBasePrice));
    }

    /**
     * 办公基价导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "住宅售价基价", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/artificial/importData/{yearMonth}")
    public AjaxResult importData(@PathVariable("yearMonth") Integer yearMonth,
                                 MultipartFile file) throws Exception {
        ExcelUtil<ComputeResidenceSaleBasePrice> util = new ExcelUtil<>(ComputeResidenceSaleBasePrice.class);
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

        List<ComputeResidenceSaleBasePrice> artificialResidenceSaleBasePrices = util.importExcel(file.getInputStream
                ());
        if (StringUtils.isNull(artificialResidenceSaleBasePrices) || artificialResidenceSaleBasePrices.size() == 0) {
            throw new CustomException("人工修正住宅销售基价不能为空！");
        }
        String message = artificialResidenceSalePriceService.batchImport(yearMonth, artificialResidenceSaleBasePrices);
        return AjaxResult.success(message);
    }

//
//    /**
//     * 人工修正住宅租赁
//     *
//     * @param artificialResidenceRentBasePrice
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:list')")
//    @GetMapping("/artificial/list")
//    public TableDataInfo artificialByList(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice) {
//        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
//        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
//        artificialResidenceRentBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
//        artificialResidenceRentBasePrice.setPageSize(pageSize);
//
//        int total = artificialResidenceRentPriceService.selectCount(artificialResidenceRentBasePrice);
//        List<ArtificialResidenceRentBasePrice> list =
//                artificialResidenceRentPriceService.selectList(artificialResidenceRentBasePrice);
//        list.forEach(x -> x.setYearMonth(artificialResidenceRentBasePrice.getYearMonth()));
//        return getDataTable(list, total);
//    }
//
//
//    // 文件保存
//
//
//    /**
//     * 查询 住宅租赁基价列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:list')")
//    @GetMapping("/ultimate/list")
//    public TableDataInfo ultimateResidenceRentBasePriceList(UltimateResidenceRentBasePrice
//    ultimateResidenceRentBasePrice) {
//        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
//        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
//        ultimateResidenceRentBasePrice.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
//        ultimateResidenceRentBasePrice.setPageSize(pageSize);
//
//        int total = ultimateResidenceRentBasePriceService.selectCount(ultimateResidenceRentBasePrice);
//        List<UltimateResidenceRentBasePrice> list =
//                ultimateResidenceRentBasePriceService.selectList(ultimateResidenceRentBasePrice);
//        list.forEach(x -> x.setYearMonth(ultimateResidenceRentBasePrice.getYearMonth()));
//        return getDataTable(list, total);
//    }
//
//    /**
//     * 人工审核住宅租赁基价导入（模板）
//     * 记录变化的值和变化次数
//     */
//
//    /**
//     * 获取 住宅租赁基价详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:query')")
//    @GetMapping(value = "/ultimate/{id}")
//    public AjaxResult ultimateResidenceRentBasePriceGet(@PathVariable("id") Integer id) {
//        return AjaxResult.success(ultimateResidenceRentBasePriceService.selectById(id));
//    }
//
//    /**
//     * 修改 住宅租赁基价
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:edit')")
//    @Log(title = "住宅租赁基价", businessType = BusinessType.UPDATE)
//    @PutMapping(value = "/ultimate")
//    public AjaxResult ultimateResidenceRentBasePriceEdit(@RequestBody UltimateResidenceRentBasePrice
//    ultimateResidenceRentBasePrice) {
//        return toAjax(ultimateResidenceRentBasePriceService.update(ultimateResidenceRentBasePrice));
//    }
//


}
