package com.ruoyi.web.controller.jxzxkhgl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhgcwjsj;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhgcwjsjService;
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
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhgcsj;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhgcsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核过程数据Controller
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxzxkhgl/jxzxkhgcsj")
public class TsbzJxzxkhgcsjController extends BaseController {
    @Autowired
    private ITsbzJxzxkhgcsjService tsbzJxzxkhgcsjService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private ITsbzJxzxkhgcwjsjService tsbzJxzxkhgcwjsjService;

    /**
     * 查询考核过程数据列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        startPage();
        tsbzJxzxkhgcsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        List<TsbzJxzxkhgcsj> list = tsbzJxzxkhgcsjService.selectTsbzJxzxkhgcsjList(tsbzJxzxkhgcsj);
        return getDataTable(list);
    }

    /**
     * 导出考核过程数据列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:export')")
    @Log(title = "考核过程数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        List<TsbzJxzxkhgcsj> list = tsbzJxzxkhgcsjService.selectTsbzJxzxkhgcsjList(tsbzJxzxkhgcsj);
        ExcelUtil<TsbzJxzxkhgcsj> util = new ExcelUtil<TsbzJxzxkhgcsj>(TsbzJxzxkhgcsj.class);
        return util.exportExcel(list, "jxzxkhgcsj");
    }

    /**
     * 获取考核过程数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        AjaxResult ajax = AjaxResult.success();
        TsbzJxzxkhgcsj tsbzJxzxkhgcsj = new TsbzJxzxkhgcsj();
        tsbzJxzxkhgcsj.setId(id);
        tsbzJxzxkhgcsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());

        //获取文件信息
        TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj = new TsbzJxzxkhgcwjsj();
        tsbzJxzxkhgcwjsj.setGcid(id);
        List<TsbzJxzxkhgcwjsj> list = tsbzJxzxkhgcwjsjService.selectTsbzJxzxkhgcwjsjList(tsbzJxzxkhgcwjsj);
        ajax.put("file", list);
        ajax.put(AjaxResult.DATA_TAG, tsbzJxzxkhgcsjService.selectTsbzJxzxkhgcsjById(tsbzJxzxkhgcsj));

        return ajax;
    }

    /**
     * 新增考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:add')")
    @Log(title = "考核过程数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        String uuid = schoolCommonController.getUuid();
        tsbzJxzxkhgcsj.setId(uuid);
        tsbzJxzxkhgcsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());

        String filePaths = tsbzJxzxkhgcsj.getFilepath();
        String fileNames = tsbzJxzxkhgcsj.getFilename();
        if (!schoolCommonController.isStringEmpty(filePaths) && !schoolCommonController.isStringEmpty(fileNames)) {
            String[] strArrFilePath = filePaths.split(";");
            String[] strArrFileName = fileNames.split(";");
            TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj = null;
            for (int i = 0; i < strArrFilePath.length; i++) {
                tsbzJxzxkhgcwjsj = new TsbzJxzxkhgcwjsj();
                tsbzJxzxkhgcwjsj.setFilepath(strArrFilePath[i]);
                tsbzJxzxkhgcwjsj.setFilename(strArrFileName[i]);
                tsbzJxzxkhgcwjsj.setGcid(uuid);
                tsbzJxzxkhgcwjsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());

                tsbzJxzxkhgcwjsjService.insertTsbzJxzxkhgcwjsj(tsbzJxzxkhgcwjsj);
            }
        }

        return toAjax(tsbzJxzxkhgcsjService.insertTsbzJxzxkhgcsj(tsbzJxzxkhgcsj));
    }

    /**
     * 修改考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:edit')")
    @Log(title = "考核过程数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        //1先清空文件数据;
        String[] tmp = new String[1];
        tmp[0] = tsbzJxzxkhgcsj.getId();
        tsbzJxzxkhgcwjsjService.deleteTsbzJxzxkhgcwjsjByGcIds(tmp);

        //2再重新创建;
        String filePaths = tsbzJxzxkhgcsj.getFilepath();
        String fileNames = tsbzJxzxkhgcsj.getFilename();
        if (!schoolCommonController.isStringEmpty(filePaths) && !schoolCommonController.isStringEmpty(fileNames)) {
            String[] strArrFilePath = filePaths.split(";");
            String[] strArrFileName = fileNames.split(";");
            TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj = null;
            for (int i = 0; i < strArrFilePath.length; i++) {
                tsbzJxzxkhgcwjsj = new TsbzJxzxkhgcwjsj();
                tsbzJxzxkhgcwjsj.setFilepath(strArrFilePath[i]);
                tsbzJxzxkhgcwjsj.setFilename(strArrFileName[i]);
                tsbzJxzxkhgcwjsj.setGcid(tsbzJxzxkhgcsj.getId());
                tsbzJxzxkhgcwjsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
                tsbzJxzxkhgcwjsjService.insertTsbzJxzxkhgcwjsj(tsbzJxzxkhgcwjsj);
            }
        }
        return toAjax(tsbzJxzxkhgcsjService.updateTsbzJxzxkhgcsj(tsbzJxzxkhgcsj));
    }

    /**
     * 删除考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:remove')")
    @Log(title = "考核过程数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //清空考核数据先清空文件数据
        tsbzJxzxkhgcwjsjService.deleteTsbzJxzxkhgcwjsjByGcIds(ids);

        return toAjax(tsbzJxzxkhgcsjService.deleteTsbzJxzxkhgcsjByIds(ids));
    }
}
