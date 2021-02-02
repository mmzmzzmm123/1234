package com.gox.web.controller.system;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import com.gox.common.core.redis.RedisCache;
import com.gox.common.utils.file.Chunk;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.service.IElectronicAttributesService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 电子文件信息Controller
 * 
 * @author gox
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/system/attributes")
public class ElectronicAttributesController extends BaseController
{
    @Autowired
    private IElectronicAttributesService electronicAttributesService;
    @Autowired
    RedisCache redisCache;
    /**
     * 查询电子文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:list')")
    @GetMapping("/list")
    public TableDataInfo list(ElectronicAttributes electronicAttributes)
    {
        startPage();
        List<ElectronicAttributes> list = electronicAttributesService.selectElectronicAttributesList(electronicAttributes);
        return getDataTable(list);
    }
//    /**
//     * 获取base64
//     */
//    @GetMapping("/base64/{id}")
//    @Log(title = "电子文件信息", businessType = BusinessType.GET)
//    @PreAuthorize("@ss.hasPermi('system:attributes:query')")
//    public AjaxResult getBase64(@PathVariable String id){
//        String s = electronicAttributesService.getBase64(id);
//        if (StrUtil.isNotBlank(s)){
//            return AjaxResult.success("成功",s);
//        }
//        return AjaxResult.error(500,"文件不存在!");
//    }
    /**
     * 导出电子文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:export')")
    @Log(title = "电子文件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ElectronicAttributes electronicAttributes)
    {
        List<ElectronicAttributes> list = electronicAttributesService.selectElectronicAttributesList(electronicAttributes);
        ExcelUtil<ElectronicAttributes> util = new ExcelUtil<ElectronicAttributes>(ElectronicAttributes.class);
        return util.exportExcel(list, "attributes");
    }

    /**
     * 获取电子文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(electronicAttributesService.selectElectronicAttributesById(id));
    }

    /**
     * 新增电子文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:add')")
    @Log(title = "电子文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ElectronicAttributes electronicAttributes)
    {
        return toAjax(electronicAttributesService.insertElectronicAttributes(electronicAttributes));
    }

    /**
     * 修改电子文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:edit')")
    @Log(title = "电子文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ElectronicAttributes electronicAttributes)
    {
        return toAjax(electronicAttributesService.updateElectronicAttributes(electronicAttributes));
    }

    /**
     * 删除电子文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:remove')")
    @Log(title = "电子文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(electronicAttributesService.deleteElectronicAttributesByIds(ids));
    }
    /**
     * 处理文件上传POST请求
     * 将上传的文件存放到服务器内
     * @param chunk 文件块
     * @param response 响应
     * @return 上传响应状态
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:add')")
    @Log(title = "电子文件信息", businessType = BusinessType.INSERT)
    @PostMapping("/fileUpload")
    public String uploadPost(@ModelAttribute Chunk chunk, HttpServletResponse response, Long metadataId) throws IOException {
        return electronicAttributesService.fileUploadPost(chunk,response,metadataId);
    }

    /**
     * 处理文件上传GET请求
     * 验证上传的文件块，是否允许浏览器再次发送POST请求（携带二进制文件的请求流，FormData）
     * @param chunk 文件块
     * @param response 响应
     * @return 文件块
     */
    @GetMapping("/fileUpload")
    public void uploadGet(@ModelAttribute Chunk chunk,HttpServletResponse response){
        electronicAttributesService.fileUploadGet(chunk,response);
    }
}
