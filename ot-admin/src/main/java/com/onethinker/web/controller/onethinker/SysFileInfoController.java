package com.onethinker.web.controller.onethinker;

import com.onethinker.onethinker.domain.SysFileInfo;
import com.onethinker.onethinker.dto.SysFileInfoReqDTO;
import com.onethinker.onethinker.service.ISysFileInfoService;
import com.onethinker.common.annotation.Log;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件信息Controller
 *
 * @author yangyouqi
 * @date 2023-10-25
 */
@RestController
@RequestMapping("/onethinker/file")
public class SysFileInfoController extends BaseController {
    @Autowired
    private ISysFileInfoService sysFileInfoService;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFileInfo sysFileInfo) {
        startPage();
        List<SysFileInfo> list = sysFileInfoService.selectSysFileInfoList(sysFileInfo);
        return getDataTable(list);
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:file:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId) {
        return success(sysFileInfoService.selectSysFileInfoByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:file:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping(value = "/upload")
    public AjaxResult upload(SysFileInfoReqDTO sysFileInfoReqDTO) {
        return AjaxResult.success(sysFileInfoService.upload(sysFileInfoReqDTO));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:file:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable String[] fileIds) {
        return toAjax(sysFileInfoService.deleteSysFileInfoByFileIds(fileIds));
    }
}
