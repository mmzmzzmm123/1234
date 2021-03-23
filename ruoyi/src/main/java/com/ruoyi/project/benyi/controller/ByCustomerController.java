package com.ruoyi.project.benyi.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByCustomer;
import com.ruoyi.project.benyi.service.IByCustomerService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 本一-客户关系管理Controller
 *
 * @author tsbz
 * @date 2021-03-01
 */
@RestController
@RequestMapping("/benyi/customer")
public class ByCustomerController extends BaseController {
    @Autowired
    private IByCustomerService byCustomerService;

    /**
     * 查询本一-客户关系管理列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByCustomer byCustomer) {
        byCustomer.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<ByCustomer> list = byCustomerService.selectByCustomerList(byCustomer);
        return getDataTable(list);
    }

    /**
     * 导出本一-客户关系管理列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:customer:export')")
    @Log(title = "本一-客户关系管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByCustomer byCustomer) {
        List<ByCustomer> list = byCustomerService.selectByCustomerList(byCustomer);
        ExcelUtil<ByCustomer> util = new ExcelUtil<ByCustomer>(ByCustomer.class);
        return util.exportExcel(list, "客户关系管理-客户列表");
    }

    /**
     * 获取本一-客户关系管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byCustomerService.selectByCustomerById(id));
    }

    /**
     * 新增本一-客户关系管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:customer:add')")
    @Log(title = "本一-客户关系管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByCustomer byCustomer) {


        try {
            // 获取当天日期并增加15天为过保日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 15);
            String dateStr = df.format(calendar.getTime());
            Date dataGuobao = df.parse(dateStr);

            byCustomer.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
            byCustomer.setCreateTime(date);
            byCustomer.setGbtime(dataGuobao);
        } catch (Exception e){
            e.printStackTrace();
        }

        // 判断电话号码是否存在
        ByCustomer byCustomer1 = new ByCustomer();
        byCustomer1.setLxdh(byCustomer.getLxdh());
        if (byCustomerService.selectByCustomerList(byCustomer1).size()>0) {
            return AjaxResult.error("新增用户失败,此手机号码已经存在");
        }else {
            return toAjax(byCustomerService.insertByCustomer(byCustomer));
        }
    }

    /**
     * 修改本一-客户关系管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:customer:edit')")
    @Log(title = "本一-客户关系管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByCustomer byCustomer) {

        if (byCustomer.getZhgj() != null) {
            byCustomer.setZhgj(byCustomer.getZhgj());
        }else {
            byCustomer.setZhgj("无");
        }

        if (byCustomer.getXfxm() != null) {
            byCustomer.setXfxm(byCustomer.getXfxm());
        }else {
            byCustomer.setXfxm("无");
        }

        if (byCustomer.getBz() != null) {
            byCustomer.setBz(byCustomer.getBz());
        }else {
            byCustomer.setBz("无");
        }

        return toAjax(byCustomerService.updateByCustomer(byCustomer));
    }

    /**
     * 删除本一-客户关系管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:customer:remove')")
    @Log(title = "本一-客户关系管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byCustomerService.deleteByCustomerByIds(ids));
    }
}
