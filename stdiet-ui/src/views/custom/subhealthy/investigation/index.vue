<template>
  <section>
    <div style="padding: 16px; text-align: center">
      <img :src="logo" style="width: 258px; height: 80px" alt="logo" />
    </div>
    <el-form ref="form" label-position="top" :model="form" :rules="rules" label-width="100px" style="padding: 16px">
      <p class="p_title_1" style="margin-top: 5px;">基础信息</p>
      <el-form-item label="真实姓名" prop="name" style="padding-top: 10px;">
        <el-input v-model="form.name" placeholder="请输入真实姓名" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="form.sex" size="small" >
          <el-radio :label="parseInt('0')"  border>男</el-radio>
          <el-radio :label="parseInt('1')" border>女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input type="number" v-model.number="form.age" placeholder="请输入年龄" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone" >
        <el-input v-model="form.phone" placeholder="请输入手机号" />
      </el-form-item>
      <p class="p_title_1">一、食品安全评估</p>
      <p class="p_title_2">1、家庭调味品</p>
      <el-form-item :label="'(1) 调味品种类'" prop="condiment" class="margin-left">
        <el-checkbox-group v-model="form.condiment">
          <el-checkbox label="鸡精" key="1">鸡精</el-checkbox>
          <el-checkbox label="耗油" key="2">耗油</el-checkbox>
          <el-checkbox label="生抽" key="3">生抽</el-checkbox>
          <el-checkbox label="老抽" key="4">老抽</el-checkbox>
          <el-checkbox label="香油" key="5">香油</el-checkbox>
          <el-checkbox label="浓汤宝" key="6">浓汤宝</el-checkbox>
          <el-checkbox label="鸡粉" key="7">鸡粉</el-checkbox>
          <el-checkbox label="花椒" key="8">花椒</el-checkbox>
          <el-checkbox label="辣椒油" key="9">辣椒油</el-checkbox>
        </el-checkbox-group>
        <div><span>其他调味品 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherCondiment" placeholder="请输入其他调味品名称" /></div>
      </el-form-item>
      <p class="p_title_2">2、喜好的烹调方式和周频次</p>
      <el-form-item :label="'(1) 喜好的烹调方式'" prop="cookingStyle" class="margin-left">
        <el-checkbox-group v-model="form.cookingStyle">
            <el-checkbox label="煎">煎</el-checkbox>
            <el-checkbox label="烤">烤</el-checkbox>
            <el-checkbox label="炸" >炸</el-checkbox>
            <el-checkbox label="卤">卤</el-checkbox>
            <el-checkbox label="腌">腌</el-checkbox>
            <el-checkbox label="腊">腊</el-checkbox>
            <el-checkbox label="煲">煲</el-checkbox>
              <el-checkbox label="炒">炒</el-checkbox>
              <el-checkbox label="蒸">蒸</el-checkbox>
              <el-checkbox label="刺身">刺身</el-checkbox>
              <el-checkbox label="水煮">水煮</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(2) 烹调方式的频次（每周）'" prop="cookingStyleRate" class="margin-left">
        <div>
          <span>煎&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[0]" :step="1" :min="0"></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">炸&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[1]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
        <div style="margin-top: 5px;">
          <span>卤&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[2]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">腌&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[3]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
        <div style="margin-top: 5px;">
          <span>腊&nbsp;</span><el-input-number style="width:35%" v-model="form.cookingStyleRate[4]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
          <span style="margin-left: 20px;">煲&nbsp;</span><el-input-number style="width:35%;" v-model="form.cookingStyleRate[5]" :step="1" :min="0" ></el-input-number><span>&nbsp;次</span>
        </div>
      </el-form-item>
      <p class="p_title_2">3、洗菜方式</p>
      <el-form-item :label="'(1) 洗菜方式'" prop="washVegetablesStyle" class="margin-left">
        <el-checkbox-group v-model="form.washVegetablesStyle">
          <el-checkbox label="先切后洗" key="1">先切后洗</el-checkbox>
          <el-checkbox label="先洗后切" key="2">先洗后切</el-checkbox>
          <el-checkbox label="切后浸泡" key="3">切后浸泡</el-checkbox>
        </el-checkbox-group>
        <div><span>其他洗菜方式 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherWashVegetablesStyle" placeholder="请输入其他洗菜方式" /></div>
      </el-form-item>


      <p class="p_title_1">二、饮食结构评估</p>
      <p class="p_title_2">1、您三餐的习惯</p>
      <el-form-item :label="'(1) 早餐习惯'" prop="breakfast" class="margin-left">
        <!--<el-checkbox-group v-model="form.breakfastType">
          <el-checkbox label="不吃" key="1">不吃</el-checkbox>
          <el-checkbox label="偶尔吃" key="2">偶尔吃</el-checkbox>
          <el-checkbox label="每天吃" key="3">每天吃</el-checkbox>
        </el-checkbox-group>-->
        <el-radio-group v-model="form.breakfastType">
          <el-radio label="不吃">不吃</el-radio>
          <el-radio label="偶尔吃">偶尔吃</el-radio>
          <el-radio label="每天吃">每天吃</el-radio>
        </el-radio-group>
        <div style="margin-top:8px;"><span>早餐通常吃&nbsp;</span><el-input  v-model="form.breakfastFood" style="width:70%" placeholder="请输入早餐名称" /></div>
      </el-form-item>
      <el-form-item :label="'(2) 午餐习惯'" prop="lunchType" class="margin-left">
        <el-checkbox-group v-model="form.lunchType">
          <el-checkbox label="外卖" key="1">外卖</el-checkbox>
          <el-checkbox label="自带餐" key="2">自带餐</el-checkbox>
          <el-checkbox label="快餐" key="3">快餐</el-checkbox>
          <el-checkbox label="餐厅" key="4">餐厅</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="'(3) 晚餐习惯'" prop="dinner" class="margin-left">
        <el-checkbox-group v-model="form.dinner">
          <el-checkbox label="餐馆吃" key="1">餐馆吃</el-checkbox>
          <el-checkbox label="在家吃" key="2">在家吃</el-checkbox>
          <el-checkbox label="丰盛" key="3">丰盛</el-checkbox>
          <el-checkbox label="清淡" key="4">清淡</el-checkbox>
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
        <el-time-select v-model="form.dinnerTime" :picker-options="{ start: '00:00', step: '01:00', end: '12:00' }" placeholder="请选择时间" :editable="false"/>
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
          <el-radio label="偏冷食">偏冷食</el-radio>
          <el-radio label="偏烫食">偏烫食</el-radio>
          <el-radio label="正常">正常</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="'(2) 口味偏好'" prop="dietFlavor" class="margin-left">
        <el-checkbox-group v-model="form.dietFlavor">
          <el-checkbox label="偏油" key="1">偏油</el-checkbox>
          <el-checkbox label="偏咸" key="2">偏咸</el-checkbox>
          <el-checkbox label="偏辣" key="3">偏辣</el-checkbox>
          <el-checkbox label="偏甜" key="4">偏甜</el-checkbox>
          <el-checkbox label="偏酸" key="5">偏酸</el-checkbox>
          <el-checkbox label="清淡" key="6">清淡</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <p class="p_title_2">4、生食果蔬状况</p>
      <el-form-item :label="'(1) 平均每周吃生/拌菜几次'" prop="vegetablesNum" class="margin-left">
        <el-input-number v-model="form.vegetablesNum" :step="1" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item :label="'(2) 每周吃生/拌菜的频次'" prop="vegetablesRateType" class="margin-left">
        <el-radio-group v-model="form.vegetablesRateType">
          <div><el-radio label="每天吃" key="1">每天吃</el-radio>
          <el-radio label="经常吃" key="2">经常吃</el-radio>
          <el-radio label="偶尔吃" key="3">偶尔吃</el-radio></div>
          <div style="margin-top: 10px;"><el-radio label="从不吃" key="4">从不吃</el-radio></div>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="'(3) 平均每天吃水果几次以及时间'" prop="fruitsNum" class="margin-left">
        <el-input-number v-model="form.fruitsNum" :step="1" :min="0"></el-input-number>
        <el-radio-group v-model="form.fruitsTime" style="margin-top: 15px;">
          <el-radio label="餐前" key="1">餐前</el-radio>
            <el-radio label="餐后" key="2">餐后</el-radio>
            <el-radio label="餐间" key="3">餐间</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(4) 平时吃水果的频次" prop="fruitsRate" class="margin-left">
        <el-radio-group v-model="form.fruitsRate">
          <div>
            <el-radio label="每天吃" key="1">每天吃</el-radio>
            <el-radio label="经常吃" key="2">经常吃</el-radio>
            <el-radio label="偶尔吃" key="3">偶尔吃</el-radio>
          </div>
          <div style="margin-top: 10px;"><el-radio label="从不吃" key="4">从不吃</el-radio></div>
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
        <div>
          <el-radio label="很快" key="1">很快</el-radio>
          <el-radio label="偏快" key="2">偏快</el-radio>
          <el-radio label="正常" key="3">正常</el-radio>
        </div>
        <div style="margin-top: 10px;">
          <el-radio label="偏慢" key="4">偏慢</el-radio>
          <el-radio label="很慢" key="5">很慢</el-radio>
        </div>
          </el-radio-group>
      </el-form-item>
      <p class="p_title_2">6、您常吃的零食</p>
      <el-form-item label="(1) 常吃的零食" prop="snacks" class="margin-left">
        <el-checkbox-group v-model="form.snacks">
          <el-checkbox label="面包" key="1">面包</el-checkbox>
          <el-checkbox label="蛋糕" key="2">蛋糕</el-checkbox>
          <el-checkbox label="饼干" key="3">饼干</el-checkbox>
          <el-checkbox label="冰淇淋" key="4">冰淇淋</el-checkbox>
          <el-checkbox label="糖果" key="5">糖果</el-checkbox>
          <el-checkbox label="巧克力" key="6">巧克力</el-checkbox>
          <el-checkbox label="方便面" key="7">方便面</el-checkbox>
          <el-checkbox label="薯条" key="8">薯条</el-checkbox>
          <el-checkbox label="肉干" key="10">肉干</el-checkbox>
          <el-checkbox label="坚果" key="11">饮料</el-checkbox>
          <el-checkbox label="果脯" key="12">果脯</el-checkbox>
          <el-checkbox label="牛奶" key="13">牛奶</el-checkbox>
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

      <p class="p_title_1">三、生活习惯评估</p>
      <p class="p_title_2">1、您每天的饮水习惯</p>
      <el-form-item label="(1) 每天饮水量（毫升）" prop="waterNum" class="margin-left">
        <el-input-number v-model="form.waterNum" :step="50" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="(2) 喜欢喝什么水" prop="waterType" class="margin-left">
        <el-checkbox-group v-model="form.waterType">
          <el-checkbox label="冰水" key="1">冰水</el-checkbox>
          <el-checkbox label="温水" key="2">温水</el-checkbox>
          <el-checkbox label="常温水" key="3">常温水</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="(3) 喝水习惯" prop="waterHabit" class="margin-left">
        <el-checkbox-group v-model="form.waterHabit">
          <el-checkbox label="均匀地喝" key="1">均匀地喝</el-checkbox>
          <el-checkbox label="餐前多喝" key="2">餐前多喝</el-checkbox>
          <el-checkbox label="餐后多喝" key="3">餐后多喝</el-checkbox>
          <el-checkbox label="餐间多喝" key="4">餐间多喝</el-checkbox>
          <el-checkbox label="随时喝" key="5">随时喝</el-checkbox>
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
          <el-radio label="经常饮酒" key="1">经常饮酒</el-radio>
          <el-radio label="不饮酒" key="2">不饮酒</el-radio>
          <el-radio label="偶尔" key="3">偶尔</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(2) 通常喝什么酒" prop="drinkWineClassify" class="margin-left">
        <el-checkbox-group v-model="form.drinkWineClassify">
          <el-checkbox label="白酒" key="1">白酒</el-checkbox>
          <el-checkbox label="红酒" key="2">红酒</el-checkbox>
          <el-checkbox label="啤酒" key="3">啤酒</el-checkbox>
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
          <el-checkbox label="工作时间长" key="1">工作时间长</el-checkbox>
          <el-checkbox label="久坐" key="2">久坐</el-checkbox>
          <el-checkbox label="久站" key="3">久站</el-checkbox>
          <el-checkbox label="走动多" key="4">走动多</el-checkbox>
          <el-checkbox label="强度大" key="5">强度大</el-checkbox>
          <el-checkbox label="用电脑多" key="6">用电脑多</el-checkbox>
          <el-checkbox label="体力工作多" key="7">体力工作多</el-checkbox>
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
          <el-checkbox label="上午" key="1">上午</el-checkbox>
          <el-checkbox label="中午" key="2">中午</el-checkbox>
          <el-checkbox label="晚上" key="3">晚上</el-checkbox>
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

      <p class="p_title_1">四、运动习惯评估</p>
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
      <el-form-item label="(1) 运动方式" prop="motionMode" class="margin-left">
        <el-radio-group v-model="form.motionMode">
          <el-radio label="有氧" key="1">有氧</el-radio>
          <el-radio label="无氧" key="2">无氧</el-radio>
          <el-radio label="有氧无氧相结合" key="3">有氧无氧相结合</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="(2) 运动项目" prop="motionProject" class="margin-left">
        <div><span>有氧运动</span>
          <el-checkbox-group v-model="form.aerobicMotionClassify">
            <el-checkbox label="跳绳" key="1">跳绳</el-checkbox>
            <el-checkbox label="跑步" key="2">跑步</el-checkbox>
            <el-checkbox label="游泳" key="3">游泳</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>有氧运动</span>
          <el-checkbox-group v-model="form.anaerobicMotionClassify">
            <el-checkbox label="撸铁" key="1">撸铁</el-checkbox>
            <el-checkbox label="俯卧撑" key="2">俯卧撑</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>有氧无氧结合运动</span>
          <el-checkbox-group v-model="form.anaerobicAerobicMotionClassify">
            <el-checkbox label="拳击" key="1">拳击</el-checkbox>
            <el-checkbox label="瑜伽" key="2">瑜伽</el-checkbox>
          </el-checkbox-group>
        </div>
        <div><span>其他项目 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionClassify" placeholder="请输入其他运动项目名称" /></div>
      </el-form-item>
      <el-form-item label="(2) 运动场地" prop="motionField" class="margin-left">
        <el-checkbox-group v-model="form.motionField">
          <el-checkbox label="居家" key="1">居家</el-checkbox>
          <el-checkbox label="健身房" key="2">健身房</el-checkbox>
          <el-checkbox label="户外" key="3">居家</el-checkbox>
          <el-checkbox label="瑜伽馆" key="4">健身房</el-checkbox>
        </el-checkbox-group>
        <div><span>其他场地 </span><el-input style="margin-top: 10px;width:70%" v-model="form.otherMotionField" placeholder="请输入其他运动场地名称" /></div>
      </el-form-item>

      <p class="p_title_1">五、睡眠质量评估</p>
      <p class="p_title_2">1、您的睡眠状况</p>
      <el-form-item label="(1) 一般晚上几点睡" prop="sleepTime" class="margin-left">
        <el-time-select v-model="form.sleepTime" :picker-options="{ start: '00:00', step: '01:00', end: '24:00' }" placeholder="请选择时间" :editable="false"/>
      </el-form-item>
      <el-form-item label="(2) 睡眠质量" prop="sleepQuality" class="margin-left">
        <el-checkbox-group v-model="form.sleepQuality">
          <el-checkbox label="好" key="1">好</el-checkbox>
          <el-checkbox label="一般" key="2">一般</el-checkbox>
          <el-checkbox label="入睡难" key="3">入睡难</el-checkbox>
          <el-checkbox label="失眠" key="4">失眠</el-checkbox>
          <el-checkbox label="易醒" key="5">易醒</el-checkbox>
          <el-checkbox label="多梦" key="6">多梦</el-checkbox>
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

      <p class="p_title_1">六、既往病史/用药史评估</p>
      <p class="p_title_2">1、家族疾病史情况</p>
      <el-form-item label="（1）家族疾病史（直系亲属例如爸爸妈妈、爷爷奶奶、外公外婆有相关疾病）（可多选）" prop="familyIllnessHistory" class="margin-left">
        <el-checkbox-group v-model="form.familyIllnessHistory">
          <el-checkbox v-for="(item, index) in familyIllnessHistoryArray" :key="index" :label="item" key="1">{{item}}</el-checkbox>
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
          <el-checkbox v-for="(item, index) in operationHistoryArray" :key="index" :label="item" key="1">{{item}}</el-checkbox>
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
          <el-checkbox v-for="(item, index) in longEatDrugClassifyArray" :key="index" :label="item" key="1">{{item}}</el-checkbox>
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
          <el-checkbox v-for="(item, index) in allergenArray" :key="index" :label="item" key="1">{{item}}</el-checkbox>
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

      <el-form-item style="text-align: center; margin: 30px auto">
        <el-button
          type="primary"
          @click="addCustomer()"
          style="margin-right: 50px"
          >已填写完成，提交数据</el-button
        >
      </el-form-item>
    </el-form>
  </section>
