package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.result.ResultTree;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * 部门信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public Result list(SysDept dept) {
        List<SysDept> depts = this.deptService.selectDeptList(dept);
        return Result.success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public Result excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = this.deptService.selectDeptList(new SysDept());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        return Result.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public Result getInfo(@PathVariable Long deptId) {
        this.deptService.checkDeptDataScope(deptId);
        return Result.success(this.deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public Result treeselect(SysDept dept) {
        List<SysDept> depts = this.deptService.selectDeptList(dept);
        return Result.success(this.deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public Result roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = this.deptService.selectDeptList(new SysDept());
        List<Long> checkedKeys = this.deptService.selectDeptListByRoleId(roleId);
        List<TreeSelect> dept = this.deptService.buildDeptTreeSelect(depts);
        return ResultTree.successDepts(checkedKeys, dept);
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(this.deptService.checkDeptNameUnique(dept))) {
            return Result.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(this.getUsername());
        return this.toAjax(this.deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@Validated @RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        this.deptService.checkDeptDataScope(deptId);
        if (UserConstants.NOT_UNIQUE.equals(this.deptService.checkDeptNameUnique(dept))) {
            return Result.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return Result.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && this.deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return Result.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(this.getUsername());
        return this.toAjax(this.deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public Result remove(@PathVariable Long deptId) {
        if (this.deptService.hasChildByDeptId(deptId)) {
            return Result.error("存在下级部门,不允许删除");
        }
        if (this.deptService.checkDeptExistUser(deptId)) {
            return Result.error("部门存在用户,不允许删除");
        }
        this.deptService.checkDeptDataScope(deptId);
        return this.toAjax(this.deptService.deleteDeptById(deptId));
    }
}
