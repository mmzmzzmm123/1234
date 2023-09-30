package com.ruoyi.office.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.RoomEquipeOpenReq;
import com.ruoyi.office.service.ITRoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/office/capi")
public class CleanerController {

    @Autowired
    ITRoomService roomService;


    /**
     * 开门禁
     *
     * @param orderId
     * @return
     */
    @ApiOperation("开房间设备 门禁 door   电源： light,aircondition ")
    @PreAuthorize("@ss.hasPermi('office:merchant')")
    @PostMapping("/roomopen")
    public AjaxResult openRoom(@RequestBody RoomEquipeOpenReq req) {

        try {
            roomService.openRoomEquipment(req, SecurityUtils.getLoginUser().getWxUser().getUserId());
        } catch (Exception e) {
            return AjaxResult.error("操作异常，请联系管理员：" + e.getMessage());
        }

        return AjaxResult.success();
    }

    /**
     * 开门禁
     *
     * @param orderId
     * @return
     */
    @ApiOperation("关房间设备 门禁 door   电源： light,aircondition ")
    @PreAuthorize("@ss.hasPermi('office:merchant')")
    @PostMapping("/roomclose")
    public AjaxResult closeRoom(@RequestBody RoomEquipeOpenReq req) {

        try {
            roomService.closeRoomEquipment(req, SecurityUtils.getLoginUser().getWxUser().getUserId());
        } catch (Exception e) {
            return AjaxResult.error("操作异常，请联系管理员：" + e.getMessage());
        }

        return AjaxResult.success();
    }
}
