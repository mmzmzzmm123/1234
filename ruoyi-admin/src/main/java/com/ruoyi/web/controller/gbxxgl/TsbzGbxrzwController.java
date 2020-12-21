package com.ruoyi.web.controller.gbxxgl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.gbxxgl.domain.TsbzGbgzjl;
import com.ruoyi.gbxxgl.service.ITsbzGbgzjlService;
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
import com.ruoyi.gbxxgl.domain.TsbzGbxrzw;
import com.ruoyi.gbxxgl.service.ITsbzGbxrzwService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部任职情况-现任职务Controller
 * 
 * @author ruoyi
 * @date 2020-12-07
 */
@RestController
@RequestMapping("/gbxxgl/gbxrzw")
public class TsbzGbxrzwController extends BaseController
{
    @Autowired
    private ITsbzGbxrzwService tsbzGbxrzwService;
    @Autowired
    private ITsbzGbgzjlService tsbzGbgzjlService;

    /**
     * 查询干部任职情况-现任职务列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbxrzw tsbzGbxrzw)
    {
        startPage();
        List<TsbzGbxrzw> list = tsbzGbxrzwService.selectTsbzGbxrzwList(tsbzGbxrzw);
        return getDataTable(list);
    }

    /**
     * 导出干部任职情况-现任职务列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:export')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbxrzw tsbzGbxrzw)
    {
        List<TsbzGbxrzw> list = tsbzGbxrzwService.selectTsbzGbxrzwList(tsbzGbxrzw);
        ExcelUtil<TsbzGbxrzw> util = new ExcelUtil<TsbzGbxrzw>(TsbzGbxrzw.class);
        return util.exportExcel(list, "gbxrzw");
    }

    /**
     * 获取干部任职情况-现任职务详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzGbxrzwService.selectTsbzGbxrzwById(id));
    }

    /**
     * 新增干部任职情况-现任职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:add')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbxrzw tsbzGbxrzw)
    {
        //先判断是否已经创建当前任职年月的干部信息
        TsbzGbxrzw tsbzGbxrzwNew = new TsbzGbxrzw();
        tsbzGbxrzwNew.setGbid(tsbzGbxrzw.getGbid());
        tsbzGbxrzwNew.setRzny(tsbzGbxrzw.getRzny());
        List<TsbzGbxrzw> selectList = tsbzGbxrzwService.selectTsbzGbxrzwList(tsbzGbxrzwNew);
        if (selectList != null && selectList.size() > 0) {
            return AjaxResult.error("当前干部任职年月信息已创建,无法重复创建");
        }

        tsbzGbxrzw.setCreatetime(new Date());
        tsbzGbxrzw.setCreateuser(SecurityUtils.getLoginUser().getUser().getUserId());

        // 同步更新工作经历
        TsbzGbgzjl tsbzGbgzjlNew = new TsbzGbgzjl();
        tsbzGbgzjlNew.setGbid(tsbzGbxrzw.getGbid());
        tsbzGbgzjlNew.setRjxk(null);
        tsbzGbgzjlNew.setGzgw("2");
        tsbzGbgzjlNew.setQsny(tsbzGbxrzw.getRzny());
        tsbzGbgzjlNew.setZzzw(tsbzGbxrzw.getXzzw());
        tsbzGbgzjlNew.setDnzw(tsbzGbxrzw.getDnzw());
        tsbzGbgzjlNew.setQtzw(tsbzGbxrzw.getQtzw());
        if (tsbzGbxrzw.getZzny() != null) {
            tsbzGbgzjlNew.setZzny(tsbzGbxrzw.getZzny());
        }else {
            tsbzGbgzjlNew.setZzny(null);
        }
        tsbzGbgzjlService.insertTsbzGbgzjl(tsbzGbgzjlNew);

        return toAjax(tsbzGbxrzwService.insertTsbzGbxrzw(tsbzGbxrzw));
    }

    /**
     * 修改干部任职情况-现任职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:edit')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbxrzw tsbzGbxrzw)
    {
//        //先判断是否已经创建当前任职年月的干部信息
//        TsbzGbxrzw tsbzGbxrzwNew = new TsbzGbxrzw();
//        tsbzGbxrzwNew.setGbid(tsbzGbxrzw.getGbid());
//        tsbzGbxrzwNew.setRzny(tsbzGbxrzw.getRzny());
//        List<TsbzGbxrzw> selectList = tsbzGbxrzwService.selectTsbzGbxrzwList(tsbzGbxrzwNew);
//        if (selectList != null && selectList.size() > 0) {
//            return AjaxResult.error("当前干部任职年月信息已创建,无法重复创建");
//        }
        return toAjax(tsbzGbxrzwService.updateTsbzGbxrzw(tsbzGbxrzw));
    }

    /**
     * 删除干部任职情况-现任职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:remove')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzGbxrzwService.deleteTsbzGbxrzwByIds(ids));
    }
}
