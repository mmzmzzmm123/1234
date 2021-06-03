package com.ruoyi.web.controller.qtjskhgl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import org.apache.commons.io.IOUtils;
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
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhwj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhwjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 群体教师考核文件管理Controller
 *
 * @author tsbz
 * @date 2021-06-03
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhwj")
public class TsbzQtjskhwjController extends BaseController {
    @Autowired
    private ITsbzQtjskhwjService tsbzQtjskhwjService;

    /**
     * 查询群体教师考核文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhwj tsbzQtjskhwj) {
        startPage();
        List<TsbzQtjskhwj> list = tsbzQtjskhwjService.selectTsbzQtjskhwjList(tsbzQtjskhwj);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:export')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhwj tsbzQtjskhwj) {
        List<TsbzQtjskhwj> list = tsbzQtjskhwjService.selectTsbzQtjskhwjList(tsbzQtjskhwj);
        ExcelUtil<TsbzQtjskhwj> util = new ExcelUtil<TsbzQtjskhwj>(TsbzQtjskhwj.class);
        return util.exportExcel(list, "qtjskhwj");
    }

    /**
     * 获取群体教师考核文件管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzQtjskhwjService.selectTsbzQtjskhwjById(id));
    }

    /**
     * 新增群体教师考核文件管理
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:add')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhwj tsbzQtjskhwj) {
        tsbzQtjskhwj.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzQtjskhwj.setCreateTime(new Date());
        tsbzQtjskhwj.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(tsbzQtjskhwjService.insertTsbzQtjskhwj(tsbzQtjskhwj));
    }

    /**
     * 修改群体教师考核文件管理
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:edit')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhwj tsbzQtjskhwj) {
        return toAjax(tsbzQtjskhwjService.updateTsbzQtjskhwj(tsbzQtjskhwj));
    }

    /**
     * 删除群体教师考核文件管理
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:remove')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjskhwjService.deleteTsbzQtjskhwjByIds(ids));
    }

    /**
     * 生成zip文件
     */
    private void writeZip(HttpServletResponse response, byte[] data, String fileName) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
