package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.office.domain.WxUserStore;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.office.service.IWxUserStoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户余额Controller
 * 
 * @author ruoyi
 * @date 2023-05-27
 */
@RestController
@RequestMapping("/wx/wxuserstore")
public class WxUserStoreController extends BaseController
{
    @Autowired
    private IWxUserStoreService wxUserStoreService;

    /**
     * 查询微信用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('wx:wxuserstore:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxUserStore wxUserStore)
    {
        final LoginUser loginUser = SecurityUtils.getLoginUser();
        startPage();
        List<WxUserStore> list = wxUserStoreService.selectWxUserStoreList(wxUserStore);
        return getDataTable(list);
    }

    /**
     * 导出微信用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('wx:wxuserstore:export')")
    @Log(title = "微信用户余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxUserStore wxUserStore)
    {
        List<WxUserStore> list = wxUserStoreService.selectWxUserStoreList(wxUserStore);
        ExcelUtil<WxUserStore> util = new ExcelUtil<WxUserStore>(WxUserStore.class);
        util.exportExcel(response, list, "微信用户余额数据");
    }

    /**
     * 获取微信用户余额详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:wxuserstore:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wxUserStoreService.selectWxUserStoreById(id));
    }

    /**
     * 新增微信用户余额
     */
    @PreAuthorize("@ss.hasPermi('wx:wxuserstore:add')")
    @Log(title = "微信用户余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxUserStore wxUserStore)
    {
        return toAjax(wxUserStoreService.insertWxUserStore(wxUserStore));
    }

    /**
     * 修改微信用户余额
     */
    @PreAuthorize("@ss.hasPermi('wx:wxuserstore:edit')")
    @Log(title = "微信用户余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxUserStore wxUserStore)
    {
        return toAjax(wxUserStoreService.updateWxUserStore(wxUserStore));
    }

    /**
     * 删除微信用户余额
     */
    @PreAuthorize("@ss.hasPermi('wx:wxuserstore:remove')")
    @Log(title = "微信用户余额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wxUserStoreService.deleteWxUserStoreByIds(ids));
    }
}
