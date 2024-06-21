package com.ruoyi.web.controller.potal;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.framework.aspectj.DataScopeAspect;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.service.extra.BusPostOrderExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ruoyi.common.utils.SecurityUtils.getLoginUser;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 14:19:04
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
@RestController
@RequestMapping("/api/postOrder")
public class PostOrderController extends BaseController {


    @Autowired
    private BusPostOrderExtraService busPostOrderService;


    /**
     * 发布订单
     *
     * @param busPostOrderForm
     * @return
     */
    @RepeatSubmit
    @PostMapping("/postOrder")
    @PreAuthorize("@ss.hasPermi('api:order')")
    public AjaxResult postOrder(@RequestBody BusPostOrderForm busPostOrderForm) {
        BusPostOrder busPostOrder = busPostOrderService.postOrder(busPostOrderForm);
        return busPostOrder != null ? AjaxResult.success(busPostOrder) : AjaxResult.error();
    }

    /**
     * 订单列表
     *
     * @param busPostOrderForm
     * @return
     */
    @RepeatSubmit
    @PostMapping("/list")
    @PreAuthorize("@ss.hasPermi('api:order')")
    public TableDataInfo postOrderList(@RequestBody BusPostOrderForm busPostOrderForm) {
        startPage();
        return getDataTable(busPostOrderService.list(busPostOrderForm));
    }


    /**
     * 没有接单之前允许修改订单
     *
     * @param busPostOrderForm
     * @return
     */
    @RepeatSubmit
    @PostMapping("/updateOrder")
    @PreAuthorize("@ss.hasPermi('api:order')")
    public AjaxResult updateOrder(@RequestBody BusPostOrderForm busPostOrderForm) {
        return busPostOrderService.updateOrderByUserId(busPostOrderForm) > 0 ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 查询我的订单
     *
     * @param busPostOrderForm
     * @return
     */
    @GetMapping("/findOrderListByUserId")
    @PreAuthorize("@ss.hasPermi('api:order')")
    @DataScope(userAlias = DataScopeAspect.DATA_SCOPE_SELF)
    public TableDataInfo findOrderListByUserId(@RequestBody BusPostOrderForm busPostOrderForm) {
        startPage();
        return  getDataTable(busPostOrderService.findOrderListByUserId(busPostOrderForm));
    }


    @RepeatSubmit
    @PostMapping("/payOrder")
    @PreAuthorize("@ss.hasPermi('api:order')")
    public AjaxResult payOrder(@RequestBody BusPostOrderForm busPostOrderForm) {
        busPostOrderService.payOrder(busPostOrderForm);
        return AjaxResult.success();
    }

    /**
     * 商家图片上传 todo:有优化的点
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String image = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", image);
            // 更新缓存用户头像
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }


    /**
     * 确认发货(同意发货)
     *
     * @param orderId
     * @return
     */
    @RepeatSubmit
    @PostMapping("/confirm")
    @PreAuthorize("@ss.hasPermi('api:order')")
    public AjaxResult confirm(Long orderId) {
        return busPostOrderService.confirm(orderId) > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
