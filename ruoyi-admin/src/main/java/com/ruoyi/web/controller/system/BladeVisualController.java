package com.ruoyi.web.controller.system;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ruoyi.common.core.page.VisualRespEmbData;
import com.ruoyi.system.service.impl.BladeVisualConfigServiceImpl;
import com.ruoyi.web.param.BladeVisualParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BladeVisual;
import com.ruoyi.system.service.IBladeVisualService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 可视化Controller
 *
 * @author yangsj
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/avue/api/visual")
public class BladeVisualController extends BaseController
{
    @Autowired
    private IBladeVisualService bladeVisualService;

    /**
     * 查询可视化列表
     */
//    @PreAuthorize("@ss.hasPermi('system:visual:list')")
    @GetMapping("/list")
    public VisualRespEmbData list(Long category)
    {
        startPage();
//        List<BladeVisual> list = bladeVisualService.selectBladeVisualList(bladeVisual);
        List<BladeVisual> list = bladeVisualService.selectVisualListByCategory(category);
        return getVisualEmbData(list);
//        return getVisualData(list);
    }

//    /**
//     * 导出可视化列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:visual:export')")
//    @Log(title = "可视化", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(BladeVisual bladeVisual)
//    {
//        List<BladeVisual> list = bladeVisualService.selectBladeVisualList(bladeVisual);
//        ExcelUtil<BladeVisual> util = new ExcelUtil<BladeVisual>(BladeVisual.class);
//        return util.exportExcel(list, "可视化数据");
//    }

    /**
     * 获取可视化详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:visual:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(Long id)
    {
        return AjaxResult.success(bladeVisualService.selectBladeVisualById(id));
    }

    @Autowired
    BladeVisualConfigServiceImpl configService;

    /**
     * 新增可视化
     */
//    @PreAuthorize("@ss.hasPermi('system:visual:add')")
    @Log(title = "可视化", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody BladeVisualParam param)
    {
        Long id = bladeVisualService.insertBladeVisual(param.getVisual());
        // 两个表id相同，方便检索
        param.getConfig().setId(id);
        param.getConfig().setVisualId(id);
        configService.insertBladeVisualConfig(param.getConfig());
        HashMap<String, String> map = new HashMap<>();
        map.put("id",String.valueOf(id));
        return new AjaxResult(200,"操作成功",map);
    }

    /**
     * 修改可视化
     */
//    @PreAuthorize("@ss.hasPermi('system:visual:edit')")
    @Log(title = "可视化", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody BladeVisualParam param)
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        param.getVisual().setUpdateBy(name);
        param.getVisual().setUpdateTime(new Date());
        
        int i = bladeVisualService.updateBladeVisual(param.getVisual());

        int j = configService.updateBladeVisualConfig(param.getConfig());
        return toAjax(i > 0 && j > 0);
    }

    /**
     * 删除可视化
     */
//    @PreAuthorize("@ss.hasPermi('system:visual:remove')")
    @Log(title = "可视化", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
    public AjaxResult remove(Long[] ids)
    {
        return toAjax(bladeVisualService.deleteBladeVisualByIds(ids));
    }




    /**
     * 处理大屏预览图片上传
     * @param file 图片
     * @return VisualRespEmbData
     */
    @PostMapping("/put-file")
    public @ResponseBody VisualRespEmbData uploadImg(@RequestParam("file") MultipartFile file) {

        //todo 前端每次更改了同一个大屏后，都重新上传一张新图片，文件名和原来不同，导致预览图片堆积。
        // 应更改前端，将大屏ID作为预览图片的文件名
        return bladeVisualService.uploadFile(file);

    }
}

