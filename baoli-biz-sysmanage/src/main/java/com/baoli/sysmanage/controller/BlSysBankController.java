package com.baoli.sysmanage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.baoli.store.domain.BaoliBizStore;
import com.baoli.store.service.IBaoliBizStoreService;
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
import com.baoli.sysmanage.domain.BlSysBank;
import com.baoli.sysmanage.service.IBlSysBankService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 银行管理Controller
 * 
 * @author niujs
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/sysmanage/bank")
public class BlSysBankController extends BaseController
{
    @Autowired
    private IBlSysBankService blSysBankService;
    @Autowired
    private IBaoliBizStoreService baoliBizStoreService;

    /**
     * 查询银行管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:list')")
    @GetMapping("/list")
    public TableDataInfo list(BlSysBank blSysBank)
    {
        if(blSysBank.isPageAble()){
            startPage();
        }
        List<BlSysBank> list = blSysBankService.selectBlSysBankList(blSysBank);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:list')")
    @GetMapping("/listSignBanks")
    public TableDataInfo listSignBanks(BlSysBank blSysBank){
        BaoliBizStore store = baoliBizStoreService.selectBaoliBizStoreById(blSysBank.getStoreId());
        JSONArray banks = JSONArray.parse(store.getSignBanks());
        List<Integer> bankIds = new ArrayList<>(1);
        for(int i=0;i<banks.size();i++){
            bankIds.add((Integer)banks.getJSONObject(i).get("bankId"));
        }
        blSysBank.setParentSignBanks(bankIds);
        List<BlSysBank> list = blSysBankService.selectBlSysBankList(blSysBank);
        // 有下级
        list = list.stream().filter((BlSysBank bl) ->{
            return bl.getParentId() !=null;
        }).collect(Collectors.toList());

        while(list.size()> 0){
            bankIds = filterList(list,bankIds);

            blSysBank.setParentSignBanks(bankIds);
            list = blSysBankService.selectBlSysBankList(blSysBank);
        }
        blSysBank.setSignBanks(bankIds);
        blSysBank.setParentSignBanks(null);
        list = blSysBankService.selectBlSysBankList(blSysBank);
        return getDataTable(list);
    }

    private List<Integer> filterList(List<BlSysBank> list,List<Integer> bankIds){
        AtomicReference<List<Integer>> finalBankIds = new AtomicReference<>(bankIds);
        list.forEach(tmpItem ->{
            finalBankIds.set(bankIds.stream().filter(bankItem -> {
                return tmpItem.getParentId().longValue() != bankItem.longValue();
            }).collect(Collectors.toList()));
        });
        list.forEach(tmpItem ->{
            finalBankIds.get().add(tmpItem.getId().intValue());
        });
        return  finalBankIds.get();
    }
    /**
     * 导出银行管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:export')")
    @Log(title = "银行管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BlSysBank blSysBank)
    {
        List<BlSysBank> list = blSysBankService.selectBlSysBankList(blSysBank);
        ExcelUtil<BlSysBank> util = new ExcelUtil<BlSysBank>(BlSysBank.class);
        util.exportExcel(response, list, "银行管理数据");
    }

    /**
     * 获取银行管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(blSysBankService.selectBlSysBankById(id));
    }

    /**
     * 新增银行管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:add')")
    @Log(title = "银行管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlSysBank blSysBank)
    {
        String bankNames;
        int result = 0;
        // 如果需要批量添加
        if(blSysBank.getName().contains(",")){
            bankNames = blSysBank.getName();
            String[] names = bankNames.split(",");
            for (String name : names) {
                blSysBank.setName(name);
                result = blSysBankService.insertBlSysBank(blSysBank);
            }
        } else {
            result = blSysBankService.insertBlSysBank(blSysBank);
        }
        return toAjax(result);
    }

    /**
     * 修改银行管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:edit')")
    @Log(title = "银行管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlSysBank blSysBank)
    {
        return toAjax(blSysBankService.updateBlSysBank(blSysBank));
    }

    /**
     * 删除银行管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:remove')")
    @Log(title = "银行管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blSysBankService.deleteBlSysBankByIds(ids));
    }
}
