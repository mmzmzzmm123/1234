package com.ruoyi.mail.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.mail.domain.MailUser;
import com.ruoyi.mail.service.IMailUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 邮件用户列表Controller
 * 
 * @author ruoyi
 * @date 2023-10-21
 */
@Api("邮件用户列表")
@RestController
@RequestMapping("/mail/user")
public class MailUserController extends BaseController
{
    @Autowired
    private IMailUserService mailUserService;

    /**
     * 查询邮件用户列表列表
     */
    @ApiOperation("查询邮件用户列表列表")
    @PreAuthorize("@ss.hasPermi('mail:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(MailUser mailUser)
    {
        startPage();
        List<MailUser> list = mailUserService.selectMailUserList(mailUser);
        return getDataTable(list);
    }

    /**
     * 导出邮件用户列表列表
     */
    @ApiOperation("导出邮件用户列表列表")
    @PreAuthorize("@ss.hasPermi('mail:user:export')")
    @Log(title = "邮件用户列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MailUser mailUser)
    {
        List<MailUser> list = mailUserService.selectMailUserList(mailUser);
        ExcelUtil<MailUser> util = new ExcelUtil<MailUser>(MailUser.class);
        util.exportExcel(response, list, "邮件用户列表数据");
    }

    /**
     * 获取邮件用户列表详细信息
     */
    @ApiOperation("获取邮件用户列表详细信息")
    @PreAuthorize("@ss.hasPermi('mail:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mailUserService.selectMailUserById(id));
    }

    /**
     * 新增邮件用户列表
     */
    @ApiOperation("新增邮件用户列表")
    @PreAuthorize("@ss.hasPermi('mail:user:add')")
    @Log(title = "邮件用户列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MailUser mailUser)
    {
        return toAjax(mailUserService.insertMailUser(mailUser));
    }

    /**
     * 修改邮件用户列表
     */
    @ApiOperation("修改邮件用户列表")
    @PreAuthorize("@ss.hasPermi('mail:user:edit')")
    @Log(title = "邮件用户列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MailUser mailUser)
    {
        return toAjax(mailUserService.updateMailUser(mailUser));
    }

    /**
     * 删除邮件用户列表
     */
    @ApiOperation("删除邮件用户列表")
    @PreAuthorize("@ss.hasPermi('mail:user:remove')")
    @Log(title = "邮件用户列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mailUserService.deleteMailUserByIds(ids));
    }
}
