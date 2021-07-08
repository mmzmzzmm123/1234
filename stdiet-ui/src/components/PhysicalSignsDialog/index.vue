<template>
  <el-dialog
    :visible.sync="visible"
    :title="title"
    append-to-body
    @closed="onClosed"
    :width="dialogWidth"
  >
    <div style="display: flex; flex-direction: row">
      <div v-if="showFlag" style="float: left; width: 900px">
        <div
          style="float: right; margin-top: -10px; margin-bottom: 10px"
          v-show="dataList.length > 0"
        >
          <!-- 只有新版健康评估信息才可修改，旧的体征数据不支持修改 -->
          <el-button
            type="info"
            v-show="dataType == 0 && (healthyData && healthyData.conditioningProjectId == 0)"
            @click="generateReport"
            plain
            >下载报告</el-button
          >
          <el-button
            type="info"
            v-show="dataType == 0"
            @click="handleEditGuidanceClick"
            plain
            >{{guidanceButtonNmae ? guidanceButtonNmae : '指导'}}</el-button
          >
          <el-button
            v-hasPermi="['custom:healthy:edit']"
            type="info"
            @click="handleEditRemarkClick"
            plain
            >修改备注</el-button
          >
          <el-button
            v-hasPermi="['custom:healthy:edit']"
            type="warning"
            v-show="dataType == 0"
            @click="handleEditHealthyClick"
            plain
            >修改信息</el-button
          >
          <el-button
            type="danger"
            v-hasPermi="['custom:healthy:remove']"
            @click="handleDelete"
            plain
            >删除信息</el-button
          >
        </div>
        <!-- 客户健康评估 -->
        <div v-if="dataList.length > 0 && dataType == 0">
          <!-- 基础信息 -->
          <div
            v-for="(item, index) in dataList.slice(0, 1)"
            style="margin-bottom: 50px"
            :key="index"
          >
            <div>
              <p class="p_title_1" style="margin-top: 5px">
                {{ getTitle(index) }}
              </p>
              <table-detail-message :data="item"></table-detail-message>
            </div>
            <!-- 备注 -->
            <el-table
              :data="remarkList"
              :show-header="false"
              border
              :cell-style="remarkColumnStyle"
              style="width: 100%"
            >
              <el-table-column width="140" prop="remarkTitle">
              </el-table-column>
              <el-table-column prop="remarkValue">
                <template slot-scope="scope">
                  <auto-hide-message
                    :data="scope.row.remarkValue"
                    :maxLength="100"
                /></template>
              </el-table-column>
            </el-table>
            <!-- 指导 -->
            <el-table
              :data="guidanceList"
              :show-header="false"
              border
              :cell-style="remarkColumnStyle"
              style="width: 100%"
            >
              <el-table-column width="140" prop="guidanceTitle">
              </el-table-column>
              <el-table-column prop="guidanceValue">
                <template slot-scope="scope">
                  <auto-hide-message
                    :data="scope.row.guidanceValue"
                    :maxLength="100"
                /></template>
              </el-table-column>
            </el-table>
          </div>
          <!-- 其他信息 -->
          <div style="height: 370px; overflow: auto">
            <div
              v-for="(item, index) in dataList.slice(1, 9)"
              style="margin-bottom: 50px"
              :key="index"
            >
             <!-- 备孕 -->
              <div v-if="index > 0 || healthyDataConstData['notExperienceIndex'].indexOf(healthyData.conditioningProjectId) == -1">
                <p class="p_title_1" style="margin-top: 5px">
                  {{ getTitle(index+1) }}
                </p>
                <table-detail-message
                  :data="item"
                  v-if="index < 7"
                ></table-detail-message>
                <el-table
                  :show-header="false"
                  v-if="index == 7"
                  :data="item"
                  border
                  :cell-style="columnStyle"
                  style="width: 100%"
                >
                  <el-table-column width="140" prop="attr_name_one">
                  </el-table-column>
                  <el-table-column prop="value_one">
                    <template slot-scope="scope">
                      <el-image
                        v-if="scope.row.value_one"
                        title="点击大图预览"
                        style="width: auto; height: 80px"
                        fit="contain"
                        :src="getImgUrl(0)"
                        :preview-src-list="[getImgUrl(0)]"
                      />
                    </template>
                  </el-table-column>
                  <el-table-column
                    width="140"
                    prop="attr_name_two"
                  ></el-table-column>
                  <el-table-column prop="value_two">
                    <template slot-scope="scope">
                      <el-image
                        v-if="scope.row.value_two"
                        title="点击大图预览"
                        fit="contain"
                        style="width: auto; height: 80px"
                        :src="getImgUrl(1)"
                        :preview-src-list="[getImgUrl(1)]"
                      />
                    </template>
                  </el-table-column>
                  <el-table-column
                    width="140"
                    prop="attr_name_three"
                  ></el-table-column>
                  <el-table-column prop="value_three">
                    <template slot-scope="scope">
                      <el-image
                        v-if="scope.row.value_three"
                        title="点击大图预览"
                        fit="contain"
                        style="width: auto; height: 80px"
                        :src="getImgUrl(2)"
                        :preview-src-list="[getImgUrl(2)]"
                      />
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
            <div
              v-for="(item, index) in dataList.slice(9, 17)"
              style="margin-bottom: 50px"
              :key="'sign'+index"
            >
              <div v-if="healthyDataConstData['extendHealthyIndex'].indexOf(healthyData.conditioningProjectId) != -1">
                <div v-if="isShowModule(index)">
                  <p class="p_title_1" style="margin-top: 5px">
                    {{ extendedTitleArray[index] }}
                  </p>
                  <table-detail-message
                    :data="item"
                  ></table-detail-message>
                </div>
              </div>

            </div>
          </div>
        </div>
        <!-- 客户体征 -->
        <div v-else-if="dataList.length > 0 && dataType === 1">
          <table-detail-message v-show="dataList.length > 0" :data="dataList" />
          <!-- 备注 -->
          <el-table
            :data="remarkList"
            :show-header="false"
            border
            :cell-style="remarkColumnStyle"
            style="width: 100%"
          >
            <el-table-column width="140" prop="remarkTitle"> </el-table-column>
            <el-table-column prop="remarkValue">
              <template slot-scope="scope">
                <auto-hide-message
                  :data="scope.row.remarkValue"
                  :maxLength="100"
              /></template>
            </el-table-column>
          </el-table>
        </div>
        <div v-else style="font-size: 20px; text-align: center">
          <VueQr :text="copyValue" :logoSrc="logo" :size="256" />
          <div style="text-align: center; margin-top: 20px">
            <el-button
              icon="el-icon-share"
              size="small"
              title="点击复制链接"
              class="copyBtn"
              type="primary"
              :data-clipboard-text="copyValue"
              @click="handleCopy()"
              >健康评估表链接
            </el-button>
          </div>
        </div>
      </div>
      <div style="width: 200px" v-show="guidanceShow">
        <!-- 编辑减脂指导 -->
        <physicalSigns-guidance
          ref="physicalSignsGuidanceDialog"
          @close="editGuidanceShow(false)"
          @refreshHealthyData="getCustomerHealthyByCusId"
        ></physicalSigns-guidance>
      </div>
    </div>
    <!-- 编辑 -->
    <physicalSigns-edit
      ref="physicalSignsEditDialog"
      @refreshHealthyData="getCustomerHealthyByCusId"
    ></physicalSigns-edit>
    <!-- 编辑备注 -->
    <physicalSigns-remark
      ref="physicalSignsRemarkDialog"
      @refreshHealthyData="getCustomerHealthyByCusId"
    ></physicalSigns-remark>
  </el-dialog>
