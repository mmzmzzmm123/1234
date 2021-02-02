<template>
  <div>
    <p class="p_title_1">{{healthyData['titleArray'][5]}}</p>
    <p class="p_title_2">1、运动频率</p>
    <el-form-item label="(1) 每周运动情况" prop="motionSituation" class="margin-left">
      <div>
        <span>每周运动次数</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.motionNum" :step="1" :min="0"></el-input-number><span>次</span>
      </div>
      <div class="margin-top-10">
        <span>每次运动时长</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.motionDuration" :step="1" :min="0"></el-input-number><span>分钟</span>
      </div>
      <div class="margin-top-10">
        <span>每天运动时间</span><el-time-select class="width-50-left-8-right-5" v-model="form.motionTime" :picker-options="{ start: '00:00', step: '01:00', end: '24:00' }" placeholder="请选择时间" :editable="false"/><span>点</span>
      </div>
    </el-form-item>
    <p class="p_title_2">2、运动方式、项目</p>
    <!--<el-form-item label="(1) 运动方式" prop="motionMode" class="margin-left">
      <el-radio-group v-model="form.motionMode">
        <el-radio label="有氧" key="1">有氧</el-radio>
        <el-radio label="无氧" key="2">无氧</el-radio>
        <el-radio label="有氧无氧相结合" key="3">有氧无氧相结合</el-radio>
      </el-radio-group>
    </el-form-item>-->
    <el-form-item label="(1) 运动方式、项目" prop="motionProject" class="margin-left">
      <div><span>有氧运动</span>
        <el-checkbox-group v-model="form.aerobicMotionClassify">
          <el-checkbox v-for="(item,index) in healthyData['aerobicMotionClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </div>
      <div><span>无氧运动</span>
        <el-checkbox-group v-model="form.anaerobicMotionClassify">
          <el-checkbox v-for="(item,index) in healthyData['anaerobicMotionClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </div>
      <div><span>有氧无氧结合运动</span>
        <el-checkbox-group v-model="form.anaerobicAerobicMotionClassify">
          <el-checkbox v-for="(item,index) in healthyData['anaerobicAerobicMotionClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </div>
      <div><span>其他项目 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionClassify" placeholder="请输入其他运动项目名称" /></div>
    </el-form-item>
    <el-form-item label="(2) 运动场地" prop="motionField" class="margin-left">
      <el-checkbox-group v-model="form.motionField">
        <el-checkbox v-for="(item,index) in healthyData['motionFieldArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他场地 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionField" placeholder="请输入其他运动场地名称" /></div>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";

export default {
  name: "Form6",
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

