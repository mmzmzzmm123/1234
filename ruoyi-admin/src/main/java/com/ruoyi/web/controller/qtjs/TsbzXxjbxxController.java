package com.ruoyi.web.controller.qtjs;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.qtjs.domain.TsbzJsjbxx;
import com.ruoyi.qtjs.service.ITsbzJsjbxxService;
import com.ruoyi.system.service.ISysDeptService;
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
import com.ruoyi.qtjs.domain.TsbzXxjbxx;
import com.ruoyi.qtjs.service.ITsbzXxjbxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学校信息Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@RestController
@RequestMapping("/qtjs/xxjbxx")
public class TsbzXxjbxxController extends BaseController
{
    @Autowired
    private ITsbzXxjbxxService tsbzXxjbxxService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ITsbzJsjbxxService tsbzJsjbxxService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询学校信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzXxjbxx tsbzXxjbxx)
    {

        startPage();
        List<TsbzXxjbxx> list = tsbzXxjbxxService.selectTsbzXxjbxxList(tsbzXxjbxx);
        return getDataTable(list);
    }

    /**
     * 导出学校信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:export')")
    @Log(title = "学校信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzXxjbxx tsbzXxjbxx)
    {
        List<TsbzXxjbxx> list = tsbzXxjbxxService.selectTsbzXxjbxxList(tsbzXxjbxx);
        ExcelUtil<TsbzXxjbxx> util = new ExcelUtil<TsbzXxjbxx>(TsbzXxjbxx.class);
        return util.exportExcel(list, "xxjbxx");
    }

    /**
     * 获取学校信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tsbzXxjbxxService.selectTsbzXxjbxxById(id));
    }

    /**
     * 新增学校信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:add')")
    @Log(title = "学校信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzXxjbxx tsbzXxjbxx)
    {
        String uuid = schoolCommonController.getUuid();
        tsbzXxjbxx.setId(uuid);
        System.out.println(tsbzXxjbxx.getId()+"-------------");
        //将当前记录插入的dept表
        SysDept dept = new SysDept();
        dept.setSchoolid(uuid);
        System.out.println(dept.getSchoolid()+"+++++++++++++++++");
        dept.setDeptName(tsbzXxjbxx.getXxmc());
        dept.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserName());
        dept.setParentId(300L);
        dept.setAncestors("0,100,300");
        dept.setOrderNum(String.valueOf(tsbzXxjbxxService.selectTsbzXxjbxxList(null).size()));
        System.out.println(dept + "======================");
        deptService.insertDept(dept);
        return toAjax(tsbzXxjbxxService.insertTsbzXxjbxx(tsbzXxjbxx));
    }

    /**
     * 修改学校信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:edit')")
    @Log(title = "学校信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzXxjbxx tsbzXxjbxx)
    {
        //创建dept实例  并且向要添加的dept中设置各个参数
        SysDept dept = new SysDept();
        //设置schoolID为xxdm
        dept.setSchoolid(tsbzXxjbxx.getId());
        SysDept deptNew = deptService.selectDeptList(dept).get(0);
        deptNew.setDeptName(tsbzXxjbxx.getXxmc());
        deptNew.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserName());
        deptService.updateDept(deptNew);
        return toAjax(tsbzXxjbxxService.updateTsbzXxjbxx(tsbzXxjbxx));
    }

    /**
     * 删除学校信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:remove')")
    @Log(title = "学校信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        TsbzJsjbxx tsbzJsjbxx = null;
        //判断当前基地校是否已分配学生
        for (int i = 0; i < ids.length; i++) {
            tsbzJsjbxx = new TsbzJsjbxx();
            tsbzJsjbxx.setDeptid(ids[i]);
            List<TsbzJsjbxx> list = tsbzJsjbxxService.selectTsbzJsjbxxList(tsbzJsjbxx);
            if (list != null && list.size() > 0) {
                return AjaxResult.error("当前学校已分配教师，无法删除");
            }
        }

        //先判断是否允许删除dept部门
        //检查是否允许删除
        SysDept dept = null;
        for (int i = 0; i < ids.length; i++) {
            dept = new SysDept();
            dept.setSchoolid(ids[i]);
            dept = deptService.selectDeptList(dept).get(0);

            SysUser user = new SysUser();
            user.setDeptId(dept.getDeptId());
            List<SysUser> list = userService.selectUserList(user);
            if (list != null && list.size() > 0) {
                return AjaxResult.error("当前选中的学校存在用户数据，无法删除");
            }
        }
        //删除过程
        for (int i = 0; i < ids.length; i++) {
            dept = new SysDept();
            dept.setSchoolid(ids[i]);
            dept = deptService.selectDeptList(dept).get(0);
            deptService.deleteDeptById(dept.getDeptId());
        }
        return toAjax(tsbzXxjbxxService.deleteTsbzXxjbxxByIds(ids));
    }
}
