<template>
  <div>
  <section v-show="!submitSuccessFlag">
    <div style="padding: 5px; text-align: center;margin-top:5px">
      <img :src="logo" style="width: auto; height: 40px" alt="logo" />
    </div>
    <el-form
      ref="form"
      label-position="top"
      :model="form"
      :rules="rules"
      label-width="100px"
      style="padding: 16px"
    >
    <p class="p_title_1 text-span-title" style="margin-top: 0px;" align="center">胜唐体控基础信息问卷表</p>
    <!--<p style="font-size: 15px; margin-bottom: 12px;margin-top: 10px;">请您确保下方姓名、手机号正确</p>-->
    <el-form-item label="1、真实姓名" prop="name" style="margin-top: 2px;">
      <el-input v-model="form.name" placeholder="请输入真实姓名" maxlength="20" />
    </el-form-item>
    <el-form-item label="2、手机号" prop="phone" >
      <el-input v-model="form.phone" type="number" placeholder="请输入手机号" />
    </el-form-item>
    <el-form-item label="3、性别" prop="sex">
      <el-radio-group  v-model="form.sex" size="small">
        <el-radio :label="parseInt('0')"  border>男</el-radio>
        <el-radio :label="parseInt('1')" border>女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="4、年龄" prop="age" >
      <el-input type="number" v-model="form.age" placeholder="请输入年龄（整数）"  autocomplete="off" ></el-input>
    </el-form-item>
    <el-form-item label="5、身高（厘米）" prop="tall" >
      <el-input type="number" v-model="form.tall" placeholder="请输入身高（整数）"  autocomplete="off" ></el-input>
    </el-form-item>
    <el-form-item label="6、体重（斤）" prop="weight" >
      <el-input v-model="form.weight" placeholder="请输入体重（可保留一位小数）" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="7、职业" prop="occupation">
      <el-input placeholder="请输入职业名称"  v-model="form.occupation" maxlength="50"
      ></el-input>
    </el-form-item>
    <el-form-item label="8、病史体征(可多选)" prop="physicalSignsIdArray" >
      <el-select v-model="form.physicalSignsIdArray" multiple placeholder="请选择">
        <el-option 
          v-for="physicalSign in physicalSignsList"
          :key="physicalSign.dictValue"
          :label="physicalSign.dictLabel"
          :value="physicalSign.dictValue"
        >
        </el-option>
      </el-select>
      <div><span class="text-span">其他病史体征</span>
        <el-input type="textarea"
                  placeholder="请输入其他病史体征，使用，隔开"
                  v-model="form.otherPhysicalSigns"
                  maxlength="200"
                  show-word-limit
                  rows="2"
        ></el-input>
      </div>
    </el-form-item>
    <el-form-item label="9、作息时间" >
        <div class="margin-left">
            <span class="text-span">睡觉时间</span>
            <el-input placeholder="请输入睡觉时间" maxlength="20"  v-model="form.timeTableArray[0]" style="width:60%;margin-left:10px"/>
        </div>          
        <div class="margin-left" style="margin-top:8px;">
            <span class="text-span">起床时间</span>
            <el-input placeholder="请输入起床时间" maxlength="20"  v-model="form.timeTableArray[1]" style="width:60%;margin-left:10px"/>
        </div>
    </el-form-item>
    <el-form-item label="10、减脂经历" prop="experience" >
      <el-input
        type="textarea"
        placeholder="请描述下减脂经历"
        v-model="form.experience"
        maxlength="200"
        show-word-limit
        rows="3"
      ></el-input>
    </el-form-item>
    <el-form-item label="11、湿气测试（可多选）" prop="moistureDataArray" >
      <el-checkbox-group v-model="form.moistureDataArray" >
        <el-checkbox v-for="moistureItem in moistureDataList" style="width:100%" :label="moistureItem.dictValue" :key="moistureItem.dictValue">{{ moistureItem.dictLabel }}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="12、气血测试（可多选）" prop="bloodDataArray" >
      <el-checkbox-group v-model="form.bloodDataArray" >
        <el-checkbox v-for="bloodItem in bloodDataList"  style="width:100%" :label="bloodItem.dictValue" :key="bloodItem.dictValue">{{ bloodItem.dictLabel }}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item style="text-align: center; margin: 0 auto">
        <el-button
          type="primary"
          @click="addCustomerSurvey()"
          style="width:80%"
          :disabled="submitFlag"
          >填写完成，提交问卷</el-button
        >
      </el-form-item>
    </el-form>
    
  </section>
  <SimpleSurveyReport ref="simpleSurveyReportRef"></SimpleSurveyReport>
  </div>
