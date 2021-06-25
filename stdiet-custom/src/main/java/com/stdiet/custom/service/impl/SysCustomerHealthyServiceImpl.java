package com.stdiet.custom.service.impl;

import java.util.*;

import com.stdiet.common.config.RuoYiConfig;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.domain.entity.SysDictData;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.bean.ObjectUtils;
import com.stdiet.common.utils.reflect.ReflectUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerHealthyExtended;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.dto.request.HealthyDetailRequest;
import com.stdiet.custom.dto.response.NutritionalCalories;
import com.stdiet.custom.mapper.SysCustomerHealthyExtendedMapper;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.service.ISysCustomerService;
import com.stdiet.custom.utils.NutritionalUtils;
import com.stdiet.custom.utils.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCustomerHealthyMapper;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.service.ISysCustomerHealthyService;

/**
 * 客户健康Service业务层处理
 *
 * @author xzj
 * @date 2021-01-23
 */
@Service
public class SysCustomerHealthyServiceImpl implements ISysCustomerHealthyService
{
    @Autowired
    private SysCustomerHealthyMapper sysCustomerHealthyMapper;

    @Autowired
    private SysCustomerMapper sysCustomerMapper;

    @Autowired
    private SysCustomerHealthyExtendedMapper sysCustomerHealthyExtendedMapper;

    public static final String reportDownFileNameFormat = "%s超重%s斤%s";

    /**
     * 查询客户健康
     *
     * @param id 客户健康ID
     * @return 客户健康
     */
    @Override
    public SysCustomerHealthy selectSysCustomerHealthyById(Long id)
    {
        return sysCustomerHealthyMapper.selectSysCustomerHealthyById(id);
    }

    /**
     * 查询客户健康列表
     *
     * @param sysCustomerHealthy 客户健康
     * @return 客户健康
     */
    @Override
    public List<SysCustomerHealthy> selectSysCustomerHealthyList(SysCustomerHealthy sysCustomerHealthy)
    {
        return sysCustomerHealthyMapper.selectSysCustomerHealthyList(sysCustomerHealthy);
    }


