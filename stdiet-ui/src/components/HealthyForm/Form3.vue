<template>
  <div>
    <p class="p_title_1">{{getTitle()}}</p>
    <p class="p_title_2">1、家庭调味品</p>
    <el-form-item :label="'(1) 调味品种类'" prop="condiment" class="margin-left">
      <el-checkbox-group v-model="form.condiment">
        <el-checkbox v-for="(item, index) in healthyData['condimentArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他调味品 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherCondiment" placeholder="请输入其他调味品名称" /></div>
    </el-form-item>
    <p class="p_title_2">2、喜好的烹调方式和周频次</p>
    <el-form-item :label="'(1) 喜好的烹调方式'" prop="cookingStyle" class="margin-left">
      <el-checkbox-group v-model="form.cookingStyle">
        <el-checkbox v-for="(item,index) in healthyData['cookingStyleArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item :label="'(2) 烹调方式的频次（每周）'" prop="cookingStyleRate" class="margin-left">
      <div>
        <span>{{healthyData['cookingStyleRateArray'][0]}}&nbsp;</span><el-input-number  style="width:35%" v-model="form.cookingStyleRate[0]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        <span style="margin-left: 20px;">{{healthyData['cookingStyleRateArray'][1]}}&nbsp;</span><el-input-number  style="width:35%" v-model="form.cookingStyleRate[1]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
      </div>
      <div style="margin-top: 5px;">
        <span>{{healthyData['cookingStyleRateArray'][2]}}&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[2]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        <span style="margin-left: 20px;">{{healthyData['cookingStyleRateArray'][3]}}&nbsp;</span><el-input-number   style="width:35%" v-model="form.cookingStyleRate[3]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
      </div>
      <div style="margin-top: 5px;">
        <span>{{healthyData['cookingStyleRateArray'][4]}}&nbsp;</span><el-input-number style="width:35%"  v-model="form.cookingStyleRate[4]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        <span style="margin-left: 20px;">{{healthyData['cookingStyleRateArray'][5]}}&nbsp;</span><el-input-number  style="width:35%;" v-model="form.cookingStyleRate[5]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
      </div>
    </el-form-item>
    <p class="p_title_2">3、洗菜方式</p>
    <el-form-item :label="'(1) 洗菜方式'" prop="washVegetablesStyle" class="margin-left">
      <el-checkbox-group v-model="form.washVegetablesStyle">
        <el-checkbox v-for="(item,index) in healthyData['washVegetablesStyleArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他洗菜方式 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherWashVegetablesStyle" placeholder="请输入其他洗菜方式" /></div>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";

export default {
  name: "Form3",
  components: {
  },
  data() {
    return {
      healthyData:healthyData
    };
  },
  methods: {
       getTitle(){
          return this.healthyData.getTitle(this.form.conditioningProjectId, 2);
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

