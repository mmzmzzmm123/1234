package com.ruoyi.business.domain.vo;

import java.io.Serializable;

import com.ruoyi.common.annotation.Excel;

/**
 * 承包商已承包的站点信息对象
 * 
 * @author yaowei
 * @date 2021-02-22
 */
public class SelectedBusCbszdxxVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 站点ID */
	@Excel(name = "站点ID")
	private Long zdId;

	/** 站点名称 */
	@Excel(name = "站点名称")
	private String zdmc;

	/** 服务项目 */
	@Excel(name = "服务项目")
	private String fwxm;

}
