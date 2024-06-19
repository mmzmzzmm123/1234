package com.renxin.gauge.vo;

import com.renxin.gauge.domain.PsyGaugeQuestionsResult;
import com.renxin.gauge.domain.PsyGaugeScoreSetting;
import com.renxin.gauge.domain.PsyOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GaugeReportVO implements Serializable {
    private static final long serialVersionUID = 2833763951738684540L;

    private PsyOrder order;

    private PsyGaugeScoreSetting setting;

    private List<String> lats;

    private List<PsyGaugeQuestionsResult> results;

}
