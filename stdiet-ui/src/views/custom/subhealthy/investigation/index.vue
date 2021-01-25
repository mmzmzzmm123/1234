<template>
  <section>
    <div style="padding: 16px; text-align: center">
      <img :src="logo" style="width: 258px; height: 80px" alt="logo" />
    </div>
    <div style="margin: 20px 15px 20px 15px;" >
      <el-steps :active="stepActive" finish-status="success">
        <el-step v-for="(item,index) in stepArray" title=""></el-step>
      </el-steps>
    </div>
    <el-form ref="form" label-position="top" :model="form" :rules="rules" label-width="100px" style="padding: 16px">
        <div v-show="stepArray[0]">
        <p class="p_title_1" style="margin-top: 5px;">一、基础信息</p>
        <el-form-item label="真实姓名" prop="name" style="padding-top: 10px;">
          <el-input v-model="form.name" placeholder="请输入真实姓名" maxlength="20"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex" size="small" >
            <el-radio :label="parseInt('0')"  border>男</el-radio>
            <el-radio :label="parseInt('1')" border>女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input type="number" v-model="form.age" placeholder="请输入年龄" autocomplete="off" maxlength="3"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" maxlength="20">
          <el-input type="number" v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>

          <el-form-item label="调理项目" prop="conditioningProjectId">
            <el-select v-model="form.conditioningProjectId" placeholder="请选择">
              <el-option
                v-for="dict in conditioningProjectIdOption"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              />
            </el-select>
          </el-form-item>
      </div>

      <div v-show="stepArray[1]">
      <p class="p_title_1">二、食品安全评估</p>
      <p class="p_title_2">1、家庭调味品</p>
      <el-form-item :label="'(1) 调味品种类'" prop="condiment" class="margin-left">
        <el-checkbox-group v-model="form.condiment">
          <el-checkbox v-for="(item, index) in condimentArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他调味品 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherCondiment" placeholder="请输入其他调味品名称" /></div>
      </el-form-item>
      <p class="p_title_2">2、喜好的烹调方式和周频次</p>
      <el-form-item :label="'(1) 喜好的烹调方式'" prop="cookingStyle" class="margin-left">
        <el-checkbox-group v-model="form.cookingStyle">
            <el-checkbox v-for="(item,index) in cookingStyleArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(2) 烹调方式的频次（每周）'" prop="cookingStyleRate" class="margin-left">
        <div>
          <span>煎&nbsp;</span><el-input-number  style="width:35%" v-model="form.cookingStyleRate[0]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">炸&nbsp;</span><el-input-number  style="width:35%" v-model="form.cookingStyleRate[1]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
        <div style="margin-top: 5px;">
          <span>卤&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[2]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">腌&nbsp;</span><el-input-number   style="width:35%" v-model="form.cookingStyleRate[3]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
        <div style="margin-top: 5px;">
          <span>腊&nbsp;</span><el-input-number style="width:35%"  v-model="form.cookingStyleRate[4]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">煲&nbsp;</span><el-input-number  style="width:35%;" v-model="form.cookingStyleRate[5]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
      </el-form-item>
      <p class="p_title_2">3、洗菜方式</p>
      <el-form-item :label="'(1) 洗菜方式'" prop="washVegetablesStyle" class="margin-left">
        <el-checkbox-group v-model="form.washVegetablesStyle">
          <el-checkbox v-for="(item,index) in washVegetablesStyleArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他洗菜方式 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherWashVegetablesStyle" placeholder="请输入其他洗菜方式" /></div>
      </el-form-item>
      </div>

      <div v-show="stepArray[2]">
      <p class="p_title_1">三、饮食结构评估</p>
      <p class="p_title_2">1、您三餐的习惯</p>
      <el-form-item :label="'(1) 早餐习惯'" prop="breakfast" class="margin-left">
        <el-radio-group v-model="form.breakfastType">
          <el-radio v-for="(item,index) in breakfastTypeArray" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
        <div style="margin-top:8px;"><span>早餐通常吃&nbsp;</span><el-input  v-model="form.breakfastFood" style="width:70%" placeholder="请输入早餐名称" /></div>
      </el-form-item>
      <el-form-item :label="'(2) 午餐习惯'" prop="lunchType" class="margin-left">
        <el-checkbox-group v-model="form.lunchType">
          <el-checkbox v-for="(item,index) in lunchTypeArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(3) 晚餐习惯'" prop="dinner" class="margin-left">
        <el-checkbox-group v-model="form.dinner">
          <el-checkbox v-for="(item,index) in dinnerArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(4) 正餐荤素中素菜占比几成'" prop="eatSituation" class="margin-left">
        <el-input-number v-model="form.vegetableRate" :step="1" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item :label="'(4) 最常吃的肉类'" prop="eatSituation" class="margin-left">
        <el-input v-model="form.commonMeat" placeholder="请输入肉类名称"></el-input>
      </el-form-item>
      <p class="p_title_2">2、您晚餐时间点和夜宵习惯</p>
      <el-form-item :label="'(1) 晚餐时间点'" prop="dinnerTime" class="margin-left">
        <el-time-select v-model="form.dinnerTime" :picker-options="{ start: '17:00', step: '00:30', end: '24:00' }" placeholder="请选择时间" :editable="false"/>
      </el-form-item>
      <el-form-item :label="'(2) 您每周吃几次夜宵'" prop="supperNum" class="margin-left">
        <el-input-number v-model="form.supperNum" :step="1" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item :label="'(3) 您夜宵通常吃的食物'" prop="supperFood" class="margin-left">
        <el-input style="width:100%" v-model="form.supperFood" placeholder="请输入食物名称"></el-input>
      </el-form-item>
      <p class="p_title_2">3、您的饮食偏好</p>
      <el-form-item :label="'(1) 冷热偏好'" prop="dietHotAndCold" class="margin-left">
        <el-radio-group v-model="form.dietHotAndCold">
          <el-radio v-for="(item,index) in dietHotAndColdArray" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="'(2) 口味偏好'" prop="dietFlavor" class="margin-left">
        <el-checkbox-group v-model="form.dietFlavor">
          <el-checkbox v-for="(item,index) in dietFlavorArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">4、生食果蔬状况</p>
      <el-form-item :label="'(1) 平均每周吃生/拌菜几次'" prop="vegetablesNum" class="margin-left">
        <el-input-number v-model="form.vegetablesNum" :step="1" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item :label="'(2) 每周吃生/拌菜的频次'" prop="vegetablesRateType" class="margin-left">
        <el-radio-group v-model="form.vegetablesRateType">
          <el-radio v-for="(item,index) in vegetablesRateTypeArray" :label="item.value" :key="index">{{item.name}}</el-radio>
          <!-- <div><el-radio label="每天吃" key="1">每天吃</el-radio>
           <el-radio label="经常吃" key="2">经常吃</el-radio>
           <el-radio label="偶尔吃" key="3">偶尔吃</el-radio></div>
           <div style="margin-top: 10px;"><el-radio label="从不吃" key="4">从不吃</el-radio></div>-->
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="'(3) 平均每天吃水果几次以及时间'" prop="fruitsNum" class="margin-left">
        <el-input-number v-model="form.fruitsNum" :step="1" :min="0"></el-input-number>
        <el-radio-group v-model="form.fruitsTime" style="margin-top: 15px;">
          <el-radio v-for="(item,index) in fruitsTimeArray" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(4) 平时吃水果的频次" prop="fruitsRate" class="margin-left">
        <el-radio-group v-model="form.fruitsRate">
          <el-radio v-for="(item,index) in fruitsRateArray" :label="item.value" :key="index">{{item.name}}</el-radio>
          <!--<div>
          <el-radio label="每天吃" key="1">每天吃</el-radio>
          <el-radio label="经常吃" key="2">经常吃</el-radio>
          <el-radio label="偶尔吃" key="3">偶尔吃</el-radio>
        </div>
          <div style="margin-top: 10px;"><el-radio label="从不吃" key="4">从不吃</el-radio></div>-->
        </el-radio-group>
      </el-form-item>
      <p class="p_title_2">5、饮食习惯</p>
      <el-form-item label="(1) 一餐吃几碗饭，几成饱" prop="riceNum" class="margin-left">
        <div>
          <el-input-number style="width:35%" v-model="form.riceNum" :step="1" :min="0"></el-input-number><span>&nbsp;碗饭</span>
          <el-input-number style="width:35%;margin-left: 20px;" v-model="form.riceFull" :step="1" :min="0" ></el-input-number><span>&nbsp;成饱</span>
        </div>
      </el-form-item>
      <el-form-item label="(2) 吃饭速度" prop="eatingSpeed" class="margin-left">
        <el-radio-group v-model="form.eatingSpeed">
          <el-radio v-for="(item,index) in eatingSpeedArray" :label="item.value" :key="index">{{item.name}}</el-radio>
          </el-radio-group>
      </el-form-item>
      <p class="p_title_2">6、您常吃的零食</p>
      <el-form-item label="(1) 常吃的零食" prop="snacks" class="margin-left">
        <el-checkbox-group v-model="form.snacks">
          <el-checkbox v-for="(item,index) in snacksArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他零食 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherSnacks" placeholder="请输入其他零食名称" /></div>
      </el-form-item>
      <p class="p_title_2">7、服用营养保健品状况</p>
      <el-form-item label="(1) 有无服用营养保健" prop="healthProductsFlag" class="margin-left">
        <el-radio-group v-model="form.healthProductsFlag">
          <el-radio :label="0" key="1">无</el-radio>
          <el-radio :label="1" key="2">有</el-radio>
        </el-radio-group>
        <div><span>营养保健品牌名 </span><el-input style="margin-top: 10px;" v-model="form.healthProductsBrand" placeholder="请输入营养保健品牌名" /></div>
        <div><span>营养保健产品名 </span><el-input style="margin-top: 10px;" v-model="form.healthProductsName" placeholder="请输入营养保健产品名" /></div>
      </el-form-item>
      <el-form-item label="(2) 服用营养保健频率" prop="healthProductsRate" class="margin-left">
        <div>
          <el-input-number style="width:35%" v-model="form.healthProductsWeekRate" :step="1" ::min="0"></el-input-number><span>&nbsp;次/周</span>
          <el-input-number style="width:35%;margin-left: 20px;" v-model="form.healthProductsDayRate" :step="1" ::min="0" ></el-input-number><span>&nbsp;次/天</span>
        </div>
      </el-form-item>
      </div>
      <div v-show="stepArray[3]">
      <p class="p_title_1">四、生活习惯评估</p>
      <p class="p_title_2">1、您每天的饮水习惯</p>
      <el-form-item label="(1) 每天饮水量（毫升）" prop="waterNum" class="margin-left">
        <el-input-number v-model="form.waterNum" :step="50" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="(2) 喜欢喝什么水" prop="waterType" class="margin-left">
        <el-checkbox-group v-model="form.waterType">
          <el-checkbox v-for="(item,index) in waterTypeArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="(3) 喝水习惯" prop="waterHabit" class="margin-left">
        <el-checkbox-group v-model="form.waterHabit">
          <el-checkbox v-for="(item,index) in waterHabitArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">2、您常喝的饮品和每周频次</p>
      <el-form-item label="(1) 每周常喝的饮品次数" prop="drinksNum" class="margin-left">
        <div>
          <span>老火汤&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 13px;" v-model="form.drinksNum[0]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>咖啡&nbsp;</span><el-input-number style="width:35%;margin-left: 25px;" v-model="form.drinksNum[1]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>浓茶&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[2]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>奶茶&nbsp;</span><el-input-number  type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[3]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>冷饮&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[4]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>碳酸饮料&nbsp;</span><el-input-number type="number" style="width:35%;" v-model="form.drinksNum[5]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>甜饮料&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 13px;" v-model="form.drinksNum[6]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>鲜榨果汁&nbsp;</span><el-input-number type="number" style="width:35%;" v-model="form.drinksNum[7]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
      </el-form-item>
      <p class="p_title_2">3、您的饮酒习惯</p>
      <el-form-item label="(1) 是否喝酒" prop="drinkWineFlag" class="margin-left">
        <el-radio-group v-model="form.drinkWineFlag">
          <el-radio v-for="(item,index) in drinkWineFlagArray" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(2) 通常喝什么酒" prop="drinkWineClassify" class="margin-left">
        <el-checkbox-group v-model="form.drinkWineClassify">
          <el-checkbox v-for="(item,index) in drinkWineClassifyArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他酒 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherWineClassify" placeholder="请输入其他酒名称" /></div>
      </el-form-item>
      <el-form-item label="(3) 通常喝多少" prop="drinkWineAmount" class="margin-left">
        <div>
          <span>白酒</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[0]" :step="1" :min="0"></el-input-number><span>&nbsp;两</span>
        </div>
        <div class="margin-top-10">
          <span>啤酒</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[1]" :step="1" :min="0"></el-input-number><span>&nbsp;瓶</span>
        </div>
        <div class="margin-top-10">
          <span>红酒</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[2]" :step="100" :min="0"></el-input-number><span>&nbsp;毫升</span>
        </div>
        <div class="margin-top-10">
          <span>其他</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[3]" :step="100" :min="0"></el-input-number><span>&nbsp;毫升</span>
        </div>
      </el-form-item>
      <p class="p_title_2">4、您的吸烟习惯</p>
      <el-form-item label="(1) 是否吸烟" prop="smokeFlag" class="margin-left">
        <el-radio-group v-model="form.smokeFlag">
          <el-radio :label="0" key="1">否</el-radio>
          <el-radio :label="1" key="2">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(2) 吸烟频次以及烟龄" prop="smokeRate" class="margin-left">
        <div>
          <span>每天抽烟</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[0]" :step="1" :min="0"></el-input-number><span>次</span>
        </div>
        <div class="margin-top-10">
          <span>烟龄</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[1]" :step="1" :min="0"></el-input-number><span>年</span>
        </div>
        <div class="margin-top-10">
          <span>已戒烟</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[2]" :step="1" :min="0"></el-input-number><span>年</span>
        </div>
      </el-form-item>
      <el-form-item label="(3) 是否常吸二手烟" prop="secondSmoke" class="margin-left">
        <el-radio-group v-model="form.secondSmoke">
          <el-radio :label="0" key="1">否</el-radio>
          <el-radio :label="1" key="2">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <p class="p_title_2">4、您的工作状态</p>
      <el-form-item label="(1) 工作行业" prop="workIndustry" style="padding-top: 10px;">
        <el-input v-model="form.workIndustry" placeholder="请输入工作行业" />
      </el-form-item>
      <el-form-item label="(2) 工作性质" prop="workType" style="padding-top: 10px;">
        <el-checkbox-group v-model="form.workType">
          <el-checkbox v-for="(item,index) in workTypeArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">4、您的排便状况</p>
      <el-form-item label="(1) 排便次数" prop="defecationNum" style="padding-top: 10px;">
        <el-radio-group v-model="form.defecationNum">
          <el-radio :label="1" key="1">1次/天</el-radio>
          <el-radio :label="2" key="2">2次/天</el-radio>
          <el-radio :label="3" key="3">3次/天</el-radio>
        </el-radio-group>
        <div class="margin-top-10"><span>其他次数 </span><el-input-number style="width:35%;margin-right: 10px;" type="number" v-model="form.otherDefecationNum" :step="1" :min="0"></el-input-number><span>次/天</span></div>
      </el-form-item>

      <el-form-item label="(2) 排便时间" prop="defecationTime" style="padding-top: 10px;">
        <el-checkbox-group v-model="form.defecationTime">
          <el-checkbox v-for="(item,index) in defecationTimeArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="(2) 排便情况" prop="defecationSituation" style="padding-top: 10px;">
        <div>
          <span>形状</span><el-input class="width-70-left-8-right-5" v-model="form.defecationShape" placeholder="请输入形状" />
        </div>
        <div class="margin-top-10">
          <span>气味</span><el-input class="width-70-left-8-right-5" v-model="form.defecationSmell" placeholder="请输入气味" />
        </div>
        <div class="margin-top-10">
          <span>速度</span><el-input class="width-70-left-8-right-5" v-model="form.defecationSpeed" placeholder="请输入速度" />
        </div>
        <div class="margin-top-10">
          <span>颜色</span><el-input class="width-70-left-8-right-5" v-model="form.defecationColor" placeholder="请输入颜色" />
        </div>
      </el-form-item>
      </div>
      <div v-show="stepArray[4]">
      <p class="p_title_1">五、运动习惯评估</p>
      <p class="p_title_2">1、运动频率</p>
      <el-form-item label="(1) 每周运动情况" prop="motionSituation" class="margin-left">
        <div>
          <span>每周运动次数</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.motionNum" :step="1" :min="0"></el-input-number><span>次</span>
        </div>
        <div class="margin-top-10">
          <span>每次运动时长</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.motionDuration" :step="1" :min="0"></el-input-number><span>分钟</span>
        </div>
        <div class="margin-top-10">
          <span>每天运动时间</span><el-time-select class="width-50-left-8-right-5" v-model="form.motionTime" :picker-options="{ start: '00:00', step: '01:00', end: '24:00' }" placeholder="请选择时间" :editable="false"/><span>点</span>
        </div>
      </el-form-item>
      <p class="p_title_2">2、运动方式、项目</p>
      <!--<el-form-item label="(1) 运动方式" prop="motionMode" class="margin-left">
        <el-radio-group v-model="form.motionMode">
          <el-radio label="有氧" key="1">有氧</el-radio>
          <el-radio label="无氧" key="2">无氧</el-radio>
          <el-radio label="有氧无氧相结合" key="3">有氧无氧相结合</el-radio>
        </el-radio-group>
      </el-form-item>-->
      <el-form-item label="(1) 运动方式、项目" prop="motionProject" class="margin-left">
        <div><span>有氧运动</span>
          <el-checkbox-group v-model="form.aerobicMotionClassify">
            <el-checkbox v-for="(item,index) in aerobicMotionClassifyArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>有氧运动</span>
          <el-checkbox-group v-model="form.anaerobicMotionClassify">
            <el-checkbox v-for="(item,index) in anaerobicMotionClassifyArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>有氧无氧结合运动</span>
          <el-checkbox-group v-model="form.anaerobicAerobicMotionClassify">
            <el-checkbox v-for="(item,index) in anaerobicAerobicMotionClassifyArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>其他项目 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionClassify" placeholder="请输入其他运动项目名称" /></div>
      </el-form-item>
      <el-form-item label="(2) 运动场地" prop="motionField" class="margin-left">
        <el-checkbox-group v-model="form.motionField">
          <el-checkbox v-for="(item,index) in motionFieldArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他场地 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionField" placeholder="请输入其他运动场地名称" /></div>
      </el-form-item>
      </div>
      <div v-show="stepArray[5]">
      <p class="p_title_1">六、睡眠质量评估</p>
      <p class="p_title_2">1、您的睡眠状况</p>
      <el-form-item label="(1) 一般晚上几点睡" prop="sleepTime" class="margin-left">
        <el-time-select v-model="form.sleepTime" :picker-options="{ start: '00:00', step: '01:00', end: '24:00' }" placeholder="请选择时间" :editable="false"/>
      </el-form-item>
      <el-form-item label="(2) 睡眠质量" prop="sleepQuality" class="margin-left">
        <el-checkbox-group v-model="form.sleepQuality">
          <el-checkbox v-for="(item,index) in sleepQualityArray" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">2、辅助入睡药物情况</p>
      <el-form-item label="(1) 是否有辅助入睡药物" prop="sleepDrugFlag" class="margin-left">
        <el-radio-group v-model="form.sleepDrugFlag">
          <el-radio :label="0" key="1">无</el-radio>
          <el-radio :label="1" key="2">有</el-radio>
        </el-radio-group>
        <div><span>辅助药物名称 </span><el-input style="margin-top: 5px;" v-model="form.sleepDrug" placeholder="请输入辅助入睡药物名称" /></div>
      </el-form-item>
      <p class="p_title_2">3、熬夜情况</p>
      <el-form-item label="(1) 是否经常熬夜（超11:00）" prop="stayupLateFlag" class="margin-left">
        <el-radio-group v-model="form.stayupLateFlag">
          <el-radio :label="0" key="1">无</el-radio>
          <el-radio :label="1" key="2">有</el-radio>
        </el-radio-group>
        <div class="margin-top-10">
          <span>熬夜频率 </span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.stayupLateWeekNum" :step="1" :min="0"></el-input-number><span>次/周</span>
        </div>
      </el-form-item>
      </div>
      <div v-show="stepArray[6]">
      <p class="p_title_1">七、既往病史/用药史评估</p>
      <p class="p_title_2">1、家族疾病史情况</p>
      <el-form-item label="（1）家族疾病史（直系亲属例如爸爸妈妈、爷爷奶奶、外公外婆有相关疾病）（可多选）" prop="familyIllnessHistory" class="margin-left">
        <el-checkbox-group v-model="form.familyIllnessHistory">
          <el-checkbox v-for="(item, index) in familyIllnessHistoryArray" :key="index" :label="item.value" >{{item.name}}</el-checkbox>
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
      <p class="p_title_2">2、手术情况</p>
      <el-form-item label="(1) 手术史，因病进行过手术治疗，手术的部分（可多选）" prop="familyIllnessHistory" class="margin-left">
        <el-checkbox-group v-model="form.operationHistory">
          <el-checkbox v-for="(item, index) in operationHistoryArray" :key="index" :label="item.value" >{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他手术史</span>
          <el-input
            type="textarea"
            placeholder="请输入其他手术史"
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
      <p class="p_title_2">3、药物情况</p>
      <el-form-item label="(1) 是否长期服用药物（连续服用6个月以上，平均每日服用一次）" prop="longEatDrugFlag" class="margin-left">
        <el-radio-group v-model="form.longEatDrugFlag">
          <el-radio :label="0" key="1">否</el-radio>
          <el-radio :label="1" key="2">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(2) 长期服用药物有（可多选）" prop="longEatDrugClassify" class="margin-left">
        <el-checkbox-group v-model="form.longEatDrugClassify">
          <el-checkbox v-for="(item, index) in longEatDrugClassifyArray" :key="index" :label="item.value">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他长期服用的药物</span>
          <el-input
            type="textarea"
            placeholder="请输入其他药物"
            v-model="form.otherLongEatDrugClassify"
            maxlength="200"
            show-word-limit
            rows="3"
          ></el-input>
          </div>
      </el-form-item>
      <p class="p_title_2">4、过敏史</p>
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
          <el-checkbox v-for="(item, index) in allergenArray" :key="index" :label="item.value">{{item.name}}</el-checkbox>
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

      <div v-show="stepArray[7]">
        <p class="p_title_1">八、体检报告</p>
        <p class="p_title_2">1、体检报告</p>
        <el-form-item label="(1) 请上传相应的体检报告" prop="fileList" class="margin-left">
          <el-upload style="margin-left: 20px;"
            class="upload-demo"
            ref="upload"
            :action="upload.url"
            :limit="upload.limit"
            :disabled="upload.isUploading"
            :file-list="upload.fileList"
            :multiple="upload.multiple"
            :on-change="handleFileChange"
            :on-exceed="handleFileexceed"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
            :on-error="handleFileFail"
            :data="upload.data"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button style="margin-left: 10px;" size="small" @click="upload.fileList = []">重置</el-button>
          <div slot="tip" class="el-upload__tip">提示：最多可上传三份，且每份文件不超过5M</div>
        </el-upload>
        </el-form-item>
      </div>
      <el-form-item style="text-align: center; margin: 30px auto" >
        <el-button
          @click="nextStep(-1)"
          style="margin-right: 10px;width: 40%"
          v-show="stepActive != 0"
        >上一步</el-button
        >
        <el-button
          type="primary"
          @click="nextStep(1)"
          style="width: 40%"
          v-show="stepActive != stepArray.length-1"
          >下一步</el-button
        >
        <el-button
          type="primary"
          @click="submit()"
          style="width: 40%"
          v-show="stepActive == stepArray.length-1"
        >提交数据</el-button
        >
      </el-form-item>

    </el-form>
  </section>
</template>
<script>
import { getDictData,addCustomerHealthy } from "@/api/custom/customerInvestigation";
const logo = require("@/assets/logo/st_logo.png");
export default {
  name: "index",
  data() {
      const checkReportFile = (rule, value, callback) => {
          if (this.upload.fileList == null || this.upload.fileList.length === 0) {
              return callback(new Error('请选择需要上传的体检报告'))
          }
          callback();
      };
    return {
      logo,
      submitFlag: false,
      //调味品种类
      condimentArray:[
        {"name":"鸡精", "value":"1"},
        {"name":"耗油", "value":"2"},
        {"name":"生抽", "value":"3"},
        {"name":"老抽", "value":"4"},
        {"name":"香油", "value":"5"},
        {"name":"浓汤宝", "value":"6"},
        {"name":"鸡粉", "value":"7"},
        {"name":"花椒", "value":"8"},
        {"name":"辣椒油", "value":"9"}
      ],
      cookingStyleArray:[
        {"name":"煎","value":"1"},{"name":"烤","value":"2"},{"name":"炸","value":"3"},{"name":"卤","value":"4"},
        {"name":"腌","value":"5"},{"name":"腊","value":"6"},{"name":"煲","value":"7"},{"name":"炒","value":"8"},
        {"name":"蒸","value":"9"},{"name":"刺身","value":"10"},{"name":"水煮","value":"11"}
      ],
      cookingStyleRateArray:["煎","炸","卤","腌","腊","煲"],
      washVegetablesStyleArray:[
        {"name":"先切后洗","value": "1"},{"name":"先洗后切","value": "2"},{"name":"切后浸泡","value": "3"}
      ],
      breakfastTypeArray:[
        {"name":"不吃","value": "1"},{"name":"偶尔吃","value": "2"},{"name":"每天吃","value": "3"}
      ],
      lunchTypeArray:[
        {"name":"外卖","value":"1"},{"name":"自带餐","value":"2"},{"name":"快餐","value":"3"},{"name":"餐厅","value":"4"}
      ],
      dinnerArray:[
        {"name":"餐馆吃","value":"1"},{"name":"在家吃","value":"2"},{"name":"丰盛","value":"3"},{"name":"清淡","value":"4"}
      ],
      dietHotAndColdArray:[
        {"name":"偏冷食","value":"1"},{"name":"偏冷食","value":"2"},{"name":"正常","value":"3"}
      ],
      dietFlavorArray:[
        {"name":"偏油","value":"1"},{"name":"偏咸","value":"2"},{"name":"偏辣","value":"3"},
        {"name":"偏甜","value":"4"},{"name":"偏酸","value":"5"},{"name":"清淡","value":"6"}
      ],
      vegetablesRateTypeArray:[
        {"name":"每天吃","value":"1"},{"name":"经常吃","value":"2"},{"name":"偶尔吃","value":"3"},{"name":"从不吃","value":"4"}
      ],
      fruitsTimeArray:[
        {"name":"餐前","value":"1"},{"name":"餐后","value":"2"},{"name":"餐间","value":"3"}
      ],
      fruitsRateArray:[
        {"name":"每天吃","value":"1"},{"name":"经常吃","value":"2"},{"name":"偶尔吃","value":"3"},{"name":"从不吃","value":"4"}
      ],
      eatingSpeedArray:[
        {"name":"很快","value":"1"},{"name":"偏快","value":"2"},{"name":"正常","value":"3"},{"name":"偏慢","value":"4"}
        ,{"name":"很慢","value":"5"}
      ],
      snacksArray:[
        {"name":"面包","value":"1"},{"name":"蛋糕","value":"2"},{"name":"饼干","value":"3"},{"name":"冰淇淋","value":"4"}
        ,{"name":"糖果","value":"5"},{"name":"巧克力","value":"6"},{"name":"方便面","value":"7"},{"name":"薯条","value":"8"},{"name":"肉干","value":"9"},
        {"name":"坚果","value":"10"},{"name":"饮料","value":"11"},{"name":"果脯","value":"12"},{"name":"牛奶","value":"13"}
      ],
      waterTypeArray:[
        {"name":"冰水","value":"1"},{"name":"温水","value":"2"},{"name":"常温水","value":"3"}
      ],
      waterHabitArray:[
        {"name":"均匀地喝","value":"1"},{"name":"餐前多喝","value":"2"},{"name":"餐后多喝","value":"3"},{"name":"餐间多喝","value":"4"},
        {"name":"随时喝","value":"5"}
      ],
      drinksNumArray:["老火汤","咖啡","浓茶","奶茶","冷饮","碳酸饮料","甜饮料","鲜榨果汁"],
      drinkWineFlagArray:[
        {"name":"经常饮酒","value": "1"},{"name":"不饮酒","value": "2"},{"name":"偶尔","value": "3"}
      ],
      drinkWineClassifyArray:[
        {"name":"白酒","value": "1"},{"name":"红酒","value": "2"},{"name":"啤酒","value": "3"}
      ],
      drinkWineAmountArray:["白酒","啤酒","红酒","其他"],
      smokeRateArray:["每天抽烟","烟龄","已戒烟"],
      workTypeArray:[
        {"name":"工作时间长","value": "1"},{"name":"久坐","value": "2"},{"name":"久站","value": "3"},
        {"name":"走动多","value": "4"},{"name":"强度大","value": "5"},{"name":"用电脑多","value": "6"},{"name":"体力工作多","value": "7"}
      ],
      defecationTimeArray:[
        {"name":"上午","value": "1"},{"name":"中午","value": "2"},{"name":"晚上","value": "3"}
      ],
      aerobicMotionClassifyArray:[
        {"name":"跳绳","value": "1"},{"name":"跑步","value": "2"},{"name":"游泳","value": "3"}
      ],
      anaerobicMotionClassifyArray:[
        {"name":"撸铁","value": "1"},{"name":"俯卧撑","value": "2"}
      ],
      anaerobicAerobicMotionClassifyArray:[
        {"name":"拳击","value": "1"},{"name":"瑜伽","value": "2"}
      ],
      motionFieldArray:[
        {"name":"居家","value": "1"},{"name":"健身房","value": "2"},{"name":"户外","value": "3"}, {"name":"健身房","value": "4"}
      ],
      sleepQualityArray:[
        {"name":"好","value": "1"},{"name":"一般","value": "2"},{"name":"入睡难","value": "3"},
        {"name":"失眠","value": "4"},{"name":"易醒","value": "5"},{"name":"多梦","value": "6"}
      ],
      familyIllnessHistoryArray:[
        {"name":"高血压病","value": "1"},{"name":"脑卒中","value": "2"},{"name":"冠心病","value": "3"},
        {"name":"外周血管病","value": "4"},{"name":"心力衰竭","value": "5"},{"name":"冠心病","value": "6"},
        {"name":"肥胖症","value": "7"},{"name":"慢性肾脏疾病","value": "8"},{"name":"骨质疏松","value": "9"},
        {"name":"痛风","value": "10"},{"name":"精神疾病","value": "11"},{"name":"恶性肿瘤","value": "12"},
        {"name":"慢性阻塞性肺病","value": "13"},{"name":"风湿性免疫性疾病","value": "14"},
      ],
      operationHistoryArray:[
        {"name":"头颅（含脑）","value": "1"},{"name":"眼","value": "2"},{"name":"耳鼻咽喉","value": "3"},
        {"name":"颌面部及口腔","value": "4"},{"name":"颈部或甲状腺","value": "5"},{"name":"胸部（含肺部）","value": "6"},
        {"name":"心脏（含心脏介入）","value": "7"},{"name":"外周血管","value": "8"},{"name":"胃肠","value": "9"},
        {"name":"肝胆","value": "10"},{"name":"肾脏","value": "11"},{"name":"脊柱","value": "12"},
        {"name":"四肢及关节","value": "13"},{"name":"前列腺","value": "14"},{"name":"妇科","value": "15"},{"name":"乳腺","value": "16"}
        ,{"name":"膀胱","value": "17"}
      ],
      longEatDrugClassifyArray:[
        {"name":"降压药","value": "1"},{"name":"降糖药","value": "2"},{"name":"降尿酸药","value": "3"},
        {"name":"抗心律失常药","value": "4"},{"name":"缓解哮喘药物","value": "5"},{"name":"抗压郁药物","value": "6"},
        {"name":"雌激素类药物","value": "7"},{"name":"利尿剂","value": "8"},{"name":"中草药","value": "9"},
        {"name":"避孕药","value": "10"},{"name":"强的松类药物","value": "11"},{"name":"镇静剂或安眠药","value": "12"},
        {"name":"调值药（降脂药）","value": "13"},{"name":"解热镇痛药（如布洛芬等）","value": "14"}
      ],
      allergenArray:[
        {"name":"青霉素","value": "1"},{"name":"磺胺类","value": "2"},{"name":"链霉素","value": "3"},
        {"name":"头孢类","value": "4"},{"name":"鸡蛋","value": "5"},{"name":"牛奶","value": "6"},
        {"name":"海鲜","value": "7"},{"name":"花粉或尘螨","value": "8"},{"name":"粉尘","value": "9"},
        {"name":"洗洁剂","value": "10"},{"name":"化妆品","value": "11"}
      ],
      conditioningProjectIdOption:[],
      stepArray: [true,false,false,false,false,false,false,false],
      stepActive: 0,
      form: {
        name: null,
        phone: null,
        conditioningProjectId: 0,
        sex: 1,
        age: null,
        condiment:["1","5","9","6","1","3"],
        otherCondiment:null,
        cookingStyle: ["8","9","4","11"],
        cookingStyleRate:[1,1,1,1,1,1],
        washVegetablesStyle:["2"],
        otherWashVegetablesStyle: null,
        breakfastType:"1",
        breakfastFood: null,
        lunchType:["3"],
        dinner:["2"],
        vegetableRate: 5,
        commonMeat: null,
        dinnerTime: "07:00",
        supperNum:1,
        supperFood:null,
        dietHotAndCold: "3",
        dietFlavor: ["1","2"],
        vegetablesNum: 1,
        vegetablesRateType: "3",
        fruitsNum: 1,
        fruitsTime: "2",
        fruitsRate: "2",
        riceNum: 1,
        riceFull: 8,
        eatingSpeed: "3",
        snacks: ["10","11","13","12","3","1"],
        otherSnacks:null,
        healthProductsFlag: 0,
        healthProductsBrand:null,
        healthProductsName: null,
        healthProductsWeekRate:0,
        healthProductsDayRate:0,

        waterNum: 1500,
        waterType: ["2"],
        waterHabit: ["5"],
        drinksNum:[0,0,0,0,0,0,0,0],
        drinkWineFlag: "3",
        drinkWineClassify:["3"],
        otherWineClassify: null,
        drinkWineAmount:[0,0,0,0],
        smokeFlag: 0,
        smokeRate:[0,0,0],
        secondSmoke: 0,
        workIndustry: null,
        workType:["2"],
        defecationNum: 1,
        otherDefecationNum:0,
        defecationTime: ["1"],
        defecationShape: null,
        defecationSmell: null,
        defecationSpeed: null,
        defecationColor: null,

        motionNum: 3,
        motionDuration: 40,
        motionTime: "08:00",
        //motionMode:"有氧",
        aerobicMotionClassify:["2"],
        anaerobicMotionClassify:[],
        anaerobicAerobicMotionClassify:[],
        otherMotionClassify: null,
        motionField:["1"],
        otherMotionField:null,

        sleepTime: "23:00",
        sleepQuality:["2"],
        sleepDrugFlag: 0,
        sleepDrug: null,
        stayupLateFlag: 0,
        stayupLateWeekNum: 0,

        familyIllnessHistory:[],
        otherFamilyIllnessHistory:null,
        operationHistory:[],
        otherOperationHistory:null,
        nearOperationFlag:0,
        recoveryeSituation:null,
        longEatDrugFlag:0,
        longEatDrugClassify:[],
        otherLongEatDrugClassify:null,
        allergyFlag: 0,
        allergySituation: null,
        allergen:[],
        otherAllergen:null,
        fileNameList:[]
      },
      upload: {
          // 是否禁用上传
          isUploading: false,
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/common/customerUploadFile",
          // 设置上传的请求头部
          headers: {},
          // 其他需要携带的数据
          data:{},
          //文件列表
          fileList:[],
          //同时上传文件上限
          limit: 3,
          //每个文件大小
          fileSize: 1024 * 1024 * 5,
          //是否支持同时选择多张
          multiple: true
      },
      rules: {
        name: [
          { required: true, trigger: "blur", message: "请填写姓名" },
          { min: 1, max: 20, trigger: "blur", message: "姓名过长" },
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
        phone: [
          { required: true, trigger: "blur", message: "请填写手机号" },
          { required: true, trigger: "blur", message: "请填写正确的手机号" },
          {
            required: true,
            trigger: "blur",
            pattern: /^[0-9]{5,11}$/,
            message: "手机号格式不正确",
          },
        ],
          conditioningProjectId:[
              { required: true, trigger: "blur", message: "请选择调理项目" }
          ],
          /*fileList:[
              {required: true, trigger: "blur", validator: checkReportFile}
          ]*/
      },
        //需要将数组转成字符串的属性名称
      arrayName:[
           "condiment","cookingStyle","cookingStyleRate", "washVegetablesStyle","lunchType","dinner","dietFlavor",
           "snacks","waterType","waterHabit","drinksNum","drinkWineClassify","drinkWineAmount","smokeRate",
           "workType","defecationTime","aerobicMotionClassify","anaerobicMotionClassify","anaerobicAerobicMotionClassify",
           "motionField","sleepQuality", "familyIllnessHistory", "operationHistory", "longEatDrugClassify", "allergen", "fileNameList"
      ]

    };
  },
  methods: {
    submit(){
        if (this.submitFlag) {
            this.$message({
                message: "请勿重复提交",
                type: "warning",
            });
            return;
        }
      this.$refs.form.validate((valid) => {
        if (valid) {
            this.submitFlag = true;
            this.form.fileNameList = [];
            if(this.upload.fileList.length > 0){
                this.$refs.upload.submit();
            }else{
                this.addCustomerHealthy();
            }
        } else {
          this.$message({
          message: "数据未填写完整",
          type: "warning",
        });
      }
    });

    },
    addCustomerHealthy(){
        //数据处理
        let cusMessage = Object.assign({}, this.form);
        this.arrayName.forEach(function (item, index) {
            cusMessage[item] = cusMessage[item] != null ? cusMessage[item].join(",") : null;
        })
        addCustomerHealthy(cusMessage).then((response) => {
            if (response.code === 200) {
                this.$notify({
                    title: "提交成功",
                    message: "",
                    type: "success",
                });
            }else{
                this.submitFlag = false;
                this.upload.isUploading = false;
            }
        });
        console.log(cusMessage.cookingStyleRate);
        console.log(cusMessage.washVegetablesStyle);
    },
    nextStep(step){
      this.$refs.form.validate((valid) => {
        if(valid || step < 0){
          this.stepArray[this.stepActive] = false;
          this.stepActive = this.stepActive + step;
          this.stepArray[this.stepActive] = true;
          this.goTop();
        }else{
          this.$message({
            message: "数据未填写完整",
            type: "warning",
          });
        }
       });
    },
    goTop (){
      window.scroll(0, 0);
    },
    getDict(type){
      getDictData(type).then(response => {
         this.conditioningProjectIdOption = response.data;
      });
    },
      //监控上传文件列表
      handleFileChange(file, fileList) {
          /*console.log("------------")
          let existFile = fileList.slice(0, fileList.length - 1).find(f => f.name === file.name);
          if (existFile) {
              this.$message({
                  message: "当前文件已经存在",
                  type: "warning",
              });
              fileList.pop();
          }*/
          let sizeFlag = file.size > this.upload.fileSize;
          if (sizeFlag) {
              this.$message({
                  message: "当前文件过大",
                  type: "warning",
              });
              fileList.pop();
          }
          this.upload.fileList = fileList;

      },
      // 文件数量超过限度
      handleFileexceed(file, fileList){
          //console.log(this.upload.fileList.length);
          this.$message({
              message: "最多可上传"+ this.upload.limit +"份文件",
              type: "warning",
          });
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
          this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
          if(response != null && response.code === 200){
              this.form.fileNameList.push(response.fileName);
              if(this.form.fileNameList.length === this.upload.fileList.length){
                  //文件全部上传成功，则调用添加客户信息方法
                  this.addCustomerHealthy();
              }
          }else{
              this.upload.isUploading = false;
              this.submitFlag = false;
              this.$message.error('文件上传失败');
          }
      },
      // 文件上传失败处理
      handleFileFail(err, file, fileList){
          this.$message.error('文件上传失败');
          this.upload.isUploading = false;
          this.submitFlag = false;
      }
  },
  created() {
    this.getDict("conditioning_project");
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


</style>
