<template>
  <div>
    <p class="p_title_1">{{getTitle()}}</p>
    <p class="p_title_2">1、{{getTitleKey()}}经历</p>
    <el-form-item :label="'(1) 用过哪些'+getTitleKey()+'方法（重点）'" prop="experience" class="margin-left">
      <el-input
        type="textarea"
        :placeholder="'请描述'+getTitleKey()+'方法'"
        v-model="form.experience"
        maxlength="200"
        show-word-limit
        rows="3"
      ></el-input>
    </el-form-item>
    <el-form-item :label="'(2) '+getTitleKey()+'中遇到的困难（重点）'" prop="difficulty" class="margin-left">
      <el-input
        type="textarea"
        :placeholder="'请描述'+getTitleKey()+'中遇到的困难'"
        v-model="form.difficulty"
        maxlength="200"
        show-word-limit
        rows="3"
      ></el-input>
    </el-form-item>
    <el-form-item :label="'(3)'+getTitleKey()+'过程中是否反弹'" prop="rebound" class="margin-left">
      <el-radio-group v-model="form.rebound" style="margin-left: 10px;">
        <el-radio :label="0" key="1">否</el-radio>
        <el-radio :label="1" key="2">是</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item :label="'(4) 是否意识到生活习惯是'+getTitleKey()+'关键'" prop="crux" class="margin-left">
      <el-radio-group v-model="form.crux" style="margin-left: 10px;">
        <el-radio :label="0" key="1">否</el-radio>
        <el-radio :label="1" key="2">是</el-radio>
      </el-radio-group>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";

export default {
  name: "Form2",
  components: {
  },
  data() {
    return {
      healthyData:healthyData
    };
  },
  methods: {
      getTitle(){
          return this.healthyData.getTitle(this.form.conditioningProjectId, 1);
      },
      getTitleKey(){
         return this.healthyData.getTitleKey(this.form.conditioningProjectId);
      }
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

