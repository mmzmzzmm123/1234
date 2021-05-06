package com.ruoyi.web.controller.qtjs;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.controller.common.SchoolCommonController;
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
import com.ruoyi.qtjs.domain.TsbzJsjbxx;
import com.ruoyi.qtjs.service.ITsbzJsjbxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教师基本信息Controller
 *
 * @author ruoyi
 * @date 2020-09-14
 */
@RestController
@RequestMapping("/qtjs/jsjbxx")
public class TsbzJsjbxxController extends BaseController {
    @Autowired
    private ITsbzJsjbxxService tsbzJsjbxxService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:list')" + "||@ss.hasPermi('xypsgl:xybmsq:list')" + "||@ss.hasPermi('xypsgl:xybmsq:query')"+ "||@ss.hasPermi('xypsgl:xybmsq:add')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJsjbxx tsbzJsjbxx) {
        startPage();
        List<TsbzJsjbxx> list = tsbzJsjbxxService.selectTsbzJsjbxxList(tsbzJsjbxx);
        return getDataTable(list);
    }

    /**
     * 导出教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:export')")
    @Log(title = "教师基本信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJsjbxx tsbzJsjbxx) {
        List<TsbzJsjbxx> list = tsbzJsjbxxService.selectTsbzJsjbxxList(tsbzJsjbxx);
        ExcelUtil<TsbzJsjbxx> util = new ExcelUtil<TsbzJsjbxx>(TsbzJsjbxx.class);
        return util.exportExcel(list, "jsjbxx");
    }

    /**
     * 获取教师基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:query')" + "||@ss.hasPermi('xypsgl:xybmsq:edit')" + "||@ss.hasPermi('xypsgl:xybmsq:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(tsbzJsjbxxService.selectTsbzJsjbxxById(id));
    }

    /**
     * 新增教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:add')")
    @Log(title = "教师基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJsjbxx tsbzJsjbxx) {
        int iCount = tsbzJsjbxxService.insertTsbzJsjbxx(tsbzJsjbxx);
        if (iCount > 0) {
            //新建用户
            SysUser user = new SysUser();
            //tsbzJsjbxx = tsbzJsjbxxService.selectTsbzJsjbxxById(tsbzJsjbxx.getId());
            user.setDeptId(schoolCommonController.xxIdToDeptId(tsbzJsjbxx.getDeptid()));
            user.setUserName(tsbzJsjbxx.getDabh());
            user.setNickName(tsbzJsjbxx.getJsxm());
            user.setDelFlag("0");
            user.setEmail(tsbzJsjbxx.getDabh() + "@mhjy.edu");
            user.setPhonenumber(tsbzJsjbxx.getDabh());//档案编号
            user.setStatus("0");
            user.setSex(tsbzJsjbxx.getXb());
            user.setPassword(SecurityUtils.encryptPassword("123456"));//默认密码123456
            Long[] postIds = new Long[1];
            postIds[0] = (long) 4;//普通用户
            user.setPostIds(postIds);
            Long[] roleIds = new Long[1];
            roleIds[0] = (long) 103;//群体教师
            user.setRoleIds(roleIds);
            user.setJstype("02");
            user.setJsid(tsbzJsjbxx.getId());

            userService.insertUser(user);
        }


        return toAjax(iCount);
    }

    /**
     * 修改教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:edit')")
    @Log(title = "教师基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJsjbxx tsbzJsjbxx) {
        return toAjax(tsbzJsjbxxService.updateTsbzJsjbxx(tsbzJsjbxx));
    }

    /**
     * 删除教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:remove')")
    @Log(title = "教师基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(tsbzJsjbxxService.deleteTsbzJsjbxxByIds(ids));
    }
}
