package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.service.ITRoomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 店铺房间Controller
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@RestController
@RequestMapping("/office/room")
public class TRoomController extends BaseController {
    @Autowired
    private ITRoomService tRoomService;

    /**
     * 查询店铺房间列表
     */
    @PreAuthorize("@ss.hasPermi('office:room:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoom tRoom) {
        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
            tRoom.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TRoom> list = tRoomService.selectTRoomList(tRoom);
        return getDataTable(list);
    }

    /**
     * 导出店铺房间列表
     */
    @PreAuthorize("@ss.hasPermi('office:room:export')")
    @Log(title = "店铺房间" , businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoom tRoom) {
        List<TRoom> list = tRoomService.selectTRoomList(tRoom);
        ExcelUtil<TRoom> util = new ExcelUtil<TRoom>(TRoom.class);
        util.exportExcel(response, list, "店铺房间数据");
    }

    /**
     * 获取店铺房间详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:room:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tRoomService.selectTRoomById(id));
    }

    /**
     * 新增店铺房间
     */
    @PreAuthorize("@ss.hasPermi('office:room:add')")
    @Log(title = "店铺房间" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoom tRoom) {
        tRoom.setCreateBy(SecurityUtils.getUserId() + "");
        return toAjax(tRoomService.insertTRoom(tRoom));
    }

    /**
     * 修改店铺房间
     */
    @PreAuthorize("@ss.hasPermi('office:room:edit')")
    @Log(title = "店铺房间" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoom tRoom) {
        return toAjax(tRoomService.updateTRoom(tRoom));
    }

    /**
     * 删除店铺房间
     */
    @PreAuthorize("@ss.hasPermi('office:room:remove')")
    @Log(title = "店铺房间" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tRoomService.deleteTRoomByIds(ids));
    }
}
