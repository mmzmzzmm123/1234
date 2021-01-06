package com.ruoyi.web.controller.jxjs;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import com.ruoyi.jxjs.mapper.TsbzJxjsjbxxMapper;
import com.ruoyi.web.controller.common.SchoolCommonController;
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
import com.ruoyi.jxjs.domain.TsbzJxjscj;
import com.ruoyi.jxjs.service.ITsbzJxjscjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 见习教师成绩Controller
 *
 * @author ruoyi
 * @date 2020-11-09
 */
@RestController
@RequestMapping("/jxjs/jxjscj")
public class TsbzJxjscjController extends BaseController {
    @Autowired
    private ITsbzJxjscjService tsbzJxjscjService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TsbzJxjsjbxxMapper tsbzJxjsjbxxMapper;

    /**
     * 查询见习教师成绩列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjscj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxjscj tsbzJxjscj) {
        //首先判断是否为学校用户
        String jdxId = schoolCommonController.deptIdToJdxId();
        TsbzJxjsjbxx tsbzJxjsjbxx = new TsbzJxjsjbxx();
        if (!schoolCommonController.isStringEmpty(jdxId)) {
            tsbzJxjsjbxx.setJdxid(jdxId);
        }
        tsbzJxjscj.setTsbzJxjsjbxx(tsbzJxjsjbxx);

        startPage();
        List<TsbzJxjscj> list = tsbzJxjscjService.selectTsbzJxjscjList(tsbzJxjscj);
        return getDataTable(list);
    }



    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<TsbzJxjscj> util = new ExcelUtil<TsbzJxjscj>(TsbzJxjscj.class);
        return util.importTemplateExcel("教师成绩数据导入");
    }

    @Log(title = "分数导入", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<TsbzJxjscj> util = new ExcelUtil<TsbzJxjscj>(TsbzJxjscj.class);
        List<TsbzJxjscj> tsbzJdcxList = util.importExcel(file.getInputStream());
        int iCount = 0;

        for (TsbzJxjscj tsbzJxjscj : tsbzJdcxList) {

            TsbzJxjsjbxx tsbzjxjsjbxx = new TsbzJxjsjbxx();
            System.out.println(tsbzJxjscj.getJsname()+ "1111111111");
            tsbzjxjsjbxx.setName(tsbzJxjscj.getJsname());
            List<TsbzJxjsjbxx> list = tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxList(tsbzjxjsjbxx);
            if (list.size() == 0 || StringUtils.isNull(list)) {
                throw new CustomException("导入数据非见习教师");
            }
            tsbzJxjscj.setJsid(list.get(0).getId());

            iCount = iCount + tsbzJxjscjService.insertTsbzJxjscj(tsbzJxjscj);
            // iCount = iCount + tsbzJxjscjService.updateTsbzJxjscj(tsbzJxjscj);
        }
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        String operName = loginUser.getUsername();
//        String message = userService.importUser(userList, updateSupport, operName);
        //String message = faide;
        return AjaxResult.success(String.valueOf(iCount), null);
    }

    /**
     * 导出见习教师成绩列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjscj:export')")
    @Log(title = "见习教师成绩", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxjscj tsbzJxjscj) {
        List<TsbzJxjscj> list = tsbzJxjscjService.selectTsbzJxjscjList(tsbzJxjscj);
        ExcelUtil<TsbzJxjscj> util = new ExcelUtil<TsbzJxjscj>(TsbzJxjscj.class);
        return util.exportExcel(list, "jxjscj");
    }

    /**
     * 获取见习教师成绩详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjscj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxjscjService.selectTsbzJxjscjById(id));
    }

    /**
     * 新增见习教师成绩
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjscj:add')")
    @Log(title = "见习教师成绩", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxjscj tsbzJxjscj) {
        tsbzJxjscj.setZhdf(tsbzJxjscj.getAlfxdf().add(tsbzJxjscj.getGbzdf()).add(tsbzJxjscj.getJasjdf()));
        return toAjax(tsbzJxjscjService.insertTsbzJxjscj(tsbzJxjscj));
    }

    /**
     * 修改见习教师成绩
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjscj:edit')")
    @Log(title = "见习教师成绩", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxjscj tsbzJxjscj) {
        tsbzJxjscj.setZhdf(tsbzJxjscj.getAlfxdf().add(tsbzJxjscj.getGbzdf()).add(tsbzJxjscj.getJasjdf()));
        return toAjax(tsbzJxjscjService.updateTsbzJxjscj(tsbzJxjscj));
    }

    /**
     * 删除见习教师成绩
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjscj:remove')")
    @Log(title = "见习教师成绩", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxjscjService.deleteTsbzJxjscjByIds(ids));
    }
}
