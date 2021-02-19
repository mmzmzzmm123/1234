<template>
  <section>
    <div style="padding: 5px; text-align: center">
      <img :src="logo" style="width: 150px; height: 35px" alt="logo" />
    </div>
    <!--<div style="margin: 10px 15px 10px 15px;" >
      <el-steps :active="stepActive" finish-status="success">
        <el-step v-for="(item,index) in stepArray" title=""></el-step>
      </el-steps>
    </div>-->
    <el-form ref="form" label-position="top" :model="form" :rules="rules" label-width="100px" style="padding: 16px">
      <div>
        <h3>个人信息</h3>
        <!--<div><span>{{form.name}}</span></div>-->
      </div>
      <el-form-item label="真实姓名" prop="name">
        <el-input v-model="customer.name" :readonly="true" placeholder="请输入真实姓名" maxlength="20"/>
      </el-form-item>
      <div>
        <h3>食材记录</h3>
        <!--<div><span>{{form.name}}</span></div>-->
      </div>
      <el-form-item label="已添加的食材" prop="name">
        <el-tag style="margin-left: 5px"
          v-for="tag in ingredientTagArray"
          :key="tag"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
          >
          {{tag}}
        </el-tag>
        <!--<el-tag class="el-icon-plus" style="margin-left: 5px">
          添加
        </el-tag>-->
      </el-form-item>
      <div>
        <el-form-item label="食材名称" prop="ingredient">
          <el-input v-model="form.ingredient"  placeholder="请输入食材名称" maxlength="50"/>
        </el-form-item>
        <el-form-item label="通俗计量" prop="numberUnit">
          <el-input-number v-model="form.number" controls-position="right" :controls="false" style="width: 48%" placeholder="请输入食材数量"  :step="1" :max="100"></el-input-number>
          <el-select v-model="form.unit" placeholder="请选择单位"  style="margin-left:5px;width: 50%"  filterable clearable>
            <el-option
              v-for="dict in cusUnitOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="重量(克)" prop="quantity">
          <el-input type="number" v-model="form.quantity"  placeholder="请输入食材重量（整数）" maxlength="10"/>
        </el-form-item>

      </div>
      <el-form-item style="text-align: center; margin: 40px auto" >
        <el-button type="primary" @click="continueAdd()" >继续添加</el-button>
        <el-button type="success" @click="submit()" >提交数据</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>
<script>
  import { getDictData,getCustomerBaseMessage,addFoodHeatStatistics } from "@/api/custom/customerInvestigation";
  const logo = require("@/assets/logo/st_logo.png");
  export default {
    name: "index",
    data() {
      return {
        logo,
        timer: null,
        customerExistFlag: false,
        submitFlag: false,
        customer:{
           customerEncId: null,
           name: null,
           phone: null
        },
        form: {
          ingredient: null,
          number: 0,
          unit: null,
          quantity: null,
        },
        rules: {
          ingredient: [{ required: true, trigger: "blur", message: "请输入食材名称" }]
        },
        ingredientTagArray:[

        ],
        ingredientArray: [

        ],
        //通俗计量单位
        cusUnitOptions:[]
      };
    },
    components: {

    },
    methods: {
      //根据用户ID获取用户基本信息（手机号、姓名）
      getCustomerBase(id){
        if(id == null || id == undefined){
          return;
        }
        getCustomerBaseMessage(id).then((response) => {
          if (response.code === 200) {
            if(response.data){
              this.customerExistFlag = true;
              this.customer.name = response.data.name;
              this.customer.phone = response.data.phone;
            }
          }
        }).catch(function() {
          console.log("error");
        });
      },
      continueAdd(){
        this.$refs.form.validate((valid) => {
          if (valid) {
            if(this.ingredientTagArray.indexOf(this.form.ingredient.trim()) == -1){
              this.ingredientArray.push(this.form);
              this.ingredientTagArray.push(this.form.ingredient);
              this.reset();
            }
          } else {
            this.$message({message: "食材数据未填写完整", type: "warning"});
          }
        });
      },
      reset(){
         this.form = {
           ingredient: null,
           number: 0,
           unit: null,
           quantity: null
         }
      },
      submit(){
        if (this.submitFlag) {
          this.$message({
            message: "请勿重复提交，1分钟后重试",
            type: "warning",
          });
          return;
        }
        this.timer = setTimeout(function(){
          this.submitFlag = false;
        },1000*60);
        if(this.form.ingredient &&  this.ingredientTagArray.indexOf(this.form.ingredient.trim()) == -1){
          this.ingredientArray.push(this.form);
          this.ingredientTagArray.push(this.form.ingredient);
          this.reset();
        }
        if(this.ingredientArray.length == 0){
          this.$message({message: "还未添加食材数据，无法提交", type: "warning"});
          return;
        }
        let submitObject = {};
        submitObject.ingredientArray = JSON.stringify(this.ingredientArray);
        submitObject.customerEncId = this.customer.customerEncId;
        this.submitFlag = true;
        addFoodHeatStatistics(submitObject).then((response) => {
          this.$notify({title: "提交成功", message: "", type: "success"});
        }).catch(function() {
          console.log("error");
        });
      },
      handleClose(tag) {
        this.ingredientTagArray.splice(this.ingredientTagArray.indexOf(tag), 1);
        let tagIndex = -1;
        this.ingredientArray.forEach((item, index) => {
           if(tag == item.ingredient){
              tagIndex = index;
           }
        });
        this.ingredientArray.splice(tagIndex, 1);
        console.log( JSON.stringify(this.ingredientArray))
      }
    },
    created() {
      this.customer.customerEncId = this.$route.params.id;
      this.getCustomerBase(this.customer.customerEncId);
      getDictData("cus_cus_unit").then(response => {
        this.cusUnitOptions = response.data;
      });
    },
    beforeCreate() {
      document.title = this.$route.meta.title;
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

  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
