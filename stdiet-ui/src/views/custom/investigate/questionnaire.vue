<template>
<section class="el-container is-vertical"><header class="el-header" style="height: 60px;">
  <div class="block" style="margin-top:18px;text-align:center;margin:0 auto;">
    <!--<span class="demonstration"></span>  require('@/assets/logo/st_logo.png')-->
    <img src="@/assets/logo/st_logo.png" style="width:240px;height:80px;"/>
  </div>
</header> <main class="el-main">
  <el-form ref="form" :model="form" :rules="rules" label-width="80px" style="margin-top:40px;">
    <!--<p>现在要先为您建立更加详细的档案，以便为您定制专属的减脂计划</p>-->
    <el-form-item label="真实姓名" prop="name">
      <el-input v-model="form.name" placeholder="请输入真实姓名" />
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入手机号" />
    </el-form-item>
    <!--<el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email" placeholder="请输入邮箱" />
    </el-form-item>-->
    <!--<el-form-item label="您的居住地址" prop="address">
      <el-input v-model="form.address" placeholder="请输入地址" />
    </el-form-item>-->
    <el-form-item label="性别" prop="sex">
      <el-select v-model="form.sex" placeholder="请选择性别">
        <el-option label="男" value="0" />
        <el-option label="女" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="年龄(岁)" prop="age">
      <el-input v-model="form.age" placeholder="请输入年龄" />
    </el-form-item>
    <el-form-item label="身高(厘米)" prop="tall">
      <el-input v-model="form.tall" placeholder="请输入身高" />
    </el-form-item>
    <el-form-item label="体重(斤)" prop="weight">
      <el-input v-model="form.weight" placeholder="请输入体重" />
    </el-form-item>
    <el-form-item label="南方人还是北方人" prop="position">
      <el-select v-model="form.position" placeholder="请选择">
        <el-option label="南方" value="0" />
        <el-option label="北方" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="病史(多选)" prop="physicalSignsId">
      <el-select v-model="form.physicalSignsId" multiple placeholder="请选择">
        <el-option
          v-for="physicalSign in physicalSignsList"
          :key="physicalSign.id" :label="physicalSign.name" :value="physicalSign.id">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="忌口或过敏源" prop="dishesIngredientId">
      <el-input v-model="form.dishesIngredientId" placeholder="请输入名称" />
    </el-form-item>
    <!--<el-form-item label="您的照片" prop="photo">
      <el-input v-model="form.photo" placeholder="请输入客户照片" />
    </el-form-item>-->
    <el-form-item label="是否便秘" prop="constipation">
      <el-select v-model="form.constipation" placeholder="请选择">
        <el-option label="是" value="0" />
        <el-option label="否" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="是否熬夜、失眠" prop="staylate">
      <el-select v-model="form.staylate" placeholder="请选择">
        <el-option label="是" value="0" />
        <el-option label="否" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="是否经常运动" prop="motion">
      <el-select v-model="form.motion" placeholder="请选择">
        <el-option label="是" value="0" />
        <el-option label="否" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="饮食方式" prop="makeFoodType">
      <el-select v-model="form.makeFoodType" placeholder="请选择">
        <el-option label="自己做" value="0" />
        <el-option label="外面吃" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="饮食特点" prop="makeFoodTaste">
      <el-select v-model="form.makeFoodTaste" placeholder="请选择">
        <el-option label="清淡" value="0" />
        <el-option label="重口味" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="工作职业" prop="vocation">
      <el-input v-model="form.vocation" placeholder="请输入工作职业" />
    </el-form-item>
    <el-form-item label="是否上夜班" prop="night">
      <el-select v-model="form.night" placeholder="请选择">
        <el-option label="是" value="0" />
        <el-option label="否" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="平时久坐多还是走动多" prop="walk">
      <el-select v-model="form.walk" placeholder="请选择">
        <el-option label="久坐多" value="0" />
        <el-option label="走动多" value="1" />
      </el-select>
    </el-form-item>

    <el-form-item label="是否浑身乏力" prop="weakness">
      <el-select v-model="form.weakness" placeholder="请选择">
        <el-option label="是" value="0" />
        <el-option label="否" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="是否减脂反弹" prop="rebound">
      <el-select v-model="form.rebound" placeholder="请选择">
        <el-option label="是" value="0" />
        <el-option label="否" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="是否意识到生活习惯是减脂关键" prop="crux">
      <el-select v-model="form.crux" placeholder="请选择">
        <el-option label="是" value="0" />
        <el-option label="否" value="1" />
      </el-select>
    </el-form-item>
    <el-form-item label="睡觉时间" prop="sleepTime">
      <el-time-select v-model="form.sleepTime" :picker-options="{start: '00:00',step: '01:00',end: '24:00'}" placeholder="请选择时间" :editable=false />
    </el-form-item>
    <el-form-item label="起床时间" prop="getupTime">
      <el-time-select v-model="form.getupTime" :picker-options="{start: '00:00',step: '01:00',end: '24:00'}" placeholder="请选择时间" :editable=false />
    </el-form-item>
    <el-form-item label="方便沟通时间" prop="connectTime">
      <el-time-select v-model="form.connectTime" :picker-options="{start: '00:00',step: '01:00',end: '24:00'}" placeholder="请选择时间" :editable=false />
    </el-form-item>
    <!--<el-form-item label="备注信息" prop="remarks">
      <el-input v-model="form.remarks" placeholder="请输入备注信息" />
    </el-form-item>-->
    <!--<p>好的，我现在给您测一下湿气和气血，有以下出现情况的请直接选择</p>-->
    <el-form-item label="湿气(多选)" prop="bloodData">
      <el-checkbox-group v-model="form.bloodData">
        <el-checkbox v-for="bloodItem in bloodDataList" :label="bloodItem.dictValue"  :key="bloodItem.dictValue">{{bloodItem.dictLabel}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="气血(多选)" prop="moistureDate">
      <el-checkbox-group v-model="form.moistureDate">
        <el-checkbox v-for="moistureItem in moistureDataList" :label="moistureItem.dictValue" :key="moistureItem.dictValue">{{moistureItem.dictLabel}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="减脂经历（重点详细填写）" prop="experience">
      <el-input v-model="form.experience" placeholder="请输入" />
    </el-form-item>
    <el-form-item label="减脂遇到的困难" prop="difficulty">
      <el-input v-model="form.difficulty" placeholder="请输入" />
    </el-form-item>
    <el-form-item style="text-align:center;margin:0 auto;">
      <el-button type="primary" @click="addCustomer()" style="margin-right:50px;">已填写完成，提交数据</el-button>
    </el-form-item>
  </el-form>

</main></section>

</template>
<script>
  import { physicalSignsList,addCustomer,getDictData } from "@/api/custom/customerInvestigation";
  import { listPhysicalSigns } from "@/api/custom/physicalSigns";
  //imgUrl = require('@/assets/logo/st_logo.png')
  export default {
    name: "Questionnaire",
    data() {
      return {
        imagePath: "../assets/logo/st_logo.png",
        submitFlag: false,
        form: {
          name: '',
          phone: '',
          address: "",
          sex: "1",
          age: null,
        tall: null,
        weight: null,
        physicalSignsId: [],
        dishesIngredientId: null,
        photo: null,
        constipation: "0",
        staylate: "0",
        motion: "1",
        makeFoodType: "1",
        makeFoodTaste: "1",
        walk: "0",
        difficulty: null,
        weakness: "0",
        rebound: "0",
        crux: "0",
        position: "1",
        sleepTime: null,
        getupTime: null,
        connectTime: null,
        remarks: null,
        bloodData: [],
        moistureDate: [],
          night: "0",
          vocation: null,
          experience: null
        },
        rules: {
          name: [
            { required: true, trigger: "blur", message: "请填写姓名" },
            { min: 1, max: 20, trigger: "blur", message: "姓名过长" }
          ],
          phone: [
            { required: true, trigger: "blur", message: "请填写手机号" },
            { required: true, trigger: "blur", message: "请填写正确的手机号" },
            { required: true, trigger: "blur", pattern: /^[0-9]{5,11}$/ , message: '手机号格式不正确'}
          ],
          sex:[
            { required: true, trigger: "blur", message: "请选择性别" }
          ],
          age:[
            { required: true, trigger: "blur", message: "请填写年龄" },
            {required: true, trigger: "blur", pattern: /^[1-9]\d*$/ , message: '年龄格式不正确'}
          ],
          tall:[
            { required: true, trigger: "blur", message: "请填写身高" },
            {required: true, trigger: "blur", pattern: /^[1-9]\d*$/ , message: '身高格式不正确'}
          ],
          weight:[
            { required: true, trigger: "blur", message: "请填写体重" },
            {required: true, trigger: "blur", pattern: /^[1-9]\d*$/ , message: '体重格式不正确'}
          ],
          constipation:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          staylate:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          motion:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          makeFoodType:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          makeFoodTaste:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          walk:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          weakness:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          rebound:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          crux:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          position:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          sleepTime:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          getupTime:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          connectTime:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          night:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          /*bloodData:[
            { required: true, trigger: "blur", message: "请测试气血" }
          ],
          moistureDate:[
            { required: true, trigger: "blur", message: "请测试湿气" }
          ]*/
        },
        physicalSignsList: [],
        bloodDataList:[

        ],
        moistureDataList:[

        ]
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      /** 查询体征列表 */
      getPhysicalSignsList() {
        physicalSignsList().then(response => {
          this.physicalSignsList = response.rows;
        });
      },
      addCustomer(){
        if(this.submitFlag){
          return;
        }
        this.$refs.form.validate(valid => {
          if(valid) {
            let cusMessage = Object.assign({}, this.form);
            cusMessage.bloodData = cusMessage.bloodData != null ? cusMessage.bloodData.join(",") : null;
            cusMessage.moistureDate = cusMessage.moistureDate != null ? cusMessage.moistureDate.join(",") : null;
            cusMessage.physicalSignsId = cusMessage.physicalSignsId != null ? cusMessage.physicalSignsId.join(",") : null;
            cusMessage.sleepTime = cusMessage.sleepTime.substring(0,2);
            cusMessage.getupTime = cusMessage.getupTime.substring(0,2);
            cusMessage.connectTime = cusMessage.connectTime.substring(0,2);
            addCustomer(cusMessage).then(response => {
              if (response.code === 200) {
                  console.log('成功');
                  this.$notify({
                    title: '提交成功',
                    message: '',
                    type: 'success'
                  });
                  this.submitFlag = true;
               }
            });
          }else{
            this.$message({
              message: '数据未填写完整',
              type: 'warning'
            });
          }
          //console.log(this.form.bloodData);
        })
      },
      getBloodDictData(type){
        getDictData(type).then(response => {
          this.bloodDataList = response.data;
        });
      },
      getMoistureDictData(type){
        getDictData(type).then(response => {
          this.moistureDataList = response.data;
        });
      }
    },
    created() {
      this.getPhysicalSignsList();
      this.getBloodDictData("sys_blood_data");
      this.getMoistureDictData("sys_moisture_data");
    },
    beforeCreate () {
      document.title = this.$route.meta.title;
      console.log(this.$route.meta.title);
    }

  }
</script>

 <style scoped>


</style>