</template>
<script>
const logo = require("@/assets/logo/st_logo.png");
export default {
  name: "index",
  data() {
    return {
      logo,
      submitFlag: false,
      familyIllnessHistoryArray:["高血压病","脑卒中","冠心病","外周血管病","心力衰竭","糖尿病","肥胖症","慢性肾脏疾病","骨质疏松", "痛风","精神疾病","恶性肿瘤","慢性阻塞性肺病","风湿性免疫性疾病"],
      operationHistoryArray:["头颅（含脑）","眼","耳鼻咽喉","颌面部及口腔","颈部或甲状腺","胸部（含肺部）","心脏（含心脏介入）","外周血管","胃肠","肝胆","肾脏","脊柱","四肢及关节","前列腺","妇科","乳腺","膀胱"],
      longEatDrugClassifyArray:["降压药","降糖药","降尿酸药","抗心律失常药","缓解哮喘药物","抗压郁药物","雌激素类药物","利尿剂",
        "中草药","避孕药","强的松类药物","镇静剂或安眠药","调值药（降脂药）","解热镇痛药（如布洛芬等）"],
      allergenArray:["青霉素","磺胺类","链霉素","头孢类","鸡蛋","牛奶","海鲜","花粉或尘螨","粉尘","洗洁剂","化妆品"],
      form: {
        name: null,
        phone: null,
        sex: 1,
        age: null,
        condiment:["耗油","香油","辣椒油","浓汤宝","鸡精","生抽"],
        otherCondiment:null,
        cookingStyle: ["炒","蒸","卤","水煮"],
        cookingStyleRate:[1,1,1,1,1,1],
        washVegetablesStyle:["先洗后切"],
        otherWashVegetablesStyle: null,
        breakfastType:"不吃",
        breakfastFood: null,
        lunchType:["快餐"],
        dinner:["在家吃"],
        vegetableRate: 5,
        commonMeat: null,
        dinnerTime: "07:00",
        supperNum:1,
        supperFood:null,
        dietHotAndCold: "正常",
        dietFlavor: ["偏油","偏咸"],
        vegetablesNum: 1,
        vegetablesRateType: "偶尔吃",
        fruitsNum: 1,
        fruitsTime: "餐后",
        fruitsRate: "经常吃",
        riceNum: 1,
        riceFull: 8,
        eatingSpeed: "正常",
        snacks: ["坚果","饮料","牛奶","果脯","饼干","面包"],
        otherSnacks:null,
        healthProductsFlag: 0,
        healthProductsBrand:null,
        healthProductsName: null,
        healthProductsWeekRate:0,
        healthProductsDayRate:0,

        waterNum: 1500,
        waterType: ["温水"],
        waterHabit: ["随时喝"],
        drinksNum:[0,0,0,0,0,0,0,0],
        drinkWineFlag: "偶尔",
        drinkWineClassify:["啤酒"],
        otherWineClassify: null,
        drinkWineAmount:[0,0,0,0],
        smokeFlag: 0,
        smokeRate:[0,0,0],
        secondSmoke: 0,
        workIndustry: null,
        workType:["久坐"],
        defecationNum: 1,
        otherDefecationNum:0,
        defecationTime: ["上午"],
        defecationShape: null,
        defecationSmell: null,
        defecationSpeed: null,
        defecationColor: null,

        motionNum: 3,
        motionDuration: 40,
        motionTime: "08:00",
        motionMode:"有氧",
        aerobicMotionClassify:["跑步"],
        anaerobicMotionClassify:[],
        anaerobicAerobicMotionClassify:[],
        otherMotionClassify: null,
        motionField:["居家"],
        otherMotionField:null,

        sleepTime: "23:00",
        sleepQuality:["一般"],
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
        otherAllergen:null
      },
      rules: {

      },
      physicalSignsList: [],
      bloodDataList: [],
      moistureDataList: [],

    };
  },
  methods: {
    addCustomer(){
      console.log(this.form.sex);
      console.log(this.form.condiment);
      console.log(this.form.cookingStyle);
    }
  },
  created() {

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
