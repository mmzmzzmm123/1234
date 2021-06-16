package com.ruoyi.web.controller.qtjskhgl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcwjsj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhgcwjsjService;
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
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcsj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhgcsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师考核过程数据Controller
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhgcsj")
public class TsbzQtjskhgcsjController extends BaseController {
    @Autowired
    private ITsbzQtjskhgcsjService tsbzQtjskhgcsjService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private ITsbzQtjskhgcwjsjService tsbzQtjskhgcwjsjService;

    /**
     * 查询群体教师考核过程数据列表
     */
    //@PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        if (tsbzQtjskhgcsj.getCreateuserid() == null) {
            tsbzQtjskhgcsj.setCreateuserid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        }
        System.out.println(tsbzQtjskhgcsj.getCreateuserid());
        //System.out.println(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        startPage();
        List<TsbzQtjskhgcsj> list = tsbzQtjskhgcsjService.selectTsbzQtjskhgcsjList(tsbzQtjskhgcsj);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核过程数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:export')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        List<TsbzQtjskhgcsj> list = tsbzQtjskhgcsjService.selectTsbzQtjskhgcsjList(tsbzQtjskhgcsj);
        ExcelUtil<TsbzQtjskhgcsj> util = new ExcelUtil<TsbzQtjskhgcsj>(TsbzQtjskhgcsj.class);
        return util.exportExcel(list, "qtjskhgcsj");
    }

    /**
     * 获取群体教师考核过程数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        AjaxResult ajax = AjaxResult.success();
        TsbzQtjskhgcsj tsbzQtjskhgcsj = new TsbzQtjskhgcsj();
        tsbzQtjskhgcsj.setId(id);
        tsbzQtjskhgcsj.setCreateuserid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));

        //获取文件信息
        TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj = new TsbzQtjskhgcwjsj();
        tsbzQtjskhgcwjsj.setGcid(id);
        List<TsbzQtjskhgcwjsj> list = tsbzQtjskhgcwjsjService.selectTsbzQtjskhgcwjsjList(tsbzQtjskhgcwjsj);
        ajax.put("file", list);
        ajax.put(AjaxResult.DATA_TAG, tsbzQtjskhgcsjService.selectTsbzQtjskhgcsjById(tsbzQtjskhgcsj));

        return ajax;
//        return AjaxResult.success(tsbzQtjskhgcsjService.selectTsbzQtjskhgcsjById(id));
    }

    /**
     * 新增群体教师考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:edit')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        String uuid = schoolCommonController.getUuid();
        tsbzQtjskhgcsj.setId(uuid);
        tsbzQtjskhgcsj.setCreateuserid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        tsbzQtjskhgcsj.setSubstatus("0");

        String filePaths = tsbzQtjskhgcsj.getFilepath();
        String fileNames = tsbzQtjskhgcsj.getFilename();
        if (!schoolCommonController.isStringEmpty(filePaths) && !schoolCommonController.isStringEmpty(fileNames)) {
            String[] strArrFilePath = filePaths.split(";");
            String[] strArrFileName = fileNames.split(";");
            TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj = null;
            for (int i = 0; i < strArrFilePath.length; i++) {
                tsbzQtjskhgcwjsj = new TsbzQtjskhgcwjsj();
                tsbzQtjskhgcwjsj.setFilepath(strArrFilePath[i]);
                tsbzQtjskhgcwjsj.setFilename(strArrFileName[i]);
                tsbzQtjskhgcwjsj.setGcid(uuid);
                tsbzQtjskhgcwjsj.setCreateuserid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
                tsbzQtjskhgcwjsjService.insertTsbzQtjskhgcwjsj(tsbzQtjskhgcwjsj);
            }
        }

        return toAjax(tsbzQtjskhgcsjService.insertTsbzQtjskhgcsj(tsbzQtjskhgcsj));
//        return toAjax(tsbzQtjskhgcsjService.insertTsbzQtjskhgcsj(tsbzQtjskhgcsj));
    }

    /**
     * 修改群体教师考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:edit')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        //1先清空文件数据;
        String[] tmp = new String[1];
        tmp[0] = tsbzQtjskhgcsj.getId();
        tsbzQtjskhgcwjsjService.deleteTsbzQtjskhgcwjsjByGcIds(tmp);

        //2再重新创建;
        String filePaths = tsbzQtjskhgcsj.getFilepath();
        String fileNames = tsbzQtjskhgcsj.getFilename();
        if (!schoolCommonController.isStringEmpty(filePaths) && !schoolCommonController.isStringEmpty(fileNames)) {
            String[] strArrFilePath = filePaths.split(";");
            String[] strArrFileName = fileNames.split(";");
            TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj = null;
            for (int i = 0; i < strArrFilePath.length; i++) {
                tsbzQtjskhgcwjsj = new TsbzQtjskhgcwjsj();
                tsbzQtjskhgcwjsj.setFilepath(strArrFilePath[i]);
                tsbzQtjskhgcwjsj.setFilename(strArrFileName[i]);
                tsbzQtjskhgcwjsj.setGcid(tsbzQtjskhgcsj.getId());
                tsbzQtjskhgcwjsj.setCreateuserid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
                tsbzQtjskhgcwjsjService.insertTsbzQtjskhgcwjsj(tsbzQtjskhgcwjsj);
            }
        }
        tsbzQtjskhgcsj.setSubstatus("1");
        return toAjax(tsbzQtjskhgcsjService.updateTsbzQtjskhgcsj(tsbzQtjskhgcsj));
//        return toAjax(tsbzQtjskhgcsjService.updateTsbzQtjskhgcsj(tsbzQtjskhgcsj));
    }

    /**
     * 删除群体教师考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:remove')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //清空考核数据先清空文件数据
        tsbzQtjskhgcwjsjService.deleteTsbzQtjskhgcwjsjByGcIds(ids);
        return toAjax(tsbzQtjskhgcsjService.deleteTsbzQtjskhgcsjByIds(ids));
    }
}
