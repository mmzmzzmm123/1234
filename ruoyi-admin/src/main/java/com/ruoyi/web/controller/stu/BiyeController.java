package com.ruoyi.web.controller.stu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提交材料参数Controller
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/stu/biye")
public class BiyeController extends BaseController
{
    @Autowired
    private IStuInfoMaterialService stuInfoMaterialService;

    @Autowired
    private ServerConfig serverConfig;

    @PreAuthorize("@ss.hasPermi('stu:biye:upload')")
    @PostMapping("upload")
    public AjaxResult uploadFile(MultipartFile file, String id ){
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            BiyeForm form = new BiyeForm();
            form.setUpdateTime(new Date());
            form.setUrl(url);
            form.setId(Integer.valueOf(id));
            String userName = SecurityUtils.getUsername();
            form.setUpdateBy(userName);
            stuInfoMaterialService.updateImgUrl(form);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    @PreAuthorize("@ss.hasPermi('stu:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stuInfoMaterialService.selectStuInfMaterialoById(id));
    }

    /**
     * 查询提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:list')")
    @GetMapping("/list")
    public AjaxResult list(BiyeForm stuInfoMaterial) {
        startPage();
        Map<String, List<StuInfoMaterial>> map = stuInfoMaterialService.selectStuMaterialList(stuInfoMaterial);
        return AjaxResult.success(map);
    }

    /**
     * 导出提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:export')")
    @Log(title = "提交材料参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BiyeForm stuInfoMaterial)
    {
//        Map<String, List<StuInfoMaterial>> list = stuInfoMaterialService.selectStuMaterialList(stuInfoMaterial);
//        ExcelUtil<StuInfoMaterial> util = new ExcelUtil<>(StuInfoMaterial.class);
//        util.exportExcel(response, list, "提交材料参数数据");
    }



    /**
     * 新增提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:add')")
    @Log(title = "提交材料参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuInfoMaterial stuInfoMaterial)
    {
        return toAjax(stuInfoMaterialService.insertStuMaterial(stuInfoMaterial));
    }

    /**
     * 修改提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:edit')")
    @Log(title = "提交材料参数", businessType = BusinessType.UPDATE)
    @PostMapping("updateMateriaFlag")
    public AjaxResult edit(@RequestBody BiyeForm biyeForm)
    {
        return toAjax(stuInfoMaterialService.updateStuMaterial(biyeForm));
    }

    /**
     * 删除提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:remove')")
    @Log(title = "提交材料参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuInfoMaterialService.deleteStuInfoMaterialByIds(ids));
    }
}
