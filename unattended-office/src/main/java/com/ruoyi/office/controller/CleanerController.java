package com.ruoyi.office.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomCleanRecord;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.CleanRecordH5Vo;
import com.ruoyi.office.domain.vo.RoomEquipeOpenReq;
import com.ruoyi.office.service.ITRoomCleanRecordService;
import com.ruoyi.office.service.ITRoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

@RestController
@RequestMapping("/office/capi")
public class CleanerController extends BaseController {

    @Autowired
    ITRoomService roomService;

    @Autowired
    ITRoomCleanRecordService cleanRecordService;


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

        Long userId = SecurityUtils.getLoginUser().getWxUser().getUserId();
        try {
            roomService.openCleanerRoomEquipment(req, userId);

            TRoomCleanRecord cleanRecord = new TRoomCleanRecord();
            cleanRecord.setCreateBy(userId + "");
            cleanRecord.setRoomId(req.getRoomId());
            cleanRecord.setStartTime(new Date());
            cleanRecord.setStatus(OfficeEnum.CleanRecordStatus.CLEANING.getCode());
            cleanRecordService.insertTRoomCleanRecord(cleanRecord);

            TRoom room = new TRoom();
            room.setId(req.getRoomId());
            // 2 清洁中
            room.setStatus(OfficeEnum.RoomStatus.IN_CLEAN.getCode());
            roomService.updateTRoom(room);

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

    @Autowired
    ITRoomCleanRecordService roomCleanRecordService;

    /**
     * 查询保洁员自己房间打扫记录列表
     */
//    @PreAuthorize("@ss.hasPermi('office:cleanrecord:list')")
    @GetMapping("/record")
    public TableDataInfo h5list(TRoomCleanRecord tRoomCleanRecord) {
       /* Long userId = SecurityUtils.getLoginUser().getWxUser().getId();
        tRoomCleanRecord.setWxUserId(userId);*/
        startPage();
        List<CleanRecordH5Vo> list = roomCleanRecordService.selectTRoomCleanRecordH5List(tRoomCleanRecord);
        for (CleanRecordH5Vo vo : list) {
            vo.setRoomStatus(OfficeEnum.RoomStatus.GetValueByCode(vo.getRoomStatus()).getInfo());
        }
        return getDataTable(list);
    }
}
