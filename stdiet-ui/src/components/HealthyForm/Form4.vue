<template>
  <div>
    <p class="p_title_1">{{getTitle()}}</p>
    <p class="p_title_2">1、您三餐的习惯</p>
    <el-form-item :label="'(1) 早餐习惯'" prop="breakfast" class="margin-left">
      <el-radio-group v-model="form.breakfastType">
        <el-radio v-for="(item,index) in healthyData['breakfastTypeArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
      </el-radio-group>
      <div style="margin-top:8px;"><span>早餐通常吃&nbsp;</span><el-input  v-model="form.breakfastFood" style="width:70%" placeholder="请输入早餐名称" /></div>
    </el-form-item>
    <el-form-item :label="'(2) 午餐习惯'" prop="lunchType" class="margin-left">
      <el-checkbox-group v-model="form.lunchType">
        <el-checkbox v-for="(item,index) in healthyData['lunchTypeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item :label="'(3) 晚餐习惯'" prop="dinner" class="margin-left">
      <el-checkbox-group v-model="form.dinner">
        <el-checkbox v-for="(item,index) in healthyData['dinnerArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item :label="'(4) 正餐荤素中素菜占比几成'" prop="eatSituation" class="margin-left">
      <el-input-number v-model="form.vegetableRate" :step="1" :min="0" :max="10"></el-input-number>
    </el-form-item>
    <el-form-item :label="'(4) 最常吃的肉类'" prop="eatSituation" class="margin-left">
      <el-input v-model="form.commonMeat" placeholder="请输入肉类名称"></el-input>
    </el-form-item>
    <p class="p_title_2">2、您晚餐时间点和夜宵习惯</p>
    <el-form-item :label="'(1) 晚餐时间点'" prop="dinnerTime" class="margin-left">
      <el-time-select v-model="form.dinnerTime" :picker-options="{ start: '17:00', step: '00:30', end: '24:00' }" placeholder="请选择时间" :editable="false"/>
    </el-form-item>
    <el-form-item :label="'(2) 您每周吃几次夜宵'" prop="supperNum" class="margin-left">
      <el-input-number v-model="form.supperNum" :step="1" :min="0"></el-input-number>
    </el-form-item>
    <el-form-item :label="'(3) 您夜宵通常吃的食物'" prop="supperFood" class="margin-left">
      <el-input style="width:100%" v-model="form.supperFood" placeholder="请输入食物名称"></el-input>
    </el-form-item>
    <p class="p_title_2">3、您的饮食偏好</p>
    <el-form-item :label="'(1) 冷热偏好'" prop="dietHotAndCold" class="margin-left">
      <el-radio-group v-model="form.dietHotAndCold">
        <el-radio v-for="(item,index) in healthyData['dietHotAndColdArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item :label="'(2) 口味偏好'" prop="dietFlavor" class="margin-left">
      <el-checkbox-group v-model="form.dietFlavor">
        <el-checkbox v-for="(item,index) in healthyData['dietFlavorArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <p class="p_title_2">4、生食果蔬状况</p>
    <el-form-item :label="'(1) 平均每周吃生蔬菜几次'" prop="vegetablesNum" class="margin-left">
      <el-input-number v-model="form.vegetablesNum" :step="1" :min="0"></el-input-number>
    </el-form-item>
    <el-form-item :label="'(2) 每周吃生蔬菜的频次'" prop="vegetablesRateType" class="margin-left">
      <el-radio-group v-model="form.vegetablesRateType">
        <el-radio v-for="(item,index) in healthyData['vegetablesRateTypeArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item :label="'(3) 平均每天吃水果几次以及时间'" prop="fruitsNum" class="margin-left">
      <el-input-number v-model="form.fruitsNum" :step="1" :min="0"></el-input-number>
      <el-radio-group v-model="form.fruitsTime" style="margin-top: 15px;">
        <el-radio v-for="(item,index) in healthyData['fruitsTimeArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="(4) 平时吃水果的频次" prop="fruitsRate" class="margin-left">
      <el-radio-group v-model="form.fruitsRate">
        <el-radio v-for="(item,index) in healthyData['fruitsRateArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
      </el-radio-group>
    </el-form-item>
    
    <el-form-item label="(5) 常吃什么水果以及对应份量" prop="eatFruitsNameArray" class="margin-left">
        <el-select v-model="form.healthyExtend.eatFruitsNameArray" multiple filterable allow-create default-first-option placeholder="请选择水果种类（可手动输入再选择）" style="width:90%">
          <el-option
            v-for="item in healthyData['eatFruitsArray']"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
        <div style="margin-top:10px" v-for="item in form.healthyExtend.eatFruitsMessage" :key="item.name">
            <el-input v-model="item.name" :disabled="true" placeholder="水果名称" style="width:37%"></el-input><el-input style="width:50%;margin-left:3%;" v-model="item.num" placeholder="份量描述"></el-input>
        </div>
    </el-form-item>
    
    <p class="p_title_2">5、饮食习惯</p>
    <el-form-item label="(1) 一餐吃几碗饭，几成饱" prop="riceNum" class="margin-left">
      <div>
        <el-input-number style="width:35%" v-model="form.riceNum" :step="1" :min="0"></el-input-number><span>&nbsp;碗饭</span>
        <el-input-number style="width:35%;margin-left: 20px;" v-model="form.riceFull" :step="1" :min="0" ></el-input-number><span>&nbsp;成饱</span>
      </div>
    </el-form-item>
    <el-form-item label="(2) 吃饭速度" prop="eatingSpeed" class="margin-left">
      <el-radio-group v-model="form.eatingSpeed">
        <el-radio v-for="(item,index) in healthyData['eatingSpeedArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="(3) 饮食特点" prop="makeFoodType" class="margin-left">
      <el-select v-model="form.makeFoodType" placeholder="请选择">
        <el-option
          v-for="item in healthyData['makeFoodTypeArray']"
          :key="item.value"
          :label="item.name"
          :value="parseInt(item.value)"
        />
      </el-select>
    </el-form-item>

    <p class="p_title_2">6、您常吃的零食</p>
    <el-form-item label="(1) 常吃的零食" prop="snacks" class="margin-left">
      <el-checkbox-group v-model="form.snacks">
        <el-checkbox v-for="(item,index) in healthyData['snacksArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他零食 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherSnacks" placeholder="请输入其他零食名称" /></div>
    </el-form-item>
    <p class="p_title_2">7、服用营养保健品状况</p>
    <el-form-item label="(1) 有无服用营养保健" prop="healthProductsFlag" class="margin-left">
      <el-radio-group v-model="form.healthProductsFlag">
        <el-radio :label="0" key="1">无</el-radio>
        <el-radio :label="1" key="2">有</el-radio>
      </el-radio-group>
      <div><span>营养保健品牌名 </span><el-input style="margin-top: 10px;" v-model="form.healthProductsBrand" placeholder="请输入营养保健品牌名" /></div>
      <div><span>营养保健产品名 </span><el-input style="margin-top: 10px;" v-model="form.healthProductsName" placeholder="请输入营养保健产品名" /></div>
    </el-form-item>
    <el-form-item label="(2) 服用营养保健频率" prop="healthProductsRate" class="margin-left">
      <div>
        <el-input-number style="width:35%" v-model="form.healthProductsWeekRate" :step="1" ::min="0"></el-input-number><span>&nbsp;次/周</span>
        <el-input-number style="width:35%;margin-left: 20px;" v-model="form.healthProductsDayRate" :step="1" ::min="0" ></el-input-number><span>&nbsp;次/天</span>
      </div>
    </el-form-item>
    <p class="p_title_2">8、忌口或过敏食物</p>
    <el-form-item label="(1) 忌口或过敏、不爱吃的食物（重点）" prop="dishesIngredient" class="margin-left">
      <el-input
        type="textarea"
        placeholder="请描述忌口或过敏、不爱吃的食物"
        v-model="form.dishesIngredient"
        maxlength="200"
        show-word-limit
        rows="3"
      ></el-input>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";

export default {
  name: "Form4",
  components: {
  },
  data() {
    return {
      healthyData:healthyData
    };
  },
  methods: {
      getTitle(){
          return this.healthyData.getTitle(this.form.conditioningProjectId, 3);
       },
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

