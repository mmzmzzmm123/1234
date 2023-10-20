package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.office.domain.vo.StoreUserVo;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
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
import com.ruoyi.office.domain.TStoreUser;
import com.ruoyi.office.service.ITStoreUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商家店铺用户Controller
 *
 * @author ruoyi
 * @date 2023-10-11
 */
@RestController
@RequestMapping("/office/storeuser")
public class TStoreUserController extends BaseController {
    @Autowired
    private ITStoreUserService tStoreUserService;

    /**
     * 查询商家店铺用户列表
     */
    @PreAuthorize("@ss.hasPermi('office:storeuser:list')")
    @GetMapping("/list")
    public TableDataInfo list(TStoreUser tStoreUser) {
        startPage();
        List<TStoreUser> list = tStoreUserService.selectTStoreUserList(tStoreUser);
        return getDataTable(list);
    }

    /**
     * 查询商家店铺用户列表
     */
    @PreAuthorize("@ss.hasPermi('office:storeuser:list')")
    @GetMapping("/h5list")
    public AjaxResult h5list(TStoreUser tStoreUser) {
        List<StoreUserVo> list = tStoreUserService.selectTStoreUserH5listList(tStoreUser);
        return AjaxResult.success(list);
    }

    /**
     * 导出商家店铺用户列表
     */
    @PreAuthorize("@ss.hasPermi('office:storeuser:export')")
    @Log(title = "商家店铺用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TStoreUser tStoreUser) {
        List<TStoreUser> list = tStoreUserService.selectTStoreUserList(tStoreUser);
        ExcelUtil<TStoreUser> util = new ExcelUtil<TStoreUser>(TStoreUser.class);
        util.exportExcel(response, list, "商家店铺用户数据");
    }

    @Autowired
    ISysUserService userService;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    ISysRoleService roleService;

    /**
     * 获取商家店铺用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:storeuser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        TStoreUser query = new TStoreUser();
        query.setId(id);
        List<StoreUserVo> list = tStoreUserService.selectTStoreUserH5listList(query);
        if(list.size() != 1){
            return AjaxResult.error("没找到该用户");
        }
        return success(list.get(0));
    }

    /**
     * 新增商家店铺用户
     */
    @PreAuthorize("@ss.hasPermi('office:storeuser:add')")
    @Log(title = "商家店铺用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreUserVo tStoreUser) {
        return toAjax(tStoreUserService.insertTStoreUser(tStoreUser));
    }

    /**
     * 修改商家店铺用户
     */
    @PreAuthorize("@ss.hasPermi('office:storeuser:edit')")
    @Log(title = "商家店铺用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreUserVo tStoreUser) {
        return toAjax(tStoreUserService.updateTStoreUser(tStoreUser));
    }

    /**
     * 删除商家店铺用户
     */
    @PreAuthorize("@ss.hasPermi('office:storeuser:remove')")
    @Log(title = "商家店铺用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tStoreUserService.deleteTStoreUserByIds(ids));
    }
}
