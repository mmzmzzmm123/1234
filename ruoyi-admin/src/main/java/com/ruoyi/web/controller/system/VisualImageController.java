package com.ruoyi.web.controller.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.service.impl.VisualImageServiceImpl;
import com.ruoyi.web.param.VisualImageAddParam;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.VisualImage;
import com.ruoyi.system.service.IVisualImageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;

/**
 * 图片管理。管理上传的图片Controller
 *
 * @author yangsj
 * @date 2021-10-28
 */
@RestController
@RequestMapping("/system/visualImage")
public class VisualImageController extends BaseController
{
    @Autowired
    private IVisualImageService visualImageService;

    /**
     * 查询图片管理。管理上传的图片列表
     */
    @PreAuthorize("@ss.hasPermi('system:image:list')")
    @GetMapping("/list")
    public TableDataInfo list(VisualImage visualImage)
    {
        startPage();
        List<VisualImage> list = visualImageService.selectVisualImageList(visualImage);
        return getDataTable(list);
    }


    /**
     * 导出图片
     */
    @PreAuthorize("@ss.hasPermi('system:image:export')")
    @Log(title = "图片管理。管理上传的图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(VisualImage visualImage)
    {
        List<VisualImage> list = visualImageService.selectVisualImageList(visualImage);
        ExcelUtil<VisualImage> util = new ExcelUtil<VisualImage>(VisualImage.class);
        return util.exportExcel(list, "图片管理。管理上传的图片数据");
    }

    /**
     * 获取图片
     */
    @PreAuthorize("@ss.hasPermi('system:image:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(visualImageService.selectVisualImageById(id));
    }

    /**
     * 新增图片
     */
    @PreAuthorize("@ss.hasPermi('system:image:add')")
    @Log(title = "图片管理。管理上传的图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisualImage visualImage)
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        visualImage.setCreateBy(name);
        // 图片处理是异步进行的
        visualImageService.imageProcess(visualImage);

        return toAjax(visualImageService.insertVisualImage(visualImage));
    }

//    @Value("${imageDir.visualImage}")
//    private String imageDir;

    @PreAuthorize("@ss.hasPermi('system:image:query')")
    @GetMapping("/link")
    private AjaxResult getLink(@RequestParam String fileName){
        IVisualImageService vService = SpringUtils.getBean(IVisualImageService.class);
        Map<String, String> map = vService.getLink(fileName);
        if (map == null) {
            return AjaxResult.error("文件名错误");
        }
        return AjaxResult.success(map);
    }


//    @PreAuthorize("@ss.hasPermi('system:image:add')")
//    @Log(title = "图片管理。管理上传的图片", businessType = BusinessType.INSERT)
    @CrossOrigin(originPatterns = "*")
    @PostMapping("/upload")
    private AjaxResult uploadImage(@RequestParam MultipartFile file){
        //todo BUG这里自动注入失败，调用服务会报空指针异常
//        String newFileName = visualImageService.saveImageToLocal(file);
        String imageDir = "/images";
        String filePath =  RuoYiConfig.getProfile() + imageDir;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String[] fileNameSplit = file.getOriginalFilename().split("\\.");
        String fileType = fileNameSplit[fileNameSplit.length - 1];

        String newFileName = UUID.fastUUID().toString(true) + "." + fileType;
        String fullFileName = filePath + "/" + newFileName;
        try {
            FileOutputStream out = new FileOutputStream(fullFileName);
            System.out.println("1111");
            out.write(file.getBytes());
            System.out.println("222");
            out.flush();
            System.out.println("333");
            out.close();
            System.out.println("4444");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("name",newFileName);
        return AjaxResult.success(map);
    }

    /**
     * 修改
     */
    @PreAuthorize("@ss.hasPermi('system:image:edit')")
    @Log(title = "图片管理。管理上传的图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisualImage visualImage)
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        visualImage.setUpdateBy(name);
        return toAjax(visualImageService.updateVisualImage(visualImage));
    }

    /**
     * 删除图片
     */
    @PreAuthorize("@ss.hasPermi('system:image:remove')")
    @Log(title = "图片管理。管理上传的图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        // todo 删除本地图片
        return toAjax(visualImageService.deleteVisualImageByIds(ids));
    }

    @Value("${imageDir.maxImageMB}")
    private Integer maxImage;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxRequestSize(DataSize.ofMegabytes(maxImage));
        factory.setMaxFileSize(DataSize.ofMegabytes(maxImage));
        return factory.createMultipartConfig();
    }


}
