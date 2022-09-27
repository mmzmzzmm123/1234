package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyConsultant;
import com.ruoyi.psychology.service.IPsyConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 心理咨询师Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/consultant")
public class AppPsyConsultantController extends BaseController
{
    @Autowired
    private IPsyConsultantService psyConsultantService;

    /**
     * 查询心理咨询师列表
     */

    @GetMapping("/list")
    public TableDataInfo list(PsyConsultant psyConsultant)
    {
        startPage();
        List<PsyConsultant> list = psyConsultantService.selectPsyConsultantList(psyConsultant);
        return getDataTable(list);
    }



    /**
     * 获取心理咨询师详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultantService.selectPsyConsultantById(id));
    }



}
