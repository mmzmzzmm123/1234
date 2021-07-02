<template>
  <section>
    <div style="padding: 5px; text-align: center">
      <img :src="logo" style="width: auto; height: 35px" alt="logo" />
    </div>
    <div style="margin: 10px 15px 10px 15px">
      <el-steps :active="stepActive" finish-status="success">
        <el-step
          v-for="(item, index) in stepArray"
          :key="index"
          title=""
        ></el-step>
      </el-steps>
    </div>
    <el-form
      ref="form"
      label-position="top"
      :model="form"
      :rules="rules"
      label-width="100px"
      style="padding: 16px"
    >
      <healthy-form1 v-show="stepArray[0]" :form.sync="form"></healthy-form1>
      <healthy-form2 v-show="stepArray[1]" :form.sync="form"></healthy-form2>
      <healthy-form3 v-show="stepArray[2]" :form.sync="form"></healthy-form3>
      <healthy-form4 v-show="stepArray[3]" :form.sync="form"></healthy-form4>
      <healthy-form5 v-show="stepArray[4]" :form.sync="form"></healthy-form5>
      <healthy-form6 v-show="stepArray[5]" :form.sync="form"></healthy-form6>
      <healthy-form7 v-show="stepArray[6]" :form.sync="form"></healthy-form7>
      <healthy-form8 v-show="stepArray[7]" :form.sync="form" @addNewDrugInput="addNewDrugInput"></healthy-form8>
      <healthy-extended v-show="stepArray[8]" :form.sync="form"></healthy-extended>
      <healthy-form9
        v-show="stepArray[9]"
        ref="fileForm"
        @addOrEditHealthy="addCustomerHealthy()"
        :form.sync="form"
      ></healthy-form9>
      <el-form-item style="text-align: center; margin: 30px auto">
        <el-button
          @click="nextStep(-1)"
          style="margin-right: 10px; width: 40%"
          v-show="stepActive != 0"
          >上一步</el-button
        >
        <el-button
          type="primary"
          @click="nextStep(1)"
          style="width: 40%"
          v-show="stepActive != stepArray.length - 1"
          >下一步</el-button
        >
        <el-button
          type="primary"
          @click="submit()"
          style="width: 40%"
          v-show="stepActive == stepArray.length - 1"
          >提交数据</el-button
        >
      </el-form-item>
    </el-form>
  </section>
