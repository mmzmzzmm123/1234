package com.gox.basic.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.gox.common.core.domain.entity.SysRole;
import com.gox.common.core.domain.entity.SysUser;
import com.gox.common.utils.StringUtils;
import com.gox.system.service.ISysRoleService;
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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.basic.domain.Node;
import com.gox.basic.service.INodeService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 *   Controller
 * 
 * @author gox
 * @date 2021-03-11
 */
@RestController
@RequestMapping("/basic/node")
public class NodeController extends BaseController
{
    @Autowired
    private INodeService nodeService;
    @Autowired
    private ISysRoleService roleService;

    /**
     * 查询  列表
     */
    @PreAuthorize("@ss.hasPermi('system:node:list')")
    @GetMapping("/list")
    public TableDataInfo list(Node node)
    {
        startPage();
        List<Node> list = nodeService.selectNodeList(node);
        return getDataTable(list);
    }

    /**
     * 导出  列表
     */
    @PreAuthorize("@ss.hasPermi('system:node:export')")
    @Log(title = "导出节点列表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Node node)
    {
        List<Node> list = nodeService.selectNodeList(node);
        ExcelUtil<Node> util = new ExcelUtil<Node>(Node.class);
        return util.exportExcel(list, "node");
    }

    /**
     * 获取  详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:node:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        //return AjaxResult.success(nodeService.selectNodeById(id));
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(id) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        if (StringUtils.isNotNull(id)) {
            ajax.put(AjaxResult.DATA_TAG, nodeService.selectNodeById(id));
            ajax.put("roleIds", roleService.selectRoleListByNodeId(id));
        }
        return ajax;
    }

    /**
     * 新增  
     */
    @PreAuthorize("@ss.hasPermi('system:node:add')")
    @Log(title = "新增节点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Node node)
    {
        return toAjax(nodeService.insertNode(node));
    }

    /**
     * 修改  
     */
    @PreAuthorize("@ss.hasPermi('system:node:edit')")
    @Log(title = "修改节点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Node node)
    {
        return toAjax(nodeService.updateNode(node));
    }

    /**
     * 删除  
     */
    @PreAuthorize("@ss.hasPermi('system:node:remove')")
    @Log(title = "删除节点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(nodeService.deleteNodeByIds(ids));
    }
}
