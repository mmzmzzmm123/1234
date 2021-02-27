package com.ruoyi.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.business.domain.BusZdjrgl;
import com.ruoyi.business.service.IBusZdjrglService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;

import io.swagger.annotations.ApiOperation;

/**
 * 重大节日管理Controller
 * 
 * @author yaowei
 * @date 2021-02-24
 */
@RestController
@RequestMapping("/business/zdjrgl")
public class BusZdjrglController extends BaseController {

	@Autowired
	private IBusZdjrglService busZdjrglService;

	/**
	 * 查询重大节日管理列表
	 */
	@ApiOperation("根据年份和月份查询该月的节假日")
	@GetMapping("/list")
	public AjaxResult list(int year, int month) {
		return AjaxResult.success(busZdjrglService.selectBusZdjrglByJrrq(year, month));
	}

	/**
	 * 获取重大节日管理详细信息
	 */
	@ApiOperation("获取节假日详情(包括承包商和值班人员)")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") String id) {
		return AjaxResult.success(busZdjrglService.selectBusZdjrglById(id));
	}

	/**
	 * 新增重大节日管理
	 */
	@ApiOperation("新增/修改节假日")
	@Log(title = "重大节日管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody BusZdjrgl busZdjrgl) {
		if (busZdjrgl.getId() == null) {
			return toAjax(busZdjrglService.insertBusZdjrgl(busZdjrgl));
		} else {
			return toAjax(busZdjrglService.updateBusZdjrgl(busZdjrgl));
		}
	}

	/**
	 * 修改重大节日管理
	 */
	@Log(title = "重大节日管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody BusZdjrgl busZdjrgl) {
		return toAjax(busZdjrglService.updateBusZdjrgl(busZdjrgl));
	}

	/**
	 * 删除重大节日管理
	 */
	@ApiOperation("根据ID删除节假日")
	@Log(title = "重大节日管理", businessType = BusinessType.DELETE)
	@PostMapping("/{ids}")
	public AjaxResult remove(@PathVariable String[] ids) {
		return toAjax(busZdjrglService.deleteBusZdjrglByIds(ids));
	}
}
