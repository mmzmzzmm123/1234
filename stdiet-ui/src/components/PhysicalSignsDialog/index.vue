<template>
  <el-dialog :visible.sync="visible" :title="title" append-to-body @closed="onClosed">
    <div v-if="showFlag">
    <div style="float:right;margin-top:-10px;margin-bottom: 10px;" v-show="dataList.length > 0">
      <!-- 只有新版健康评估信息才可修改，旧的体征数据不支持修改 -->
      <el-button v-hasPermi="['custom:healthy:edit']" type="info" v-show="dataType == 0" @click="handleEditRemarkClick()"  plain>修改备注</el-button>
      <el-button v-hasPermi="['custom:healthy:edit']" type="warning" v-show="dataType == 0" @click="handleEditHealthyClick()"  plain>修改信息</el-button>
      <el-button type="danger" v-hasPermi="['custom:healthy:remove']" @click="handleDelete()" plain>删除信息</el-button>
    </div>
    <!-- 客户健康评估 -->
    <div v-if="dataList.length > 0 && dataType == 0">
      <!-- 基础信息 -->
      <div v-for="(item,index) in dataList.slice(0,1)" style="margin-bottom: 50px;" :key="index">
        <div>
          <p class="p_title_1" style="margin-top: 5px;">{{titleArray[index]}}</p>
          <table-detail-message :data="item" ></table-detail-message>
        </div>
        <!-- 备注 -->
        <el-table :data="remarkList" :show-header="false" border :cell-style="remarkColumnStyle" style="width: 100%;">
          <el-table-column width="140" prop="remarkTitle">
          </el-table-column>
          <el-table-column prop="remarkValue">
            <template slot-scope="scope">
              <auto-hide-message :data="scope.row.remarkValue" :maxLength="100"/></template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 其他信息 -->
      <div style="height:400px;overflow: auto">
        <div v-for="(item,index) in dataList.slice(1,10)" style="margin-bottom: 50px;" :key="index">
          <div>
            <p class="p_title_1" style="margin-top: 5px;">{{titleArray[index+1]}}</p>
            <table-detail-message :data="item" v-if="index != dataList.length-2"></table-detail-message>
            <el-table :show-header="false" v-if="index == dataList.length-2" :data="item" border :cell-style="columnStyle" style="width: 100%;">
              <el-table-column width="140" prop="attr_name_one">
              </el-table-column>
              <el-table-column prop="value_one">
                <template slot-scope="scope">
                  <auto-hide-message :data="scope.row.value_one == null ? '' : (scope.row.value_one+'')" :maxLength="20"/>
                  <el-button type="primary" v-show="scope.row.value_one" @click="downloadFile(medicalReportPathArray[0])">下载</el-button>
                </template>
              </el-table-column>
              <el-table-column width="140" prop="attr_name_two"></el-table-column>
              <el-table-column prop="value_two">
                <template slot-scope="scope">
                  <auto-hide-message :data="scope.row.value_two == null ? '' : (scope.row.value_two+'')" :maxLength="20"/>
                  <el-button type="primary" v-show="scope.row.value_two" @click="downloadFile(medicalReportPathArray[1])">下载</el-button>
                </template>
              </el-table-column>
              <el-table-column width="140" prop="attr_name_three"></el-table-column>
              <el-table-column prop="value_three">
                <template slot-scope="scope">
                  <auto-hide-message :data="scope.row.value_three == null ? '' : (scope.row.value_three+'')" :maxLength="20"/>
                  <el-button type="primary" v-show="scope.row.value_three" @click="downloadFile(medicalReportPathArray[2])">下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>
    <!-- 客户体征 -->
    <div v-else>
      <table-detail-message v-show="dataList.length > 0" :data="dataList" ></table-detail-message>
      <p v-show="dataList.length == 0" style="font-size: 20px;text-align:center;">暂无数据！
        <el-button icon="el-icon-share" size="small" title="点击复制链接" class="copyBtn" type="primary" :data-clipboard-text="copyValue" @click="handleCopy()">健康评估表链接</el-button>
      </p>
    </div>
    </div>
    <!-- 编辑 -->
    <physicalSigns-edit ref="physicalSignsEditDialog" @refreshHealthyData="getCustomerHealthyByCusId()"></physicalSigns-edit>
    <!-- 编辑备注 -->
    <physicalSigns-remark ref="physicalSignsRemarkDialog" @refreshHealthyData="getCustomerHealthyByCusId()"></physicalSigns-remark>
  </el-dialog>
