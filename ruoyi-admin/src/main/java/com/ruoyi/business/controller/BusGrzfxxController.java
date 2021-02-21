package com.ruoyi.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.business.domain.BusGrzfxx;
import com.ruoyi.business.service.IBusGrzfxxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 工人证书信息Controller
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@RestController
@RequestMapping("/business/grzfxx")
public class BusGrzfxxController extends BaseController {
	@Autowired
	private IBusGrzfxxService busGrzfxxService;

	/**
	 * 查询工人证书信息列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(BusGrzfxx busGrzfxx) {
		startPage();
		List<BusGrzfxx> list = busGrzfxxService.selectBusGrzfxxList(busGrzfxx);
		return getDataTable(list);
	}

	/**
	 * 导出工人证书信息列表
	 */
	@Log(title = "工人证书信息", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(BusGrzfxx busGrzfxx) {
		List<BusGrzfxx> list = busGrzfxxService.selectBusGrzfxxList(busGrzfxx);
		ExcelUtil<BusGrzfxx> util = new ExcelUtil<BusGrzfxx>(BusGrzfxx.class);
		return util.exportExcel(list, "grzfxx");
	}

	/**
	 * 获取工人证书信息详细信息
	 */
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(busGrzfxxService.selectBusGrzfxxById(id));
	}

	/**
	 * 新增工人证书信息
	 */
	@Log(title = "工人证书信息", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody BusGrzfxx busGrzfxx) {
		return toAjax(busGrzfxxService.insertBusGrzfxx(busGrzfxx));
	}

	/**
	 * 修改工人证书信息
	 */
	@Log(title = "工人证书信息", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody BusGrzfxx busGrzfxx) {
		return toAjax(busGrzfxxService.updateBusGrzfxx(busGrzfxx));
	}

	/**
	 * 删除工人证书信息
	 */
	@Log(title = "工人证书信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(busGrzfxxService.deleteBusGrzfxxByIds(ids));
	}
}
