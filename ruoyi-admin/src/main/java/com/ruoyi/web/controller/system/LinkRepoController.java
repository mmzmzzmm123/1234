package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.vo.IdNameVo;
import com.ruoyi.system.service.IBladeVisualService;
import com.ruoyi.system.service.IPanoramaService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.LinkRepo;
import com.ruoyi.system.service.ILinkRepoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.xml.crypto.Data;

/**
 * 链接仓库Controller
 *
 * @author ysj
 * @date 2021-12-07
 */
@RestController
@RequestMapping("/system/repo")
public class LinkRepoController extends BaseController
{
    @Autowired
    private ILinkRepoService linkRepoService;

    @Autowired
    IBladeVisualService visualService;

    @Autowired
    ISysUserService userService;

    @Autowired
    IPanoramaService panoramaService;

    /**
     * 查询链接仓库列表
     */
//    @PreAuthorize("@ss.hasPermi('system:common:list')")
    @GetMapping("/list")
    public TableDataInfo list(LinkRepo linkRepo)
    {
        // 超级用户
        List<LinkRepo> list;
        if (SecurityUtils.getUserId() == 1) {
            startPage();
            list = linkRepoService.selectLinkRepoList(linkRepo);
            return getDataTable(list);
        }
        else {
            startPage();
            list = linkRepoService.selectLinkRepoListOfUser(SecurityUtils.getUserId());
            return getDataTable(list);
        }

    }

    @GetMapping("/expireCheck")
    public AjaxResult expireCheck(@RequestParam("visualId")Long visualId,@RequestParam("panId")Long panId){

        Long userId = SecurityUtils.getUserId();
        // 超级管理员
        if (userId == 1) {
            return toAjax(1);
        }

        return toAjax(linkRepoService.check(userId,visualId,panId));
    }

    /**
     *
     * todo 下面这三个接口返回数据库所有项，最好分页
     */

    @GetMapping("/visualIds")
    public List<IdNameVo> getVisualIds(){
        return visualService.getIdNames();
    }

    @GetMapping("/panoramaIds")
    public  List<IdNameVo> getPanIds(){
        return panoramaService.getIdNames();
    }

    @GetMapping("/users")
    public List<IdNameVo> getUsers(){
        return userService.getIdNames();
    }

    /**
     * 导出链接仓库列表
     */
    @PreAuthorize("@ss.hasPermi('system:repo:export')")
    @Log(title = "链接仓库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LinkRepo linkRepo)
    {
        List<LinkRepo> list = linkRepoService.selectLinkRepoList(linkRepo);
        ExcelUtil<LinkRepo> util = new ExcelUtil<LinkRepo>(LinkRepo.class);
        return util.exportExcel(list, "链接仓库数据");
    }

    /**
     * 获取链接仓库详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:repo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(linkRepoService.selectLinkRepoById(id));
    }

    /**
     * 新增链接仓库
     */
    @PreAuthorize("@ss.hasPermi('system:repo:add')")
    @Log(title = "链接仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated LinkRepo linkRepo)
    {
        return toAjax(linkRepoService.insertLinkRepo(linkRepo));
    }

    /**
     * 修改链接仓库
     */
    @PreAuthorize("@ss.hasPermi('system:repo:edit')")
    @Log(title = "链接仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated LinkRepo linkRepo)
    {
        return toAjax(linkRepoService.updateLinkRepo(linkRepo));
    }

    /**
     * 删除链接仓库
     */
    @PreAuthorize("@ss.hasPermi('system:repo:remove')")
    @Log(title = "链接仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(linkRepoService.deleteLinkRepoByIds(ids));
    }
}
