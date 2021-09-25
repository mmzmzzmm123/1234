package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.project.benyi.domain.BySchoolcharge;
import com.ruoyi.project.benyi.service.IBySchoolchargeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 园所收费标准Controller
 *
 * @author tsbz
 * @date 2020-12-10
 */
@RestController
@RequestMapping("/benyi/schoolcharge")
public class BySchoolchargeController extends BaseController {
    @Autowired
    private IBySchoolchargeService bySchoolchargeService;

    /**
     * 查询园所收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:list')")
    @GetMapping("/list")
    public TableDataInfo list(BySchoolcharge bySchoolcharge) {
        startPage();
        List<BySchoolcharge> list = bySchoolchargeService.selectBySchoolchargeList(bySchoolcharge);
        return getDataTable(list);
    }

    /**
     * 查询幼儿收费列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:list')")
    @GetMapping("/child/list")
    public TableDataInfo childlist(BySchoolcharge bySchoolcharge) {
        System.out.println("month---" + bySchoolcharge.getMonth());
        bySchoolcharge.setMonthday(bySchoolcharge.getMonth() + "-15");
        List<BySchoolcharge> listScope = bySchoolchargeService.selectByChildchargeListByMonth(bySchoolcharge);
        if (listScope != null && listScope.size() > 0) {
            bySchoolcharge.setId(listScope.get(0).getId());
            startPage();
            List<BySchoolcharge> list = bySchoolchargeService.selectByChildchargeList(bySchoolcharge);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setZj(getZongji(list.get(i)));
                }
            }
            return getDataTable(list);
        } else {
            bySchoolcharge.setId(Long.valueOf(0));
            List<BySchoolcharge> list = bySchoolchargeService.selectByChildchargeList(bySchoolcharge);
            return getDataTable(list);
        }
    }

    //根据每条幼儿统计数计算总费用
    public double getZongji(BySchoolcharge bySchoolcharge) {
        double d = 0;
        //幼儿出勤天数
        Long days = bySchoolcharge.getDays();
        //班级类型
        String classType = bySchoolcharge.getBjtype();
        if (classType.equals("1")) {
            //托班
            //托班收费标准
            String sfbz = bySchoolcharge.getByfT();
            d = getTuofei(sfbz, days, bySchoolcharge.getHsfT());
        } else if (classType.equals("2")) {
            //小班
            //小班收费标准
            String sfbz = bySchoolcharge.getByfX();
            d = getTuofei(sfbz, days, bySchoolcharge.getHsfX());
        } else if (classType.equals("3")) {
            //中班
            //中班收费标准
            String sfbz = bySchoolcharge.getByfZ();
            d = getTuofei(sfbz, days, bySchoolcharge.getHsfZ());
        } else if (classType.equals("4")) {
            //大班
            //大班收费标准
            String sfbz = bySchoolcharge.getByf();
            d = getTuofei(sfbz, days, bySchoolcharge.getHsf());
        } else {

        }
        return d;
    }

    //根据班级收费标准、幼儿出勤天数、伙食费收费标准 计算幼儿总费用
    public double getTuofei(String tfsfbz, Long days, Double hsf) {
        double d = 0;
        //1-3:100;4-10:500;11-31:1000
        //分割各个阶段收费
        String[] arrList = tfsfbz.split(";");
        for (int i = 0; i < arrList.length; i++) {
            //分割阶段收费
            String[] arr = arrList[i].split(":");
            Long one = Long.valueOf(arr[0].split("-")[0]);
            Long two = Long.valueOf(arr[0].split("-")[1]);
            if (days >= one && days <= two) {
                d = days * hsf + Double.valueOf(arr[1]);
                break;
            }
        }

        return d;
    }

    /**
     * 导出园所收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:export')")
    @Log(title = "园所收费标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BySchoolcharge bySchoolcharge) {
        List<BySchoolcharge> list = bySchoolchargeService.selectBySchoolchargeList(bySchoolcharge);
        ExcelUtil<BySchoolcharge> util = new ExcelUtil<BySchoolcharge>(BySchoolcharge.class);
        return util.exportExcel(list, "schoolcharge");
    }

    /**
     * 获取园所收费标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bySchoolchargeService.selectBySchoolchargeById(id));
    }

    /**
     * 新增园所收费标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:edit')")
    @Log(title = "园所收费标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BySchoolcharge bySchoolcharge) {
        bySchoolcharge.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        bySchoolcharge.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(bySchoolchargeService.insertBySchoolcharge(bySchoolcharge));
    }

    /**
     * 修改园所收费标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:edit')")
    @Log(title = "园所收费标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BySchoolcharge bySchoolcharge) {
        int iCount = 0;
        BySchoolcharge bySchoolchargeNew = bySchoolchargeService.selectBySchoolchargeById(bySchoolcharge.getId());
        if (bySchoolcharge.getStartTime().equals(bySchoolchargeNew.getStartTime())) {
            if (bySchoolcharge.getEndtime().equals(bySchoolchargeNew.getEndtime())) {
                iCount = iCount + bySchoolchargeService.updateBySchoolcharge(bySchoolcharge);
            } else {
                bySchoolchargeNew.setIsdel("1");
                iCount = iCount + bySchoolchargeService.updateBySchoolcharge(bySchoolchargeNew);
                iCount = iCount + bySchoolchargeService.insertBySchoolcharge(bySchoolcharge);
            }
        } else {
            bySchoolchargeNew.setIsdel("1");
            iCount = iCount + bySchoolchargeService.updateBySchoolcharge(bySchoolchargeNew);
            iCount = iCount + bySchoolchargeService.insertBySchoolcharge(bySchoolcharge);
        }

        return toAjax(iCount);
    }

    /**
     * 删除园所收费标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:remove')")
    @Log(title = "园所收费标准", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bySchoolchargeService.deleteBySchoolchargeByIds(ids));
    }
}
