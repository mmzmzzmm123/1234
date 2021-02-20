<template>
  <!-- 计算食材热量对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
    <el-form ref="form" :model="form" label-position="top" :rules="rules" label-width="100px">

      <el-form-item v-for="(item,index) in foodHeatList" label="" class="margin-left">
        <div>
          <span>食材名称：</span><el-input style="width:30%" placeholder="" :readonly="true" :value="item.ingredient"/>
          <span style="margin-left: 10px">份量：</span><el-input style="width:25%" placeholder="" :readonly="true"  :value="getNumberString(item)"/>
          <span style="margin-left: 10px">热量：</span><el-input style="width:15%" type="number" placeholder="" v-model="item.heatValue"/><span>千卡</span>
        </div>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>

  import { getFoodHeatStatistics,addFoodHeatData } from "@/api/custom/foodHeatStatistics";
  import {getOptions} from "@/api/custom/order";

  export default {
    name: "index",
    components: {

    },
    props: {},
    data() {
      return {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        callback: undefined,
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          projectId:[
            {required: true, message: "请选择调理项目", trigger: "blur"}
          ]
        },
        heatData: null,
        foodHeatList: []
      };
    },
    created() {

    },
    methods: {
      showDialog(data, callback) {
        this.callback = callback;
        this.reset(data);
        this.title = "计算"+`「${data.edibleDate}」食材热量`;
        this.open = true;
        this.getFoodHeatList(data.id);
      },
      getFoodHeatList(id){
        getFoodHeatStatistics(id).then((response) => {
           //let contractDetail = response.data;
            this.heatData = response.data;
           this.foodHeatList = response.data.foodHeatStatisticsList != null ? response.data.foodHeatStatisticsList : [];
        });
      },
      getNumberString(foodData){
         let numberString = "";
         if(foodData.number){
           numberString += foodData.number + foodData.unitName;
         }
         if(foodData.quantity){
           numberString += (numberString != ""  ?  "/" : "" ) + foodData.quantity + "克";
         }
         return numberString;
      },
      // 表单重置
      reset(obj) {
        this.heatData = null;
        this.foodHeatList = [];
        this.resetForm("form");
      },
      // 取消按钮
      cancel() {
        this.open = false;
      },
      /** 提交按钮 */
      submitForm() {
        if(this.foodHeatList.length == 0){
           return;
        }
        let obj = {};
        obj.foodHeatIdList = [];
        obj.foodHeatList = [];
        obj.id = this.heatData.id;
        obj.customerId = this.heatData.customerId;
        this.foodHeatList.forEach((item,index) => {
          obj.foodHeatIdList.push(item.id);
          if(!/^[1-9]\d*$/.test(item.heatValue)){
            obj.foodHeatList.push(0);
          }else{
            obj.foodHeatList.push(item.heatValue);
          }
        });
        console.log(obj.foodHeatIdList.length);
        addFoodHeatData(obj).then(response => {
          if (response.code === 200) {
            this.msgSuccess("提交成功");
            this.open = false;
            this.callback && this.callback();
          }
        });
      }
    }
  };
</script>
