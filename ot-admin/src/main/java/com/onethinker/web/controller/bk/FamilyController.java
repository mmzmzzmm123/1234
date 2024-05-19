package com.onethinker.web.controller.bk;

import com.onethinker.bk.domain.Family;
import com.onethinker.bk.service.IFamilyService;
import com.onethinker.bk.vo.FamilyVO;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 家庭信息 前端控制器
 * </p>
 *
 * @author yangyouqi
 * @date 2024-01-19
 */
@RestController
@RequestMapping("/bk/family")
public class FamilyController {

    @Autowired
    private IFamilyService familyService;

    /**
     * 保存
     */
    @PostMapping("/saveFamily")
    public AjaxResult saveFamily(@Validated @RequestBody FamilyVO familyVO) {
        familyService.insertFamily(familyVO);
        return AjaxResult.success();
    }

    /**
     * 删除
     */
    @GetMapping("/deleteFamily")
    public AjaxResult deleteFamily(@RequestParam("id") Long id) {
        familyService.deleteFamilyById(id);
        return AjaxResult.success();
    }

    /**
     * 获取
     */
    @GetMapping("/getFamily")
    public AjaxResult getFamily() {
        return AjaxResult.success(familyService.selectFamilyByUserId(SecurityUtils.getUserId()));
    }

    /**
     * 获取
     */
    @GetMapping("/getAdminFamily")
    public AjaxResult getAdminFamily() {
        return AjaxResult.success(familyService.selectFamilyByUserId(1L));
    }

    /**
     * 查询随机家庭
     */
    @GetMapping("/listRandomFamily")
    public AjaxResult listRandomFamily(@RequestParam(value = "size", defaultValue = "10") Integer size) {
        return AjaxResult.success(familyService.listRandomFamily(size));
    }

    /**
     * 查询
     */
    @PostMapping("/listFamily")
    public AjaxResult listFamily(@RequestBody Family family) {
        return AjaxResult.success(familyService.selectFamilyList(family));
    }

    /**
     * 修改状态
     */
    @GetMapping("/changeLoveStatus")
    public AjaxResult changeLoveStatus(@RequestParam("id") Long id, @RequestParam("flag") Boolean flag) {
        Family family = new Family();
        family.setId(id);
        family.setStatus(flag);
        return AjaxResult.success(familyService.selectFamilyList(family));
    }
}
