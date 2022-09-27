package com.ruoyi.app.controller.wechat;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.service.IPsyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/user")
public class AppPsyUserController extends BaseController
{
    @Autowired
    private IPsyUserService psyUserService;

    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyUser psyUser)
    {
        startPage();
        List<PsyUser> list = psyUserService.selectPsyUserList(psyUser);
        return getDataTable(list);
    }

    /**
     * 导出用户列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:user:export')")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyUser psyUser)
    {
        List<PsyUser> list = psyUserService.selectPsyUserList(psyUser);
        ExcelUtil<PsyUser> util = new ExcelUtil<PsyUser>(PsyUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    /**
     * 获取用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(psyUserService.selectPsyUserById(id));
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('psychology:user:add')")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyUser psyUser)
    {
        return toAjax(psyUserService.insertPsyUser(psyUser));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('psychology:user:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyUser psyUser)
    {
        return toAjax(psyUserService.updatePsyUser(psyUser));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('psychology:user:remove')")
    @Log(title = "用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(psyUserService.deletePsyUserByIds(ids));
    }
}
