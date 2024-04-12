package com.ruoyi.autoee.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.autoee.domain.ARoom;
import com.ruoyi.autoee.service.IARoomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间管理Controller
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/autoee/room")
public class ARoomController extends BaseController
{
    @Autowired
    private IARoomService aRoomService;

    /**
     * 查询房间管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:room:list')")
    @GetMapping("/list")
    public TableDataInfo list(ARoom aRoom)
    {
        startPage();
        List<ARoom> list = aRoomService.selectARoomList(aRoom);
        return getDataTable(list);
    }

    /**
     * 导出房间管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:room:export')")
    @Log(title = "房间管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ARoom aRoom)
    {
        List<ARoom> list = aRoomService.selectARoomList(aRoom);
        ExcelUtil<ARoom> util = new ExcelUtil<ARoom>(ARoom.class);
        util.exportExcel(response, list, "房间管理数据");
    }

    /**
     * 获取房间管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('autoee:room:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aRoomService.selectARoomById(id));
    }

    /**
     * 新增房间管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:room:add')")
    @Log(title = "房间管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ARoom aRoom)
    {
        return toAjax(aRoomService.insertARoom(aRoom));
    }

    /**
     * 修改房间管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:room:edit')")
    @Log(title = "房间管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ARoom aRoom)
    {
        return toAjax(aRoomService.updateARoom(aRoom));
    }

    /**
     * 删除房间管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:room:remove')")
    @Log(title = "房间管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(aRoomService.deleteARoomByIds(ids));
    }
}
