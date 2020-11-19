package com.ruoyi.web.controller.jxzxkhgl;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.TokenService;
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
import com.ruoyi.jxzxkhgl.domain.TsbzDsjbxx;
import com.ruoyi.jxzxkhgl.service.ITsbzDsjbxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 导师基本信息Controller
 * 
 * @author ruoyi
 * @date 2020-11-10
 */
@RestController
@RequestMapping("/jxzxkhgl/dsjbxx")
public class TsbzDsjbxxController extends BaseController
{
    @Autowired
    private ITsbzDsjbxxService tsbzDsjbxxService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询导师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:dsjbxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzDsjbxx tsbzDsjbxx)
    {
        startPage();
        List<TsbzDsjbxx> list = tsbzDsjbxxService.selectTsbzDsjbxxList(tsbzDsjbxx);
        return getDataTable(list);
    }

    /**
     * 导出导师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:dsjbxx:export')")
    @Log(title = "导师基本信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzDsjbxx tsbzDsjbxx)
    {
        List<TsbzDsjbxx> list = tsbzDsjbxxService.selectTsbzDsjbxxList(tsbzDsjbxx);
        ExcelUtil<TsbzDsjbxx> util = new ExcelUtil<TsbzDsjbxx>(TsbzDsjbxx.class);
        return util.exportExcel(list, "dsjbxx");
    }

    /**
     * 导入导师基本信息列表
     */
    @Log(title = "导师管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:dsjbxx:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<TsbzDsjbxx> util = new ExcelUtil<TsbzDsjbxx>(TsbzDsjbxx.class);
        List<TsbzDsjbxx> dsjbxxList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = tsbzDsjbxxService.importDsjbxx(dsjbxxList, updateSupport, operName);
        return AjaxResult.success(message);
    }
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<TsbzDsjbxx> util = new ExcelUtil<TsbzDsjbxx>(TsbzDsjbxx.class);
        return util.importTemplateExcel("导师数据");
    }

    /**
     * 获取导师基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:dsjbxx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzDsjbxxService.selectTsbzDsjbxxById(id));
    }

    /**
     * 新增导师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:dsjbxx:add')")
    @Log(title = "导师基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzDsjbxx tsbzDsjbxx)
    {
        return toAjax(tsbzDsjbxxService.insertTsbzDsjbxx(tsbzDsjbxx));
    }

    /**
     * 修改导师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:dsjbxx:edit')")
    @Log(title = "导师基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzDsjbxx tsbzDsjbxx)
    {
        return toAjax(tsbzDsjbxxService.updateTsbzDsjbxx(tsbzDsjbxx));
    }

    /**
     * 删除导师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:dsjbxx:remove')")
    @Log(title = "导师基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzDsjbxxService.deleteTsbzDsjbxxByIds(ids));
    }
}
