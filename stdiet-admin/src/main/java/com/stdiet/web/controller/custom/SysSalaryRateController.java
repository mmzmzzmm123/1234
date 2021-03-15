package com.stdiet.web.controller.custom;


import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.custom.domain.SysSalaryComposeConfig;
import com.stdiet.custom.domain.SysSalaryRate;
import com.stdiet.custom.dto.request.SalaryRequest;
import com.stdiet.custom.service.ISysSalaryRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工资比例Controller
 * @author xzj
 * @date 2021-03-15
 */
@RestController
@RequestMapping("/custom/salaryRate")
public class SysSalaryRateController extends BaseController {

    @Autowired
    private ISysSalaryRateService sysSalaryRateService;

    @Log(title = "工资比例管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('custom:salaryRate:list')")
    @PostMapping("/importSalary")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        AjaxResult result = AjaxResult.success();
        ExcelUtil<SalaryRequest> util = new ExcelUtil<SalaryRequest>(SalaryRequest.class);
        List<SalaryRequest> salaryList = util.importExcel(file.getInputStream());
        if(salaryList == null && salaryList.size() > 0){
            return AjaxResult.error("Excel数据格式错误");
        }
        //薪资比例集合
        List<SysSalaryRate> sysSalaryRateList = new ArrayList<>();
        Map<String, SysSalaryComposeConfig> configMap = sysSalaryRateService.getSalaryComposeConfigMap();
        if(configMap != null && configMap.size() > 0){
            for (SalaryRequest salaryRequest : salaryList) {
                if(StringUtils.isEmpty(salaryRequest.getName()) || salaryRequest.getSalary() == null || salaryRequest.getSalary().doubleValue() < 0 ||
                        StringUtils.isEmpty(salaryRequest.getSalaryPostName()) || salaryRequest.getFullWork() == null
                        || (!"是".equals(salaryRequest.getFullWork().trim()) &&  !"否".equals(salaryRequest.getFullWork().trim()))){
                    return AjaxResult.error("Excel中第"+(sysSalaryRateList.size()+1)+"条数据格式错误");
                }
                SysSalaryComposeConfig config = configMap.get(salaryRequest.getSalaryPostName().trim());
                if(config == null){
                    return AjaxResult.error("Excel中第"+(sysSalaryRateList.size()+1)+"条数据的岗位不存在");
                }
                if(config.getBaseRateSum() > 100){
                    return AjaxResult.error("Excel中第"+(sysSalaryRateList.size()+1)+"条数据的岗位薪资配置比例超过100");
                }
                boolean fullWork = "是".equals(salaryRequest.getFullWork().trim());
                SysSalaryRate sysSalaryRate = sysSalaryRateService.calculateSingleRate(salaryRequest.getSalary(), fullWork, config);
                sysSalaryRate.setName(salaryRequest.getName().trim());
                sysSalaryRate.setSalaryPostName(salaryRequest.getSalaryPostName());
                sysSalaryRateList.add(sysSalaryRate);
            }
        }
        ExcelUtil<SysSalaryRate> sysSalaryRateExcelUtil = new ExcelUtil<SysSalaryRate>(SysSalaryRate.class);
        return sysSalaryRateExcelUtil.exportExcel(sysSalaryRateList, "sysSalaryRate");
    }

    @PreAuthorize("@ss.hasPermi('custom:salaryRate:list')")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() throws Exception{
        List<SalaryRequest> sysSalaryRateList = new ArrayList<>();
        ExcelUtil<SalaryRequest> util = new ExcelUtil<SalaryRequest>(SalaryRequest.class);
        return util.exportExcel(sysSalaryRateList, "sysSalaryRateTemplate");
    }


}
