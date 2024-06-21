package com.renxin.app.controller.psychology;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.renxin.psychology.domain.PsyConsultantAccountRecord;
import com.renxin.psychology.service.IPsyConsultantAccountRecordService;
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
import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.enums.BusinessType;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.common.core.page.TableDataInfo;

/**
 * 账户明细流水Controller
 * 
 * @author renxin
 * @date 2024-06-20
 */
@RestController
@RequestMapping("/finance/record")
public class PsyConsultantAccountRecordController extends BaseController
{
    @Autowired
    private IPsyConsultantAccountRecordService psyConsultantAccountRecordService;

    /**
     * 查询账户明细流水列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultantAccountRecord psyConsultantAccountRecord)
    {
        startPage();
        List<PsyConsultantAccountRecord> list = psyConsultantAccountRecordService.selectPsyConsultantAccountRecordList(psyConsultantAccountRecord);
        return getDataTable(list);
    }

    /**
     * 导出账户明细流水列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:export')")
    @Log(title = "账户明细流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultantAccountRecord psyConsultantAccountRecord)
    {
        List<PsyConsultantAccountRecord> list = psyConsultantAccountRecordService.selectPsyConsultantAccountRecordList(psyConsultantAccountRecord);
        ExcelUtil<PsyConsultantAccountRecord> util = new ExcelUtil<PsyConsultantAccountRecord>(PsyConsultantAccountRecord.class);
        util.exportExcel(response, list, "账户明细流水数据");
    }

    /**
     * 获取账户明细流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(psyConsultantAccountRecordService.selectPsyConsultantAccountRecordByRecordId(recordId));
    }

    /**
     * 新增账户明细流水
     */
    @PreAuthorize("@ss.hasPermi('system:record:add')")
    @Log(title = "账户明细流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultantAccountRecord psyConsultantAccountRecord)
    {
        return toAjax(psyConsultantAccountRecordService.insertPsyConsultantAccountRecord(psyConsultantAccountRecord));
    }

    /**
     * 修改账户明细流水
     */
    @PreAuthorize("@ss.hasPermi('system:record:edit')")
    @Log(title = "账户明细流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultantAccountRecord psyConsultantAccountRecord)
    {
        return toAjax(psyConsultantAccountRecordService.updatePsyConsultantAccountRecord(psyConsultantAccountRecord));
    }

    /**
     * 删除账户明细流水
     */
    @PreAuthorize("@ss.hasPermi('system:record:remove')")
    @Log(title = "账户明细流水", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(psyConsultantAccountRecordService.deletePsyConsultantAccountRecordByRecordIds(recordIds));
    }
}
