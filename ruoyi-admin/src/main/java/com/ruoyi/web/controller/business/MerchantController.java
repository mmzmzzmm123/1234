package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 10:25
 * @Description : 商家管理
 */
@RestController
@RequestMapping("merchant")
public class MerchantController {

    @Resource
    private ISysDictDataService iSysDictDataService;

    @PostMapping("page")
//    public AjaxResult page(@RequestBody QueryMerchantPageDTO dto){
    public AjaxResult page(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = SecurityUtils.getLoginUser().getUser();


        return AjaxResult.success();
    }


}