</template>
<script>
import {
  getDictData,
  getCustomerSurvey,
  addSimpleCustomerSurvey
} from "@/api/custom/customerInvestigation";
const logo = require("@/assets/logo/st_logo.png");
import SimpleSurveyReport from "@/components/SimpleSurveyReport"
export default {
  name: "index",
  data() {
    return {
      logo,
      submitFlag: false,
      submitSuccessFlag: false,
      moistureDataList:[],
      bloodDataList:[],
      physicalSignsList:[],
      form:{
        customerKey: null,
        name: "",
        phone: "",
        sex: 1,
        age: null,
        tall: null,
        weight: null,
        physicalSignsIdArray:[],
        physicalSignsId: "",
        otherPhysicalSigns: "",
        timeTableArray:["",""],
        timeTable: "",
        experience: "",
        occupation: "",
        bloodDataArray: [],
        bloodData: "",
        moistureDataArray: [],
        moistureData: "",
      },
      rules: {
        name: [{ required: true, trigger: "blur", message: "请填写姓名" }],
        phone: [
            //{ required: false, trigger: "blur", message: "请选择手机号" },
            {
                required: false,
                trigger: "blur",
                pattern: /^\d{5,11}$/ ,
                message: "手机号格式不正确",
            }
        ],
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
        ]
      }
    };
  },
  components: {
    SimpleSurveyReport
  },
  methods: {
    addCustomerSurvey(){
        this.submitFlag = true;
        this.form.physicalSignsId =  this.form.physicalSignsIdArray.join(",");
        this.form.timeTable = this.form.timeTableArray.join(",");
        this.form.bloodData = this.form.bloodDataArray.join(",");
        this.form.moistureData = this.form.moistureDataArray.join(",");
        this.$refs.form.validate((valid) => {
          if (valid){
              addSimpleCustomerSurvey(this.form).then((response) => {
                if(response.code == 200){
                    this.goTop();
                    this.submitFlag = true;
                    this.form.standardWeight = response.data;
                    let self = this;
                    this.$notify({
                        title: "提交成功，正在生成报告...",
                        message: "",
                        type: "success",
                        duration: "1000",
                        onClose: function(){
                            self.submitSuccessFlag = true;
                            self.$refs.simpleSurveyReportRef.showReportView(self.form, self.moistureDataList, self.bloodDataList, self.physicalSignsList);
                        }
                    });
                }else{
                    this.submitFlag = false;
                }         
              });
          }else{
            this.$message({
              message: "数据未填写完整",
              type: "warning",
            });
            this.submitFlag = false;
          }
        })
    },
    goTop() {
      window.scroll(0, 0);
    },
    //获取湿气
    getMoistureDictData() {
      getDictData("moisture_data_extended").then((response) => {
          this.moistureDataList = response.data;
        });
    },
    //获取气血
    getBloodDictData() {
        getDictData("blood_data_extended").then((response) => {
          this.bloodDataList = response.data;
        });
    },
    /** 查询体征列表 */
    getPhysicalSignsList() {
        getDictData("simple_sign_type").then((response) => {
          this.physicalSignsList = response.data;
        });
    }
  },
  created() {
    //this.form.customerKey = this.$route.params.customerKey;
    //this.getCustomerSurvey(this.form.customerKey);
    this.getPhysicalSignsList();
    this.getMoistureDictData();
    this.getBloodDictData();
  },
  beforeCreate() {
    document.title = this.$route.meta.title;
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
.text-span{
    color:#606266;
    font-weight: 700;
    font-size: 14px
  }

  .text-span-title{
    color:#606266;
    font-weight: 800;
    font-size: 16px
  }

  .report_div{
      width: 100%;
      padding-top: 25px;
      padding-bottom: 200px;
      background: linear-gradient(30deg, #4B8AFF, #329FFE);
  }

  .report_title_div{
      padding: 25px 15px;
      width: 90%;
      margin:0 auto;
      background: #FFFFFF;
      box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.1);
      border-radius: 20px;
  }

  .report_title{
      margin:0 auto;
      text-align: center;
      font-size: 20px;
      font-family: Source Han Sans CN;
      font-weight: 500;
      color: #4B8AFF;
  }

  
  .report_weight{
      margin-top: 25px;
      border-top:2px solid #F1F1F1;
      border-bottom:2px solid #F1F1F1;
      padding: 0px 5%
  }

  .weight_name{
      font-size: 18px;
      font-family: Source Han Sans CN;
      font-weight: 500;
      color: #4B8AFF;
      line-height: 50px;
      margin-right:5px;
  }

  .weight_value{
    font-size: 18px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    color: #333333;
    line-height: 50px;
  }

  .sign_div_title{
      font-size: 18px;
    font-family: Source Han Sans CN;
    font-weight: 500;
    color: #4B8AFF;
    line-height: 40px;
    margin-top:10px;
  }

  .sign_div_title_small{
    font-size: 15px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    color: #999999;
    line-height: 20px;
  }

  .sign_type{
      margin-top:10px;
      padding-bottom:20px;
      border-bottom:2px solid #F1F1F1;
  }

  .sign_type_div{
    height: 30px;
    background: #F1F1F1;
    border-radius: 24px;
    text-align: center;
    margin:10px 5px;
    padding: 0px 12px;
    width:auto; 
    display:inline-block !important; 
    display:inline; 
  }

  .sign_type_div_value{
    font-size: 15px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    color: #333333;
    line-height: 30px;
  }

  .blood_moisture{
      margin-top: 20px;
  }

  .blood_moisture_title{
      float: left;
      font-size: 16px;
     font-family: Source Han Sans CN;
     font-weight: 500;
    color: #4B8AFF;
    line-height: 30px;
  }

  .blood_moisture_value{
       margin-left:10px;
       float: left;
       width: 220px;
       height:30px;
       border-radius: 20px;
  }

  .clear{ clear:both} 

  .light{
      background: #20d866;
  }

  .moderate{
      background: #f0a70b;
  }

  .severe{
      background: #e94545;
  }

  body{
    width: 100%;

    height: 100%;

    background-color: #20d866;

}

  

 

  

 

 
 
  
</style>
