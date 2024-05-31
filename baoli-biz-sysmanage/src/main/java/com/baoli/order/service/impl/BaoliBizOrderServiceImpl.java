package com.baoli.order.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baoli.company.domain.BaoliBizCompanyFeeRateRule;
import com.baoli.company.mapper.BaoliBizCompanyFeeRateRuleMapper;
import com.baoli.order.domain.BaoliBizRefuseOrder;
import com.baoli.order.mapper.BaoliBizRefuseOrderMapper;
import com.baoli.store.domain.BaoliBizFeeRule;
import com.baoli.store.domain.BaoliBizStore;
import com.baoli.store.mapper.BaoliBizFeeRuleMapper;
import com.baoli.store.mapper.BaoliBizStoreMapper;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.baoli.order.mapper.BaoliBizOrderMapper;
import com.baoli.order.domain.BaoliBizOrder;
import com.baoli.order.service.IBaoliBizOrderService;

/**
 * 订单Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-07
 */
@Service
public class BaoliBizOrderServiceImpl implements IBaoliBizOrderService 
{
    @Autowired
    private BaoliBizOrderMapper baoliBizOrderMapper;
    @Autowired
    private BaoliBizStoreMapper baoliBizStoreMapper;
    @Autowired
    private BaoliBizFeeRuleMapper baoliBizFeeRuleMapper;
    @Autowired
    private BaoliBizCompanyFeeRateRuleMapper baoliBizCompanyFeeRateRuleMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BaoliBizRefuseOrderMapper baoliBizRefuseOrderMapper;
    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public BaoliBizOrder selectBaoliBizOrderById(Long id)
    {
        return baoliBizOrderMapper.selectBaoliBizOrderById(id);
    }

    /**
     * 查询订单列表
     * 
     * @param baoliBizOrder 订单
     * @return 订单
     */
    @Override
    public List<BaoliBizOrder> selectBaoliBizOrderList(BaoliBizOrder baoliBizOrder)
    {
        return baoliBizOrderMapper.selectBaoliBizOrderList(baoliBizOrder);
    }

    @Override
    public List<BaoliBizOrder> selectMyOrderList(BaoliBizOrder baoliBizOrder) {
        return baoliBizOrderMapper.selectMyOrderList(baoliBizOrder);
    }

    @Override
    public List<Map<String, Object>> selectMyOrder(BaoliBizOrder baoliBizOrder) {
        return baoliBizOrderMapper.selectMyOrder(baoliBizOrder);
    }

    @Override
    public List<Map<String, Object>> selectMyOrderCount(BaoliBizOrder baoliBizOrder) {
        return baoliBizOrderMapper.selectMyOrderCount(baoliBizOrder);
    }

    /**
     * 新增订单
     * 
     * @param baoliBizOrder 订单
     * @return 结果
     */
    @Override
    public int insertBaoliBizOrder(BaoliBizOrder baoliBizOrder)
    {
        baoliBizOrder.setCreateTime(DateUtils.getNowDate());
        return baoliBizOrderMapper.insertBaoliBizOrder(baoliBizOrder);
    }

    @Override
    public int transRefuseOrder(BaoliBizOrder  order) {
        BaoliBizOrder baoliBizOrder = baoliBizOrderMapper.selectBaoliBizOrderById(order.getId());
        //创建拒单
        BaoliBizRefuseOrder refuseOrder = new BaoliBizRefuseOrder();
        refuseOrder.setCity(baoliBizOrder.getCity());
        refuseOrder.setStoreId(baoliBizOrder.getStoreId());
        refuseOrder.setCarType(baoliBizOrder.getCarType());
        refuseOrder.setLevel1BrandId(baoliBizOrder.getLevel1BrandId());
        refuseOrder.setLevel2BrandId(baoliBizOrder.getLevel2BrandId());
        refuseOrder.setCarSeriesId(baoliBizOrder.getCarSeriesId());
        refuseOrder.setCarModelId(baoliBizOrder.getCarModelId());
        refuseOrder.setLoanType(baoliBizOrder.getLoanType());
        refuseOrder.setOrderNumber(baoliBizOrder.getOrderNumber());
        refuseOrder.setBank(baoliBizOrder.getBank());
        refuseOrder.setCustomerName(baoliBizOrder.getCustomerName());
        refuseOrder.setApplyNumber(baoliBizOrder.getApplyNumber());
        refuseOrder.setLoanAmount(baoliBizOrder.getLoanAmount());
        refuseOrder.setInStore(baoliBizOrder.getApplicantId());
        refuseOrder.setRefuseReason(order.getRefuseReason());
        refuseOrder.setRemark(order.getRemark());
        refuseOrder.setStatus(baoliBizOrder.getStatus());
        refuseOrder.setCreateTime(DateUtils.getNowDate());
        refuseOrder.setApplicantId(baoliBizOrder.getApplicantId());
        refuseOrder.setProvinceId(baoliBizOrder.getProvinceId());
        baoliBizRefuseOrderMapper.insertBaoliBizRefuseOrder(refuseOrder);
        //修改订单(id,refuseOrderId)
        baoliBizOrder.setRefuseOrderId(refuseOrder.getId());
        return baoliBizOrderMapper.updateBaoliBizOrder(baoliBizOrder);
    }

