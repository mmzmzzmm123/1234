package com.jlt.csa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.jlt.csa.domain.FarmerContract;
import com.jlt.csa.service.IFarmerContractService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员签约Controller
 * 
 * @author 郏磊涛
 * @date 2022-04-01
 */
@RestController
@RequestMapping("/csa/contract")
public class FarmerContractController extends BaseController
{
    @Autowired
    private IFarmerContractService farmerContractService;

    /**
     * 查询会员签约列表
     */
    @PreAuthorize("@ss.hasPermi('csa:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(FarmerContract farmerContract)
    {
        startPage();
        List<FarmerContract> list = farmerContractService.selectFarmerContractList(farmerContract);
        return getDataTable(list);
    }

    /**
     * 导出会员签约列表
     */
    @PreAuthorize("@ss.hasPermi('csa:contract:export')")
    @Log(title = "会员签约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FarmerContract farmerContract)
    {
        List<FarmerContract> list = farmerContractService.selectFarmerContractList(farmerContract);
        ExcelUtil<FarmerContract> util = new ExcelUtil<FarmerContract>(FarmerContract.class);
        util.exportExcel(response, list, "会员签约数据");
    }

    /**
     * 获取会员签约详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:contract:query')")
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") Long contractId)
    {
        return AjaxResult.success(farmerContractService.selectFarmerContractByContractId(contractId));
    }

    /**
     * 新增会员签约
     */
    @PreAuthorize("@ss.hasPermi('csa:contract:add')")
    @Log(title = "会员签约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FarmerContract farmerContract)
    {
        return toAjax(farmerContractService.insertFarmerContract(farmerContract));
    }

    /**
     * 修改会员签约
     */
    @PreAuthorize("@ss.hasPermi('csa:contract:edit')")
    @Log(title = "会员签约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FarmerContract farmerContract)
    {
        return toAjax(farmerContractService.updateFarmerContract(farmerContract));
    }

    /**
     * 删除会员签约
     */
    @PreAuthorize("@ss.hasPermi('csa:contract:remove')")
    @Log(title = "会员签约", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contractIds}")
    public AjaxResult remove(@PathVariable Long[] contractIds)
    {
        return toAjax(farmerContractService.deleteFarmerContractByContractIds(contractIds));
    }
}
