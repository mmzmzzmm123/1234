package com.ruoyi.business.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Objects;
import com.ruoyi.business.domain.BusCbszdxx;
import com.ruoyi.business.domain.ViewBusZdxx;
import com.ruoyi.business.domain.vo.ViewBusZdxxVO;
import com.ruoyi.business.service.IBusCbszdxxService;
import com.ruoyi.business.service.IViewBusZdxxService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;

import io.swagger.annotations.ApiOperation;

/**
 * VIEWController
 * 
 * @author yaowei
 * @date 2021-02-22
 */
@RestController
@RequestMapping("/business/zdxx")
public class ViewBusZdxxController extends BaseController {
	@Autowired
	private IViewBusZdxxService viewBusZdxxService;

	@Autowired
	private IBusCbszdxxService busCbszdxxService;

	/**
	 * 查询VIEW列表
	 */
	@ApiOperation("查询站点和定价信息")
	@GetMapping("/list")
	public List<ViewBusZdxxVO> list(Long cbsId, String year, ViewBusZdxx viewBusZdxx) {
		if (cbsId == null) {
			throw new CustomException("承包商ID不能为空");
		}
		// 默认查询当前年份的承包站点信息
		String cbYear = year;
		if (StringUtils.isEmpty(year)) {
			cbYear = DateUtils.dateTimeNow("yyyy");
		}
		List<BusCbszdxx> busCbszdxxList = findBusCbsZdxxList(cbsId, cbYear);

		// 查询所有站点信息，如果有承包商承包的站点，标记为selected状态
		List<ViewBusZdxx> list = viewBusZdxxService.selectViewBusZdxxList(viewBusZdxx);
		List<ViewBusZdxxVO> viewBusZdxxVOList = new ArrayList<>();
		for (ViewBusZdxx zdxx : list) {
			ViewBusZdxxVO viewBusZdxxVO = new ViewBusZdxxVO();
			BeanUtils.copyProperties(zdxx, viewBusZdxxVO);
			boolean selected = isSelected(viewBusZdxxVO, busCbszdxxList);
			viewBusZdxxVO.setSelected(selected);
			viewBusZdxxVOList.add(viewBusZdxxVO);
		}
		return viewBusZdxxVOList;
	}

	private List<BusCbszdxx> findBusCbsZdxxList(Long cbsId, String cbYear) {
		BusCbszdxx busCbszdxx = new BusCbszdxx();
		busCbszdxx.setCbsId(cbsId);
		busCbszdxx.setYear(cbYear);
		return busCbszdxxService.selectBusCbszdxxList(busCbszdxx);
	}

	/**
	 * 判断当前站点有没有被承包商承包
	 * 
	 * @param viewBusZdxxVO
	 *            站点信息
	 * @param busCbszdxxList
	 *            承包商承包的站点信息
	 * @return
	 */
	private boolean isSelected(ViewBusZdxxVO viewBusZdxxVO, List<BusCbszdxx> busCbszdxxList) {
		if (busCbszdxxList.isEmpty()) {
			return false;
		}
		Long zdId = viewBusZdxxVO.getId();
		return busCbszdxxList.stream().anyMatch(e -> Objects.equal(e.getZdId(), zdId));
	}

	/**
	 * 获取VIEW详细信息
	 */
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(viewBusZdxxService.selectViewBusZdxxById(id));
	}

	/**
	 * 新增VIEW
	 */
	@Log(title = "VIEW", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody ViewBusZdxx viewBusZdxx) {
		return toAjax(viewBusZdxxService.insertViewBusZdxx(viewBusZdxx));
	}

	/**
	 * 修改VIEW
	 */
	@Log(title = "VIEW", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody ViewBusZdxx viewBusZdxx) {
		return toAjax(viewBusZdxxService.updateViewBusZdxx(viewBusZdxx));
	}

	/**
	 * 删除VIEW
	 */
	@Log(title = "VIEW", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(viewBusZdxxService.deleteViewBusZdxxByIds(ids));
	}
}
