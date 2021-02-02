<template>
  <el-dialog v-if="visible" :visible.sync="visible" :title="title" width="500px" append-to-body @closed="onClosed">
    <div style="margin-top: -10px">
      <el-form ref="form" label-position="top" label-width="100px">
        <el-form-item :label="'请勾选需要修改的模块'" prop="showModuleArray">
          <el-checkbox-group v-model="showModuleArray" @change="changeShowModule()">
            <el-checkbox v-for="(item, index) in healthyData['titleArray']" :label="index" :key="index">{{item}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
    </div>
    <div style="height:400px;overflow: auto">
      <el-form ref="form" label-position="top" :model="form" :rules="rules" label-width="100px" style="padding: 16px">
        <healthy-form1 v-show="stepArray[0]" :flag="1" :form.sync="form"></healthy-form1>
        <healthy-form2 v-show="stepArray[1]" :form.sync="form"></healthy-form2>
        <healthy-form3 v-show="stepArray[2]" :form.sync="form"></healthy-form3>
        <healthy-form4 v-show="stepArray[3]" :form.sync="form"></healthy-form4>
        <healthy-form5 v-show="stepArray[4]" :form.sync="form"></healthy-form5>
        <healthy-form6 v-show="stepArray[5]" :form.sync="form"></healthy-form6>
        <healthy-form7 v-show="stepArray[6]" :form.sync="form"></healthy-form7>
        <healthy-form8 v-show="stepArray[7]" :flag="1" :form.sync="form"></healthy-form8>
        <edit-file v-show="stepArray[8]" ref="editFile" :form.sync="form"></edit-file>
        <el-form-item style="text-align: center; margin: 30px auto" >
          <el-button type="primary" @click="submit()" style="width: 40%" >提交</el-button>
          <el-button @click="onClosed()" style="width: 40%" >取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>
<script>
import { getCustomerPhysicalSignsByCusId,delCustomerHealthy } from "@/api/custom/customer";
import { updateHealthy } from "@/api/custom/healthy";
import * as healthyData from "@/utils/healthyData";
import Form1 from "@/components/HealthyForm/Form1";
import Form2 from "@/components/HealthyForm/Form2";
import Form3 from "@/components/HealthyForm/Form3";
import Form4 from "@/components/HealthyForm/Form4";
import Form5 from "@/components/HealthyForm/Form5";
import Form6 from "@/components/HealthyForm/Form6";
import Form7 from "@/components/HealthyForm/Form7";
import Form8 from "@/components/HealthyForm/Form8";
import Form9 from "@/components/HealthyForm/Form9";
import EditHealthyFile from "@/components/HealthyForm/EditHealthyFile";
export default {
  name: "PhysicalSignsEdit",
  components: {
    'healthy-form1': Form1,
    'healthy-form2': Form2,
    'healthy-form3': Form3,
    'healthy-form4': Form4,
    'healthy-form5': Form5,
    'healthy-form6': Form6,
    'healthy-form7': Form7,
    'healthy-form8': Form8,
    'healthy-form9': Form9,
    'edit-file':EditHealthyFile
  },
  data() {
    return {
      healthyData:healthyData,
      showModuleArray:[0],
      stepArray: [true,false,false,false,false,false,false,false,false],
      visible: false,
      title: "",
      data: undefined,
      form: {},
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
            pattern: /^[1-9]\d*$/,
            message: "体重格式不正确",
          },
        ],
        conditioningProjectId:[
          { required: true, trigger: "blur", message: "请选择调理项目" }
        ],
        position:[
          { required: true, trigger: "blur", message: "请选择地理位置" }
        ]
      }
    };
  },
  methods: {
    showDialog(data, customerHealthyData) {
      this.data = data;
      this.title = "修改"+`「${data.name}」`+"健康评估信息";
      let healthy = Object.assign({}, customerHealthyData);
      if(data && customerHealthyData){
        this.healthyData['arrayName'].forEach(function (item, index) {
          healthy[item] = healthy[item] ? healthy[item].split(",") : [];
        });
        this.form = healthy;
        this.visible = true;
      }else{
        this.$message.error('健康评估信息获取失败');
      }
      /*getCustomerPhysicalSignsByCusId(data.id).then((res) => {
        if (res.data.customerHealthy && res.data.type == 0) {
            let healthyObj = res.data.customerHealthy;
            this.healthyData['arrayName'].forEach(function (item, index) {
               healthyObj[item] = healthyObj[item] ? healthyObj[item].split(",") : [];
            });
            this.form = healthyObj;
            this.visible = true;
        }else{
           this.$message.error('健康评估信息获取失败');
        }
      });*/
    },
    onClosed() {
      this.visible = false;
      this.data = null;
      //this.form = null;
    },
    submit(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.editCustomerHealthy();
        } else {
          this.$message({message: "数据未填写完整", type: "warning"});
        }
      });
    },
    editCustomerHealthy(){
      this.$refs.editFile.addUploadNewFile();
      //数据处理
      let cusMessage = Object.assign({}, this.form);
      this.healthyData['arrayName'].forEach(function (item, index) {
        cusMessage[item] = cusMessage[item] != null ? cusMessage[item].join(",") : null;
      });
      updateHealthy(cusMessage).then((response) => {
        if (response.code === 200) {
          this.msgSuccess("修改成功");
          this.onClosed();
          this.$emit('refreshHealthyData');
        }
      }).catch(function() {
        console.log("error");
      });
    },
    changeShowModule(){
        console.log("---------------");
        for(var i = 0; i < this.stepArray.length; i++){
          let flag = this.showModuleArray.find((opt) => opt === i);
          console.log(flag != null && flag != undefined);
          this.$set(this.stepArray, i, (flag != null && flag != undefined));
        }
    }
  }
};
</script>

<style>
  .margin-top-20{
    margin-top:20px;
  }
  .p_title_1{
    font-size: 18px;
    font-weight: bold;
    margin-top: 20px;
  }
</style>
