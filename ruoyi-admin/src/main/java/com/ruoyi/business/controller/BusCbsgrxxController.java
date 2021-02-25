package com.ruoyi.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.business.domain.BusCbsgrxx;
import com.ruoyi.business.domain.vo.BusCbsgrxxSaveVO;
import com.ruoyi.business.service.IBusCbsgrxxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 承包商工人信息Controller
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@RestController
@RequestMapping("/business/cbsgrxx")
public class BusCbsgrxxController extends BaseController {
	@Autowired
	private IBusCbsgrxxService busCbsgrxxService;

	/**
	 * 查询承包商工人信息列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(BusCbsgrxx busCbsgrxx) {
		startPage();
		List<BusCbsgrxx> list = busCbsgrxxService.selectBusCbsgrxxList(busCbsgrxx);
		return getDataTable(list);
	}

	/**
	 * 导出承包商工人信息列表
	 */
	@Log(title = "承包商工人信息", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(BusCbsgrxx busCbsgrxx) {
		List<BusCbsgrxx> list = busCbsgrxxService.selectBusCbsgrxxList(busCbsgrxx);
		ExcelUtil<BusCbsgrxx> util = new ExcelUtil<BusCbsgrxx>(BusCbsgrxx.class);
		return util.exportExcel(list, "cbsgrxx");
	}

	/**
	 * 获取承包商工人信息详细信息
	 */
	@ApiOperation("根据工人ID获取工人详情")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(busCbsgrxxService.selectBusCbsgrxxById(id));
	}

	/**
	 * 新增承包商工人信息
	 */
	@ApiOperation("新增修改/承包商工人信息")
	@Log(title = "承包商工人信息", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody BusCbsgrxxSaveVO busCbsgrxxSaveVO) {
		if (busCbsgrxxSaveVO.getId() == null) {
			return toAjax(busCbsgrxxService.insertBusCbsgrxx(busCbsgrxxSaveVO));
		} else {
			return toAjax(busCbsgrxxService.updateBusCbsgrxx(busCbsgrxxSaveVO));
		}
	}

	/**
	 * 修改承包商工人信息
	 */
	@ApiOperation("修改承包商工人信息")
	@Log(title = "承包商工人信息", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody BusCbsgrxxSaveVO busCbsgrxxSaveVO) {
		return toAjax(busCbsgrxxService.updateBusCbsgrxx(busCbsgrxxSaveVO));
	}

	/**
	 * 删除承包商工人信息
	 */
	@ApiOperation("删除承包商工人")
	@Log(title = "承包商工人信息", businessType = BusinessType.DELETE)
	@PostMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(busCbsgrxxService.deleteBusCbsgrxxByIds(ids));
	}
}
