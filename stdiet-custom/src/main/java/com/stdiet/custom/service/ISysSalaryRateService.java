package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysSalaryComposeConfig;
import com.stdiet.custom.domain.SysSalaryRate;

import java.math.BigDecimal;
import java.util.Map;

public interface ISysSalaryRateService {

    Map<String, SysSalaryComposeConfig> getSalaryComposeConfigMap();

    SysSalaryRate calculateSingleRate(BigDecimal totalSalary, Boolean fullWorkFlag, SysSalaryComposeConfig sysSalaryComposeConfig);
}
