package com.ruoyi.web.controller.jxjs;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import com.ruoyi.jxjs.service.ITsbzJxjsjbxxService;
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
import com.ruoyi.jxjs.domain.TsbzJdx;
import com.ruoyi.jxjs.service.ITsbzJdxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地校Controller
 *
 * @author ruoyi
 * @date 2020-08-20
 */
@RestController
@RequestMapping("/jxjs/jdx")
public class TsbzJdxController extends BaseController {
    @Autowired
    private ITsbzJdxService tsbzJdxService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private ITsbzJxjsjbxxService tsbzJxjsjbxxService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询基地校列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:list')"+ "||@ss.hasPermi('jxjs:jxzxmd:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdx tsbzJdx) {
        //首先判断是否为学校用户
        String jdxId = schoolCommonController.deptIdToJdxId();
        if (!schoolCommonController.isStringEmpty(jdxId)) {
            tsbzJdx.setId(jdxId);
        }
        startPage();
        List<TsbzJdx> list = tsbzJdxService.selectTsbzJdxList(tsbzJdx);
        return getDataTable(list);
    }

    /**
     * 导出基地校列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:export')")
    @Log(title = "基地校", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdx tsbzJdx) {
        List<TsbzJdx> list = tsbzJdxService.selectTsbzJdxList(tsbzJdx);
        ExcelUtil<TsbzJdx> util = new ExcelUtil<TsbzJdx>(TsbzJdx.class);
        return util.exportExcel(list, "jdx");
    }

    /**
     * 获取基地校详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(tsbzJdxService.selectTsbzJdxById(id));
    }

    /**
     * 新增基地校
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:add')")
    @Log(title = "基地校", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdx tsbzJdx) {
        String uuid = schoolCommonController.getUuid();
        tsbzJdx.setId(uuid);
        //将当前记录插入的dept表
        SysDept dept = new SysDept();
        dept.setSchoolid(uuid);
        dept.setDeptName(tsbzJdx.getJdxmc());
        dept.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserName());
        dept.setParentId(200L);
        dept.setAncestors("0,100,200");
        dept.setOrderNum(String.valueOf(tsbzJdxService.selectTsbzJdxList(null).size()));
        deptService.insertDept(dept);
        System.out.println(dept + "======================");
        return toAjax(tsbzJdxService.insertTsbzJdx(tsbzJdx));
    }

    /**
     * 修改基地校
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:edit')")
    @Log(title = "基地校", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdx tsbzJdx) {
        //创建dept实例  并且向要添加的dept中设置各个参数
        SysDept dept = new SysDept();
        //设置schoolID为xxdm
        dept.setSchoolid(tsbzJdx.getId());
        SysDept deptNew = deptService.selectDeptList(dept).get(0);
        deptNew.setDeptName(tsbzJdx.getJdxmc());
        deptNew.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserName());
        deptService.updateDept(deptNew);
        return toAjax(tsbzJdxService.updateTsbzJdx(tsbzJdx));
    }

    /**
     * 删除基地校
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:remove')")
    @Log(title = "基地校", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        TsbzJxjsjbxx tsbzJxjsjbxx = null;
        //判断当前基地校是否已分配学生
        for (int i = 0; i < ids.length; i++) {
            tsbzJxjsjbxx = new TsbzJxjsjbxx();
            tsbzJxjsjbxx.setJdxid(ids[i]);
            List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxList(tsbzJxjsjbxx);
            if (list != null && list.size() > 0) {
                return AjaxResult.error("当前基地校已分配见习教师，无法删除");
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
                return AjaxResult.error("当前选中的基地校存在用户数据，无法删除");
            }
        }
        //删除过程
        for (int i = 0; i < ids.length; i++) {
            dept = new SysDept();
            dept.setSchoolid(ids[i]);
            dept = deptService.selectDeptList(dept).get(0);
            deptService.deleteDeptById(dept.getDeptId());
        }
        return toAjax(tsbzJdxService.deleteTsbzJdxByIds(ids));
    }
}
