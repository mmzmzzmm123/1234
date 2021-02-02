<template>
  <div>
    <p class="p_title_1" style="margin-top: 10px;">{{healthyData['titleArray'][0]}}</p>
    <p style="font-size: 15px; margin-bottom: 12px;margin-top: 10px;">请您确认下方姓名、手机号是否正确</p>
    <el-form-item label="真实姓名" prop="name">
      <el-input v-model="form.name" :readonly="true" placeholder="请输入真实姓名" maxlength="20"/>
    </el-form-item>
    <el-form-item label="手机号" prop="phone" >
      <el-input type="number" :readonly="true" v-model="form.phone" placeholder="请输入手机号" />
    </el-form-item>
    <el-form-item label="性别" prop="sex">
      <el-radio-group v-model="form.sex" size="small" >
        <el-radio :label="parseInt('0')"  border>男</el-radio>
        <el-radio :label="parseInt('1')" border>女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="年龄" prop="age" >
      <el-input type="number" v-model="form.age" placeholder="请输入年龄" autocomplete="off" ></el-input>
    </el-form-item>
    <el-form-item label="身高（厘米）" prop="tall" >
      <el-input type="number" v-model="form.tall" placeholder="请输入身高" autocomplete="off" ></el-input>
    </el-form-item>
    <el-form-item label="体重（斤）" prop="weight" >
      <el-input type="number" v-model="form.weight" placeholder="请输入体重" autocomplete="off" ></el-input>
    </el-form-item>
    <el-form-item label="调理项目" prop="conditioningProjectId">
      <el-select v-model="form.conditioningProjectId" filterable clearable placeholder="请选择">
        <el-option
          v-for="dict in conditioningProjectIdOption"
          :key="dict.dictValue"
          :label="dict.dictLabel"
          :value="parseInt(dict.dictValue)"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="南方人还是北方人" prop="position">
      <el-radio-group v-model="form.position" style="margin-left: 10px;">
        <el-radio :label="0" key="1">南方</el-radio>
        <el-radio :label="1" key="2">北方</el-radio>
      </el-radio-group>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";
import { getDictData } from "@/api/custom/customerInvestigation";

export default {
  name: "HealthyForm",
  components: {
  },
  data() {
    return {
      healthyData:healthyData,
      conditioningProjectIdOption:[]
    };
  },
  methods: {
    getDict(type){
      getDictData(type).then(response => {
        this.conditioningProjectIdOption = response.data;
      });
    },
  },
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  created() {
    this.getDict("conditioning_project");
  },

};
</script>

<style>

</style>
