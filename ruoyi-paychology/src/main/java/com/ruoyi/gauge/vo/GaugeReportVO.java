package com.ruoyi.gauge.vo;

import com.ruoyi.gauge.domain.PsyGaugeScoreSetting;
import com.ruoyi.gauge.domain.PsyOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GaugeReportVO implements Serializable {
    private static final long serialVersionUID = 2833763951738684540L;

    private PsyOrder order;

    private PsyGaugeScoreSetting setting;

    private List<String> lats;

}
