<template>
  <div>
    <p class="p_title_1">{{healthyData['titleArray'][6]}}</p>
    <p class="p_title_2">1、您的睡眠状况</p>
    <el-form-item label="(1) 一般晚上几点睡" prop="sleepTime" class="margin-left">
      <el-time-select v-model="form.sleepTime" :picker-options="{ start: '00:00', step: '01:00', end: '24:00' }" placeholder="请选择时间" :editable="false"/>
    </el-form-item>
    <el-form-item label="(2) 睡眠质量" prop="sleepQuality" class="margin-left">
      <el-checkbox-group v-model="form.sleepQuality">
        <el-checkbox v-for="(item,index) in healthyData['sleepQualityArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <p class="p_title_2">2、辅助入睡药物情况</p>
    <el-form-item label="(1) 是否有辅助入睡药物" prop="sleepDrugFlag" class="margin-left">
      <el-radio-group v-model="form.sleepDrugFlag">
        <el-radio :label="0" key="1">无</el-radio>
        <el-radio :label="1" key="2">有</el-radio>
      </el-radio-group>
      <div><span>辅助药物名称 </span><el-input style="margin-top: 5px;" v-model="form.sleepDrug" placeholder="请输入辅助入睡药物名称" /></div>
    </el-form-item>
    <p class="p_title_2">3、熬夜情况</p>
    <el-form-item label="(1) 是否经常熬夜（超11:00）" prop="stayupLateFlag" class="margin-left">
      <el-radio-group v-model="form.stayupLateFlag">
        <el-radio :label="0" key="1">无</el-radio>
        <el-radio :label="1" key="2">有</el-radio>
      </el-radio-group>
      <div class="margin-top-10">
        <span>熬夜频率 </span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.stayupLateWeekNum" :step="1" :min="0"></el-input-number><span>次/周</span>
      </div>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";

export default {
  name: "Form7",
  components: {
  },
  data() {
    return {
      healthyData:healthyData
    };
  },
  methods: {

  },
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  created() {

  },

};
</script>

<style scoped>

  .el-form-item {
    margin-bottom: 8px;
  }

  .p_title_1{
    font-size: 18px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_2{
    font-size: 16px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_3{
    font-size: 14px;
    font-weight: bold;
    margin-top: 30px;
  }
  .margin-left{
    margin-left: 14px;
  }
  .el-input__inner{
    width: 30%;
  }
  .margin-top-10{
    margin-top: 10px;
  }
  .width-50-left-8-right-5{
    width:50%;
    margin-left: 8px;
    margin-right: 5px;
  }
  .width-70-left-8-right-5{
    width:70%;
    margin-left: 8px;
    margin-right: 5px;
  }


</style>

