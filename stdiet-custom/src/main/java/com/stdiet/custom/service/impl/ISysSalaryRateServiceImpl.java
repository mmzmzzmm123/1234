package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.NumberUtils;
import com.stdiet.custom.domain.SysSalaryComposeConfig;
import com.stdiet.custom.domain.SysSalaryRate;
import com.stdiet.custom.mapper.SysSalaryComposeConfigMapper;
import com.stdiet.custom.service.ISysSalaryRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ISysSalaryRateServiceImpl implements ISysSalaryRateService {

    @Autowired
    private SysSalaryComposeConfigMapper sysSalaryComposeConfigMapper;

   /* @Override
    public AjaxResult calculateSalaryRate(List<SalaryRequest> salaryList){
        AjaxResult result = AjaxResult.success();
        List<SysSalaryRate> sysSalaryRateList = new ArrayList<>();
        Map<String,SysSalaryComposeConfig> configMap = getSalaryComposeConfigMap();
        if(salaryList != null && salaryList.size() > 0 && configMap != null && configMap.size() > 0){
            for (SalaryRequest salaryRequest : salaryList) {
                if(StringUtils.isEmpty(salaryRequest.getName()) || salaryRequest.getSalary().doubleValue() < 0 ||
                        StringUtils.isEmpty(salaryRequest.getSalaryPostName()) || salaryRequest.getFullWork() == null){
                    return AjaxResult.error("Excel数据格式错误");
                }
                SysSalaryComposeConfig config = configMap.get(salaryRequest.getSalaryPostName().trim());
                if(config == null){
                    return AjaxResult.error(salaryRequest.getName()+"所在岗位不存在");
                }
                if(config.getBaseRateSum() > 100){
                    return AjaxResult.error("岗位薪资配置比例超过100");
                }
                SysSalaryRate sysSalaryRate = calculateSingleRate(salaryRequest.getSalary(), salaryRequest.getFullWork(), config);
                sysSalaryRate.setName(salaryRequest.getName().trim());
                sysSalaryRate.setSalaryPostName(salaryRequest.getSalaryPostName());
                sysSalaryRateList.add(sysSalaryRate);
            }
        }
        result.put("sysSalaryRateList", sysSalaryRateList);
        return result;
    }*/

    /**
     * 获取薪资部门配置
     * @return
     */
    @Override
    public Map<String,SysSalaryComposeConfig> getSalaryComposeConfigMap(){
        Map<String,SysSalaryComposeConfig> configMap = new HashMap<>();
        List<SysSalaryComposeConfig> configList = sysSalaryComposeConfigMapper.selectSysSalaryComposeConfigList(new SysSalaryComposeConfig());
        if(configList != null && configList.size() > 0){
            for (SysSalaryComposeConfig config : configList) {
                configMap.put(config.getSalaryPostName(), config);
            }
        }
        return configMap;
    }

    /**
     * 计算工资比例
     * @param totalSalary 总工资
     * @param fullWorkFlag 是否全勤
     * @param sysSalaryComposeConfig 比例配置
     * @return
     */
    @Override
    public SysSalaryRate calculateSingleRate(BigDecimal totalSalary, Boolean fullWorkFlag, SysSalaryComposeConfig sysSalaryComposeConfig){
        SysSalaryRate sysSalaryRate = new SysSalaryRate();
        //计算加班补贴
        sysSalaryRate.setOvertimeAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getOvertimeAllowanceRate()/100, 2));
        //计算车补
        sysSalaryRate.setCarAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getCarAllowanceRate()/100, 2));
        //计算餐补
        sysSalaryRate.setMealAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getMealAllowanceRate()/100, 2));
        //计算房补
        sysSalaryRate.setHouseAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getHouseAllowanceRate()/100, 2));
        //计算差旅费
        sysSalaryRate.setTravelAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getTravelAllowanceRate()/100, 2));
        //计算技能补贴
        sysSalaryRate.setSkillAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getSkillAllowanceRate()/100, 2));
        //计算培训补贴
        sysSalaryRate.setTrainAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getTrainAllowanceRate()/100, 2));
        //计算部门补贴
        sysSalaryRate.setDepartmentAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getDepartmentAllowanceRate()/100, 2));
        //该岗位存在继续教育补贴而且大于等于该薪资界限才有继续教育补贴
        if(sysSalaryComposeConfig.getEducationAllowanceRate().intValue() > 0 && totalSalary.doubleValue() >= SysSalaryComposeConfig.educationAllowanceLimit){
            sysSalaryRate.setEducationAllowance(NumberUtils.getNumberByRoundHalfUp(totalSalary.doubleValue()*sysSalaryComposeConfig.getDepartmentAllowanceRate()/100, 2));
        }
        BigDecimal totalRateSalary = sysSalaryRate.getTotalRateSalary();
        //计算剩余薪资
        Double surplusSalary = totalSalary.doubleValue() - totalRateSalary.doubleValue();
        if(surplusSalary <= 0){
            return sysSalaryRate;
        }
        //是否全勤
        if(fullWorkFlag){
            //减去全勤奖
            surplusSalary -= sysSalaryComposeConfig.getFullAttendanceSalary().doubleValue();
            sysSalaryRate.setFullAttendanceSalary(surplusSalary >= 0 ? sysSalaryComposeConfig.getFullAttendanceSalary() : BigDecimal.valueOf(surplusSalary + sysSalaryComposeConfig.getFullAttendanceSalary().doubleValue()));
            if(surplusSalary <= 0){
                return sysSalaryRate;
            }
        }
        //减去基本薪资
        surplusSalary -= sysSalaryComposeConfig.getBaseSalary().doubleValue();
        sysSalaryRate.setBaseSalary(surplusSalary >= 0 ? sysSalaryComposeConfig.getBaseSalary() : BigDecimal.valueOf(surplusSalary + sysSalaryComposeConfig.getBaseSalary().doubleValue()));
        if(surplusSalary <= 0){
            return sysSalaryRate;
        }
        BigDecimal postSalary = null;
        Double baseSalarySum = totalRateSalary.doubleValue() >= SysSalaryComposeConfig.baseSalarySumLimit ? SysSalaryComposeConfig.higherBaseSalarySum : SysSalaryComposeConfig.lowerBaseSalarySum;
        //计算原有基础岗位补贴
        postSalary = NumberUtils.getNumberByRoundHalfUp((baseSalarySum - sysSalaryComposeConfig.getBaseSalary().doubleValue()) * sysSalaryComposeConfig.getPostAllowanceRate()/100, 2);
        //减去岗位补贴
        surplusSalary -= postSalary.doubleValue();
        sysSalaryRate.setPostAllowance(surplusSalary >= 0 ? postSalary : BigDecimal.valueOf(surplusSalary + postSalary.doubleValue()));
        if(surplusSalary <= 0){
            return sysSalaryRate;
        }
        //剩余薪资全部为绩效
        sysSalaryRate.setMeritsAllowance(BigDecimal.valueOf(surplusSalary));
        return sysSalaryRate;
    }
}