</template>
<script>
import { getCustomerPhysicalSignsByCusId,delCustomerHealthy } from "@/api/custom/customer";
import TableDetailMessage from "@/components/TableDetailMessage";
import AutoHideMessage from "@/components/AutoHideMessage";
import * as healthyData from "@/utils/healthyData";
import Clipboard from 'clipboard';
import PhysicalSignsEdit from "@/components/PhysicalSignsEdit";
import PhysicalSignsRemark from "@/components/PhysicalSignsRemark";
export default {
  name: "PhysicalSignsDialog",
  components: {
    "auto-hide-message": AutoHideMessage,
    "table-detail-message": TableDetailMessage,
    "physicalSigns-edit":PhysicalSignsEdit,
    "physicalSigns-remark":PhysicalSignsRemark
  },
  data() {
    return {
      visible: false,
      showFlag: false,
      title: "",
      data: null,
      dataList: [],
      dataType: 0,
      healthyData: null,
      remarkList:[{"remarkTitle": "备注信息", "remarkValue": ""}],
      // 体征标题
      signTitleData: [
        ["创建时间", "姓名", "年龄"],
        ["性别", "手机号", "身高(cm)"],
        ["体重(斤)", "地域", "病史"],
        ["忌口或过敏源", "便秘", "熬夜失眠"],
        ["经常运动", "饮食方式", "饮食备注"],
        ["饮食特点", "工作职业", "上夜班"],
        ["久坐/运动多", "浑身乏力", "减脂反弹"],
        ["意识到生活习惯是减脂关键", "睡觉时间", "起床时间"],
        ["方便沟通时间", "湿气数据", "气血数据"],
        ["减脂经历", "减脂遇到的困难", "备注"],
      ],
      // 体征属性
      signValueData: [
        ["createTime", "name", "age"],
        ["sex", "phone", "tall"],
        ["weight", "position", "signStr"],
        ["dishesIngredientId", "constipation", "staylate"],
        ["motion", "makeFoodType", "remarks"],
        ["makeFoodTaste", "vocation", "night"],
        ["walk", "weakness", "rebound"],
        ["crux", "sleepTime", "getupTime"],
        ["connectTime", "bloodData", "moistureDate"],
        ["experience", "difficulty", "comments"],
      ],
      //健康评估每个模板标题，与调查表保持一致
      titleArray: healthyData['titleArray'],
      // 健康评估标题
      healthyTitleData:[
        [
          ["创建时间","客户姓名","手机号"],["调理项目","性别","年龄"],["身高(厘米)","体重(斤)","位置"]
        ],
        [
          ["减脂经历","减脂遇到的困难","减脂是否反弹"],["是否意识到生活习惯是减脂关键","",""]
        ],
        [
          ["调味品种","烹调方式","烹调频次"],["洗菜方式","",""]
        ],
        [
          ["早餐习惯","早餐吃的食物","午餐习惯"],["晚餐习惯","正餐中素菜占比","最常吃的肉类"],
          ["晚餐时间","每周吃夜宵次数","夜宵通常吃的食物"],["食物的冷热偏好","食物的口味偏好","平均每周吃生蔬菜几次"],
          ["每周吃生蔬菜的频次类型","平均每天吃水果次数","吃水果的时间段"],["平时吃水果的频次","一餐吃几碗饭","吃几成饱"],
          ["吃饭速度","饮食特点","常吃的零食"],["有无服用营养保健品","营养保健品品牌名","营养保健品产品名"],
          ["服用营养保健品频次","忌口过敏食物",""]
        ],
        [
          ["每天的饮水量","喜欢喝什么水","喝水习惯"],["常喝的饮品的每周频次","是否喝酒","喝酒种类"],["对应酒的量","是否抽烟","抽烟频次和烟龄"],
          ["是否经常抽二手烟","工作行业","工作性质"],["排便次数","排便时间段","排便的形状"],["排便的气味","排便的速度","排便的颜色"]
        ],
        [
          ["每周运动次数","每次运动的时长","每天运动的时间"],
          ["运动","运动场地",""]
        ],
        [
          ["睡觉时间","睡眠质量","是否有辅助入睡药物"],
          ["辅助睡眠类药物名称","是否经常熬夜","熬夜频次"]
        ],
        [
          ["病史体征","湿气数据","气血数据"],
          ["家族疾病史","手术史","近期是否做过手术"],
          ["手术恢复情况","是否长期服用药物","长期服用的药物"],
          ["是否出现过过敏症状","过敏症状","过敏源"]
        ],
        [
          ["体检报告(1)","体检报告(2)","体检报告(3)"]
        ]
      ],
      // 健康评估属性
      healthyValueData:[
        [["createTime","name","phone"],["conditioningProject","sex","age"],["tall","weight","position"]],
        [
          ["experience","difficulty","rebound"],["crux","",""]
        ],
        [["condiment","cookingStyle","cookingStyleRate"],["washVegetablesStyle","",""]],
        [
          ["breakfastType","breakfastFood","lunchType"],["dinner","vegetableRate","commonMeat"],
          ["dinnerTime","supperNum","supperFood"],["dietHotAndCold","dietFlavor","vegetablesNum"],
          ["vegetablesRateType","fruitsNum","fruitsTime"],["fruitsRate","riceNum","riceNum"],
          ["eatingSpeed","makeFoodType","snacks"],
          ["healthProductsFlag","healthProductsBrand","healthProductsName"],
          ["healthProductsWeekRate","dishesIngredient",""]
        ],
        [
          ["waterNum","waterType","waterHabit"],["drinksNum","drinkWineFlag","drinkWineClassify"],["drinkWineAmount","smokeFlag","smokeRate"],
          ["secondSmoke","workIndustry","workType"],["defecationNum","defecationTime","defecationShape"],["defecationSmell","defecationSpeed","defecationColor"]
        ],
        [
          ["motionNum","motionDuration","motionTime"],["motion","motionField",""]
        ],
        [
          ["sleepTime","sleepQuality","sleepDrugFlag"],
          ["sleepDrug","stayupLateFlag","stayupLateWeekNum"]
        ],
        [
          ["physicalSigns","moistureDate","bloodData"],
          ["familyIllnessHistory","operationHistory","nearOperationFlag"],
          ["recoveryeSituation","longEatDrugFlag","longEatDrugClassify"],
          ["allergyFlag","allergySituation","allergen"]
        ],
        [
          ["medicalReport_one","medicalReport_two","medicalReport_three"]
        ]
      ],
      copyValue: "",
      enc_id: ""
    };
  },
  methods: {
    // 自定义列背景色
    columnStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex % 2 === 0) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
    // 自定义备注列背景色
    remarkColumnStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex % 2 === 0) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
    showDialog(data) {
      this.data = data;
      this.title = `「${data.name}」客户健康评估信息`;
      this.getCustomerHealthyByCusId();
    },
    getCustomerHealthyByCusId(){
      getCustomerPhysicalSignsByCusId(this.data.id).then((res) => {
        this.showFlag = false;
        if (res.data.customerHealthy) {
          //判断是体征还是健康评估
          this.dataType = res.data.type;
          if(this.dataType == 0){
             this.healthyData = Object.assign({}, res.data.customerHealthy);
             this.remarkList[0].remarkValue = this.healthyData.remark;
             this.getDataListByHealthyMessage(res.data.customerHealthy);
          }else{
            this.getDataListBySignMessage(res.data.customerHealthy)
          }
        }
        this.enc_id = res.data.enc_id;
        this.showFlag = true;
        this.visible = true;
      });
    },
    onClosed() {
      this.dataList = [];
      this.data = null;
      this.enc_id = "";
      this.copyValue = "";
    },
    //对体征信息进行处理
    getDataListBySignMessage(sign){
      sign.sex = sign.sex === 0 ? `男` : "女";
      sign.position = sign.position === 0 ? `南方` : "北方";
      sign.signStr = this.getSignString(sign.signList);
      sign.constipation = sign.constipation === 0 ? "是" : "否";
      sign.staylate = sign.staylate === 0 ? "是" : "否";
      sign.motion = sign.motion === 0 ? "是" : "否";
      sign.makeFoodType = sign.makeFoodType === 0 ? `自己做` : "外面吃";
      sign.makeFoodTaste = sign.makeFoodTaste === 0 ? "清淡" : "重口味";
      sign.night = sign.night === 0 ? `是` : "否";
      sign.walk = sign.walk === 0 ? `久坐多` : "走动多";
      sign.weakness = sign.weakness === 0 ? `是` : "否";
      sign.rebound = sign.rebound === 0 ? `是` : "否";
      sign.crux = sign.crux === 0 ? `是` : "否";
      sign.sleepTime = sign.sleepTime + "点";
      sign.getupTime = sign.getupTime + "点";
      sign.connectTime = sign.connectTime + "点";
      for (let i = 0; i < this.signTitleData.length; i++) {
        this.dataList.push({
          attr_name_one: this.signTitleData[i][0],
          value_one: sign[this.signValueData[i][0]],
          attr_name_two: this.signTitleData[i][1],
          value_two: sign[this.signValueData[i][1]],
          attr_name_three: this.signTitleData[i][2],
          value_three: sign[this.signValueData[i][2]],
        });
      }
    },
    //对健康评估信息进行处理
    getDataListByHealthyMessage(healthy){
      let detailHealthy = this.dealHealthy(healthy);
      //性别
      detailHealthy.sex = detailHealthy.sex == 0 ?  "男" : (detailHealthy.sex == 1 ? "女" : "未知");
      detailHealthy.position = detailHealthy.position == 0 ?  "南方" : "北方";
      detailHealthy.rebound = detailHealthy.rebound == 0 ? "否" : "是";
      detailHealthy.crux = detailHealthy.crux == 0 ? "否" : "是";
      //调味品
      detailHealthy.condiment += detailHealthy.otherCondiment ? ("，"+detailHealthy.otherCondiment) : "";
      //烹饪
      let cookingStyleRate = "";
      if(detailHealthy.cookingStyleRate != null){
        detailHealthy.cookingStyleRate.split(",").forEach(function(item, index){
          cookingStyleRate += (cookingStyleRate != "" ? "，" : "") + (healthyData["cookingStyleRateArray"][index])+item +"次";
        });
      }
      detailHealthy.cookingStyleRate = cookingStyleRate;
      //洗菜方式
      detailHealthy.washVegetablesStyle += detailHealthy.otherWashVegetablesStyle ? ("，"+detailHealthy.otherWashVegetablesStyle) : "";
      //素菜占比
      detailHealthy.vegetableRate += "成";

      let makeFoodTypeOption = healthyData["makeFoodTypeArray"].find(opt => opt.value == detailHealthy.makeFoodType+"");
      detailHealthy.makeFoodType = makeFoodTypeOption ? makeFoodTypeOption.name : "";
      //零食
      detailHealthy.snacks += detailHealthy.otherSnacks ? ("，" + detailHealthy.otherSnacks) : "";
      detailHealthy.healthProductsFlag = detailHealthy.healthProductsFlag == 1 ? "有" : "无";
      detailHealthy.healthProductsWeekRate = detailHealthy.healthProductsDayRate+"次/天，"+detailHealthy.healthProductsWeekRate+"次/周";
      detailHealthy.waterNum += "毫升";
      //饮品
      let drinksNumString = "";
      if(detailHealthy.drinksNum != null){
        detailHealthy.drinksNum.split(",").forEach(function(item, index){
          drinksNumString += (drinksNumString != "" ? "，" : "") + (healthyData["drinksNumArray"][index])+item +"次";
        });
      }
      detailHealthy.drinksNum = drinksNumString;
      detailHealthy.drinkWineClassify += detailHealthy.otherWineClassify ? ("，"+detailHealthy.otherWineClassify) : "";
      let drinkWineAmountString = "";
      if(detailHealthy.drinkWineAmount != null){
        detailHealthy.drinkWineAmount.split(",").forEach(function(item, index){
          drinkWineAmountString += (drinkWineAmountString != "" ? "，" : "") + (healthyData["drinkWineAmountArray"][index])+item + healthyData["drinkWineAmountUnitArray"][index];
        });
      }
      detailHealthy.drinkWineAmount = drinkWineAmountString;
      detailHealthy.smokeFlag = detailHealthy.smokeFlag == 1 ? "是" : "否";
      detailHealthy.secondSmoke = detailHealthy.secondSmoke == 1 ? "是" : "否";
      let smokeRateString = "";
      if(detailHealthy.smokeRate != null){
        detailHealthy.smokeRate.split(",").forEach(function(item, index){
          smokeRateString += (smokeRateString != "" ? "，" : "") + (healthyData["smokeRateArray"][index])+item + healthyData["smokeRateUnitArray"][index];
        });
      }
      detailHealthy.smokeRate = smokeRateString;
      detailHealthy.defecationNum = detailHealthy.defecationNum + "次/天";
      detailHealthy.motionDuration = detailHealthy.motionDuration +"分钟";
      let motionStr = "";
      if(detailHealthy.aerobicMotionClassify != null){
        detailHealthy.aerobicMotionClassify.split(",").forEach(function(item, index){
          motionStr += item ? ((motionStr != "" ? "，" : "") + item) : "";
        });
      }
      if(detailHealthy.anaerobicMotionClassify != null){
        detailHealthy.anaerobicMotionClassify.split(",").forEach(function(item, index){
          motionStr += item ? ((motionStr != "" ? "，" : "") + item) : "";
        });
      }
      if(detailHealthy.anaerobicAerobicMotionClassify != null){
        detailHealthy.anaerobicAerobicMotionClassify.split(",").forEach(function(item, index){
          motionStr += item ? ((motionStr != "" ? "，" : "") + item) : "";
        });
      }
      detailHealthy.motion =  this.trimComma(motionStr + (detailHealthy.otherMotionClassify ? ( "，"+ detailHealthy.otherMotionClassify) : ""));
      detailHealthy.motionField += this.trimComma(detailHealthy.otherMotionField ? ("，"+detailHealthy.otherMotionField) : "");
      detailHealthy.sleepDrugFlag = detailHealthy.sleepDrugFlag == 1 ? "有" : "无";
      detailHealthy.stayupLateFlag = detailHealthy.stayupLateFlag == 1 ? "有" : "无";
      detailHealthy.stayupLateWeekNum += "次/周";
      let physicalSigns = this.getSignString(detailHealthy.signList);
      /*if(detailHealthy.signList != null && detailHealthy.signList.length > 0){
        detailHealthy.signList.forEach(function (sign, index) {
          physicalSigns += "，" + sign.name;
        })
      }*/
      physicalSigns += "，" + (detailHealthy.otherPhysicalSigns ? detailHealthy.otherPhysicalSigns : "");
      detailHealthy.physicalSigns = this.trimComma(physicalSigns);
      detailHealthy.familyIllnessHistory += this.trimComma(detailHealthy.otherFamilyIllnessHistory ? ("，" + detailHealthy.otherFamilyIllnessHistory) : "");
      detailHealthy.operationHistory += this.trimComma(detailHealthy.otherOperationHistory ? ("，" + detailHealthy.otherOperationHistory) : "");
      detailHealthy.nearOperationFlag = detailHealthy.nearOperationFlag == 1 ? "有" : "无";
      detailHealthy.longEatDrugFlag = detailHealthy.longEatDrugFlag == 1 ? "有" : "无";
      detailHealthy.longEatDrugClassify += detailHealthy.otherLongEatDrugClassify ? ("，" + detailHealthy.otherLongEatDrugClassify) : "";
      detailHealthy.allergyFlag = detailHealthy.allergyFlag == 1 ? "有" : "无";
      detailHealthy.allergen += detailHealthy.otherAllergen ? ("，" +detailHealthy.otherAllergen) : "";
      detailHealthy.allergen = this.trimComma(detailHealthy.allergen);
      let medicalReportPathArray = detailHealthy.medicalReport ? detailHealthy.medicalReport.split(",") : [];
      let medicalReportNameArray = detailHealthy.medicalReportName ? detailHealthy.medicalReportName.split(",") : [];
      this.medicalReportPathArray = medicalReportPathArray;
      detailHealthy.medicalReport_one = medicalReportPathArray.length > 0 ? (medicalReportNameArray.length > 0 ? medicalReportNameArray[0] : "体检报告（1）") : "";
      detailHealthy.medicalReport_two =  medicalReportPathArray.length > 1 ? (medicalReportNameArray.length > 1 ? medicalReportNameArray[1] : "体检报告（2）") : "";
      detailHealthy.medicalReport_three = medicalReportPathArray.length > 2 ? (medicalReportNameArray.length > 2 ? medicalReportNameArray[2] : "体检报告（3）") : "";
      for(let i = 0; i < this.healthyTitleData.length; i++){
        let stepArray = [];
        for(let j= 0; j < this.healthyTitleData[i].length; j++){
          stepArray[j] = ({"attr_name_one": this.healthyTitleData[i][j][0],"value_one": detailHealthy[this.healthyValueData[i][j][0]],"attr_name_two": this.healthyTitleData[i][j][1],"value_two": detailHealthy[this.healthyValueData[i][j][1]],"attr_name_three": this.healthyTitleData[i][j][2],"value_three": detailHealthy[this.healthyValueData[i][j][2]]});
        }
        this.dataList[i] = stepArray;
      }
    },
    //健康信息处理，将数组转为字符串
    dealHealthy(customerHealthy){
      let array = healthyData["needAttrName"];
      for(let i = 0; i < array.length; i++){
        customerHealthy[array[i]] = this.getHealthyStringByArray(array[i]+"Array", customerHealthy[array[i]]);
      }
      return customerHealthy;
    },
    //将数组中的value值转为name
    getHealthyStringByArray(key, valueArray){
      var str = "";
      if(valueArray != null && valueArray.split(",").length > 0){
        valueArray.split(",").forEach(function (item, index) {
          let data = healthyData[key].find(opt => opt.value == item);
          if(data != null){
            str += (str != "" ? "，" : "") + data.name;
          }
        })
      }
      return str;
    },
    getSignString(signList){
      if(signList == null){
        return "";
      }
      let signStr = "";
      for(let i in signList){
        signStr += "，"+signList[i].name;
      }
      return signList.length > 0 ? signStr.substring(1) : signStr;
    },
    downloadFile(fileName){
      this.downloadResource(fileName);
    },
    trimComma(str){
      if(str.startsWith("，") || str.startsWith(",")){
        str = str.substring(1,str.length);
      }
      if(str.endsWith("，") || str.endsWith(",")){
        str = str.substring(0,str.length-1);
      }
      return str;
    },
    handleCopy() {
      this.copyValue = window.location.origin.replace('manage', 'sign') + "/subhealthyInvestigation/"+this.enc_id;
      const btnCopy = new Clipboard('.copyBtn');
      this.$message({
        message: '拷贝成功',
        type: 'success'
      });
    },
    /** 删除健康信息操作 */
    handleDelete() {
      const ids = this.data.id;
      this.$confirm(
        '是否确认删除客户姓名为"' + this.data.name + '"的健康评估信息?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(function () {
          return delCustomerHealthy(ids);
      }).then(() => {
          this.dataList = [];
          this.msgSuccess("删除成功");
      }).catch(function () {});
    },
    handleEditHealthyClick() {
      //console.log(JSON.stringify(this.healthyData));
      this.$refs["physicalSignsEditDialog"].showDialog(this.data, this.healthyData);
    },
    handleEditRemarkClick(){
      this.$refs["physicalSignsRemarkDialog"].showDialog(this.data, this.healthyData);
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
