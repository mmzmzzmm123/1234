<template>
    <div class="report_div" v-show="showFlag">
        <div class="report_title_div">
            <div class="report_title">{{reportData.name ? reportData.name : '您的'}}身体评估报告</div>
            <div class="report_weight">
                <div style="text-align:center">
                    <span><span class="weight_name">{{reportData.overWeight > 0 ? '超重' : '增重'}}</span>{{Math.abs(reportData.overWeight).toFixed(2)}} 斤</span> <span style="margin-left:8px"><span class="weight_name">标准体重</span>{{reportData.standardWeight.toFixed(2)}} 斤</span>
                </div>
            </div>
            <div>
                <div class="sign_div_title">
                    病史体征
                </div>
                <div class="sign_div_title_small">
                    选择准确的体征，获得更精准的评估、内容与服务
                </div>
                <div class="sign_type">
                    <div class="sign_type_div" v-for="(item,index) in reportData.signTypeArray" :key="index">
                      <span class="sign_type_div_value">{{item}}</span>
                    </div>
                </div>
               
            </div>
            <div class="blood_moisture">
                <div style="float: left;">
                    <div class="blood_moisture_title">湿气指数</div>
                    <div :class="getBloodMoistureIndexClass(reportData.moistureIndex)">
                      <span class="blood_moisture_index_span" :style="'margin-left:'+getBloodMoistureIndexClass(reportData.moistureIndex, 2)+'%;'">{{getBloodMoistureIndexClass(reportData.moistureIndex, 1)}}</span>
                    </div>
                </div>
                <div style="float: left;margin-top:20px">
                    <div class="blood_moisture_title">气血指数</div>
                    <div :class="getBloodMoistureIndexClass(reportData.bloodIndex)">
                       <span class="blood_moisture_index_span" :style="'margin-left:'+getBloodMoistureIndexClass(reportData.bloodIndex, 2)+'%;'">{{getBloodMoistureIndexClass(reportData.bloodIndex, 1)}}</span>
                    </div>
                </div>
                
                <div class="clear"></div> 
                <div class="sign_div_title_small" style="margin-top:20px">
                    绿色、橙色、红色分别表示轻度、中度、重度
                </div>
            </div>
            <div class="bottom_content">
                改善身体情况请和营养顾问详谈
            </div>
        </div>
    </div>
</template>

<script>
import {
  getDictData,
  getCustomerSurvey,
  addSimpleCustomerSurvey
} from "@/api/custom/customerInvestigation";
export default {
  name: "index",
  data() {
    return {
      showFlag: false,
      data: null,
      moistureDataList:[],
      bloodDataList:[],
      physicalSignsList:[],
      reportData:{
        name: "",
        overWeight: 0.0,
        standardWeight: 0.0,
        signTypeArray: [],
        moistureIndex: 0,
        bloodIndex: 0
      },
      //
      //分别代表轻度、中度、重度的界限，如：1-3之间为轻度
      indexRange: [1,4,7]
    };
  },
  components: {
    
  },
  methods: {
    showReportView(data, moistureDataList, bloodDataList, physicalSignsList){
        this.moistureDataList = moistureDataList;
        this.bloodDataList = bloodDataList;
        this.physicalSignsList = physicalSignsList;
        this.reportData.name = data.name;
        this.data = data;
        if(data.standardWeight != undefined && data.standardWeight != null){
            this.reportData.standardWeight = data.standardWeight;
            this.reportData.overWeight = data.weight - data.standardWeight;
        }
        data.physicalSignsIdArray.forEach((item,index) => {
            let signName = this.getSignLabelByValue(item);
            if(signName != null){
                this.reportData.signTypeArray.push(signName);
            }
        });
        if(data.otherPhysicalSigns != null && data.otherPhysicalSigns != ""){
            data.otherPhysicalSigns = data.otherPhysicalSigns.replace("，", ",");
            this.reportData.signTypeArray = this.reportData.signTypeArray.concat(data.otherPhysicalSigns.split(","));
        }
        this.reportData.moistureIndex = data.moistureDataArray.length;
        this.reportData.bloodIndex = data.bloodDataArray.length;
        this.showFlag = true;
        
    },
    getSignLabelByValue(value){
        let signObj = this.physicalSignsList.find((obj) => obj.dictValue === value);
        return signObj != null ? signObj.dictLabel : null;
    },
    getBloodMoistureIndexClass(num, type){
      let className = "blood_moisture_value";
      let spanValue = "";
      let marginLeft = 0;
      if(num < this.indexRange[0]){
        className += " none";
      }else if(num < this.indexRange[1]){
        spanValue = "低";
        marginLeft = 21;
        className += " light";
      }else if(num < this.indexRange[2]){
        spanValue = "中";
        marginLeft = 55;
        className += " moderate";
      }else{
        spanValue = "高";
        marginLeft = 86;
        className += " severe";
      }
      return (type != undefined && type > 0) ? (type == 1 ? spanValue : marginLeft) : className;
    }
  },
  created() {
    
  },
};
</script>

 <style scoped>

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
      padding: 0px 0px;
  }

  .weight_name{
      font-size: 18px;
      font-family: Source Han Sans CN;
      font-weight: 500;
      color: #4B8AFF;
      line-height: 50px;
      margin-right:2px;
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
    background: #e16664;
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
    color: #ffffff;
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
       width: 180px;
       height:30px;
       border-radius: 20px;
  }

  .clear{ clear:both} 

  .none{
    background: #CCCCCC
  }

  .light{
      background: linear-gradient(to right,#37bc02,#e6b46a 33%,#CCCCCC 35%);
  }

  .moderate{
      background: linear-gradient(to right,#37bc02,#f5b95e 33%, #fa9500 67%,#CCCCCC 67%);
  }

  .severe{
      background: linear-gradient(to right,#37bc02,#fa9500,#da302a);
  }
  
  .bottom_content{
        font-size: 14px;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #4B8AFF;
        line-height: 30px;
        text-align:center;
        margin-top:20px;
  }

  .blood_moisture_index_span{
      font-size: 16px;
      font-family: Source Han Sans CN;
      font-weight: 500;
      color: #FEFEFF;
      line-height: 30px;
  }
</style>
