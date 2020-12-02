package com.stdiet.web.controller.custom;

import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.page.WxServeInfo;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.ISysWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信用户Controller
 *
 * @author wonder
 * @date 2020-11-28
 */
@RestController
@RequestMapping("/custom/wxUserInfo")
public class SysWxUserInfoController extends BaseController {
    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    @Autowired
    private ISysOrderService sysOrderService;

    /**
     * 查询微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxUserInfo sysWxUserInfo) {
        startPage();
        List<SysWxUserInfo> list = sysWxUserInfoService.selectSysWxUserInfoList(sysWxUserInfo);

        for (SysWxUserInfo userInfo : list) {
            if (StringUtils.isNotEmpty(userInfo.getPhone())) {
                userInfo.setPhone(userInfo.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:export')")
    @Log(title = "微信用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxUserInfo sysWxUserInfo) {
        List<SysWxUserInfo> list = sysWxUserInfoService.selectSysWxUserInfoList(sysWxUserInfo);
        ExcelUtil<SysWxUserInfo> util = new ExcelUtil<SysWxUserInfo>(SysWxUserInfo.class);
        return util.exportExcel(list, "wxUserInfo");
    }

    /**
     * 获取微信用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:query')")
    @GetMapping(value = "/{openid}")
    public AjaxResult getInfo(@PathVariable("openid") String openid) {
        return AjaxResult.success(sysWxUserInfoService.selectSysWxUserInfoById(openid));
    }

    /**
     * 新增微信用户
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:add')")
    @Log(title = "微信用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxUserInfo sysWxUserInfo) {
        return toAjax(sysWxUserInfoService.insertSysWxUserInfo(sysWxUserInfo));
    }

    /**
     * 修改微信用户
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:edit')")
    @Log(title = "微信用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxUserInfo sysWxUserInfo) {
        return toAjax(sysWxUserInfoService.updateSysWxUserInfo(sysWxUserInfo));
    }

    /**
     * 删除微信用户
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:remove')")
    @Log(title = "微信用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{openids}")
    public AjaxResult remove(@PathVariable String[] openids) {
        return toAjax(sysWxUserInfoService.deleteSysWxUserInfoByIds(openids));
    }

    @PostMapping("/wx/serveinfo")
    public AjaxResult addUser(@RequestBody SysWxUserInfo sysWxUserInfo) {

        // 查询微信用户
        SysWxUserInfo userInfo = sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserInfo.getOpenid());
        if (StringUtils.isNull(userInfo) && StringUtils.isEmpty(sysWxUserInfo.getPhone())
                || StringUtils.isNotNull(userInfo) && StringUtils.isEmpty(userInfo.getPhone()) && StringUtils.isEmpty(sysWxUserInfo.getPhone())) {
            return AjaxResult.error(5001, "没有手机号");
        }

        // 插入/更新用户信息
        if (StringUtils.isNull(userInfo)) {
            sysWxUserInfoService.insertSysWxUserInfo(sysWxUserInfo);
        } else {
            if (StringUtils.isNotEmpty(userInfo.getPhone())) {
                sysWxUserInfo.setPhone(userInfo.getPhone());
            }
            sysWxUserInfoService.updateSysWxUserInfo(sysWxUserInfo);
        }

        // 查找订单记录
        SysOrder querySysOrder = new SysOrder();
        querySysOrder.setPhone(sysWxUserInfo.getPhone());
        List<SysOrder> list = sysOrderService.selectSysOrderList(querySysOrder);

        if (list.isEmpty()) {
            return AjaxResult.error(5002, "没有订单信息");
        }

        WxServeInfo wxServeInfo = new WxServeInfo();
        wxServeInfo.setServeStatus(list.get(0).getStatus());
        wxServeInfo.setServeTime(list.get(0).getServeTime());
        wxServeInfo.setStartTime(list.get(list.size() - 1).getStartTime());
        wxServeInfo.setWeight(list.get(list.size() - 1).getWeight());
        wxServeInfo.setPhone(list.get(0).getPhone());

        return AjaxResult.success(wxServeInfo);
    }
}