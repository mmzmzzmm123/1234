package com.ruoyi.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.student.domain.StuInfo;
import com.ruoyi.student.service.IStuInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Controller
 * 
 * @author wangzq
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/student/info")
public class StuInfoController extends BaseController
{
    @Autowired
    private IStuInfoService stuInfoService;

    /**
     * 查询学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuInfo stuInfo)
    {
        startPage();
        List<StuInfo> list = stuInfoService.selectStuInfoList(stuInfo);
        return getDataTable(list);
    }

    /**
     * 导出学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:info:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StuInfo stuInfo)
    {
        List<StuInfo> list = stuInfoService.selectStuInfoList(stuInfo);
        list = stuInfoService.matchDict(list);
        ExcelUtil<StuInfo> util = new ExcelUtil<StuInfo>(StuInfo.class);
        util.exportExcel(response, list, "学生信息数据");
    }

    /**
     * 获取学生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stuInfoService.selectStuInfoById(id));
    }

    /**
     * 新增学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuInfo stuInfo)
    {
        return toAjax(stuInfoService.insertStuInfo(stuInfo));
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:edit') or @ss.hasPermi('student:state:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StuInfo stuInfo)
    {
        StringBuilder msg = new StringBuilder();
        boolean pass = true;
        // 校验逻辑
        String isOnSchool = stuInfo.getIsOnSchool();
        String accommodation = stuInfo.getAccommodation();
        String accommodationPark = stuInfo.getAccommodationPark();
        String dormitoryNo = stuInfo.getDormitoryNo();
        String address = stuInfo.getAddress();
        if ("Y".equals(isOnSchool)) {
            if (StringUtils.isEmpty(accommodation)) {
                pass = false;
                msg.append("请选择住宿地点;");
            }
            if ("0".equals(accommodation)) {
                if (StringUtils.isEmpty(accommodationPark)) {
                    pass = false;
                    msg.append("请选择宿舍园区;");
                }
                if (StringUtils.isEmpty(dormitoryNo)) {
                    pass = false;
                    msg.append("请选择宿舍号;");
                }
            } else if ("1".equals(accommodationPark) || "2".equals(accommodationPark)) {
                if (StringUtils.isEmpty(address)) {
                    pass = false;
                    msg.append("请填写住址;");
                }
            }
        } else if ("N".equals(isOnSchool)) {
            if (!"3".equals(accommodation)) {
                pass = false;
                msg.append("不在校时，住宿地点请选择走读;");
            }
        }
        if (!pass) {
            return AjaxResult.error(msg.toString());
        }
        return toAjax(stuInfoService.updateStuInfo(stuInfo));
    }

    /**
     * 删除学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuInfoService.deleteStuInfoByIds(ids));
    }
}
