package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByChild;
import com.ruoyi.project.benyi.service.IByChildService;
import com.ruoyi.project.common.SchoolCommon;
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
import com.ruoyi.project.benyi.domain.ByChildLearndevelopmentFamily;
import com.ruoyi.project.benyi.service.IByChildLearndevelopmentFamilyService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 儿童学习与发展档案（家长）Controller
 *
 * @author tsbz
 * @date 2020-08-10
 */
@RestController
@RequestMapping("/benyi/learndevelopmentfamily")
public class ByChildLearndevelopmentFamilyController extends BaseController {
    @Autowired
    private IByChildLearndevelopmentFamilyService byChildLearndevelopmentFamilyService;
    @Autowired
    private IByChildService byChildService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询儿童学习与发展档案（家长）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        byChildLearndevelopmentFamily.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        //判断是否为班主任
        if (!schoolCommon.isStringEmpty(schoolCommon.getClassId())) {
            byChildLearndevelopmentFamily.setClassid(schoolCommon.getClassId());
        }
        startPage();
        List<ByChildLearndevelopmentFamily> list = byChildLearndevelopmentFamilyService.selectByChildLearndevelopmentFamilyList(byChildLearndevelopmentFamily);
        return getDataTable(list);
    }

    /**
     * 导出儿童学习与发展档案（家长）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:export')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        List<ByChildLearndevelopmentFamily> list = byChildLearndevelopmentFamilyService.selectByChildLearndevelopmentFamilyList(byChildLearndevelopmentFamily);
        ExcelUtil<ByChildLearndevelopmentFamily> util = new ExcelUtil<ByChildLearndevelopmentFamily>(ByChildLearndevelopmentFamily.class);
        return util.exportExcel(list, "family");
    }

    /**
     * 获取儿童学习与发展档案（家长）详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byChildLearndevelopmentFamilyService.selectByChildLearndevelopmentFamilyById(id));
    }

    /**
     * 新增儿童学习与发展档案（家长）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:add')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool()) {
            //首先判断，当前学年学期是否添加了幼儿档案
            ByChildLearndevelopmentFamily byChildLearndevelopmentFamilyNew = new ByChildLearndevelopmentFamily();
            byChildLearndevelopmentFamilyNew.setXnxq(byChildLearndevelopmentFamily.getXnxq());
            byChildLearndevelopmentFamilyNew.setChildid(byChildLearndevelopmentFamily.getChildid());
            //幼儿信息 需要班级id
            ByChild byChild = byChildService.selectByChildById(byChildLearndevelopmentFamily.getChildid());
            String strClassId = byChild.getClassid();
            if (byChild != null && !schoolCommon.isStringEmpty(strClassId)) {
                List<ByChildLearndevelopmentFamily> list = byChildLearndevelopmentFamilyService.selectByChildLearndevelopmentFamilyList(byChildLearndevelopmentFamilyNew);
                if (list != null && list.size() > 0) {
                    return AjaxResult.error("当前学期的幼儿档案已创建，无法重复创建");
                }
                byChildLearndevelopmentFamily.setClassid(strClassId);
                byChildLearndevelopmentFamily.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
                return toAjax(byChildLearndevelopmentFamilyService.insertByChildLearndevelopmentFamily(byChildLearndevelopmentFamily));
            } else {
                return AjaxResult.error("当前幼儿没有班级信息");
            }
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法添加幼儿档案");
        }
    }

    /**
     * 修改儿童学习与发展档案（家长）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:edit')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        return toAjax(byChildLearndevelopmentFamilyService.updateByChildLearndevelopmentFamily(byChildLearndevelopmentFamily));
    }

    /**
     * 删除儿童学习与发展档案（家长）
     */
    @PreAuthorize("@ss.hasPermi('benyi:learndevelopmentfamily:remove')")
    @Log(title = "儿童学习与发展档案（家长）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byChildLearndevelopmentFamilyService.deleteByChildLearndevelopmentFamilyByIds(ids));
    }
}
