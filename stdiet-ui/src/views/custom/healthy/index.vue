<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <!--<el-form-item label="调理项目" prop="conditioningProjectId">
        <el-input
          v-model="queryParams.conditioningProjectId"
          placeholder="请输入调理项目"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>-->
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:healthy:add']"
        >新增</el-button>
      </el-col>-->
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:healthy:edit']"
        >修改</el-button>
      </el-col>-->
      <!--<el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:healthy:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:healthy:export']"
        >导出</el-button>
      </el-col>-->
      <el-col :span="1.5" title="客户健康评估表链接">
        <el-button
          type="primary"
          size="mini"
        ><a href="http://sign.shengtangdiet.com/subhealthyInvestigation" target='_blank'>客户健康评估表</a></el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="healthyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="创建时间" align="center" prop="createTime"fixed="left">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="调理项目" align="center" prop="conditioningProject" fixed="left"/>
      <el-table-column label="姓名" align="center" prop="name"  fixed="left"/>
      <el-table-column label="手机号" align="center" prop="phone" fixed="left"/>
      <el-table-column label="性别" align="center" prop="sex" >
        <template slot-scope="scope">
          {{scope.row.sex == 0 ? `男` : '女'}}
        </template>
      </el-table-column>

      <el-table-column label="年龄" align="center" prop="age" />
      <!--<el-table-column label="调味品种类，使用 , 隔开" align="center" prop="condiment" />
      <el-table-column label="其他调味品种类" align="center" prop="otherCondiment" />
      <el-table-column label="喜好的烹调方式，使用 , 隔开" align="center" prop="cookingStyle" />
      <el-table-column label="烹调方式对应频次，每周几次，使用 , 隔开" align="center" prop="cookingStyleRate" />
      <el-table-column label="洗菜方式，使用 , 隔开" align="center" prop="washVegetablesStyle" />
      <el-table-column label="其他洗菜方式" align="center" prop="otherWashVegetablesStyle" />
      <el-table-column label="早餐习惯" align="center" prop="breakfastType" />
      <el-table-column label="早餐吃的食物" align="center" prop="breakfastFood" />
      <el-table-column label="午餐习惯，使用 , 隔开" align="center" prop="lunchType" />
      <el-table-column label="晚餐习惯，使用 , 隔开" align="center" prop="dinner" />
      <el-table-column label="早餐当中素菜占比" align="center" prop="vegetableRate" />
      <el-table-column label="最常吃的肉类" align="center" prop="commonMeat" />
      <el-table-column label="晚餐时间" align="center" prop="dinnerTime" />
      <el-table-column label="每周吃夜宵次数，默认0" align="center" prop="supperNum" />
      <el-table-column label="夜宵通常吃的食物" align="center" prop="supperFood" />
      <el-table-column label="食物的冷热偏好" align="center" prop="dietHotAndCold" />
      <el-table-column label="食物的口味偏好，使用 , 隔开" align="center" prop="dietFlavor" />
      <el-table-column label="平均每周吃生菜几次" align="center" prop="vegetablesNum" />
      <el-table-column label="每周吃生菜的频次类型" align="center" prop="vegetablesRateType" />
      <el-table-column label="平均每天吃水果次数，默认0" align="center" prop="fruitsNum" />
      <el-table-column label="吃水果的时间段" align="center" prop="fruitsTime" />
      <el-table-column label="平时吃水果的频次" align="center" prop="fruitsRate" />
      <el-table-column label="一餐吃几碗饭" align="center" prop="riceNum" />
      <el-table-column label="吃几成饱" align="center" prop="riceFull" />
      <el-table-column label="吃饭速度" align="center" prop="eatingSpeed" />
      <el-table-column label="常吃的零食，使用 , 隔开" align="center" prop="snacks" />
      <el-table-column label="其他零食" align="center" prop="otherSnacks" />
      <el-table-column label="有无服用营养保健品，0无 1有" align="center" prop="healthProductsFlag" />
      <el-table-column label="营养保健品品牌名" align="center" prop="healthProductsBrand" />
      <el-table-column label="营养保健品产品名" align="center" prop="healthProductsName" />
      <el-table-column label="服用营养保健品频次，每周几次" align="center" prop="healthProductsWeekRate" />
      <el-table-column label="服用营养保健品频次，每天几次" align="center" prop="healthProductsDayRate" />
      <el-table-column label="每天的饮水量，单位：毫升" align="center" prop="waterNum" />
      <el-table-column label="喜欢喝什么水，使用 , 隔开" align="center" prop="waterType" />
      <el-table-column label="喝水习惯，使用 , 隔开" align="center" prop="waterHabit" />
      <el-table-column label="常喝的饮品的每周频次，使用，隔开" align="center" prop="drinksNum" />
      <el-table-column label="是否喝酒" align="center" prop="drinkWineFlag" />
      <el-table-column label="喝酒种类，使用，隔开" align="center" prop="drinkWineClassify" />
      <el-table-column label="其他酒种类" align="center" prop="otherWineClassify" />
      <el-table-column label="对应酒的量" align="center" prop="drinkWineAmount" />
      <el-table-column label="是否抽烟，0否 1是，默认0" align="center" prop="smokeFlag" />
      <el-table-column label="抽烟频次和烟龄，戒烟几年，使用，隔开" align="center" prop="smokeRate" />
      <el-table-column label="是否经常抽二手烟 0否 1是，默认0" align="center" prop="secondSmoke" />
      <el-table-column label="工作行业" align="center" prop="workIndustry" />
      <el-table-column label="工作性质，使用，隔开" align="center" prop="workType" />
      <el-table-column label="排便次数" align="center" prop="defecationNum" />
      <el-table-column label="其他手动输入的排便次数" align="center" prop="otherDefecationNum" />
      <el-table-column label="排便时间段，使用，隔开" align="center" prop="defecationTime" />
      <el-table-column label="排便的形状" align="center" prop="defecationShape" />
      <el-table-column label="排便的气味" align="center" prop="defecationSmell" />
      <el-table-column label="排便的速度" align="center" prop="defecationSpeed" />
      <el-table-column label="排便的颜色" align="center" prop="defecationColor" />
      <el-table-column label="每周运动次数" align="center" prop="motionNum" />
      <el-table-column label="每次运动的时长，分钟" align="center" prop="motionDuration" />
      <el-table-column label="每天运动的时间，24小时制" align="center" prop="motionTime" />
      <el-table-column label="有氧运动项目，使用，隔开" align="center" prop="aerobicMotionClassify" />
      <el-table-column label="无氧运动项目，使用，隔开" align="center" prop="anaerobicMotionClassify" />
      <el-table-column label="无氧有氧项目，使用，隔开" align="center" prop="anaerobicAerobicMotionClassify" />
      <el-table-column label="其他运动项目，使用，隔开" align="center" prop="otherMotionClassify" />
      <el-table-column label="运动场地，使用，隔开" align="center" prop="motionField" />
      <el-table-column label="其他运动场地" align="center" prop="otherMotionField" />
      <el-table-column label="睡觉时间，24小时制" align="center" prop="sleepTime" />
      <el-table-column label="睡眠质量" align="center" prop="sleepQuality" />
      <el-table-column label="是否有辅助入睡药物，0否 1是，默认0" align="center" prop="sleepDrugFlag" />
      <el-table-column label="辅助睡眠类药物名称" align="center" prop="sleepDrug" />
      <el-table-column label="是否经常熬夜" align="center" prop="stayupLateFlag" />
      <el-table-column label="熬夜频次，每周几次" align="center" prop="stayupLateWeekNum" />
      <el-table-column label="家族疾病史，使用，隔开" align="center" prop="familyIllnessHistory" />
      <el-table-column label="其他家族病史" align="center" prop="otherFamilyIllnessHistory" />
      <el-table-column label="手术史，使用，隔开" align="center" prop="operationHistory" />
      <el-table-column label="其他手术史" align="center" prop="otherOperationHistory" />
      <el-table-column label="近期是否做过手术，0否 1是，默认0" align="center" prop="nearOperationFlag" />
      <el-table-column label="手术恢复情况" align="center" prop="recoveryeSituation" />
      <el-table-column label="是否长期服用药物，0否 1是，默认0" align="center" prop="longEatDrugFlag" />
      <el-table-column label="长期服用的药物，使用，隔开" align="center" prop="longEatDrugClassify" />
      <el-table-column label="其他长期服用的药物" align="center" prop="otherLongEatDrugClassify" />
      <el-table-column label="是否出现过过敏症状，0否 1是，默认0" align="center" prop="allergyFlag" />
      <el-table-column label="过敏症状" align="center" prop="allergySituation" />
      <el-table-column label="过敏源，使用，隔开" align="center" prop="allergen" />
      <el-table-column label="其他过敏源" align="center" prop="otherAllergen" />
      <el-table-column label="体检报告" align="center" prop="medicalReport" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120" fixed="right" >
        <template slot-scope="scope">
          <!--<el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:healthy:edit']"
          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-c-scale-to-original"
            @click="handleLookDetail(scope.row)"
            v-hasPermi="['custom:healthy:query']"
          >查看详情</el-button>
          <!--<el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:healthy:remove']"
          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 添加或修改客户健康对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户ID" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入客户ID" />
        </el-form-item>
        <el-form-item label="调理项目id" prop="conditioningProjectId">
          <el-input v-model="form.conditioningProjectId" placeholder="请输入调理项目id" />
        </el-form-item>
        <el-form-item label="0男 1女 2未知，默认2" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择0男 1女 2未知，默认2">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="调味品种类，使用 , 隔开" prop="condiment">
          <el-input v-model="form.condiment" placeholder="请输入调味品种类，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="其他调味品种类" prop="otherCondiment">
          <el-input v-model="form.otherCondiment" placeholder="请输入其他调味品种类" />
        </el-form-item>
        <el-form-item label="喜好的烹调方式，使用 , 隔开" prop="cookingStyle">
          <el-input v-model="form.cookingStyle" placeholder="请输入喜好的烹调方式，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="烹调方式对应频次，每周几次，使用 , 隔开" prop="cookingStyleRate">
          <el-input v-model="form.cookingStyleRate" placeholder="请输入烹调方式对应频次，每周几次，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="洗菜方式，使用 , 隔开" prop="washVegetablesStyle">
          <el-input v-model="form.washVegetablesStyle" placeholder="请输入洗菜方式，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="其他洗菜方式" prop="otherWashVegetablesStyle">
          <el-input v-model="form.otherWashVegetablesStyle" placeholder="请输入其他洗菜方式" />
        </el-form-item>
        <el-form-item label="早餐习惯" prop="breakfastType">
          <el-select v-model="form.breakfastType" placeholder="请选择早餐习惯">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="早餐吃的食物" prop="breakfastFood">
          <el-input v-model="form.breakfastFood" placeholder="请输入早餐吃的食物" />
        </el-form-item>
        <el-form-item label="午餐习惯，使用 , 隔开" prop="lunchType">
          <el-select v-model="form.lunchType" placeholder="请选择午餐习惯，使用 , 隔开">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="晚餐习惯，使用 , 隔开" prop="dinner">
          <el-input v-model="form.dinner" placeholder="请输入晚餐习惯，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="正餐中素菜占比" prop="vegetableRate">
          <el-input v-model="form.vegetableRate" placeholder="请输入早餐当中素菜占比" />
        </el-form-item>
        <el-form-item label="最常吃的肉类" prop="commonMeat">
          <el-input v-model="form.commonMeat" placeholder="请输入最常吃的肉类" />
        </el-form-item>
        <el-form-item label="晚餐时间" prop="dinnerTime">
          <el-input v-model="form.dinnerTime" placeholder="请输入晚餐时间" />
        </el-form-item>
        <el-form-item label="每周吃夜宵次数，默认0" prop="supperNum">
          <el-input v-model="form.supperNum" placeholder="请输入每周吃夜宵次数，默认0" />
        </el-form-item>
        <el-form-item label="夜宵通常吃的食物" prop="supperFood">
          <el-input v-model="form.supperFood" placeholder="请输入夜宵通常吃的食物" />
        </el-form-item>
        <el-form-item label="食物的冷热偏好" prop="dietHotAndCold">
          <el-input v-model="form.dietHotAndCold" placeholder="请输入食物的冷热偏好" />
        </el-form-item>
        <el-form-item label="食物的口味偏好，使用 , 隔开" prop="dietFlavor">
          <el-input v-model="form.dietFlavor" placeholder="请输入食物的口味偏好，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="平均每周吃生菜几次" prop="vegetablesNum">
          <el-input v-model="form.vegetablesNum" placeholder="请输入平均每周吃生菜几次" />
        </el-form-item>
        <el-form-item label="每周吃生菜的频次类型" prop="vegetablesRateType">
          <el-select v-model="form.vegetablesRateType" placeholder="请选择每周吃生菜的频次类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="平均每天吃水果次数，默认0" prop="fruitsNum">
          <el-input v-model="form.fruitsNum" placeholder="请输入平均每天吃水果次数，默认0" />
        </el-form-item>
        <el-form-item label="吃水果的时间段" prop="fruitsTime">
          <el-input v-model="form.fruitsTime" placeholder="请输入吃水果的时间段" />
        </el-form-item>
        <el-form-item label="平时吃水果的频次" prop="fruitsRate">
          <el-input v-model="form.fruitsRate" placeholder="请输入平时吃水果的频次" />
        </el-form-item>
        <el-form-item label="一餐吃几碗饭" prop="riceNum">
          <el-input v-model="form.riceNum" placeholder="请输入一餐吃几碗饭" />
        </el-form-item>
        <el-form-item label="吃几成饱" prop="riceFull">
          <el-input v-model="form.riceFull" placeholder="请输入吃几成饱" />
        </el-form-item>
        <el-form-item label="吃饭速度" prop="eatingSpeed">
          <el-input v-model="form.eatingSpeed" placeholder="请输入吃饭速度" />
        </el-form-item>
        <el-form-item label="常吃的零食，使用 , 隔开" prop="snacks">
          <el-input v-model="form.snacks" placeholder="请输入常吃的零食，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="其他零食" prop="otherSnacks">
          <el-input v-model="form.otherSnacks" placeholder="请输入其他零食" />
        </el-form-item>
        <el-form-item label="有无服用营养保健品，0无 1有" prop="healthProductsFlag">
          <el-input v-model="form.healthProductsFlag" placeholder="请输入有无服用营养保健品，0无 1有" />
        </el-form-item>
        <el-form-item label="营养保健品品牌名" prop="healthProductsBrand">
          <el-input v-model="form.healthProductsBrand" placeholder="请输入营养保健品品牌名" />
        </el-form-item>
        <el-form-item label="营养保健品产品名" prop="healthProductsName">
          <el-input v-model="form.healthProductsName" placeholder="请输入营养保健品产品名" />
        </el-form-item>
        <el-form-item label="服用营养保健品频次，每周几次" prop="healthProductsWeekRate">
          <el-input v-model="form.healthProductsWeekRate" placeholder="请输入服用营养保健品频次，每周几次" />
        </el-form-item>
        <el-form-item label="服用营养保健品频次，每天几次" prop="healthProductsDayRate">
          <el-input v-model="form.healthProductsDayRate" placeholder="请输入服用营养保健品频次，每天几次" />
        </el-form-item>
        <el-form-item label="每天的饮水量，单位：毫升" prop="waterNum">
          <el-input v-model="form.waterNum" placeholder="请输入每天的饮水量，单位：毫升" />
        </el-form-item>
        <el-form-item label="喜欢喝什么水，使用 , 隔开" prop="waterType">
          <el-select v-model="form.waterType" placeholder="请选择喜欢喝什么水，使用 , 隔开">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="喝水习惯，使用 , 隔开" prop="waterHabit">
          <el-input v-model="form.waterHabit" placeholder="请输入喝水习惯，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="常喝的饮品的每周频次，使用，隔开" prop="drinksNum">
          <el-input v-model="form.drinksNum" placeholder="请输入常喝的饮品的每周频次，使用，隔开" />
        </el-form-item>
        <el-form-item label="是否喝酒" prop="drinkWineFlag">
          <el-input v-model="form.drinkWineFlag" placeholder="请输入是否喝酒" />
        </el-form-item>
        <el-form-item label="喝酒种类，使用，隔开" prop="drinkWineClassify">
          <el-input v-model="form.drinkWineClassify" placeholder="请输入喝酒种类，使用，隔开" />
        </el-form-item>
        <el-form-item label="其他酒种类" prop="otherWineClassify">
          <el-input v-model="form.otherWineClassify" placeholder="请输入其他酒种类" />
        </el-form-item>
        <el-form-item label="对应酒的量" prop="drinkWineAmount">
          <el-input v-model="form.drinkWineAmount" placeholder="请输入对应酒的量" />
        </el-form-item>
        <el-form-item label="是否抽烟，0否 1是，默认0" prop="smokeFlag">
          <el-input v-model="form.smokeFlag" placeholder="请输入是否抽烟，0否 1是，默认0" />
        </el-form-item>
        <el-form-item label="抽烟频次和烟龄，戒烟几年，使用，隔开" prop="smokeRate">
          <el-input v-model="form.smokeRate" placeholder="请输入抽烟频次和烟龄，戒烟几年，使用，隔开" />
        </el-form-item>
        <el-form-item label="是否经常抽二手烟 0否 1是，默认0" prop="secondSmoke">
          <el-input v-model="form.secondSmoke" placeholder="请输入是否经常抽二手烟 0否 1是，默认0" />
        </el-form-item>
        <el-form-item label="工作行业" prop="workIndustry">
          <el-input v-model="form.workIndustry" placeholder="请输入工作行业" />
        </el-form-item>
        <el-form-item label="工作性质，使用，隔开" prop="workType">
          <el-select v-model="form.workType" placeholder="请选择工作性质，使用，隔开">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="排便次数" prop="defecationNum">
          <el-input v-model="form.defecationNum" placeholder="请输入排便次数" />
        </el-form-item>
        <el-form-item label="其他手动输入的排便次数" prop="otherDefecationNum">
          <el-input v-model="form.otherDefecationNum" placeholder="请输入其他手动输入的排便次数" />
        </el-form-item>
        <el-form-item label="排便时间段，使用，隔开" prop="defecationTime">
          <el-input v-model="form.defecationTime" placeholder="请输入排便时间段，使用，隔开" />
        </el-form-item>
        <el-form-item label="排便的形状" prop="defecationShape">
          <el-input v-model="form.defecationShape" placeholder="请输入排便的形状" />
        </el-form-item>
        <el-form-item label="排便的气味" prop="defecationSmell">
          <el-input v-model="form.defecationSmell" placeholder="请输入排便的气味" />
        </el-form-item>
        <el-form-item label="排便的速度" prop="defecationSpeed">
          <el-input v-model="form.defecationSpeed" placeholder="请输入排便的速度" />
        </el-form-item>
        <el-form-item label="排便的颜色" prop="defecationColor">
          <el-input v-model="form.defecationColor" placeholder="请输入排便的颜色" />
        </el-form-item>
        <el-form-item label="每周运动次数" prop="motionNum">
          <el-input v-model="form.motionNum" placeholder="请输入每周运动次数" />
        </el-form-item>
        <el-form-item label="每次运动的时长，分钟" prop="motionDuration">
          <el-input v-model="form.motionDuration" placeholder="请输入每次运动的时长，分钟" />
        </el-form-item>
        <el-form-item label="每天运动的时间，24小时制" prop="motionTime">
          <el-input v-model="form.motionTime" placeholder="请输入每天运动的时间，24小时制" />
        </el-form-item>
        <el-form-item label="有氧运动项目，使用，隔开" prop="aerobicMotionClassify">
          <el-input v-model="form.aerobicMotionClassify" placeholder="请输入有氧运动项目，使用，隔开" />
        </el-form-item>
        <el-form-item label="无氧运动项目，使用，隔开" prop="anaerobicMotionClassify">
          <el-input v-model="form.anaerobicMotionClassify" placeholder="请输入无氧运动项目，使用，隔开" />
        </el-form-item>
        <el-form-item label="无氧有氧项目，使用，隔开" prop="anaerobicAerobicMotionClassify">
          <el-input v-model="form.anaerobicAerobicMotionClassify" placeholder="请输入无氧有氧项目，使用，隔开" />
        </el-form-item>
        <el-form-item label="其他运动项目，使用，隔开" prop="otherMotionClassify">
          <el-input v-model="form.otherMotionClassify" placeholder="请输入其他运动项目，使用，隔开" />
        </el-form-item>
        <el-form-item label="运动场地，使用，隔开" prop="motionField">
          <el-input v-model="form.motionField" placeholder="请输入运动场地，使用，隔开" />
        </el-form-item>
        <el-form-item label="其他运动场地" prop="otherMotionField">
          <el-input v-model="form.otherMotionField" placeholder="请输入其他运动场地" />
        </el-form-item>
        <el-form-item label="睡觉时间，24小时制" prop="sleepTime">
          <el-input v-model="form.sleepTime" placeholder="请输入睡觉时间，24小时制" />
        </el-form-item>
        <el-form-item label="睡眠质量" prop="sleepQuality">
          <el-input v-model="form.sleepQuality" placeholder="请输入睡眠质量" />
        </el-form-item>
        <el-form-item label="是否有辅助入睡药物，0否 1是，默认0" prop="sleepDrugFlag">
          <el-input v-model="form.sleepDrugFlag" placeholder="请输入是否有辅助入睡药物，0否 1是，默认0" />
        </el-form-item>
        <el-form-item label="辅助睡眠类药物名称" prop="sleepDrug">
          <el-input v-model="form.sleepDrug" placeholder="请输入辅助睡眠类药物名称" />
        </el-form-item>
        <el-form-item label="是否经常熬夜" prop="stayupLateFlag">
          <el-input v-model="form.stayupLateFlag" placeholder="请输入是否经常熬夜" />
        </el-form-item>
        <el-form-item label="熬夜频次，每周几次" prop="stayupLateWeekNum">
          <el-input v-model="form.stayupLateWeekNum" placeholder="请输入熬夜频次，每周几次" />
        </el-form-item>
        <el-form-item label="家族疾病史，使用，隔开" prop="familyIllnessHistory">
          <el-input v-model="form.familyIllnessHistory" placeholder="请输入家族疾病史，使用，隔开" />
        </el-form-item>
        <el-form-item label="其他家族病史" prop="otherFamilyIllnessHistory">
          <el-input v-model="form.otherFamilyIllnessHistory" placeholder="请输入其他家族病史" />
        </el-form-item>
        <el-form-item label="手术史，使用，隔开" prop="operationHistory">
          <el-input v-model="form.operationHistory" placeholder="请输入手术史，使用，隔开" />
        </el-form-item>
        <el-form-item label="其他手术史" prop="otherOperationHistory">
          <el-input v-model="form.otherOperationHistory" placeholder="请输入其他手术史" />
        </el-form-item>
        <el-form-item label="近期是否做过手术，0否 1是，默认0" prop="nearOperationFlag">
          <el-input v-model="form.nearOperationFlag" placeholder="请输入近期是否做过手术，0否 1是，默认0" />
        </el-form-item>
        <el-form-item label="手术恢复情况" prop="recoveryeSituation">
          <el-input v-model="form.recoveryeSituation" placeholder="请输入手术恢复情况" />
        </el-form-item>
        <el-form-item label="是否长期服用药物，0否 1是，默认0" prop="longEatDrugFlag">
          <el-input v-model="form.longEatDrugFlag" placeholder="请输入是否长期服用药物，0否 1是，默认0" />
        </el-form-item>
        <el-form-item label="长期服用的药物，使用，隔开" prop="longEatDrugClassify">
          <el-input v-model="form.longEatDrugClassify" placeholder="请输入长期服用的药物，使用，隔开" />
        </el-form-item>
        <el-form-item label="其他长期服用的药物" prop="otherLongEatDrugClassify">
          <el-input v-model="form.otherLongEatDrugClassify" placeholder="请输入其他长期服用的药物" />
        </el-form-item>
        <el-form-item label="是否出现过过敏症状，0否 1是，默认0" prop="allergyFlag">
          <el-input v-model="form.allergyFlag" placeholder="请输入是否出现过过敏症状，0否 1是，默认0" />
        </el-form-item>
        <el-form-item label="过敏症状" prop="allergySituation">
          <el-input v-model="form.allergySituation" placeholder="请输入过敏症状" />
        </el-form-item>
        <el-form-item label="过敏源，使用，隔开" prop="allergen">
          <el-input v-model="form.allergen" placeholder="请输入过敏源，使用，隔开" />
        </el-form-item>
        <el-form-item label="其他过敏源" prop="otherAllergen">
          <el-input v-model="form.otherAllergen" placeholder="请输入其他过敏源" />
        </el-form-item>
        <el-form-item label="体检报告" prop="medicalReport">
          <el-input v-model="form.medicalReport" placeholder="请输入体检报告" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看详情 -->
    <el-dialog title="客户健康评估表" v-if="healthyDetailOpen" :visible.sync="healthyDetailOpen" width="1000px" style="overflow: auto;" append-to-body>
      <el-table :show-header="false" v-for="(item,index) in healthyDetailList"  :data="item" border :cell-style="columnStyle" style="width: 100%;margin-top:20px;">
        <el-table-column width="140" prop="attr_name_one">
        </el-table-column>
        <el-table-column prop="value_one">
          <template slot-scope="scope">
            <AutoHideMessage :data="scope.row.value_one == null ? '' : (scope.row.value_one+'')" :maxLength="20"/>
          </template>
        </el-table-column>
        <el-table-column width="140" prop="attr_name_two"></el-table-column>
        <el-table-column prop="value_two">
          <template slot-scope="scope">
            <AutoHideMessage :data="scope.row.value_two == null ? '' : (scope.row.value_two+'')" :maxLength="20"/>
          </template>
        </el-table-column>
        <el-table-column width="140" prop="attr_name_three"></el-table-column>
        <el-table-column prop="value_three">
          <template slot-scope="scope">
            <AutoHideMessage :data="scope.row.value_three == null ? '' : (scope.row.value_three+'')" :maxLength="20"/>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
  import { listHealthy, getHealthy, delHealthy, addHealthy, updateHealthy, exportHealthy } from "@/api/custom/healthy";
  import AutoHideMessage from "@/components/AutoHideMessage";
  import * as healthyData from "@/utils/healthyData";

  export default {
    name: "Healthy",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 客户健康表格数据
        healthyList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          conditioningProjectId: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          vegetableRate: [
            { required: true, message: "早餐当中素菜占比不能为空", trigger: "blur" }
          ],
        },
        healthyDetailOpen:false,
        healthyDetailList:[[]],
        healthyTitleData:[
          [["创建时间","客户姓名","手机号"],["调理项目","性别","年龄"]],
          [["调味品种","烹调方式","烹调频次"],["洗菜方式","",""]],
          [
            ["早餐习惯","早餐吃的食物","午餐习惯"],["晚餐习惯","正餐中素菜占比","最常吃的肉类"],
            ["晚餐时间","每周吃夜宵次数","夜宵通常吃的食物"],["食物的冷热偏好","食物的口味偏好","平均每周吃生菜几次"],
            ["每周吃生菜的频次类型","平均每天吃水果次数","吃水果的时间段"],["平时吃水果的频次","一餐吃几碗饭","吃几成饱"],
            ["吃饭速度","常吃的零食","有无服用营养保健品"],["营养保健品品牌名","营养保健品产品名","服用营养保健品频次"]
          ],
          [
            ["每天的饮水量","喜欢喝什么水","喝水习惯"],["常喝的饮品的每周频次","是否喝酒","喝酒种类"],["对应酒的量","是否抽烟","抽烟频次和烟龄"],
            ["是否经常抽二手烟","工作行业","工作性质"],["排便次数","排便时间段","排便的形状"],["排便的气味","排便的速度","排便的颜色"]
          ],
          [
            ["每周运动次数","每次运动的时长","每天运动的时间"],["运动","运动场地","睡觉时间"],["睡眠质量","是否有辅助入睡药物","辅助睡眠类药物名称"],
            ["是否经常熬夜","熬夜频次","家族疾病史"],["手术史","近期是否做过手术","手术恢复情况"],
            ["是否长期服用药物","长期服用的药物","是否出现过过敏症状"],["过敏症状","过敏源",""]
          ]
        ],
        //健康详情的属性名称，与标题对应，按竖显示
        healthyValueData:[
          [["createTime","name","phone"],["conditioningProject","sex","age"]],
          [["condiment","cookingStyle","cookingStyleRate"],["washVegetablesStyle","",""]],
          [
            ["breakfastType","breakfastFood","lunchType"],["dinner","vegetableRate","commonMeat"],
            ["dinnerTime","supperNum","supperFood"],["dietHotAndCold","dietFlavor","vegetablesNum"],
            ["vegetablesRateType","fruitsNum","fruitsTime"],["fruitsRate","riceNum","riceNum"],["eatingSpeed","snacks","healthProductsFlag"],
            ["healthProductsBrand","healthProductsName","healthProductsWeekRate"]
          ],
          [
            ["waterNum","waterType","waterHabit"],["drinksNum","drinkWineFlag","drinkWineClassify"],["drinkWineAmount","smokeFlag","smokeRate"],
            ["secondSmoke","workIndustry","workType"],["defecationNum","defecationTime","defecationShape"],["defecationSmell","defecationSpeed","defecationColor"]
          ],
          [
            ["motionNum","motionDuration","motionTime"],["motion","motionField","sleepTime"],["sleepQuality","sleepDrugFlag","sleepDrug"],
            ["stayupLateFlag","stayupLateWeekNum","familyIllnessHistory"],["operationHistory","nearOperationFlag","recoveryeSituation"],
            ["longEatDrugFlag","longEatDrugClassify","allergyFlag"],["allergySituation","allergen",""]
          ]
        ]


      };
    },
    created() {
      this.getList();
    },
    components: {
      AutoHideMessage
    },
    methods: {
      /** 查询客户健康列表 */
      getList() {
        this.loading = true;
        listHealthy(this.queryParams).then(response => {
          this.healthyList = response.rows;
          this.total = response.total;
          this.loading = false;
      });
      },
      /** 查看按钮操作 */
      handleLookDetail(row) {
        const id = row.id || this.ids
        getHealthy(id).then(response => {
          let detailHealthy = this.dealHealthy(response.data);
          //性别
          detailHealthy.sex = detailHealthy.sex == 0 ?  "男" : (detailHealthy.sex == 1 ? "女" : "未知");
          //调味品
          detailHealthy.condiment += detailHealthy.otherCondiment ? ("，"+detailHealthy.otherCondiment) : "";
          //烹饪频次
          let cookingStyleRate = "";
          console.log(detailHealthy.cookingStyleRate);
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
          detailHealthy.motion =  motionStr + (detailHealthy.otherMotionClassify ? ( "，"+ detailHealthy.otherMotionClassify) : "");
          detailHealthy.motionField += detailHealthy.otherMotionField ? ("，"+detailHealthy.otherMotionField) : "";

          detailHealthy.sleepDrugFlag = detailHealthy.sleepDrugFlag == 1 ? "有" : "无";
          detailHealthy.stayupLateFlag = detailHealthy.stayupLateFlag == 1 ? "有" : "无";
          detailHealthy.stayupLateWeekNum += "次/周";
          detailHealthy.familyIllnessHistory += detailHealthy.otherFamilyIllnessHistory ? ("，" + detailHealthy.otherFamilyIllnessHistory) : "";
          detailHealthy.operationHistory += detailHealthy.otherOperationHistory ? ("，" + detailHealthy.otherOperationHistory) : "";
          detailHealthy.nearOperationFlag = detailHealthy.nearOperationFlag == 1 ? "有" : "无";

          detailHealthy.longEatDrugFlag = detailHealthy.longEatDrugFlag == 1 ? "有" : "无";
          detailHealthy.longEatDrugClassify += detailHealthy.otherLongEatDrugClassify ? ("，" + detailHealthy.otherLongEatDrugClassify) : "";

          detailHealthy.allergyFlag = detailHealthy.allergyFlag == 1 ? "有" : "无";
          detailHealthy.allergen += detailHealthy.otherAllergen ? ("，" +detailHealthy.otherAllergen) : "";

          for(let i = 0; i < this.healthyTitleData.length; i++){
            let stepArray = [];
            for(let j= 0; j < this.healthyTitleData[i].length; j++){
              stepArray[j] = ({"attr_name_one": this.healthyTitleData[i][j][0],"value_one": detailHealthy[this.healthyValueData[i][j][0]],"attr_name_two": this.healthyTitleData[i][j][1],"value_two": detailHealthy[this.healthyValueData[i][j][1]],"attr_name_three": this.healthyTitleData[i][j][2],"value_three": detailHealthy[this.healthyValueData[i][j][2]]});
            }
            this.healthyDetailList[i] = stepArray;
          }
          this.healthyDetailOpen = true;
        });
      },
      //健康信息处理
      dealHealthy(customerHealthy){
        let array = healthyData["needAttrName"];
        for(let i = 0; i < array.length; i++){
          customerHealthy[array[i]] = this.getHealthyStringByArray(array[i]+"Array", customerHealthy[array[i]]);
        }
        return customerHealthy;
      },
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
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          customerId: null,
          conditioningProjectId: null,
          sex: null,
          age: null,
          condiment: null,
          otherCondiment: null,
          cookingStyle: null,
          cookingStyleRate: null,
          washVegetablesStyle: null,
          otherWashVegetablesStyle: null,
          breakfastType: null,
          breakfastFood: null,
          lunchType: null,
          dinner: null,
          vegetableRate: null,
          commonMeat: null,
          dinnerTime: null,
          supperNum: null,
          supperFood: null,
          dietHotAndCold: null,
          dietFlavor: null,
          vegetablesNum: null,
          vegetablesRateType: null,
          fruitsNum: null,
          fruitsTime: null,
          fruitsRate: null,
          riceNum: null,
          riceFull: null,
          eatingSpeed: null,
          snacks: null,
          otherSnacks: null,
          healthProductsFlag: null,
          healthProductsBrand: null,
          healthProductsName: null,
          healthProductsWeekRate: null,
          healthProductsDayRate: null,
          waterNum: null,
          waterType: null,
          waterHabit: null,
          drinksNum: null,
          drinkWineFlag: null,
          drinkWineClassify: null,
          otherWineClassify: null,
          drinkWineAmount: null,
          smokeFlag: null,
          smokeRate: null,
          secondSmoke: null,
          workIndustry: null,
          workType: null,
          defecationNum: null,
          otherDefecationNum: null,
          defecationTime: null,
          defecationShape: null,
          defecationSmell: null,
          defecationSpeed: null,
          defecationColor: null,
          motionNum: null,
          motionDuration: null,
          motionTime: null,
          aerobicMotionClassify: null,
          anaerobicMotionClassify: null,
          anaerobicAerobicMotionClassify: null,
          otherMotionClassify: null,
          motionField: null,
          otherMotionField: null,
          sleepTime: null,
          sleepQuality: null,
          sleepDrugFlag: null,
          sleepDrug: null,
          stayupLateFlag: null,
          stayupLateWeekNum: null,
          familyIllnessHistory: null,
          otherFamilyIllnessHistory: null,
          operationHistory: null,
          otherOperationHistory: null,
          nearOperationFlag: null,
          recoveryeSituation: null,
          longEatDrugFlag: null,
          longEatDrugClassify: null,
          otherLongEatDrugClassify: null,
          allergyFlag: null,
          allergySituation: null,
          allergen: null,
          otherAllergen: null,
          medicalReport: null,
          createTime: null,
          createBy: null,
          updateTime: null,
          updateBy: null,
          delFlag: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加客户健康";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getHealthy(id).then(response => {
          this.form = response.data;
        this.open = true;
        this.title = "修改客户健康";
      });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateHealthy(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
            } else {
              addHealthy(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除客户健康编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delHealthy(ids);
        }).then(() => {
          this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有客户健康数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportHealthy(queryParams);
        }).then(response => {
          this.download(response.msg);
      }).catch(function() {});
      },
      // 自定义列背景色
      columnStyle({ row, column, rowIndex, columnIndex }) {
        if (columnIndex == 0 || columnIndex == 2 || columnIndex == 4 || columnIndex == 6) {
          //第三第四列的背景色就改变了2和3都是列数的下标
          return "background:#f3f6fc;font-weight:bold";
        }else{
          return "background:#ffffff;";
        }
      },
    }
  };
</script>
