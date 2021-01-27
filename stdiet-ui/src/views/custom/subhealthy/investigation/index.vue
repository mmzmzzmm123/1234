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
        <p class="p_title_1" style="margin-top: 5px;">{{healthyData['titleArray'][0]}}</p>
        <el-form-item label="真实姓名" prop="name" style="padding-top: 10px;">
          <el-input v-model="form.name" placeholder="请输入真实姓名" maxlength="20"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex" size="small" >
            <el-radio :label="parseInt('0')"  border>男</el-radio>
            <el-radio :label="parseInt('1')" border>女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age" >
          <el-input type="number" v-model="form.age" placeholder="请输入年龄" autocomplete="off" ></el-input>
        </el-form-item>
          <el-form-item label="身高（厘米）" prop="tall" >
            <el-input type="number" v-model="form.tall" placeholder="请输入身高" autocomplete="off" ></el-input>
          </el-form-item>
          <el-form-item label="体重（斤）" prop="weight" >
            <el-input type="number" v-model="form.weight" placeholder="请输入体重" autocomplete="off" ></el-input>
          </el-form-item>

        <el-form-item label="手机号" prop="phone" >
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
          <el-form-item label="南方人还是北方人" prop="position">
            <el-radio-group v-model="form.position" style="margin-left: 10px;">
              <el-radio :label="0" key="1">南方</el-radio>
              <el-radio :label="1" key="2">北方</el-radio>
            </el-radio-group>
          </el-form-item>
      </div>
      <div v-show="stepArray[1]">
        <p class="p_title_1">{{healthyData['titleArray'][1]}}</p>
        <p class="p_title_2">1、减脂经历</p>
        <el-form-item label="(1) 用过哪些减脂方法（重点）" prop="experience" class="margin-left">
          <el-input
            type="textarea"
            placeholder="请描述减脂方法"
            v-model="form.experience"
            maxlength="200"
            show-word-limit
            rows="3"
          ></el-input>
        </el-form-item>
        <el-form-item label="(2) 减脂中遇到的困难（重点）" prop="difficulty" class="margin-left">
          <el-input
            type="textarea"
            placeholder="请描述减脂中遇到的困难"
            v-model="form.difficulty"
            maxlength="200"
            show-word-limit
            rows="3"
          ></el-input>
        </el-form-item>
        <el-form-item label="(3) 减脂过程中是否反弹" prop="rebound" class="margin-left">
          <el-radio-group v-model="form.rebound" style="margin-left: 10px;">
            <el-radio :label="0" key="1">否</el-radio>
            <el-radio :label="1" key="2">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="(4) 是否意识到生活习惯是减脂关键" prop="crux" class="margin-left">
          <el-radio-group v-model="form.crux" style="margin-left: 10px;">
            <el-radio :label="0" key="1">否</el-radio>
            <el-radio :label="1" key="2">是</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>
      <div v-show="stepArray[2]">
      <p class="p_title_1">{{healthyData['titleArray'][2]}}</p>
      <p class="p_title_2">1、家庭调味品</p>
      <el-form-item :label="'(1) 调味品种类'" prop="condiment" class="margin-left">
        <el-checkbox-group v-model="form.condiment">
          <el-checkbox v-for="(item, index) in healthyData['condimentArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他调味品 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherCondiment" placeholder="请输入其他调味品名称" /></div>
      </el-form-item>
      <p class="p_title_2">2、喜好的烹调方式和周频次</p>
      <el-form-item :label="'(1) 喜好的烹调方式'" prop="cookingStyle" class="margin-left">
        <el-checkbox-group v-model="form.cookingStyle">
            <el-checkbox v-for="(item,index) in healthyData['cookingStyleArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(2) 烹调方式的频次（每周）'" prop="cookingStyleRate" class="margin-left">
        <div>
          <span>{{healthyData['cookingStyleRateArray'][0]}}&nbsp;</span><el-input-number  style="width:35%" v-model="form.cookingStyleRate[0]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">{{healthyData['cookingStyleRateArray'][1]}}&nbsp;</span><el-input-number  style="width:35%" v-model="form.cookingStyleRate[1]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
        <div style="margin-top: 5px;">
          <span>{{healthyData['cookingStyleRateArray'][2]}}&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[2]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">{{healthyData['cookingStyleRateArray'][3]}}&nbsp;</span><el-input-number   style="width:35%" v-model="form.cookingStyleRate[3]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
        <div style="margin-top: 5px;">
          <span>{{healthyData['cookingStyleRateArray'][4]}}&nbsp;</span><el-input-number style="width:35%"  v-model="form.cookingStyleRate[4]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">{{healthyData['cookingStyleRateArray'][5]}}&nbsp;</span><el-input-number  style="width:35%;" v-model="form.cookingStyleRate[5]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
      </el-form-item>
      <p class="p_title_2">3、洗菜方式</p>
      <el-form-item :label="'(1) 洗菜方式'" prop="washVegetablesStyle" class="margin-left">
        <el-checkbox-group v-model="form.washVegetablesStyle">
          <el-checkbox v-for="(item,index) in healthyData['washVegetablesStyleArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他洗菜方式 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherWashVegetablesStyle" placeholder="请输入其他洗菜方式" /></div>
      </el-form-item>
      </div>

      <div v-show="stepArray[3]">
      <p class="p_title_1">{{healthyData['titleArray'][3]}}</p>
      <p class="p_title_2">1、您三餐的习惯</p>
      <el-form-item :label="'(1) 早餐习惯'" prop="breakfast" class="margin-left">
        <el-radio-group v-model="form.breakfastType">
          <el-radio v-for="(item,index) in healthyData['breakfastTypeArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
        <div style="margin-top:8px;"><span>早餐通常吃&nbsp;</span><el-input  v-model="form.breakfastFood" style="width:70%" placeholder="请输入早餐名称" /></div>
      </el-form-item>
      <el-form-item :label="'(2) 午餐习惯'" prop="lunchType" class="margin-left">
        <el-checkbox-group v-model="form.lunchType">
          <el-checkbox v-for="(item,index) in healthyData['lunchTypeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(3) 晚餐习惯'" prop="dinner" class="margin-left">
        <el-checkbox-group v-model="form.dinner">
          <el-checkbox v-for="(item,index) in healthyData['dinnerArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(4) 正餐荤素中素菜占比几成'" prop="eatSituation" class="margin-left">
        <el-input-number v-model="form.vegetableRate" :step="1" :min="0" :max="10"></el-input-number>
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
          <el-radio v-for="(item,index) in healthyData['dietHotAndColdArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="'(2) 口味偏好'" prop="dietFlavor" class="margin-left">
        <el-checkbox-group v-model="form.dietFlavor">
          <el-checkbox v-for="(item,index) in healthyData['dietFlavorArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">4、生食果蔬状况</p>
      <el-form-item :label="'(1) 平均每周吃生/拌菜几次'" prop="vegetablesNum" class="margin-left">
        <el-input-number v-model="form.vegetablesNum" :step="1" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item :label="'(2) 每周吃生/拌菜的频次'" prop="vegetablesRateType" class="margin-left">
        <el-radio-group v-model="form.vegetablesRateType">
          <el-radio v-for="(item,index) in healthyData['vegetablesRateTypeArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="'(3) 平均每天吃水果几次以及时间'" prop="fruitsNum" class="margin-left">
        <el-input-number v-model="form.fruitsNum" :step="1" :min="0"></el-input-number>
        <el-radio-group v-model="form.fruitsTime" style="margin-top: 15px;">
          <el-radio v-for="(item,index) in healthyData['fruitsTimeArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(4) 平时吃水果的频次" prop="fruitsRate" class="margin-left">
        <el-radio-group v-model="form.fruitsRate">
          <el-radio v-for="(item,index) in healthyData['fruitsRateArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
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
          <el-radio v-for="(item,index) in healthyData['eatingSpeedArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
          </el-radio-group>
      </el-form-item>
        <el-form-item label="(3) 饮食特点" prop="makeFoodType" class="margin-left">
          <el-select v-model="form.makeFoodType" placeholder="请选择">
            <el-option
              v-for="item in healthyData['makeFoodTypeArray']"
              :key="item.value"
              :label="item.name"
              :value="parseInt(item.value)"
            />
          </el-select>
        </el-form-item>

      <p class="p_title_2">6、您常吃的零食</p>
      <el-form-item label="(1) 常吃的零食" prop="snacks" class="margin-left">
        <el-checkbox-group v-model="form.snacks">
          <el-checkbox v-for="(item,index) in healthyData['snacksArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
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
        <p class="p_title_2">8、忌口或过敏食物</p>
        <el-form-item label="(1) 忌口或过敏、不爱吃的食物（重点）" prop="dishesIngredient" class="margin-left">
          <el-input
            type="textarea"
            placeholder="请描述忌口或过敏、不爱吃的食物"
            v-model="form.dishesIngredient"
            maxlength="200"
            show-word-limit
            rows="3"
          ></el-input>
        </el-form-item>
      </div>

      <div v-show="stepArray[4]">
      <p class="p_title_1">{{healthyData['titleArray'][4]}}</p>
      <p class="p_title_2">1、您每天的饮水习惯</p>
      <el-form-item label="(1) 每天饮水量（毫升）" prop="waterNum" class="margin-left">
        <el-input-number v-model="form.waterNum" :step="50" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="(2) 喜欢喝什么水" prop="waterType" class="margin-left">
        <el-checkbox-group v-model="form.waterType">
          <el-checkbox v-for="(item,index) in healthyData['waterTypeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="(3) 喝水习惯" prop="waterHabit" class="margin-left">
        <el-checkbox-group v-model="form.waterHabit">
          <el-checkbox v-for="(item,index) in healthyData['waterHabitArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">2、您常喝的饮品和每周频次</p>
      <el-form-item label="(1) 每周常喝的饮品次数" prop="drinksNum" class="margin-left">
        <div>
          <span>{{healthyData['drinksNumArray'][0]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 13px;" v-model="form.drinksNum[0]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinksNumArray'][1]}}&nbsp;</span><el-input-number style="width:35%;margin-left: 25px;" v-model="form.drinksNum[1]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinksNumArray'][2]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[2]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinksNumArray'][3]}}&nbsp;</span><el-input-number  type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[3]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinksNumArray'][4]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 25px;" v-model="form.drinksNum[4]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinksNumArray'][5]}}&nbsp;</span><el-input-number type="number" style="width:35%;" v-model="form.drinksNum[5]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinksNumArray'][6]}}&nbsp;</span><el-input-number type="number" style="width:35%;margin-left: 13px;" v-model="form.drinksNum[6]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinksNumArray'][7]}}&nbsp;</span><el-input-number type="number" style="width:35%;" v-model="form.drinksNum[7]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
        </div>
      </el-form-item>
      <p class="p_title_2">3、您的饮酒习惯</p>
      <el-form-item label="(1) 是否喝酒" prop="drinkWineFlag" class="margin-left">
        <el-radio-group v-model="form.drinkWineFlag">
          <el-radio v-for="(item,index) in healthyData['drinkWineFlagArray']" :label="item.value" :key="index">{{item.name}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(2) 通常喝什么酒" prop="drinkWineClassify" class="margin-left">
        <el-checkbox-group v-model="form.drinkWineClassify">
          <el-checkbox v-for="(item,index) in healthyData['drinkWineClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他酒 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherWineClassify" placeholder="请输入其他酒名称" /></div>
      </el-form-item>
      <el-form-item label="(3) 通常喝多少" prop="drinkWineAmount" class="margin-left">
        <div>
          <span>{{healthyData['drinkWineAmountArray'][0]}}</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[0]" :step="1" :min="0"></el-input-number><span>&nbsp;两</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinkWineAmountArray'][1]}}</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[1]" :step="1" :min="0"></el-input-number><span>&nbsp;瓶</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['drinkWineAmountArray'][2]}}</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[2]" :step="100" :min="0"></el-input-number><span>&nbsp;毫升</span>
        </div>
        <!--<div class="margin-top-10">
          <span>其他</span><el-input-number class="width-50-left-8-right-5" type="number" v-model="form.drinkWineAmount[3]" :step="100" :min="0"></el-input-number><span>&nbsp;毫升</span>
        </div>-->
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
          <span>{{healthyData['smokeRateArray'][0]}}</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[0]" :step="1" :min="0"></el-input-number><span>次</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['smokeRateArray'][1]}}</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[1]" :step="1" :min="0"></el-input-number><span>年</span>
        </div>
        <div class="margin-top-10">
          <span>{{healthyData['smokeRateArray'][2]}}</span><el-input-number type="number" class="width-50-left-8-right-5" v-model="form.smokeRate[2]" :step="1" :min="0"></el-input-number><span>年</span>
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
          <el-checkbox v-for="(item,index) in healthyData['workTypeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">4、您的排便状况</p>
      <el-form-item label="(1) 排便次数" prop="defecationNum" style="padding-top: 10px;">
        <!--<el-radio-group v-model="form.defecationNum">
          <el-radio :label="1" key="1">1次/天</el-radio>
          <el-radio :label="2" key="2">2次/天</el-radio>
          <el-radio :label="3" key="3">3次/天</el-radio>
        </el-radio-group>-->
        <div class="margin-top-10"><span>排便次数 </span><el-input-number style="width:35%;margin-right: 10px;" type="number" v-model="form.defecationNum" :step="1" :min="0"></el-input-number><span>次/天</span></div>
      </el-form-item>

      <el-form-item label="(2) 排便时间" prop="defecationTime" style="padding-top: 10px;">
        <el-checkbox-group v-model="form.defecationTime">
          <el-checkbox v-for="(item,index) in healthyData['defecationTimeArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
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

      <div v-show="stepArray[5]">
      <p class="p_title_1">{{healthyData['titleArray'][5]}}</p>
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
            <el-checkbox v-for="(item,index) in healthyData['aerobicMotionClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>无氧运动</span>
          <el-checkbox-group v-model="form.anaerobicMotionClassify">
            <el-checkbox v-for="(item,index) in healthyData['anaerobicMotionClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>有氧无氧结合运动</span>
          <el-checkbox-group v-model="form.anaerobicAerobicMotionClassify">
            <el-checkbox v-for="(item,index) in healthyData['anaerobicAerobicMotionClassifyArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>其他项目 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionClassify" placeholder="请输入其他运动项目名称" /></div>
      </el-form-item>
      <el-form-item label="(2) 运动场地" prop="motionField" class="margin-left">
        <el-checkbox-group v-model="form.motionField">
          <el-checkbox v-for="(item,index) in healthyData['motionFieldArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
        </el-checkbox-group>
        <div><span>其他场地 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionField" placeholder="请输入其他运动场地名称" /></div>
      </el-form-item>
      </div>

      <div v-show="stepArray[6]">
      <p class="p_title_1">{{healthyData['titleArray'][6]}}</p>
      <p class="p_title_2">1、您的睡眠状况</p>
      <el-form-item label="(1) 一般晚上几点睡" prop="sleepTime" class="margin-left">
        <el-time-select v-model="form.sleepTime" :picker-options="{ start: '00:00', step: '01:00', end: '24:00' }" placeholder="请选择时间" :editable="false"/>
      </el-form-item>
      <el-form-item label="(2) 睡眠质量" prop="sleepQuality" class="margin-left">
        <el-checkbox-group v-model="form.sleepQuality">
          <el-checkbox v-for="(item,index) in healthyData['sleepQualityArray']" :label="item.value" :key="index">{{item.name}}</el-checkbox>
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
      <div v-show="stepArray[7]">
      <p class="p_title_1">{{healthyData['titleArray'][7]}}</p>
        <p class="p_title_2">1、本人病史情况</p>
        <el-form-item label="(1) 病史体征(可多选)" prop="physicalSignsId" class="margin-left">
          <el-select v-model="form.physicalSignsId" multiple placeholder="请选择">
            <el-option
              v-for="physicalSign in physicalSignsList"
              :key="physicalSign.id"
              :label="physicalSign.name"
              :value="physicalSign.id"
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
      <p class="p_title_2">5、药物情况</p>
      <el-form-item label="(1) 是否长期服用药物（连续服用6个月以上，平均每日服用一次）" prop="longEatDrugFlag" class="margin-left">
        <el-radio-group v-model="form.longEatDrugFlag">
          <el-radio :label="0" key="1">否</el-radio>
          <el-radio :label="1" key="2">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(2) 长期服用药物有（可多选）" prop="longEatDrugClassify" class="margin-left">
        <el-checkbox-group v-model="form.longEatDrugClassify">
          <el-checkbox v-for="(item, index) in healthyData['longEatDrugClassifyArray']" :key="index" :label="item.value">{{item.name}}</el-checkbox>
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

      <div v-show="stepArray[8]">
        <p class="p_title_1">{{healthyData['titleArray'][8]}}</p>
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
          <el-button style="margin-left: 10px;" size="small" ref="removeFile" @click="upload.fileList = []">移除文件</el-button>
          <div slot="tip" class="el-upload__tip">提示：最多可上传三份，且每份文件不超过20M</div>
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
import { getDictData,addCustomerHealthy,physicalSignsList } from "@/api/custom/customerInvestigation";
import * as healthyData from "@/utils/healthyData";
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
      healthyData:healthyData,
      logo,
      submitFlag: false,
      conditioningProjectIdOption:[],
      physicalSignsList: [],
      moistureDataList:[],
      bloodDataList:[],
      stepArray: [true,false,false,false,false,false,false,false,false],
      stepActive: 0,
      form: {
        name: null,
        phone: null,
        conditioningProjectId: 0,
        sex: 1,
        age: null,
        tall: null,
        weight: null,
        condiment:["1","2","3"],
        otherCondiment:null,
        cookingStyle: ["8","9","4","11"],
        cookingStyleRate:[1,1,1,1,1,1],
        washVegetablesStyle:["2"],
        otherWashVegetablesStyle: null,
        breakfastType:"2",
        breakfastFood: null,
        lunchType:["3"],
        dinner:["2"],
        vegetableRate: 5,
        commonMeat: null,
        dinnerTime: "19:00",
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
        snacks: ["1"],
        otherSnacks:null,
        healthProductsFlag: 0,
        healthProductsBrand:null,
        healthProductsName: null,
        healthProductsWeekRate:0,
        healthProductsDayRate:0,

        waterNum: 1500,
        waterType: ["3"],
        waterHabit: ["5"],
        drinksNum:[0,0,0,0,0,0,0,0],
        drinkWineFlag: "3",
        drinkWineClassify:["3"],
        otherWineClassify: null,
        drinkWineAmount:[0,0,0],
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

        physicalSignsId:[],
        otherPhysicalSigns: null,
        bloodData:[],
        moistureDate:[],
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
        medicalReport:[],
        medicalReportName:[],
        position:0,
        experience: null,
        rebound: 0,
        difficulty:null,
        crux:0,
        dishesIngredient:null,
        makeFoodType:3
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
          fileSize: 1024 * 1024 * 20,
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
            pattern: /^[1-9]\d*$/,
            message: "体重格式不正确",
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
        position:[
          { required: true, trigger: "blur", message: "请选择地理位置" }
        ]
      }
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
            this.form.medicalReport = [];
            if(this.upload.fileList.length > 0){
                this.$refs.upload.submit();removeFile
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
        this.healthyData['arrayName'].forEach(function (item, index) {
            cusMessage[item] = cusMessage[item] != null ? cusMessage[item].join(",") : null;
        });
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
         console.log(file.name);
          if(response != null && response.code === 200){
              this.form.medicalReport.push(response.fileName);
              this.form.medicalReportName.push(file.name);
              if(this.form.medicalReport.length === this.upload.fileList.length){
                  //文件全部上传成功，则调用添加客户信息方法
                  this.addCustomerHealthy();
              }
          }else{
              this.upload.isUploading = false;
              this.submitFlag = false;
              this.$message.error('文件上传失败，请检查文件格式');
          }
      },
      // 文件上传失败处理
      handleFileFail(err, file, fileList){
          this.$message.error('文件上传失败，请检查文件格式');
          this.upload.isUploading = false;
          this.submitFlag = false;
      },
      //获取湿气
      getMoistureDictData() {
        getDictData("sys_blood_data").then((response) => {
          this.moistureDataList = response.data;
        });
      },
      //获取气血
      getBloodDictData() {
        getDictData("sys_moisture_data").then((response) => {
          this.bloodDataList = response.data;
        });
      },
      /** 查询体征列表 */
      getPhysicalSignsList() {
        physicalSignsList().then((response) => {
          this.physicalSignsList = response.rows;
        });
    },
  },
  created() {
    this.getDict("conditioning_project");
    this.getPhysicalSignsList();
    this.getMoistureDictData();
    this.getBloodDictData();
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
