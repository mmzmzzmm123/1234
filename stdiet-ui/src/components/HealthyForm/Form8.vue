<template>
  <div>
    <p class="p_title_1">{{getTitle()}}</p>
    <p class="p_title_2">1、本人病史情况</p>
    <el-form-item label="(1) 病史体征(可多选)" prop="physicalSignsId" class="margin-left">
      <el-select v-model="form.physicalSignsId" multiple placeholder="请选择">
        <el-option
          v-for="physicalSign in physicalSignsList"
          :key="physicalSign.id"
          :label="physicalSign.name"
          :value="physicalSign.id+''"
        >
        </el-option>
      </el-select>
      <div><span>其他病史体征</span>
        <el-input type="textarea"
                  placeholder="请输入病史体征"
                  v-model="form.otherPhysicalSigns"
                  maxlength="200"
                  show-word-limit
                  rows="2"
        ></el-input>
      </div>
    </el-form-item>
    <p class="p_title_2">2、湿气、气血测试</p>
    <el-form-item label="(1) 湿气测试（可多选）" prop="moistureDate" class="margin-left">
      <el-checkbox-group v-model="form.moistureDate">
        <el-checkbox v-for="moistureItem in moistureDataList" :label="moistureItem.dictValue" :key="moistureItem.dictValue">{{ moistureItem.dictLabel }}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="(2) 气血测试（可多选）" prop="bloodData" class="margin-left">
      <el-checkbox-group v-model="form.bloodData">
        <el-checkbox v-for="moistureItem in bloodDataList" :label="moistureItem.dictValue" :key="moistureItem.dictValue">{{ moistureItem.dictLabel }}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>

    <p class="p_title_2">3、家族疾病史情况</p>
    <el-form-item label="（1）家族疾病史（直系亲属例如爸爸妈妈、爷爷奶奶、外公外婆有相关疾病）（可多选）" prop="familyIllnessHistory" class="margin-left">
      <el-checkbox-group v-model="form.familyIllnessHistory">
        <el-checkbox v-for="(item, index) in healthyData['familyIllnessHistoryArray']" :key="index" :label="item.value" >{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他家族病史</span>
        <el-input
          type="textarea"
          placeholder="请输入其他家族病史"
          v-model="form.otherFamilyIllnessHistory"
          maxlength="200"
          show-word-limit
          rows="3"
        ></el-input>
      </div>
    </el-form-item>
    <p class="p_title_2">4、手术情况</p>
    <el-form-item label="(1) 手术史，因病进行过手术治疗，手术的部分（可多选）" prop="familyIllnessHistory" class="margin-left">
      <el-checkbox-group v-model="form.operationHistory">
        <el-checkbox v-for="(item, index) in healthyData['operationHistoryArray']" :key="index" :label="item.value" >{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他手术史以及手术时间</span>
        <el-input
          type="textarea"
          placeholder="请输入其他手术史以及手术时间"
          v-model="form.otherOperationHistory"
          maxlength="200"
          show-word-limit
          rows="3"
        ></el-input></div>
    </el-form-item>
    <el-form-item label="(2) 近期是否做过手术" prop="nearOperation" class="margin-left">
      <el-radio-group v-model="form.nearOperationFlag">
        <el-radio :label="0" key="1">否</el-radio>
        <el-radio :label="1" key="2">是</el-radio>
      </el-radio-group>
      <div><span>手术恢复情况</span>
        <el-input
          type="textarea"
          placeholder="请输入恢复情况"
          v-model="form.recoveryeSituation"
          maxlength="200"
          show-word-limit
          rows="3"
        ></el-input>
      </div>
    </el-form-item>
    <p class="p_title_2">5、药物情况</p>
    <el-form-item label="(1) 是否长期服用药物（连续服用6个月以上，平均每日服用一次）" prop="longEatDrugFlag" class="margin-left">
      <el-radio-group v-model="form.longEatDrugFlag">
        <el-radio :label="0" key="1">否</el-radio>
        <el-radio :label="1" key="2">是</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="(2) 长期服用药物以及对应药物信息（可多选）" prop="longEatDrugClassify" class="margin-left">
      <el-checkbox-group v-model="form.longEatDrugClassify">
        <el-checkbox v-for="(item, index) in healthyData['longEatDrugClassifyArray']" :key="index" :label="item.value">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div style="margin-top:2px" v-for="item in form.healthyExtend.longEatDrugMessage" :key="item.type">
            <div><span>{{getDrugTypeName(item.type)}}</span><el-button type="text" size="normal" style="margin-left:5px" @click="addNewDrugInput(item.type)">新增</el-button></div>
            <div v-for="(it,index) in item.drug" :key="index" :style="index == 0 ? '' : 'margin-top:10px'">
            <el-input placeholder="药物名称" v-model="it.name" style="width:33%"></el-input><el-input style="width:30%;margin-left:3%;" v-model="it.num" placeholder="用量"></el-input><el-input style="width:30%;margin-left:3%;" placeholder="服用时间" v-model="it.time"></el-input>
            </div>
      </div>
      <div  style="margin-top:10px"><span>其他长期服用的药物（药物名称、用量、服用时间）</span>
        <el-input
          type="textarea"
          placeholder="请输入其他药物名称、用量、服用时间"
          v-model="form.otherLongEatDrugClassify"
          maxlength="200"
          show-word-limit
          rows="3"
        ></el-input>
      </div>
    </el-form-item>
    <p class="p_title_2">6、过敏史</p>
    <el-form-item label="(1) 曾经是否出现过过敏" prop="allergyFlag" class="margin-left">
      <el-radio-group v-model="form.allergyFlag">
        <el-radio :label="0" key="1">无</el-radio>
        <el-radio :label="1" key="2">有</el-radio>
      </el-radio-group>
      <div><span>过敏症状</span>
        <el-input
          type="textarea"
          placeholder="请输入过敏症状"
          v-model="form.allergySituation"
          maxlength="200"
          show-word-limit
          rows="3"
        ></el-input>
      </div>
    </el-form-item>

    <el-form-item label="(2) 引起过敏源(可多选)" prop="allergen" class="margin-left">
      <el-checkbox-group v-model="form.allergen">
        <el-checkbox v-for="(item, index) in healthyData['allergenArray']" :key="index" :label="item.value">{{item.name}}</el-checkbox>
      </el-checkbox-group>
      <div><span>其他过敏源</span>
        <el-input
          type="textarea"
          placeholder="请输入过敏源"
          v-model="form.otherAllergen"
          maxlength="200"
          show-word-limit
          rows="3"
        ></el-input>
      </div>
    </el-form-item>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";
import { getDictData,physicalSignsList } from "@/api/custom/customerInvestigation";
import { listPhysicalSigns } from "@/api/custom/physicalSigns";
export default {
  name: "Form8",
  components: {
  },
  data() {
    return {
      healthyData:healthyData,
      physicalSignsList: [],
      moistureDataList:[],
      bloodDataList:[],
    };
  },
  methods: {
    getTitle(){
          return this.healthyData.getTitle(this.form.conditioningProjectId, 7);
       },
    //获取湿气
    getMoistureDictData() {
      if(this.flag == 0){
        getDictData("sys_blood_data").then((response) => {
          this.moistureDataList = response.data;
        });
      }else{
        this.getDicts("sys_blood_data").then(response => {
          this.moistureDataList = response.data;
        });
      }
    },
    //获取气血
    getBloodDictData() {
      if(this.flag == 0){
        getDictData("sys_moisture_data").then((response) => {
          this.bloodDataList = response.data;
        });
      }else{
        this.getDicts("sys_moisture_data").then(response => {
          this.bloodDataList = response.data;
        });
      }
    },
    /** 查询体征列表 */
    getPhysicalSignsList() {
      if(this.flag == 0){
        physicalSignsList().then((response) => {
          this.physicalSignsList = response.rows;
        });
      }else{
        listPhysicalSigns().then(response => {
          this.physicalSignsList = response.rows;
        });
      }
    },
    getDrugTypeName(type){
        if(healthyData['longEatDrugClassifyArray'] && healthyData['longEatDrugClassifyArray'].length > 0){
            for(let i = 0 ; i < healthyData['longEatDrugClassifyArray'].length; i++){
                if(healthyData['longEatDrugClassifyArray'][i].value == type){
                  //console.log(healthyData['longEatDrugClassifyArray'][i].name);
                   return healthyData['longEatDrugClassifyArray'][i].name;
                }
            }
        }else{
          return "";
        }
    },
    addNewDrugInput(type){
      this.$emit('addNewDrugInput', type);
    }
  },
  props: {
    form: {
      type: Object,
      default: null
    },
    //是否已登录，1已登录 0未登录，需要调不同的方法获取数据，避免403错误
    flag:{
      type: Number,
      default: 0
    }
  },
  created() {
      this.getPhysicalSignsList();
      this.getMoistureDictData();
      this.getBloodDictData();
  },
  computed:{
    
  }

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

