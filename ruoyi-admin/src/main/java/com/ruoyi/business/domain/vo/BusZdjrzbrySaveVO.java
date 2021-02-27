package com.ruoyi.business.domain.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.ruoyi.common.annotation.Excel;

/**
 * 承包商重大节假日值班人员 bus_zdjrzbry
 * 
 * @author yaowei
 * @date 2021-02-24
 */
@Validated
public class BusZdjrzbrySaveVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 重大节日主键 */
	@NotNull(message = "节假日ID不能为空")
	@Excel(name = "重大节日主键")
	private Long zdjrId;

	/** 承包商ID */
	@NotNull(message = "承包商ID不能为空")
	@Excel(name = "承包商ID")
	private Long cbsId;

	/** 承包商名称 */
	@Excel(name = "承包商名称")
	private String cbsmc;

	@Valid
	@NotEmpty(message = "值班人员不能为空")
	private List<SelectedBusZbgrVO> zbryList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getZdjrId() {
		return zdjrId;
	}

	public void setZdjrId(Long zdjrId) {
		this.zdjrId = zdjrId;
	}

	public Long getCbsId() {
		return cbsId;
	}

	public void setCbsId(Long cbsId) {
		this.cbsId = cbsId;
	}

	public String getCbsmc() {
		return cbsmc;
	}

	public void setCbsmc(String cbsmc) {
		this.cbsmc = cbsmc;
	}

	public List<SelectedBusZbgrVO> getZbryList() {
		return zbryList;
	}

	public void setZbryList(List<SelectedBusZbgrVO> zbryList) {
		this.zbryList = zbryList;
	}

}
