package com.ruoyi.web.controller.bk;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.onethinker.bk.domain.WeiYan;
import com.onethinker.bk.service.IWeiYanService;
import com.onethinker.bk.vo.BaseRequestVO;
import com.ruoyi.common.constant.BkConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 微言表 前端控制器
 * </p>
 *
 * @author yangyouqi
 * @date 2024-01-19
 */
@RestController
@RequestMapping("/bk/weiYan")
public class WeiYanController {

    @Autowired
    private IWeiYanService weiYanService;

    /**
     * 保存
     */
    @PostMapping("/saveWeiYan")
    public AjaxResult saveWeiYan(@RequestBody WeiYan weiYanVO) {
        weiYanService.insertWeiYan(weiYanVO, BkConstants.WEIYAN_TYPE_FRIEND);
        return AjaxResult.success();
    }


    /**
     * 保存
     */
    @PostMapping("/saveNews")
    public AjaxResult saveNews(@RequestBody WeiYan weiYanVO) {
        weiYanService.insertWeiYan(weiYanVO, BkConstants.WEIYAN_TYPE_NEWS);
        return AjaxResult.success();
    }



    /**
     * 删除
     */
    @GetMapping("/deleteWeiYan")
    public AjaxResult deleteWeiYan(@RequestParam("id") Long id) {
        weiYanService.deleteWeiYanById(id);
        return AjaxResult.success();
    }

    /**
     * 查询List
     */
    @PostMapping("/listNews")
    public AjaxResult listNews(@RequestBody WeiYan weiYan) {
        return AjaxResult.success(weiYanService.selectWeiYanList(weiYan,BkConstants.WEIYAN_TYPE_NEWS));
    }


    /**
     * 查询List
     */
    @PostMapping("/listWeiYan")
    public AjaxResult listWeiYan(@RequestBody WeiYan weiYan) {
        return AjaxResult.success(weiYanService.selectWeiYanList(weiYan,BkConstants.WEIYAN_TYPE_FRIEND));
    }
}
