package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.psychology.domain.PsyConsultant;
import com.ruoyi.psychology.service.IPsyConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(psyConsultantService.selectPsyConsultantById(id));
    }



}
