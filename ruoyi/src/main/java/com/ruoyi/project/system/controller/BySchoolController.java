package com.ruoyi.project.system.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.service.ISysDeptService;
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.system.domain.BySchool;
import com.ruoyi.project.system.service.IBySchoolService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysDept;

import javax.annotation.Resource;

/**
 * 幼儿园机构Controller
 * 
 * @author tsbz
 * @date 2020-04-08
 */





@RestController
@RequestMapping("/system/school")
public class BySchoolController extends BaseController
{
    @Autowired
    private IBySchoolService bySchoolService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询幼儿园机构列表
     */
    @PreAuthorize("@ss.hasPermi('system:school:list')")
    @GetMapping("/list")
    public TableDataInfo list(BySchool bySchool)
    {
        startPage();
        List<BySchool> list = bySchoolService.selectBySchoolList(bySchool);
        return getDataTable(list);
    }

    /**
     * 导出幼儿园机构列表
     */
    @PreAuthorize("@ss.hasPermi('system:school:export')")
    @Log(title = "幼儿园机构", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BySchool bySchool)
    {
        List<BySchool> list = bySchoolService.selectBySchoolList(bySchool);
        ExcelUtil<BySchool> util = new ExcelUtil<BySchool>(BySchool.class);
        return util.exportExcel(list, "school");
    }

    /**
     * 获取幼儿园机构详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:school:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bySchoolService.selectBySchoolById(id));
    }

    /**
     * 新增幼儿园机构
     */

    //调取jdbctemplate方法  读取数据库中的ID值
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PreAuthorize("@ss.hasPermi('system:school:add')")
    @Log(title = "幼儿园机构", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BySchool bySchool)
    {
        //从school表中取出当前最大的ID值  并且将其赋值给dept中的ordernumber
        String maxId = jdbcTemplate.queryForObject(String.format("select MAX(id) from by_school"),String.class);
        //生成32位UUID
        String uuid = getUUID32();
        //将UUID赋值给xxdm
        bySchool.setXxdm(uuid);
        bySchool.setCreateUser(SecurityUtils.getLoginUser().getUser().getUserId());
        bySchool.setApprovalUser(SecurityUtils.getLoginUser().getUser().getUserId());
        bySchool.setCreateTime(new Date());
        //创建dept实例  并且向要添加的dept中设置各个参数
        SysDept dept = new SysDept();
        if (maxId != "0")
        {
            dept.setOrderNum(maxId);
            System.out.println("------------" + maxId);
        }else
            {
                dept.setOrderNum("6");
            }
        dept.setSchoolId(bySchool.getXxdm());
        dept.setCreateBy(SecurityUtils.getUsername());
        dept.setParentId(200L);
        dept.setAncestors("0,100,200");
        dept.setDeptName(bySchool.getSchoolName());
        dept.setPhone(bySchool.getTel());
        dept.setLeader(SecurityUtils.getUsername());
        //插入数据
        deptService.insertDept(dept);
        return toAjax(bySchoolService.insertBySchool(bySchool));
    }

    /**
     * 修改幼儿园机构
     */
    @PreAuthorize("@ss.hasPermi('system:school:edit')")
    @Log(title = "幼儿园机构", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BySchool bySchool)
    {
        //创建dept实例  并且向要添加的dept中设置各个参数
        SysDept dept = new SysDept();
        //设置schoolID为xxdm
        dept.setSchoolId(bySchool.getXxdm());
        //通过selectdeptlist来获取deptid
        dept.setDeptId(deptService.selectDeptList(dept).get(0).getDeptId());
        dept.setStatus(bySchool.getStatus());
        deptService.updateDept(dept);
        return toAjax(bySchoolService.updateBySchool(bySchool));
    }

    /**
     * 删除幼儿园机构
     */
    @PreAuthorize("@ss.hasPermi('system:school:remove')")
    @Log(title = "幼儿园机构", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        //遍历ids
        for (int i=0;i<ids.length;i++)
        {
            //循环找出school表的id
            Long bySchId = ids[i];
            //用school表ID获取xxdm的值
            //将值赋给dept中的schoolID
            SysDept dept = new SysDept();
            dept.setSchoolId(bySchoolService.selectBySchoolById(bySchId).getXxdm());
            //通过dept表的schoolID来找出dept的List
            deptService.deleteDeptById(deptService.selectDeptList(dept).get(0).getDeptId());
    }
        return toAjax(bySchoolService.deleteBySchoolByIds(ids));
    }
}