</template>
<script>
import {
  getDictData,
  addCustomerHealthy,
  physicalSignsList,
  getCustomerBaseMessage,
} from "@/api/custom/customerInvestigation";
import * as healthyData from "@/utils/healthyData";
const logo = require("@/assets/logo/st_logo.png");
import Form1 from "@/components/HealthyForm/Form1";
import Form2 from "@/components/HealthyForm/Form2";
import Form3 from "@/components/HealthyForm/Form3";
import Form4 from "@/components/HealthyForm/Form4";
import Form5 from "@/components/HealthyForm/Form5";
import Form6 from "@/components/HealthyForm/Form6";
import Form7 from "@/components/HealthyForm/Form7";
import Form8 from "@/components/HealthyForm/Form8";
import Form9 from "@/components/HealthyForm/Form9";
import FormExtended from "@/components/HealthyForm/FormExtended";
export default {
  name: "index",
  data() {
    return {
      //客户是否存在标识
      customerExistFlag: false,
      healthyData: healthyData,
      logo,
      submitFlag: false,
      stepArray: [true, false, false, false, false, false, false, false, false, false],
      stepActive: 0,
      form: {
        customerEncId: null,
        name: null,
        phone: null,
        conditioningProjectId: 0,
        projectDisabledFlag: false,
        sex: 1,
        age: null,
        tall: null,
        weight: null,
        condiment: ["1", "2", "3"],
        otherCondiment: null,
        cookingStyle: ["8", "9", "4", "11"],
        cookingStyleRate: [1, 1, 1, 1, 1, 1],
        washVegetablesStyle: ["2"],
        otherWashVegetablesStyle: null,
        breakfastType: "2",
        breakfastFood: null,
        lunchType: ["3"],
        dinner: ["2"],
        vegetableRate: 5,
        commonMeat: null,
        dinnerTime: "19:00",
        supperNum: 1,
        supperFood: null,
        dietHotAndCold: "3",
        dietFlavor: ["1", "2"],
        vegetablesNum: 1,
        vegetablesRateType: "3",
        fruitsNum: 1,
        fruitsTime: "2",
        fruitsRate: "2",
        riceNum: 1,
        riceFull: 8,
        eatingSpeed: "3",
        snacks: ["1"],
        otherSnacks: null,
        healthProductsFlag: 0,
        healthProductsBrand: null,
        healthProductsName: null,
        healthProductsWeekRate: 0,
        healthProductsDayRate: 0,

        waterNum: 1500,
        waterType: ["3"],
        waterHabit: ["5"],
        drinksNum: [0, 0, 0, 0, 0, 0, 0, 0],
        drinkWineFlag: "3",
        drinkWineClassify: ["3"],
        otherWineClassify: null,
        drinkWineAmount: [0, 0, 0],
        smokeFlag: 0,
        smokeRate: [0, 0, 0],
        secondSmoke: 0,
        workIndustry: null,
        workType: ["2"],
        defecationNum: 1,
        otherDefecationNum: 0,
        defecationTime: ["1"],
        defecationShape: null,
        defecationSmell: null,
        defecationSpeed: null,
        defecationColor: null,

        motionNum: 3,
        motionDuration: 40,
        motionTime: "08:00",
        //motionMode:"有氧",
        aerobicMotionClassify: ["2"],
        anaerobicMotionClassify: [],
        anaerobicAerobicMotionClassify: [],
        otherMotionClassify: null,
        motionField: ["1"],
        otherMotionField: null,

        sleepTime: "23:00",
        sleepQuality: ["2"],
        sleepDrugFlag: 0,
        sleepDrug: null,
        stayupLateFlag: 0,
        stayupLateWeekNum: 0,

        physicalSignsId: [],
        otherPhysicalSigns: null,
        bloodData: [],
        moistureDate: [],
        familyIllnessHistory: [],
        otherFamilyIllnessHistory: null,
        operationHistory: [],
        otherOperationHistory: null,
        nearOperationFlag: 0,
        recoveryeSituation: null,
        longEatDrugFlag: 0,
        longEatDrugClassify: [],
        otherLongEatDrugClassify: null,
        allergyFlag: 0,
        allergySituation: null,
        allergen: [],
        otherAllergen: null,
        medicalReport: [],
        medicalReportName: [],
        position: 0,
        experience: null,
        rebound: 1,
        difficulty: null,
        crux: 1,
        dishesIngredient: null,
        makeFoodType: 3,

        healthyExtend:{
            //腰围
            waist: null,
            //臀围
            hipline: null,
            //常吃水果名称，['']
            eatFruitsNameArray:[],
            //常吃水果json数组.[{'name':'苹果', "num": '200克'}, {'name'：'西瓜','num': '两个'}]
            eatFruitsMessage:[],
            //长期服用药物JSON数组, [{'type': 1, 'drug':[{'name':'降压药1','num':'每天三粒','time':'早中晚'}]}]
            longEatDrugMessage:[],
            //服药依从性
            medicationCompliance: "1",
            //药物不良反应
            adverseReactionsFlag: 0,
            adverseReactions: null,
            //高血糖评估
            bloodSugarMessage: {
              beforeMealBloodSugar: null,
              afterMealBloodSugar: null,
              measureBloodSugarFlag: 0,
              measureBloodSugarTime: null,
              nearBloodSugar: null,
              lowBloodSugarFlag: 0,
              lowBloodSugarTime: null,
              complicationFlag: 0,
              complication: null,
              inferiorSymptomFlag: 0,
              inferiorSymptom: [],
              weightChangeFlag: 0,
              //糖化血红蛋白
              sugarHemoglobin: null,
              //体重变化描述
              weightChangeDescribe: null
            },
            //高血压评估
            bloodPressureMessage:{
              beforeMealBloodPressure: null,
              afterMealBloodPressure: null,
              measureBloodPressureFlag: 0,
              measureBloodPressureTime: null,
              nearBloodPressure: null,
              lowBloodPressureFlag: 0,
              lowBloodPressureTime: null,
              complicationFlag: 0,
              complication: null,
              inferiorSymptomFlag: 0,
              inferiorSymptom: [],
              weightChangeFlag: 0
            },
            //焦虑情绪评估
            anxietyStateMessage:{
                easyAnxiousFlag: 0,
                upsetRecently: 0,
                nervousOnSpecialOccasionsFlag: 0,
                terrifiedFlag:0
            },
            //郁抑情绪评估
            depressedStateMessage:{
                listlessRecentlyFlag: 0,
                cryRecentlyFlag:0,
                wakeUpEarlyRecentlyFlag:0,
                noFunLiving:0
            },
            //月经不调/多囊问卷信息
            menstruationMessage:{
                //月经周期（单位：天）
                menstrualCycle: null,
                //月经天数
                menstrualDays: null,
                //月经预估量
                menstrualForecast: null,
                //推迟天数
                menstrualDelayDays: null,
                //提前天数
                menstrualAdvanceDays: null,
                //是否闭经
                amenorrhoeaFlag: 0,
                //闭经时长
                amenorrhoeaDays: null,
                //颜色
                menstrualColor: null,
                //其他颜色
                otherMenstrualColor: null,
                //形状
                menstrualCharacter: null,
                //其他形状
                otherMenstrualCharacter: null,
                //是否痛经
                menstrualPainFlag: 0,
                //痛经性质
                menstrualNature: "0",
                //痛经类型
                menstrualType: "0",
                //是否用药
                medicationFlag: 0,
                //药物情况描述
                medication: null,
                //有无生育计划
                familyPlann: 0,
                //同房出血
                sameRoomBleed: 0,
                //排卵期出血
                ovulationBleed: 0,
                //是否出现胰岛素抵抗
                insulinResistanceFlag: 0,
                //其他补充描述
                otherDescriptions: null
            },
            //胃肠肿瘤信息
            gastrointestinalMessage:{
                //三个月体重变化描述
                weightChangeDescribe: null,
                //几碗饭、几个菜、几个汤
                riceNum: null,
                dishNum: null,
                soupNum: null,
                //一天食量
                dailyFoodIntake: null,
                //食欲
                appetite: null,
                otherAppetite: null,
                //睡觉时间、起床时间
                sleepTime: null,
                upTime: null,
                //午休时间
                lunchBreak: null,
                //是否化疗
                chemotherapyFlag: 0,
                chemotherapy: null,
                //食物不耐受
                foodIntoleranceFlag: 0,
                foodIntolerance: null
            },
            //产后调信息
            postpartumConditioningMessage:{
              //增长几斤
                weightGain: null,
                //产后几个月
                postpartumMonth: null,
                //生产状况
                productionStatus: null,
                otherProductionStatus: null,
                //孕期疾病
                pregnancyDisease: null,
                otherPregnancyDisease: null,
                //喂养方式
                feedingMethods: null,
                otherFeedingMethods: null,
                //乳汁状态
                milkState: null,
                otherMilkState: null,
                //产后症状
                postpartumSymptoms: [],
                //其他产后症状
                otherPostpartumSymptoms: null,
                //是否恢复月经
                resumeMenstruationFlag: 0,
                //生产几次
                productionNum: null,
                //流产次数
                abortionNum: null
            },
            //高血压（第二版）
            hypertensionMessage:{
                //现病史
                historyOfPresentIllness: null,
                maxHighPressure: null,
                maxLowPressure: null,
                recentTime: null,
                recentHighPressure: null,
                recentLowPressure: null,
                //静息心率
                restingHeartRate: null,
                measureBloodPressure: "1",

                totalCholesterol: null,
                //高密度脂蛋白胆固醇
                highLipoproteinCholesterol: null,
                //低密度脂蛋白胆固醇
                lowLipoproteinCholesterol: null,
                //甘油三酯
                triglyceride: null,
                //血糖
                bloodSugar: null,
                //尿常规
                urinalysis: null,
                //月经史
                menstrualHistory: null,
                //妊娠史
                historyOfPregnancy: null
            },
            //高血脂
            hyperlipidemiaMessage:{
                totalCholesterol: null,
                //高密度脂蛋白胆固醇
                highLipoproteinCholesterol: null,
                //低密度脂蛋白胆固醇
                lowLipoproteinCholesterol: null,
                //甘油三酯
                triglyceride: null,
                //肝功能
                liverFunction: null
            }
        }
      },
      timer: null,
      rules: {
        sex: [{ required: true, trigger: "blur", message: "请选择性别" }],
        age: [
          { required: true, trigger: "blur", message: "请填写年龄" },
          {
            required: true,
            trigger: "blur",
            pattern: /^[1-9]\d*$/,
            message: "年龄格式不正确",
          },
        ],
        tall: [
          { required: true, trigger: "blur", message: "请填写身高" },
          {
            required: true,
            trigger: "blur",
            pattern: /^[1-9]\d*$/,
            message: "身高格式不正确",
          },
        ],
        weight: [
          { required: true, trigger: "blur", message: "请填写体重" },
          {
            required: true,
            trigger: "blur",
            pattern: /^(\d+)(\.\d{1})?$/,
            message: "体重格式不正确",
          },
        ],
        conditioningProjectId: [
          { required: true, trigger: "blur", message: "请选择调理项目" },
        ],
        position: [
          { required: true, trigger: "blur", message: "请选择地理位置" },
        ],
      },
    };
  },
  components: {
    "healthy-form1": Form1,
    "healthy-form2": Form2,
    "healthy-form3": Form3,
    "healthy-form4": Form4,
    "healthy-form5": Form5,
    "healthy-form6": Form6,
    "healthy-form7": Form7,
    "healthy-form8": Form8,
    "healthy-form9": Form9,
    'healthy-extended':FormExtended
  },
  methods: {
    //根据用户ID获取用户基本信息（手机号、姓名）
    getCustomerBase(id) {
      if (id == null || id == undefined) {
        return;
      }
      getCustomerBaseMessage(id)
        .then((response) => {
          if (response.code === 200) {
            if (response.data) {
              this.customerExistFlag = true;
              this.form.name = response.data.name;
              this.form.phone = response.data.phone;
              this.form.conditioningProjectId = response.data.projectId == null ? 0 : response.data.projectId;
              if(response.data.projectId != null){
                 this.form.projectDisabledFlag = true;
              }
            }
          }
        })
        .catch(function () {
          console.log("error");
        });
    },
    submit() {
      if (this.submitFlag) {
        this.$message({
          message: "请勿重复提交，1分钟后重试",
          type: "warning",
        });
        return;
      }
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.submitFlag = true;
          this.timer = setTimeout(this.fail, 1000 * 60);
          this.$refs.fileForm.uploadFile();
        } else {
          this.$message({
            message: "数据未填写完整",
            type: "warning",
          });
        }
      });
    },
    addCustomerHealthy() {
      //数据处理
      let cusMessage = Object.assign({}, this.form);
      this.healthyData["arrayName"].forEach(function (item, index) {
        cusMessage[item] = cusMessage[item] != null ? cusMessage[item].join(",") : null;
      });
       let cusMessageExtended = Object.assign({}, this.form.healthyExtend);
      //处理healthyExtend扩展数据
      cusMessage.healthyExtend = this.healthyData.dealHealthyExtendJson(cusMessageExtended, false);
      addCustomerHealthy(cusMessage)
        .then((response) => {
          if (response.code === 200) {
            this.$notify({
              title: "提交成功",
              message: "",
              type: "success",
            });
          }
        })
        .catch(function () {
          console.log("error");
        });
    },
    fail() {
      // console.log("定时--------");
      this.submitFlag = false;
    },
    nextStep(step) {
      var reg = new RegExp(/^(\d+)(\.\d{1})?$/) ;
      if (!this.customerExistFlag) {
        this.$message.error("客户不存在");
        return;
      }
      this.$refs.form.validate((valid) => {
        if (valid || step < 0) {
          if(this.stepActive == 0){
              if((this.form.healthyExtend.waist != null && this.form.healthyExtend.waist.trim() != "")){
                  if(!reg.test(this.form.healthyExtend.waist)){
                   this.$message.error("腰围格式错误，整数或一位小数");
                   return;
                }
              }
              if((this.form.healthyExtend.hipline != null && this.form.healthyExtend.hipline.trim() != "")){
                  if(!reg.test(this.form.healthyExtend.hipline)){
                   this.$message.error("臀围格式错误，整数或一位小数");
                   return;
                }
              }
          }
          if((this.stepActive == 7 && step > 0) || (this.stepActive == 9 && step < 0)){
              //跳过慢病调理问卷
              if(this.healthyData.extendHealthyIndex.indexOf(this.form.conditioningProjectId) == -1){
                  step = step * 2;
              }
          }
          if((this.stepActive == 0 && step > 0) || (this.stepActive == 2 && step < 0)){
              //跳过减脂经历
              if(this.healthyData.notExperienceIndex.indexOf(this.form.conditioningProjectId) != -1){
                  step = step * 2;
              }
          }
          this.stepArray[this.stepActive] = false;
          this.stepActive = this.stepActive + step;
          this.stepArray[this.stepActive] = true;
          this.goTop();
        } else {
          this.$message({
            message: "数据未填写完整",
            type: "warning",
          });
        }
      });
    },
    goTop() {
      window.scroll(0, 0);
    },
    addNewDrugInput(type){
          let index = null;
          this.form.healthyExtend.longEatDrugMessage.forEach((v, i) => {
             if(v.type == type){
               index = i;
             }
          });
          this.form.healthyExtend.longEatDrugMessage[index].drug.push({'name':'','num':'','time':''});
    }
  },
  created() {
    this.form.customerEncId = this.$route.params.id;
    this.getCustomerBase(this.form.customerEncId);
  },
  beforeCreate() {
    document.title = this.$route.meta.title;
  },
  watch:{
    'form.healthyExtend.eatFruitsNameArray'(newArray, oldArray){
       oldArray = (oldArray == undefined || oldArray == null) ? [] : oldArray;
       newArray = (newArray == undefined || newArray == null) ? [] : newArray;
       if(newArray.length > oldArray.length){
          this.form.healthyExtend.eatFruitsMessage.push({'name': newArray[newArray.length-1], "num": ''});
       }else{
          let array = newArray.concat(oldArray).filter(function(v, i, arr) {
            return arr.indexOf(v) === arr.lastIndexOf(v);
          });
          this.form.healthyExtend.eatFruitsMessage = this.form.healthyExtend.eatFruitsMessage.filter(function(v, i, arr) {
            return array.indexOf(v.name) == -1;
          });
       }
    },
    'form.longEatDrugClassify'(newArray, oldArray){
        oldArray = (oldArray == undefined || oldArray == null) ? [] : oldArray;
        newArray = (newArray == undefined || newArray == null) ? [] : newArray;
        if(newArray.length > oldArray.length){
            this.form.healthyExtend.longEatDrugMessage.push({'type': newArray[newArray.length-1], 'drug': [{'name':'','num':'','time':''}]});
        }else{
            let array = newArray.concat(oldArray).filter(function(v, i, arr) {
              return arr.indexOf(v) === arr.lastIndexOf(v);
            });
            this.form.healthyExtend.longEatDrugMessage = this.form.healthyExtend.longEatDrugMessage.filter(function(v, i, arr) {
              return array.indexOf(v.name) == -1;
            });
        }
    }
  }
};
</script>

 <style scoped>
.el-form-item {
  margin-bottom: 8px;
}

.p_title_1 {
  font-size: 18px;
  font-weight: bold;
  margin-top: 30px;
}
.p_title_2 {
  font-size: 16px;
  font-weight: bold;
  margin-top: 30px;
}
.p_title_3 {
  font-size: 14px;
  font-weight: bold;
  margin-top: 30px;
}
.margin-left {
  margin-left: 14px;
}
.el-input__inner {
  width: 30%;
}
.margin-top-10 {
  margin-top: 10px;
}
.width-50-left-8-right-5 {
  width: 50%;
  margin-left: 8px;
  margin-right: 5px;
}
.width-70-left-8-right-5 {
  width: 70%;
  margin-left: 8px;
  margin-right: 5px;
}
</style>