</template>
<script>
import {
  getCustomerPhysicalSignsByCusId,
  delCustomerHealthy,
} from "@/api/custom/customer";
import { generateHealthyReport } from "@/api/custom/healthy";
import TableDetailMessage from "@/components/TableDetailMessage";
import AutoHideMessage from "@/components/AutoHideMessage";
import * as healthyData from "@/utils/healthyData";
import Clipboard from "clipboard";
import PhysicalSignsEdit from "@/components/PhysicalSignsEdit";
import PhysicalSignsRemark from "@/components/PhysicalSignsRemark";
import PhysicalSignsGuidance from "@/components/PhysicalSignsGuidance";
import VueQr from "vue-qr";
const logo = require("@/assets/logo/logo_b.png");
export default {
  name: "PhysicalSignsDialog",
  components: {
    "auto-hide-message": AutoHideMessage,
    "table-detail-message": TableDetailMessage,
    "physicalSigns-edit": PhysicalSignsEdit,
    "physicalSigns-remark": PhysicalSignsRemark,
    "physicalSigns-guidance": PhysicalSignsGuidance,
    VueQr,
  },
  data() {
    return {
      healthyDataConstData: healthyData,
      logo,
      visible: false,
      showFlag: false,
      title: "",
      data: null,
      dataList: [],
      dataType: 0,
      healthyData: null,
      remarkList: [{ remarkTitle: "备注信息", remarkValue: "" }],
      guidanceList: [{ guidanceTitle: "减脂指导", guidanceValue: "" }],
      guidanceButtonNmae:"减脂指导",
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
      titleArray: healthyData["titleArray"],
      // 健康评估标题
      healthyTitleData: healthyData['healthyTitleData'],
      // 健康评估属性
      healthyValueData: healthyData['healthyValueData'],
       extendedTitleArray:['十、高血糖信息评估',"十、高血压信息评估","十一、焦虑信息评估","十二、抑郁信息评估","九、月经不调、多囊信息评估","九、胃肠肿瘤信息评估","九、产后调理信息评估","九、高血脂信息评估"],
      copyValue: "",
      detailHealthy: null,
      dialogWidth: "950px",
      guidanceShow: false,
    };
  },
  methods: {
    getTitle(index){
      return healthyData.getShowTitle(this.healthyData.conditioningProjectId, index)
    },
    getImgUrl(idx) {
      return `${window.location.origin}${this.medicalReportPathArray[idx]}`;
    },
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
      this.copyValue =
        window.location.origin.replace("manage", "sign") +
        "/subhealthyInvestigation/" +
        this.data.encId;
      this.getCustomerHealthyByCusId();
    },
    getCustomerHealthyByCusId() {
      getCustomerPhysicalSignsByCusId(this.data.id).then((res) => {
        this.showFlag = false;
        if (res.data.customerHealthy) {
          if(res.data.type == 0){
            //json字符串转json对象
            res.data.customerHealthy.healthyExtend = healthyData.dealHealthyExtendJson(res.data.customerHealthy.healthyExtend, true);
          }
          //判断是体征还是健康评估
          this.dataType = res.data.type;
          this.healthyData = JSON.parse(
            JSON.stringify(res.data.customerHealthy)
          );
          this.dataList = [];
          this.remarkList[0].remarkValue = this.healthyData.remark;
          if (this.dataType == 0) {
            this.guidanceList[0].guidanceValue = this.healthyData.guidance;
            this.getDataListByHealthyMessage(res.data.customerHealthy);
          } else {
            this.getDataListBySignMessage(res.data.customerHealthy);
          }
        }
        //this.enc_id = res.data.enc_id;
        this.showFlag = true;
        this.visible = true;
      });
    },
    onClosed() {
      this.dataList = [];
      this.data = null;
      this.detailHealthy = null;
      //this.enc_id = "";
      this.copyValue = "";
      this.editGuidanceShow(false);
    },
    //对体征信息进行处理
    getDataListBySignMessage(sign) {
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
    getDataListByHealthyMessage(healthy) {
      let detailHealthy = this.dealHealthy(healthy);
      //修改第二栏经历信息（高血糖项目：减脂都改为降血糖）
      this.healthyTitleData[1] = healthyData.getTitleShowArray(healthy.conditioningProjectId);
      //修改指导名称（高血糖项目：降血糖指导）
      this.guidanceList[0].guidanceTitle = healthyData.extendHealthyTitle[healthy.conditioningProjectId+""] + "指导";
      this.guidanceButtonNmae = healthyData.extendHealthyTitle[healthy.conditioningProjectId+""] + "指导";

      //性别
      detailHealthy.sex =
        detailHealthy.sex == 0 ? "男" : detailHealthy.sex == 1 ? "女" : "未知";
      detailHealthy.position = detailHealthy.position == 0 ? "南方" : "北方";
      detailHealthy.rebound = detailHealthy.rebound == 0 ? "否" : "是";
      detailHealthy.crux = detailHealthy.crux == 0 ? "否" : "是";
      //调味品
      detailHealthy.condiment += detailHealthy.otherCondiment
        ? "，" + detailHealthy.otherCondiment
        : "";
      //烹饪
      let cookingStyleRate = "";
      if (detailHealthy.cookingStyleRate != null) {
        detailHealthy.cookingStyleRate
          .split(",")
          .forEach(function (item, index) {
            cookingStyleRate +=
              (cookingStyleRate != "" ? "，" : "") +
              healthyData["cookingStyleRateArray"][index] +
              item +
              "次";
          });
      }
      detailHealthy.cookingStyleRate = cookingStyleRate;
      //洗菜方式
      detailHealthy.washVegetablesStyle += detailHealthy.otherWashVegetablesStyle
        ? "，" + detailHealthy.otherWashVegetablesStyle
        : "";
      //素菜占比
      detailHealthy.vegetableRate += "成";

      let makeFoodTypeOption = healthyData["makeFoodTypeArray"].find(
        (opt) => opt.value == detailHealthy.makeFoodType + ""
      );
      detailHealthy.makeFoodType = makeFoodTypeOption
        ? makeFoodTypeOption.name
        : "";
      //零食
      detailHealthy.snacks += detailHealthy.otherSnacks
        ? "，" + detailHealthy.otherSnacks
        : "";
      detailHealthy.healthProductsFlag =
        detailHealthy.healthProductsFlag == 1 ? "有" : "无";
      detailHealthy.healthProductsWeekRate =
        detailHealthy.healthProductsDayRate +
        "次/天，" +
        detailHealthy.healthProductsWeekRate +
        "次/周";
      detailHealthy.waterNum += "毫升";
      //饮品
      let drinksNumString = "";
      if (detailHealthy.drinksNum != null) {
        detailHealthy.drinksNum.split(",").forEach(function (item, index) {
          drinksNumString +=
            (drinksNumString != "" ? "，" : "") +
            healthyData["drinksNumArray"][index] +
            item +
            "次";
        });
      }
      detailHealthy.drinksNum = drinksNumString;
      detailHealthy.drinkWineClassify += detailHealthy.otherWineClassify
        ? "，" + detailHealthy.otherWineClassify
        : "";
      let drinkWineAmountString = "";
      if (detailHealthy.drinkWineAmount != null) {
        detailHealthy.drinkWineAmount
          .split(",")
          .forEach(function (item, index) {
            drinkWineAmountString +=
              (drinkWineAmountString != "" ? "，" : "") +
              healthyData["drinkWineAmountArray"][index] +
              item +
              healthyData["drinkWineAmountUnitArray"][index];
          });
      }
      detailHealthy.drinkWineAmount = drinkWineAmountString;
      detailHealthy.smokeFlag = detailHealthy.smokeFlag == 1 ? "是" : "否";
      detailHealthy.secondSmoke = detailHealthy.secondSmoke == 1 ? "是" : "否";
      let smokeRateString = "";
      if (detailHealthy.smokeRate != null) {
        (detailHealthy.smokeRate || "")
          .split(",")
          .forEach(function (item, index) {
            smokeRateString +=
              (smokeRateString != "" ? "，" : "") +
              healthyData["smokeRateArray"][index] +
              item +
              healthyData["smokeRateUnitArray"][index];
          });
      }
      detailHealthy.smokeRate = smokeRateString;
      detailHealthy.defecationNum = detailHealthy.defecationNum + "次/天";
      detailHealthy.motionDuration = detailHealthy.motionDuration + "分钟";
      let motionStr = "";
      if (detailHealthy.aerobicMotionClassify != null) {
        detailHealthy.aerobicMotionClassify
          .split(",")
          .forEach(function (item, index) {
            motionStr += item ? (motionStr != "" ? "，" : "") + item : "";
          });
      }
      if (detailHealthy.anaerobicMotionClassify != null) {
        detailHealthy.anaerobicMotionClassify
          .split(",")
          .forEach(function (item, index) {
            motionStr += item ? (motionStr != "" ? "，" : "") + item : "";
          });
      }
      if (detailHealthy.anaerobicAerobicMotionClassify != null) {
        detailHealthy.anaerobicAerobicMotionClassify
          .split(",")
          .forEach(function (item, index) {
            motionStr += item ? (motionStr != "" ? "，" : "") + item : "";
          });
      }
      detailHealthy.motion = this.trimComma(
        motionStr +
          (detailHealthy.otherMotionClassify
            ? "，" + detailHealthy.otherMotionClassify
            : "")
      );
      detailHealthy.motionField = this.trimComma(
        detailHealthy.motionField +
          (detailHealthy.otherMotionField
            ? "，" + detailHealthy.otherMotionField
            : "")
      );
      detailHealthy.sleepDrugFlag =
        detailHealthy.sleepDrugFlag == 1 ? "有" : "无";
      detailHealthy.stayupLateFlag =
        detailHealthy.stayupLateFlag == 1 ? "有" : "无";
      detailHealthy.stayupLateWeekNum += "次/周";
      let physicalSigns = this.getSignString(detailHealthy.signList);
      /*if(detailHealthy.signList != null && detailHealthy.signList.length > 0){
        detailHealthy.signList.forEach(function (sign, index) {
          physicalSigns += "，" + sign.name;
        })
      }*/
      physicalSigns +=
        "，" +
        (detailHealthy.otherPhysicalSigns
          ? detailHealthy.otherPhysicalSigns
          : "");
      detailHealthy.physicalSigns = this.trimComma(physicalSigns);
      detailHealthy.familyIllnessHistory = this.trimComma(
        detailHealthy.familyIllnessHistory +
          "，" +
          (detailHealthy.otherFamilyIllnessHistory
            ? detailHealthy.otherFamilyIllnessHistory
            : "")
      );
      detailHealthy.operationHistory = this.trimComma(
        detailHealthy.operationHistory +
          "，" +
          (detailHealthy.otherOperationHistory
            ? detailHealthy.otherOperationHistory
            : "")
      );
      detailHealthy.nearOperationFlag =
        detailHealthy.nearOperationFlag == 1 ? "有" : "无";
      detailHealthy.longEatDrugFlag =
        detailHealthy.longEatDrugFlag == 1 ? "有" : "无";
      detailHealthy.longEatDrugClassify = this.trimComma(
        detailHealthy.longEatDrugClassify +
          "，" +
          (detailHealthy.otherLongEatDrugClassify
            ? detailHealthy.otherLongEatDrugClassify
            : "")
      );
      if (detailHealthy.healthyExtend.longEatDrugMessage != null) {
          let m = "";
          detailHealthy.healthyExtend.longEatDrugMessage.forEach((item,i) => {
            let mm = "";
            if(item.drug != null && item.drug.length > 0){
              item.drug.forEach((it, j) => {
                mm += (mm == "" ? "" : ",") + it.name + "/" + it.num + "/" + it.time;
              })
            }
            m += (m == "" ? "" : ",") + mm;
          });
          detailHealthy.longEatDrugClassify += " 药物详情：" + m;
      }
      detailHealthy.allergyFlag = detailHealthy.allergyFlag == 1 ? "有" : "无";
      detailHealthy.allergen = this.trimComma(
        detailHealthy.allergen +
          "，" +
          (detailHealthy.otherAllergen ? detailHealthy.otherAllergen : "")
      );
      let medicalReportPathArray = detailHealthy.medicalReport
        ? detailHealthy.medicalReport.split(",")
        : [];
      let medicalReportNameArray = detailHealthy.medicalReportName
        ? detailHealthy.medicalReportName.split(",")
        : [];
      this.medicalReportPathArray = medicalReportPathArray;
      detailHealthy.medicalReport_one =
        medicalReportPathArray.length > 0
          ? medicalReportNameArray.length > 0
            ? medicalReportNameArray[0]
            : "体检报告（1）"
          : "";
      detailHealthy.medicalReport_two =
        medicalReportPathArray.length > 1
          ? medicalReportNameArray.length > 1
            ? medicalReportNameArray[1]
            : "体检报告（2）"
          : "";
      detailHealthy.medicalReport_three =
        medicalReportPathArray.length > 2
          ? medicalReportNameArray.length > 2
            ? medicalReportNameArray[2]
            : "体检报告（3）"
          : "";
      detailHealthy.moistureDate = (detailHealthy.moistureDate || "")
        .split(",")
        .reduce((arr, cur) => {
          const tarData = healthyData.moistureDateArray.find(
            (obj) => obj.value === cur
          );
          if (tarData) {
            arr.push(tarData.name);
          }
          return arr;
        }, []);
      detailHealthy.bloodData = (detailHealthy.bloodData || "")
        .split(",")
        .reduce((arr, cur) => {
          const tarData = healthyData.bloodDataArray.find(
            (obj) => obj.value === cur
          );
          if (tarData) {
            arr.push(tarData.name);
          }
          return arr;
        }, []);
      detailHealthy.bmi = (
        detailHealthy.weight /
        2 /
        (detailHealthy.tall / 100) /
        (detailHealthy.tall / 100)
      ).toFixed(1);

      healthyData.dealHealthyExtend(detailHealthy);
      this.detailHealthy = detailHealthy;
      for (let i = 0; i < this.healthyTitleData.length; i++) {
        let stepArray = [];
        for (let j = 0; j < this.healthyTitleData[i].length; j++) {
          stepArray[j] = {
            attr_name_one: this.healthyTitleData[i][j][0],
            value_one: this.healthyValueData[i][j][0].indexOf(",") == -1 ? detailHealthy[this.healthyValueData[i][j][0]] : detailHealthy[this.healthyValueData[i][j][0].split(",")[0]][this.healthyValueData[i][j][0].split(",")[1]],
            attr_name_two: this.healthyTitleData[i][j][1],
            value_two: this.healthyValueData[i][j][1].indexOf(",") == -1 ? detailHealthy[this.healthyValueData[i][j][1]] : detailHealthy[this.healthyValueData[i][j][1].split(",")[0]][this.healthyValueData[i][j][1].split(",")[1]],
            attr_name_three: this.healthyTitleData[i][j][2],
            value_three: this.healthyValueData[i][j][2].indexOf(",") == -1 ? detailHealthy[this.healthyValueData[i][j][2]] : detailHealthy[this.healthyValueData[i][j][2].split(",")[0]][this.healthyValueData[i][j][2].split(",")[1]],
          };
        }
        this.dataList[i] = stepArray;
      }
    },
     isShowModule(index){
         if(this.healthyData.conditioningProjectId == 5 || this.healthyData.conditioningProjectId == 6){
             return index == 2 || index == 3 || (this.healthyData.conditioningProjectId == 5 && index == 1) || (this.healthyData.conditioningProjectId == 6 && index == 0)
         }else if(this.healthyData.conditioningProjectId == 1 || this.healthyData.conditioningProjectId == 2){
             return index == 4;
         }else if(this.healthyData.conditioningProjectId == 13){
           return index == 5;
         }else if(this.healthyData.conditioningProjectId == 4){
           return index == 6;
         }else if(this.healthyData.conditioningProjectId == 7){
           return index == 7;
         }
         return false;
     },
    //健康信息处理，将数组转为字符串
    dealHealthy(customerHealthy) {
      let array = healthyData["needAttrName"];
      for (let i = 0; i < array.length; i++) {
        customerHealthy[array[i]] = this.getHealthyStringByArray(
          array[i] + "Array",
          customerHealthy[array[i]]
        );
      }
      return customerHealthy;
    },
    //将数组中的value值转为name
    getHealthyStringByArray(key, valueArray) {
      var str = "";
      if (valueArray != null && valueArray.split(",").length > 0) {
        valueArray.split(",").forEach(function (item, index) {
          let data = healthyData[key].find((opt) => opt.value == item);
          if (data != null) {
            str += (str != "" ? "，" : "") + data.name;
          }
        });
      }
      return str;
    },
    getSignString(signList) {
      if (signList == null) {
        return "";
      }
      let signStr = "";
      for (let i in signList) {
        signStr += "，" + signList[i].name;
      }
      return signList.length > 0 ? signStr.substring(1) : signStr;
    },
    downloadFile(fileName) {
      // this.downloadResource(fileName);
      // this.previewVisible = true;
      // this.previewUrl = `${window.location.origin}${fileName}`;
    },
    generateReport() {
      let data = this.detailHealthy;
      console.log(this.detailHealthy);
      if (
        !this.guidanceList[0].guidanceValue ||
        this.guidanceList[0].guidanceValue.length == 0
      ) {
        this.$confirm("该客户还未添加减脂指导，是否确认下载报告?", "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            return generateHealthyReport(data);
          })
          .then((response) => {
            if (response.code == 200 && response.path != null) {
              //文件路径、获取下载文件名称
              this.download(response.path, response.downReportFileName);
            }
          })
          .catch(function () {});
      } else {
        generateHealthyReport(data).then((res) => {
          if (res.code == 200 && res.path != null) {
            this.download(res.path);
          }
        });
      }
    },
    trimComma(str) {
      if (str.startsWith("，") || str.startsWith(",")) {
        str = str.substring(1, str.length);
      }
      if (str.endsWith("，") || str.endsWith(",")) {
        str = str.substring(0, str.length - 1);
      }
      return str;
    },
    handleCopy() {
      new Clipboard(".copyBtn");
      this.$message({
        message: "拷贝成功",
        type: "success",
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
      )
        .then(function () {
          return delCustomerHealthy(ids);
        })
        .then(() => {
          this.dataList = [];
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    handleEditHealthyClick() {
      //console.log(JSON.stringify(this.healthyData));
      this.$refs["physicalSignsEditDialog"].showDialog(
        this.data,
        this.healthyData
      );
    },
    handleEditRemarkClick() {
      this.$refs["physicalSignsRemarkDialog"].showDialog(
        this.data,
        this.healthyData,
        this.dataType
      );
    },
    handleEditGuidanceClick() {
      this.editGuidanceShow(true);
      this.$refs["physicalSignsGuidanceDialog"].showDialog(
        this.data,
        this.healthyData,
        this.guidanceButtonNmae
      );
    },
    editGuidanceShow(flag) {
      this.guidanceShow = flag;
      this.dialogWidth = flag ? "1200px" : "950px";
    },
  },
};
</script>

<style lang="scss" scoped>
.margin-top-20 {
  margin-top: 20px;
}
.p_title_1 {
  font-size: 18px;
  font-weight: bold;
  margin-top: 20px;
}
.preview_dialog_wrapper {
  .preview_content {
    text-align: center;
    .preview_img {
      max-height: 600px;
      width: auto;
    }
  }
}
</style>
