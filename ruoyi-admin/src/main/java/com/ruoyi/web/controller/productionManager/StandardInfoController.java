package com.ruoyi.web.controller.productionManager;

import java.util.List;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.productionManager.domain.StandardInfo;
import org.apache.ibatis.annotations.Param;
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
import com.ruoyi.productionManager.service.IStandardInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 试验标准管理Controller
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
@RestController
@RequestMapping("/productionManager/standard")
public class StandardInfoController extends BaseController
{
    @Autowired
    private IStandardInfoService standardInfoService;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询试验标准管理列表
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:list')")
    @GetMapping("/list")
    public TableDataInfo list(StandardInfo standardInfo)
    {
        startPage();
        List<StandardInfo> list = standardInfoService.selectStandardInfoList(standardInfo);
        return getDataTable(list);
    }

    /**
     * 导出试验标准管理列表
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:export')")
    @Log(title = "试验标准管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(StandardInfo standardInfo)
    {
        List<StandardInfo> list = standardInfoService.selectStandardInfoList(standardInfo);
        ExcelUtil<StandardInfo> util = new ExcelUtil<StandardInfo>(StandardInfo.class);
        return util.exportExcel(list, "试验标准管理数据");
    }

    /**
     * 获取试验标准管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:query')")
    @GetMapping(value = "/{standardId}")
    public AjaxResult getInfo(@PathVariable("standardId") Long standardId)
    {
        return AjaxResult.success(standardInfoService.selectStandardInfoByStandardId(standardId));
    }

    /**
     * 新增试验标准管理
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:add')")
    @Log(title = "试验标准管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StandardInfo standardInfo)
    {
        return toAjax(standardInfoService.insertStandardInfo(standardInfo));
    }

    /**
     * 修改试验标准管理
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:edit')")
    @Log(title = "试验标准管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StandardInfo standardInfo)
    {
        return toAjax(standardInfoService.updateStandardInfo(standardInfo));
    }

    /**
     * 删除试验标准管理
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:remove')")
    @Log(title = "试验标准管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{standardIds}")
    public AjaxResult remove(@PathVariable Long[] standardIds)
    {
        return toAjax(standardInfoService.deleteStandardInfoByStandardIds(standardIds));
    }

    @PostMapping("/upload")
    public AjaxResult getFiles(@Param("file") MultipartFile file){
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
            String fileName = standardInfoService.uploadFile(file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            url.split("/");
            String path ="";
            for(String s:url.split("/",4)){
                System.out.println("path>>>"+s);
                path = s;
            }
            ajax.put("url", path);
            return ajax;

    }

}
