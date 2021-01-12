package com.gox.web.controller.system;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import com.gox.common.core.domain.entity.SysDictData;
import com.gox.common.core.redis.RedisCache;
import com.gox.common.utils.DictUtils;
import com.gox.common.utils.SecurityUtils;
import com.gox.common.utils.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.service.IElectronicAttributesService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;

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
    /**
     * 完整文件验证
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:add')")
    //@Log(title = "电子文件信息", businessType = BusinessType.INSERT)
    @PostMapping("/full")
    public AjaxResult upload(HttpServletRequest req){
        String filename = req.getParameter("file_name");
        String md5 = req.getParameter("md5");
        Long metadataId = Long.valueOf(req.getParameter("metadataId"));
        if (electronicAttributesService.merge(filename,md5,metadataId)){
            return AjaxResult.success();
        }
        return AjaxResult.error("系统错误,上传失败");
    }
    /**
     * 上传文件分块
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:add')")
    //@Log(title = "电子文件信息", businessType = BusinessType.INSERT)
    @PostMapping("/chunk")
    public AjaxResult upload2(HttpServletRequest req, MultipartFile data) {
        if (data==null) {
            return AjaxResult.error("网络错误,上传失败");
        }
        String res=null;
        Map<String,Object> map = new HashMap<>();
        map.put("filename",req.getParameter("file_name"));
        map.put("md5",req.getParameter("md5"));
        map.put("chunks",req.getParameter("chunks"));
        map.put("chunkIndex",req.getParameter("chunk_index"));
        map.put("chunkMd5",req.getParameter("chunk_md5"));
        map.put("data",data);
        res = electronicAttributesService.mergeChunk(map);
        if (StrUtil.isBlank(res)){
            return AjaxResult.success();
        }
        else {
            return AjaxResult.error(res);
        }

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
}
