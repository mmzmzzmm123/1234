<template>
  <div>
    <p class="p_title_1">{{healthyData['titleArray'][4]}}</p>
    <p class="p_title_2">1、您每天的饮水习惯</p>
    <el-form-item label="(1) 每天饮水量（毫升）" prop="waterNum" class="margin-left">
      <el-input-number v-model="form.waterNum" :step="50" :min="0"></el-input-number>
    </el-form-item>
    <el-form-item label="(2) 喜欢喝什么水" prop="waterType" class="margin-left">
      <el-checkbox-group v-model="form.waterType">
        <el-checkbox v-for="(item,index) in healthyData['waterTypeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="(3) 喝水习惯" prop="waterHabit" class="margin-left">
      <el-checkbox-group v-model="form.waterHabit">
        <el-checkbox v-for="(item,index) in healthyData['waterHabitArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <p class="p_title_2">2、您常喝的饮品和每周频次</p>
    <el-form-item label="(1) 每周常喝的饮品次数" prop="drinksNum" class="margin-left">
      <div>
        <span>{{healthyData['drinksNumArray'][0]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 13px;" v-model="form.drinksNum[0]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinksNumArray'][1]}}&nbsp;</span><el-input-number style="width:35%;margin-left: 25px;" v-model="form.drinksNum[1]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinksNumArray'][2]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[2]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinksNumArray'][3]}}&nbsp;</span><el-input-number  type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[3]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinksNumArray'][4]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[4]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinksNumArray'][5]}}&nbsp;</span><el-input-number type="number" style="width:35%;" v-model="form.drinksNum[5]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinksNumArray'][6]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 13px;" v-model="form.drinksNum[6]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinksNumArray'][7]}}&nbsp;</span><el-input-number type="number" style="width:35%;" v-model="form.drinksNum[7]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
      </div>
    </el-form-item>
    <p class="p_title_2">3、您的饮酒习惯</p>
    <el-form-item label="(1) 是否喝酒" prop="drinkWineFlag" class="margin-left">
      <el-radio-group v-model="form.drinkWineFlag">
        <el-radio v-for="(item,index) in healthyData['drinkWineFlagArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="(2) 通常喝什么酒" prop="drinkWineClassify" class="margin-left">
      <el-checkbox-group v-model="form.drinkWineClassify">
        <el-checkbox v-for="(item,index) in healthyData['drinkWineClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他酒 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherWineClassify" placeholder="请输入其他酒名称" /></div>
    </el-form-item>
    <el-form-item label="(3) 通常喝多少" prop="drinkWineAmount" class="margin-left">
      <div>
        <span>{{healthyData['drinkWineAmountArray'][0]}}</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[0]" :step="1" :min="0"></el-input-number><span>&nbsp;两</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinkWineAmountArray'][1]}}</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[1]" :step="1" :min="0"></el-input-number><span>&nbsp;瓶</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['drinkWineAmountArray'][2]}}</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[2]" :step="100" :min="0"></el-input-number><span>&nbsp;毫升</span>
      </div>
      <!--<div class="margin-top-10">
        <span>其他</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[3]" :step="100" :min="0"></el-input-number><span>&nbsp;毫升</span>
      </div>-->
    </el-form-item>
    <p class="p_title_2">4、您的吸烟习惯</p>
    <el-form-item label="(1) 是否吸烟" prop="smokeFlag" class="margin-left">
      <el-radio-group v-model="form.smokeFlag">
        <el-radio :label="0" key="1">否</el-radio>
        <el-radio :label="1" key="2">是</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="(2) 吸烟频次以及烟龄" prop="smokeRate" class="margin-left">
      <div>
        <span>{{healthyData['smokeRateArray'][0]}}</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[0]" :step="1" :min="0"></el-input-number><span>次</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['smokeRateArray'][1]}}</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[1]" :step="1" :min="0"></el-input-number><span>年</span>
      </div>
      <div class="margin-top-10">
        <span>{{healthyData['smokeRateArray'][2]}}</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[2]" :step="1" :min="0"></el-input-number><span>年</span>
      </div>
    </el-form-item>
    <el-form-item label="(3) 是否常吸二手烟" prop="secondSmoke" class="margin-left">
      <el-radio-group v-model="form.secondSmoke">
        <el-radio :label="0" key="1">否</el-radio>
        <el-radio :label="1" key="2">是</el-radio>
      </el-radio-group>
    </el-form-item>
    <p class="p_title_2">4、您的工作状态</p>
    <el-form-item label="(1) 工作行业" prop="workIndustry" style="padding-top: 10px;">
      <el-input v-model="form.workIndustry" placeholder="请输入工作行业" />
    </el-form-item>
    <el-form-item label="(2) 工作性质" prop="workType" style="padding-top: 10px;">
      <el-checkbox-group v-model="form.workType">
        <el-checkbox v-for="(item,index) in healthyData['workTypeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <p class="p_title_2">4、您的排便状况</p>
    <el-form-item label="(1) 排便次数" prop="defecationNum" style="padding-top: 10px;">
      <!--<el-radio-group v-model="form.defecationNum">
        <el-radio :label="1" key="1">1次/天</el-radio>
        <el-radio :label="2" key="2">2次/天</el-radio>
        <el-radio :label="3" key="3">3次/天</el-radio>
      </el-radio-group>-->
      <div class="margin-top-10"><span>排便次数 </span><el-input-number style="width:35%;margin-right: 10px;" type="number" v-model="form.defecationNum" :step="1" :min="0"></el-input-number><span>次/天</span></div>
    </el-form-item>

    <el-form-item label="(2) 排便时间" prop="defecationTime" style="padding-top: 10px;">
      <el-checkbox-group v-model="form.defecationTime">
        <el-checkbox v-for="(item,index) in healthyData['defecationTimeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="(2) 排便情况" prop="defecationSituation" style="padding-top: 10px;">
      <div>
        <span>形状</span><el-input class="width-70-left-8-right-5" v-model="form.defecationShape" placeholder="请输入形状" />
      </div>
      <div class="margin-top-10">
        <span>气味</span><el-input class="width-70-left-8-right-5" v-model="form.defecationSmell" placeholder="请输入气味" />
      </div>
      <div class="margin-top-10">
        <span>速度</span><el-input class="width-70-left-8-right-5" v-model="form.defecationSpeed" placeholder="请输入速度" />
      </div>
      <div class="margin-top-10">
        <span>颜色</span><el-input class="width-70-left-8-right-5" v-model="form.defecationColor" placeholder="请输入颜色" />
      </div>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";

export default {
  name: "Form5",
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

