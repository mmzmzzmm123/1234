package com.ruoyi.web;


import com.ruoyi.system.domain.HeFeiCompanyAdvice;
import lombok.Data;

import java.util.List;

@Data
public class PageResult {
    private Long total;


    private List<?> rows;

    private List<HeFeiCompanyAdvice> companyAdvices;

    public PageResult() {
    }

    public PageResult(Long total, List<?> rows, List<HeFeiCompanyAdvice> companyAdvices) {
        this.total = total;
        this.rows = rows;
        this.companyAdvices = companyAdvices;
    }
}