    /**
     * 修改订单
     * 
     * @param baoliBizOrder 订单
     * @return 结果
     */
    @Override
    public int updateBaoliBizOrder(BaoliBizOrder baoliBizOrder)
    {
        BaoliBizOrder srcOrder = baoliBizOrderMapper.selectBaoliBizOrderById(baoliBizOrder.getId());
        // 由暂存生成订单 或 待分享 转分享 需要确认费用规则，是否计算费用
        if((srcOrder.getStatus().equals("05") && baoliBizOrder.getStatus().equals("06")) || (srcOrder.getStatus().equals("08") && baoliBizOrder.getStatus().equals("09"))){
            //计算促销费
            srcOrder.setStatus(baoliBizOrder.getStatus());
            calculateFee(srcOrder,baoliBizOrder);
            //计算对司费用
            calculateIncome(srcOrder,baoliBizOrder);
            //计算对店费用
            calculateFeeRate(srcOrder,baoliBizOrder);

        }

        srcOrder.setUpdateTime(DateUtils.getNowDate());
        return baoliBizOrderMapper.updateBaoliBizOrder(baoliBizOrder);
    }

    /**
     * 计算对司费率，及 服务费
     * @date 2024/5/29 16:27
     * @Description
     */
    private void calculateIncome(BaoliBizOrder baoliBizOrder,BaoliBizOrder target){
        BaoliBizStore baoliBizStore = baoliBizStoreMapper.selectBaoliBizStoreById(baoliBizOrder.getStoreId());
        // 1 根据商户所属 城市 ，选取 对应的 对司费率规则
        List<BaoliBizCompanyFeeRateRule> feeRateRule = getCompanyFeeRateRule(baoliBizStore.getCityId());

        // 遍历对司费率，计算收入
        Double feeRate = 0d;
        if(feeRateRule!=null && feeRateRule.size() >0){
            for(int i=0;i<feeRateRule.size();i++){
                BaoliBizCompanyFeeRateRule rule = feeRateRule.get(i);
                boolean nextStep = false;
                //根据出单日
                if(rule.getExecuteWay().equals("01") && baoliBizOrder.getStatus().equals("06")){
                    nextStep = true;
                }
                //根据放款日
                else if(rule.getExecuteWay().equals("02") && baoliBizOrder.getStatus().equals("09")){
                    nextStep = true;
                }
                if(nextStep){
                    JSONObject ruleObj = JSONObject.parseObject(rule.getContent());
                    JSONArray mainRule = null;
                    if(ruleObj.get("standardRate")!=null){
                        // 标准
                        mainRule = ruleObj.getJSONArray("standardRate");
                        for(int j=0;j<mainRule.size();j++){
                            if(mainRule.getJSONObject(j).getString("periodNumber").equals(baoliBizOrder.getPeriodNumber())){
                                feeRate = mainRule.getJSONObject(j).getDouble("feeRate");
                            }
                            if(feeRate!=null && feeRate.longValue()!=0){
                                break;
                            }
                        }
                    }

                    if(ruleObj.get("interestMargin")!=null){
                        // 息差
                        mainRule = ruleObj.getJSONArray("interestMargin");
                        for(int j=0;j<mainRule.size();j++){
                            String period = baoliBizOrder.getPeriodNumber(); //期数
                            Double storeFeeRate = baoliBizOrder.getFeeRatio();//总费率
                            Double rebateRate = baoliBizOrder.getRebateRate();// 返佣比例
                            // 期数匹配、总费率匹配
                            if(mainRule.getJSONObject(j).getString("periodNumber").equals(period) && (mainRule.getJSONObject(j).getDouble("stagesInterestRate").longValue() == storeFeeRate.longValue())){
                                Double standFeeRate = mainRule.getJSONObject(j).getDouble("allServiceStandard");
                                feeRate = standFeeRate - rebateRate;
                                break;
                            }
                        }
                    }
                    if(ruleObj.get("highSigningRewards")!=null){
                        // 高谦
                        mainRule = ruleObj.getJSONArray("highSigningRewards");
                    }

                    if(mainRule!=null){
                        JSONArray bonus = ruleObj.getJSONArray("loanAmountData");
                        if(feeRate!=null){
                            //如果有 奖励规则
                            if(bonus!=null && bonus.size() > 0){
                                Double totalAmount =jdbcTemplate.queryForObject("select ifnull(sum(loan_amount),0) loan_amount from baoli_biz_order where  (year(now()) = year(submit_date)) and (month(submit_date) = month(now())) and city = ?",Double.class,baoliBizStore.getCityId());
                                for(int u=0;u<bonus.size();u++){
                                    JSONObject bonusItem = bonus.getJSONObject(u);
                                    Double bonusRate = 0d;
                                    String leftOperator = bonusItem.getString("leftOperator");
                                    String rightOperator = bonusItem.getString("rightOperator");
                                    String leftValue = bonusItem.getString("leftValue");
                                    String rightValue = bonusItem.getString("rightValue");
                                    boolean condTrue = false;
                                    if(leftValue!=null && !leftValue.equals("")){
                                        Double val = Double.parseDouble(leftValue);
                                        if(leftOperator!=null && leftOperator.equals("<")){
                                            condTrue = totalAmount > val;
                                        }
                                        if(leftOperator!=null && leftOperator.equals("<=")){
                                            condTrue = totalAmount >= val;
                                        }
                                    }

                                    if(condTrue){
                                        if(rightValue!=null && !rightValue.equals("")){
                                            Double val = Double.parseDouble(rightValue);
                                            if(rightOperator!=null && rightOperator.equals("<")){
                                                condTrue = totalAmount < val;
                                            }
                                            if(rightOperator!=null && rightOperator.equals("<=")){
                                                condTrue = totalAmount <= val;
                                            }
                                        }
                                    }
                                    if(condTrue){
                                        String bonusRateStr = bonusItem.getString("rewardRate");
                                        if(bonusRateStr!=null && !bonusRateStr.equals("")){
                                            bonusRate = bonusItem.getDouble("rewardRate");
                                            //绑定了期数
                                            if(bonusItem.getString("periodNumber")!=null && !bonusItem.getString("periodNumber").equals("")){
                                                if(!bonusItem.getString("periodNumber").equals(baoliBizOrder.getPeriodNumber())){
                                                    bonusRate =0d;
                                                }
                                            }
                                            feeRate += bonusRate;
                                            if(bonusRate!=0d){
                                                break;
                                            }
                                        }
                                    }
                                    //遍历所有订单，确认是否要升档 城市、期数
                                }
                            }
                        }
                        if(feeRate!=0d){
                            target.setIncomeRate(feeRate);
                            target.setIncome(baoliBizOrder.getLoanAmount() * feeRate / 100);
                            break;
                        }
                    }
                }
            }
        }
    }

