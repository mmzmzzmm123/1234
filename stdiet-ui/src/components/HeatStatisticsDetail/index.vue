<template>
  <!-- 热量分析 -->
  <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body >
    <div v-show="showFlag" >
      <div style="margin-top: -30px">
        <h3 class="blue-color">基础信息</h3>
      </div>
      <el-table :show-header="false" :data="baseData" border :cell-style="baseColumnStyle" >
        <el-table-column width="200" prop="name_one">
        </el-table-column>
        <el-table-column prop="value_one">
          <template slot-scope="scope">
            {{scope.row.value_one}}
          </template>
        </el-table-column>
        <el-table-column width="200" prop="name_two">
        </el-table-column>
        <el-table-column prop="value_two">
          <template slot-scope="scope">
            {{scope.row.value_two}}
          </template>
        </el-table-column>
      </el-table>
      <div>
        <h3 class="blue-color">热量分析</h3>
      </div>
      <el-table :show-header="false" :data="heatData" border :cell-style="baseColumnStyle" >
        <el-table-column width="200" prop="name_one">
        </el-table-column>
        <el-table-column prop="value_one">
          <template slot-scope="scope">
            {{scope.row.value_one}}
          </template>
        </el-table-column>
        <el-table-column width="200" prop="name_two">
        </el-table-column>
        <el-table-column prop="value_two">
          <template slot-scope="scope">
            {{scope.row.value_two}}
          </template>
        </el-table-column>
      </el-table>
      <div>
        <h3 class="blue-color">营养热量定量结果</h3>
      </div>
      <el-table :show-header="false" :data="nutritData" border :cell-style="nutritColumnStyle" >
        <el-table-column width="200" prop="name">
        </el-table-column>
        <el-table-column prop="value[0]">
          <template slot-scope="scope">
            {{scope.row.value[0]}}
          </template>
        </el-table-column>
        <el-table-column prop="value[1]">
          <template slot-scope="scope">
            {{scope.row.value[1]}}
          </template>
        </el-table-column>
        <el-table-column prop="value[2]">
          <template slot-scope="scope">
            {{scope.row.value[2]}}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-show="!showFlag">
      <p style="font-size: 20px;text-align:center;">暂无数据！</p>
    </div>
  </el-dialog>
</template>
<script>

  import { getNutritionalCalories } from "@/api/custom/foodHeatStatistics";

  export default {
    name: "index",
    components: {

    },
    props: {},
    data() {
      return {
        showFlag: true,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        nutritionalData:null,
        baseData: [],
        baseTitleData:[["姓名","体重(斤)"],["身高(CM)","年龄(岁)"],["标准体重(斤)","超重(斤)"]],
        baseValueData:[["name","weight"],["tall","age"],["standardWeight","overWeight"]],
        heatData:[],
        heatTitleData:[["基础代谢BMR(千卡）","减脂最大摄入量(千卡)"],["每公斤体重占比(千卡)","目标(千卡)"],["减脂热量控制范围(千卡)",""]],
        heatValueData:[["metabolizeHeat","maxIntakeHeat"],["everyWeightHeat","targetEveryWeightHeat"],["standardEveryWeightHeat",""]],
        nutritData:[],
        nutritTitleData:["比例","热量(千卡)","质量(克)","每公斤体重占比(克/公斤)","今日已摄入量(千卡)","今日剩余可摄入量(千卡)"],
        nutritValueData:["nutritionalRate","nutritionalHeat","nutritionalQuality","weightNutritionalRate","ingestedNutritionalHeat","surplusNutritionalHeat"],
      };
    },
    created() {

    },
    methods: {
      showDialog(data, customerName) {
        this.title = customerName+"「"+ data.edibleDate+"」减脂营养热量计算";
        this.reset();
        this.getNutritionalDetail(data.id);
      },
      getNutritionalDetail(id){
        getNutritionalCalories(id).then((response) => {
            this.open = true;
            this.nutritionalData = response.data;
            if(this.nutritionalData == null || this.nutritionalData == undefined){
              this.showFlag = false;
              return;
            }
            this.baseValueData.forEach((item,index) => {
               var obj = {};
               obj["name_one"] = this.baseTitleData[index][0];
               obj["value_one"] = this.nutritionalData[item[0]];
               obj["name_two"] = this.baseTitleData[index][1];
               obj["value_two"] = this.nutritionalData[item[1]];
               this.baseData.push(obj);
            });
            this.heatValueData.forEach((item,index) => {
              var obj = {};
              obj["name_one"] = this.heatTitleData[index][0];
              obj["value_one"] = this.getHeatValue(item[0]);
              obj["name_two"] = this.heatTitleData[index][1];
              obj["value_two"] = this.getHeatValue(item[1]);
              this.heatData.push(obj);
            });
            this.nutritData.push({"name": "营养","value":["蛋白质","脂肪","碳水"]});
            this.nutritValueData.forEach((item,index) => {
              var obj = {};
              obj["name"] = this.nutritTitleData[index];
              obj["value"] = this.getNutritValue(item);
              this.nutritData.push(obj);
            });
        });
      },
      getHeatValue(item){
         if(item == "targetEveryWeightHeat" || item == "standardEveryWeightHeat"){
           return this.nutritionalData[item][0]+"-"+this.nutritionalData[item][1];
         }else{
           return item != "" ? this.nutritionalData[item] : "";
         }
      },
      getNutritValue(item){
         if(item == "nutritionalRate"){
           this.nutritionalData[item][0] += "%";
           this.nutritionalData[item][1] += "%";
           this.nutritionalData[item][2] += "%";
         }
         return this.nutritionalData[item];
      },
      reset(){
        this.showFlag = true;
        this.baseData = [];
        this.heatData = [];
        this.nutritData = [];
      },
      // 取消按钮
      cancel() {
        this.open = false;
      },
      // 自定义列背景色
      baseColumnStyle({ row, column, rowIndex, columnIndex }) {
        if (columnIndex == 0 || columnIndex == 2) {
          //第三第四列的背景色就改变了2和3都是列数的下标
          return "background:#f3f6fc;font-weight:bold";
        }else{
          return "background:#ffffff;";
        }
      },
      // 自定义列背景色
      nutritColumnStyle({ row, column, rowIndex, columnIndex }) {
        if (columnIndex == 0 || rowIndex == 0) {
          //第三第四列的背景色就改变了2和3都是列数的下标
          return "background:#f3f6fc;font-weight:bold";
        }else{
          return "background:#ffffff;";
        }
      }
    }
  };
</script>

<style scoped>
  .blue-color {
    font-size: 18px;
    font-weight: bold;
  }
</style>