    /**
     * 新增客户健康
     *
     * @param sysCustomerHealthy 客户健康
     * @return 结果
     */
    public AjaxResult insertSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy){
        //客户ID解密
        String customerId = StringUtils.isNotEmpty(sysCustomerHealthy.getCustomerEncId()) ? AesUtils.decrypt(sysCustomerHealthy.getCustomerEncId(), null) : "";
        if(sysCustomerHealthy.getCustomerId() == null && StringUtils.isEmpty(customerId)){
            return AjaxResult.error("客户不存在");
        }
        //判断客户是否存在
        SysCustomer sysCustomer = sysCustomerMapper.selectSysCustomerById(Long.parseLong(customerId));
        if(sysCustomer == null){
            return AjaxResult.error("客户不存在");
        }
        //判断是否已存在客户健康评估
        SysCustomerHealthy customerHealthy = selectSysCustomerHealthyByCustomerId(Long.parseLong(customerId));
        if(customerHealthy != null){
            return AjaxResult.error("已存在健康评估信息，无法重复添加");
        }
        //设置客户ID
        sysCustomerHealthy.setCustomerId(Long.parseLong(customerId));
        int rows = sysCustomerHealthyMapper.insertSysCustomerHealthy(sysCustomerHealthy);
        if(rows > 0 && sysCustomerHealthy.getId() != null){
            //添加扩展信息
            sysCustomerHealthy.getHealthyExtend().setHealthyId(sysCustomerHealthy.getId());
            rows = sysCustomerHealthyExtendedMapper.insertSysCustomerHealthyExtended(sysCustomerHealthy.getHealthyExtend());
        }
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 编辑客户健康
     * @param sysCustomerHealthy
     * @return
     */
    @Override
    public int updateSysCustomerHealthy(SysCustomerHealthy sysCustomerHealthy){
        int rows = sysCustomerHealthyMapper.updateSysCustomerHealthy(sysCustomerHealthy);
        if(rows > 0 && sysCustomerHealthy.getHealthyExtend() != null && sysCustomerHealthy.getHealthyExtend().getId() != null){
            rows = sysCustomerHealthyExtendedMapper.updateSysCustomerHealthyExtended(sysCustomerHealthy.getHealthyExtend());
        }
        return rows;
    }

    /**
     * 批量删除客户健康
     *
     * @param ids 需要删除的客户健康ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerHealthyByIds(Long[] ids)
    {
        return sysCustomerHealthyMapper.deleteSysCustomerHealthyByIds(ids);
    }

    /**
     * 删除客户健康信息
     *
     * @param id 客户健康ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerHealthyById(Long id)
    {
        return sysCustomerHealthyMapper.deleteSysCustomerHealthyById(id);
    }

    /**
     * 根据手机号查询客户健康评估信息
     * @param phone
     */
    @Override
    public SysCustomerHealthy selectSysCustomerHealthyByPhone(String phone){
        return sysCustomerHealthyMapper.selectSysCustomerHealthyByPhone(phone);
    }

    /**
     * 根据客户ID查询健康评估表信息
     */
    public SysCustomerHealthy selectSysCustomerHealthyByCustomerId(Long customerId){
        return sysCustomerHealthyMapper.selectSysCustomerHealthyByCustomerId(customerId);
    }

    /**
     * 根据客户ID删除客户健康评估信息
     * @param customerId
     * @return
     */
    public int deleteCustomerHealthyByCustomerId(Long customerId){
        return sysCustomerHealthyMapper.deleteCustomerHealthyByCustomerId(customerId);
    }

    /**
     * 生成健康评估报告
     * @return
     */
    @Override
    public AjaxResult generateHealthyReport(HealthyDetailRequest healthyDetailRequest){
        AjaxResult ajaxResult = AjaxResult.error();
        String templatePath = "/home/workspace/ShengTangManage/running/pdf/healthyReport.pdf";
        //String templatePath = "D:\\contract\\healthyReport.pdf";
        String fileName = "healthyReport" + healthyDetailRequest.getCustomerId() + System.currentTimeMillis() + ".pdf";
        String filePath = RuoYiConfig.getDownloadPath() + fileName;
        //查询客户健康信息
        SysCustomerHealthy sysCustomerHealthy = selectSysCustomerHealthyById(healthyDetailRequest.getId());
        if(sysCustomerHealthy != null){
            Map<String, String> reportData = getReportData(sysCustomerHealthy, healthyDetailRequest);
            ajaxResult = PdfUtils.generatePdfFile(templatePath, filePath, reportData);
            ajaxResult.put("path", fileName);
            ajaxResult.put("downReportFileName", reportData.get("downReportFileName")+".pdf");
        }
        return ajaxResult;
    }

    public static final String[] healthyAttrNameAray = {"createTime","name","phone","conditioningProject","sex","age","tall","weight","position",
            "experience","difficulty","rebound","crux","condiment","cookingStyle","cookingStyleRate","washVegetablesStyle",
            "breakfastType","breakfastFood","lunchType","dinner","vegetableRate","commonMeat","dinnerTime","supperNum","supperFood",
            "dietHotAndCold","dietFlavor","vegetablesNum","vegetablesRateType","fruitsNum","fruitsTime","fruitsRate","riceNum","riceFull",
            "eatingSpeed","makeFoodType","snacks","healthProductsFlag","healthProductsBrand","healthProductsName",
            "healthProductsWeekRate","dishesIngredient","waterNum","waterType","waterHabit",
            "drinksNum","drinkWineFlag","drinkWineClassify","drinkWineAmount","smokeFlag","smokeRate",
            "secondSmoke","workIndustry","workType","defecationNum","defecationTime","defecationShape",
            "defecationSmell","defecationSpeed","defecationColor","motionNum","motionDuration","motionTime",
            "motion","motionField","sleepTime","sleepQuality","sleepDrugFlag","sleepDrug","stayupLateFlag","stayupLateWeekNum",
            "physicalSigns","moistureDateString","bloodDataString","familyIllnessHistory","operationHistory","nearOperationFlag",
            "recoveryeSituation","longEatDrugFlag","longEatDrugClassify","allergyFlag","allergySituation","allergen"
    };

    public static final String[] nutriAttrNameArray = {"standardWeight","overWeight","metabolizeHeat","activityFactor","withoutExerciseHeat",
            "exerciseHeat","targetEveryWeightHeat","standardEveryWeightHeat","nutritionalHeat_one","nutritionalHeat_two","nutritionalHeat_three",
            "nutritionalQuality_one","nutritionalQuality_two","nutritionalQuality_three",
            "weightNutritionalRate_one","weightNutritionalRate_two","weightNutritionalRate_three"
    };

    private Map<String,String> getReportData(SysCustomerHealthy sysCustomerHealthy, HealthyDetailRequest healthyDetailRequest){
        if(healthyDetailRequest.getBloodData() != null){
            String bloodString = "";
            for (String value : healthyDetailRequest.getBloodData()) {
                bloodString += value + "\n";
            }
            healthyDetailRequest.setBloodDataString(bloodString);
        }
        if(healthyDetailRequest.getMoistureDate() != null){
            String moistureString = "";
            for (String value : healthyDetailRequest.getMoistureDate()) {
                moistureString += value + "\n";
            }
            healthyDetailRequest.setMoistureDateString(moistureString);
        }
        Map<String,String> data = new HashMap<>();
        for (String key : healthyAttrNameAray) {
            data.put(key, ReflectUtils.getFieldValueByFieldName(key, healthyDetailRequest));
        }
        //减脂指导
        data.put("guidance", sysCustomerHealthy.getGuidance());
        //营养热量分析数据
        NutritionalCalories nutritionalCalories = NutritionalUtils.getNutritionalCaloriesData(sysCustomerHealthy);
        nutritionalCalories.setNutritionalHeat_one(nutritionalCalories.getNutritionalHeat()[0].toString());
        nutritionalCalories.setNutritionalHeat_two(nutritionalCalories.getNutritionalHeat()[1].toString());
        nutritionalCalories.setNutritionalHeat_three(nutritionalCalories.getNutritionalHeat()[2].toString());
        nutritionalCalories.setNutritionalQuality_one(nutritionalCalories.getNutritionalQuality()[0].toString());
        nutritionalCalories.setNutritionalQuality_two(nutritionalCalories.getNutritionalQuality()[1].toString());
        nutritionalCalories.setNutritionalQuality_three(nutritionalCalories.getNutritionalQuality()[2].toString());
        nutritionalCalories.setWeightNutritionalRate_one(nutritionalCalories.getWeightNutritionalRate()[0].toString());
        nutritionalCalories.setWeightNutritionalRate_two(nutritionalCalories.getWeightNutritionalRate()[1].toString());
        nutritionalCalories.setWeightNutritionalRate_three(nutritionalCalories.getWeightNutritionalRate()[2].toString());
        for (String key : nutriAttrNameArray) {
            if("targetEveryWeightHeat".equals(key)){
                data.put(key, nutritionalCalories.getTargetEveryWeightHeat()[0].intValue()+"-"+nutritionalCalories.getTargetEveryWeightHeat()[1].intValue());
                continue;
            }
            if("standardEveryWeightHeat".equals(key)){
                data.put(key, nutritionalCalories.getStandardEveryWeightHeat()[0].intValue()+"-"+nutritionalCalories.getStandardEveryWeightHeat()[1].intValue());
                continue;
            }
            data.put(key, ReflectUtils.getFieldValueByFieldName(key, nutritionalCalories));
        }
        data.put("company","深圳胜唐体控有限公司");
        data.put("date", DateUtils.getDate());

        //文件下载时的名称，名字+超重几斤+病史体征
        String sign = StringUtils.isEmpty(healthyDetailRequest.getPhysicalSigns()) ? "" : healthyDetailRequest.getPhysicalSigns().replace("，","").replace(",","").replace("/", "");
        String downReportFileName = String.format(reportDownFileNameFormat, healthyDetailRequest.getName(), nutritionalCalories.getOverWeight() > 0 ? nutritionalCalories.getOverWeight().toString() : "0", sign);
        data.put("downReportFileName", downReportFileName);
        return data;
    }


    /**
     * 根据类型、键值集合查询字典类型
     * @param sysDictData
     * @return
     */
    @Override
    public List<SysDictData> selectDictDataByTypeAndValue(SysDictData sysDictData){
        return sysCustomerHealthyMapper.selectDictDataByTypeAndValue(sysDictData);
    }
}