    // 根据城市id 获取规则（对司）
    List<BaoliBizCompanyFeeRateRule> getCompanyFeeRateRule(String cityId){
        List<BaoliBizCompanyFeeRateRule> result = null;
        BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule = new BaoliBizCompanyFeeRateRule();
        baoliBizCompanyFeeRateRule.setCityId(cityId);
        result = baoliBizCompanyFeeRateRuleMapper.selectBaoliBizCompanyFeeRateRuleList(baoliBizCompanyFeeRateRule);
        return result;
    }
    /**
     * @author 计算返佣
     * @date 2024/5/29 16:26
     * @Description
     */
    private void calculateFeeRate(BaoliBizOrder baoliBizOrder,BaoliBizOrder target){
        if(baoliBizOrder.getRebateRate()!=null){
            target.setStoreFee(baoliBizOrder.getLoanAmount() * baoliBizOrder.getRebateRate() / 100);
        }
    }
    /***
     * 计算费用
     * @date 2024/5/29 10:41
     * @Description
     */
    private void calculateFee(BaoliBizOrder baoliBizOrder,BaoliBizOrder target){
        //1 根据商户获取费用规则
        BaoliBizStore baoliBizStore= baoliBizStoreMapper.selectBaoliBizStoreById(baoliBizOrder.getStoreId());
        //2 遍历规则，匹配 期数、费用类型
        if(baoliBizStore.getFeeSchema()!=null){
            JSONArray feeSchema = JSONArray.parse(baoliBizStore.getFeeSchema());
            for(int i=0;i<feeSchema.size();i++){
                JSONObject fee = feeSchema.getJSONObject(i);
                JSONArray rules = fee.getJSONArray("ruleIds");

                BaoliBizFeeRule feeRule =baoliBizFeeRuleMapper.selectBaoliBizFeeRuleById(rules.getLong(0));
                boolean nextStep = false;
                //根据出单日
                if(feeRule.getExecuteType().equals("01") && baoliBizOrder.getStatus().equals("06")){
                    nextStep = true;
                }
                //根据放款日
                else if(feeRule.getExecuteType().equals("02") && baoliBizOrder.getStatus().equals("09")){
                    nextStep = true;
                }
                Double feeValue = 0d;
                if(nextStep){
                    JSONObject ruleObject = JSONObject.parseObject(feeRule.getContent());
                    JSONObject referenceData = ruleObject.getJSONArray("referenceData").getJSONObject(0);
                    // 参考期数
                    if(referenceData.getString("isReferencePeriods").equals("1")){
                        JSONObject ruleInfo = ruleObject.getJSONArray("havePeriodsData").getJSONObject(0);
                        //比率参数
                        if(ruleInfo.getString("valueType").equals("2")){
                            //匹配期数有值，则计算
                            if(ruleInfo.getString(baoliBizOrder.getPeriodNumber())!=null){
                                double feeRate = ruleInfo.getDouble(baoliBizOrder.getPeriodNumber()) / 100;
                                feeValue = baoliBizOrder.getLoanAmount() * feeRate;
                                target.setFee1(feeValue);
                            }
                        }
                    }
                    // 不参考期数
                    else if(referenceData.getString("isReferencePeriods").equals("2")){
                        String leftOperator ="";
                        String rightOperator="";
                        String valueType = null;
                        Double leftNumber =0d;
                        Double rightNumber =0d;
                        Double targetValue =0d;
                        JSONArray ruleInfo = ruleObject.getJSONArray("feeRulesData");
                        for(int j=0;j<ruleInfo.size();j++){
                            JSONObject rule = ruleInfo.getJSONObject(j);
                            leftOperator =rule.getString("leftOperator");
                            rightOperator=rule.getString("rightOperator");
                            valueType = rule.getString("valueType");
                            leftNumber =rule.getDouble("leftNumber");
                            rightNumber =rule.getDouble("rightNumber");
                            targetValue =rule.getDoubleValue("targetValue");
                            boolean isTrue = false;
                            if(leftNumber!=null && leftNumber <= baoliBizOrder.getLoanAmount()){
                                isTrue = true;
                            }
                            if(rightNumber!=null && baoliBizOrder.getLoanAmount() <= rightNumber){
                                isTrue = true;
                            }
                            if(isTrue){
                                //金额
                                if(valueType.equals("1")){
                                    target.setFee1(targetValue);
                                }
                                //比率
                                else if(valueType.equals("2")){
                                    target.setFee1(baoliBizOrder.getLoanAmount() * targetValue /100);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public int batchUpdateOrder(Map<String, Object> baoliBizOrder) {
        return baoliBizOrderMapper.batchUpdateOrder(baoliBizOrder);
    }
    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizOrderByIds(Long[] ids)
    {
        return baoliBizOrderMapper.deleteBaoliBizOrderByIds(ids);
    }

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizOrderById(Long id)
    {
        return baoliBizOrderMapper.deleteBaoliBizOrderById(id);
    }
}
