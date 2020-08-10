package com.ruoyi.project.data.price.controller;

import java.util.List;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import com.ruoyi.project.data.price.service.IUltimateOfficeBasePriceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 办公基价
 *
 * @author purple
 */
@RestController
@RequestMapping("/data/compute/price/office")
public class UltimateOfficeBasePriceController extends BaseController {
    @Autowired
    private IUltimateOfficeBasePriceService officeBasePriceUltimateService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询办公基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(UltimateOfficeBasePrice officeBasePriceUltimate) {
        int pageIndex = ServletUtils.getParameterToInt(TableSupport.PAGE_NUM);
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        officeBasePriceUltimate.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        officeBasePriceUltimate.setPageSize(pageSize);
        int total = officeBasePriceUltimateService.selectOfficeBasePriceUltimateListCount(officeBasePriceUltimate);
        List<UltimateOfficeBasePrice> list =
                officeBasePriceUltimateService.selectOfficeBasePriceUltimateList(officeBasePriceUltimate);
        return getDataTable(list, total);
    }

    /**
     * 查询 年月
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/yearmonth")
    public AjaxResult yearMonthList() {
        List<VueSelectModel> list = officeBasePriceUltimateService.getYearMonthList();

        return AjaxResult.success(list);
    }

    /**
     * 获取办公基价详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/{yearMonth}/{id}")
    public AjaxResult getInfo(@PathVariable("yearMonth") Integer yearMonth, @PathVariable("id") String id) {
        return AjaxResult.success(officeBasePriceUltimateService.getById(yearMonth, id));
    }


    /**
     * 导出办公基价列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "办公基价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UltimateOfficeBasePrice officeBasePriceUltimate) {
        int total = officeBasePriceUltimateService.selectOfficeBasePriceUltimateListCount(officeBasePriceUltimate);
        officeBasePriceUltimate.setPageIndex(0);
        officeBasePriceUltimate.setPageSize(total);
        List<UltimateOfficeBasePrice> list =
                officeBasePriceUltimateService.selectOfficeBasePriceUltimateList(officeBasePriceUltimate);
        ExcelUtil<UltimateOfficeBasePrice> util = new ExcelUtil<>(UltimateOfficeBasePrice.class);
        return util.exportExcel(list, "办公基价初始化" + officeBasePriceUltimate.getYearMonth().toString());
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
    @PostMapping("/importData/{yearMonth}")
    public AjaxResult importData(@PathVariable("yearMonth") Integer yearMonth, MultipartFile file) throws Exception {
        // 修改计价
        ExcelUtil<UltimateOfficeBasePrice> util = new ExcelUtil<>(UltimateOfficeBasePrice.class);
        List<UltimateOfficeBasePrice> officeBasePriceUltimates = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = officeBasePriceUltimateService.batchImport(yearMonth, officeBasePriceUltimates, operName);
        return AjaxResult.success(message);
    }
}

