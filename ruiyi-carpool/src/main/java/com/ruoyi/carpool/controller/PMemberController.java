package com.ruoyi.carpool.controller;

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
import com.ruoyi.carpool.domain.PMember;
import com.ruoyi.carpool.service.IPMemberService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信注册用户信息Controller
 * 
 * @author ruoyi
 * @date 2021-12-25
 */
@RestController
@RequestMapping("/carpool/member")
public class PMemberController extends BaseController
{
    @Autowired
    private IPMemberService pMemberService;

    /**
     * 查询微信注册用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(PMember pMember)
    {
        startPage();
        List<PMember> list = pMemberService.selectPMemberList(pMember);
        return getDataTable(list);
    }

    /**
     * 导出微信注册用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:member:export')")
    @Log(title = "微信注册用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PMember pMember)
    {
        List<PMember> list = pMemberService.selectPMemberList(pMember);
        ExcelUtil<PMember> util = new ExcelUtil<PMember>(PMember.class);
        util.exportExcel(response, list, "微信注册用户信息数据");
    }

    /**
     * 获取微信注册用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:member:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(pMemberService.selectPMemberById(id));
    }

    /**
     * 新增微信注册用户信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:member:add')")
    @Log(title = "微信注册用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PMember pMember)
    {
        return toAjax(pMemberService.insertPMember(pMember));
    }

    /**
     * 修改微信注册用户信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:member:edit')")
    @Log(title = "微信注册用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PMember pMember)
    {
        return toAjax(pMemberService.updatePMember(pMember));
    }

    /**
     * 删除微信注册用户信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:member:remove')")
    @Log(title = "微信注册用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(pMemberService.deletePMemberByIds(ids));
    }
}
