<template>
  <div class="health_view_wrapper">
    <div>
      <h2>{{ this.data.name }}</h2>
      <el-button
        v-if="dev"
        size="mini"
        type="primary"
        class="remark_btn"
        @click="handleOnRemark"
        >修改备注</el-button
      >
      <div class="msg-info" v-for="(info, idx) in basicInfo" :key="idx">
        <text-info
          v-for="i in info"
          :title="i.title"
          :key="i.title"
          :value="data[i.value]"
          extraclass="text-info-extra"
        />
      </div>
    </div>
    <el-collapse>
      <el-collapse-item
        v-for="item in healthyInvestigate"
        :key="item.title"
        :title="item.title"
        :name="item.title"
      >
        <div v-for="con in item.content" :key="con.value">
          <text-info
            :title="con.title"
            :value="data[con.value]"
            extraclass="text-info-extra"
          />
        </div>
      </el-collapse-item>
    </el-collapse>

    <!-- 备注弹窗 -->
    <el-dialog title="修改备注" :visible.sync="open" width="480px">
      <el-input
        type="textarea"
        v-model="data.remark"
        rows="6"
        placeholder="请输入备注信息"
        maxlength="300"
        show-word-limit
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit">确 定</el-button>
        <el-button @click="onClosed">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import TextInfo from "@/components/TextInfo";
import { updateHealthy } from "@/api/custom/healthy";

export default {
  name: "HealthyView",
  props: {
    data: {
      type: Object,
      default: {},
    },
    dev: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    "text-info": TextInfo,
  },
  data() {
    const basicInfo = [
      [
        { title: "调理项目", value: "conditioningProject" },
        { title: "电话", value: "phone" },
      ],
      [
        { title: "性别", value: "sex" },
        { title: "年龄", value: "age" },
      ],
      [
        { title: "身高", value: "tall" },
        { title: "体重", value: "weight" },
      ],
      [{ title: "地域", value: "position" }],
    ];
    if (this.dev) {
      basicInfo.splice(3, 0, [{ title: "基础代谢BMR", value: "basicBMR" }]);
      basicInfo.splice(4, 0, [
        { title: "不运动总热量", value: "notSportHeat" },
      ]);
      basicInfo.splice(6, 0, [{ title: "备注", value: "remark" }]);
    }

    return {
      open: false,
      basicInfo,
      healthyInvestigate: [
        {
          title: "减脂经历评估",
          content: [
            { title: "减脂经历", value: "experience" },
            { title: "减肥遇到的困难", value: "difficulty" },
            { title: "减脂是否反弹", value: "rebound" },
            { title: "是否意识到生活习惯是减脂关键", value: "crux" },
          ],
        },
        {
          title: "食品安全评估",
          content: [
            { title: "调味品种", value: "condiment" },
            { title: "烹调方式", value: "cookingStyle" },
            { title: "烹调频次", value: "cookingStyleRate" },
            { title: "洗菜方式", value: "washVegetablesStyle" },
          ],
        },
        {
          title: "饮食结构评估",
          content: [
            { title: "早餐习惯", value: "breakfastType" },
            { title: "早餐吃的食物", value: "breakfastFood" },
            { title: "午餐习惯", value: "lunchType" },
            { title: "晚餐习惯", value: "dinner" },
            { title: "正餐中素菜占比", value: "vegetableRate" },
            { title: "最常吃的肉类", value: "commonMeat" },
            { title: "晚餐时间", value: "dinnerTime" },
            { title: "每周吃夜宵次数", value: "supperNum" },
            { title: "夜宵通常吃的食物", value: "supperFood" },
            { title: "食物的冷热偏好", value: "dietHotAndCold" },
            { title: "食物的口味偏好", value: "dietFlavor" },
            { title: "平均每周吃生蔬菜几次", value: "vegetablesNum" },
            { title: "每周吃生蔬菜的频次类型", value: "vegetablesRateType" },
            { title: "平均每天吃水果次数", value: "fruitsNum" },
            { title: "吃水果的时间段", value: "fruitsTime" },
            { title: "平时吃水果的频次", value: "fruitsRate" },
            { title: "一餐吃几碗饭", value: "riceNum" },
            { title: "吃几成饱", value: "riceFull" },
            { title: "吃饭速度", value: "eatingSpeed" },
            { title: "饮食特点", value: "makeFoodType" },
            { title: "常吃的零食", value: "snacks" },
            { title: "有无服用营养保健品", value: "healthProductsFlag" },
            { title: "营养保健品品牌名", value: "healthProductsBrand" },
            { title: "营养保健品产品名", value: "healthProductsName" },
            { title: "服用营养保健品频次", value: "healthProductsWeekRate" },
            { title: "忌口过敏食物", value: "dishesIngredient" },
          ],
        },
        {
          title: "生活习惯评估",
          content: [
            { title: "每天的饮水量", value: "waterNum" },
            { title: "喜欢喝什么水", value: "waterType" },
            { title: "喝水习惯", value: "waterHabit" },
            { title: "常喝的饮品的每周频次", value: "drinksNum" },
            { title: "是否喝酒", value: "drinkWineFlag" },
            { title: "喝酒种类", value: "drinkWineClassify" },
            { title: "对应酒的量", value: "drinkWineAmount" },
            { title: "是否抽烟", value: "smokeFlag" },
            { title: "抽烟频次和烟龄", value: "smokeRate" },
            { title: "是否经常抽二手烟", value: "secondSmoke" },
            { title: "工作行业", value: "workIndustry" },
            { title: "工作性质", value: "workType" },
            { title: "排便次数", value: "defecationNum" },
            { title: "排便时间段", value: "defecationTime" },
            { title: "排便的形状", value: "defecationShape" },
            { title: "排便的气味", value: "defecationSmell" },
            { title: "排便的速度", value: "defecationSpeed" },
            { title: "排便的颜色", value: "defecationColor" },
          ],
        },
        {
          title: "运动习惯评估",
          content: [
            { title: "每周运动次数", value: "motionNum" },
            { title: "每次运动的时长", value: "motionDuration" },
            { title: "每天运动的时间", value: "motionTime" },
            { title: "运动", value: "motion" },
            { title: "运动场地", value: "motionField" },
          ],
        },
        {
          title: "睡眠质量评估",
          content: [
            { title: "睡觉时间", value: "sleepTime" },
            { title: "睡眠质量", value: "sleepQuality" },
            { title: "是否有辅助入睡药物", value: "sleepDrugFlag" },
            { title: "辅助睡眠类药物名称", value: "sleepDrug" },
            { title: "是否经常熬夜", value: "stayupLateFlag" },
            { title: "熬夜频次", value: "stayupLateWeekNum" },
          ],
        },
        {
          title: "既往病史/用药史评估",
          content: [
            { title: "病史体征", value: "physicalSigns" },
            { title: "湿气数据", value: "moistureDate" },
            { title: "气血数据", value: "bloodData" },
            { title: "家族疾病史", value: "familyIllnessHistory" },
            { title: "手术史", value: "operationHistory" },
            { title: "近期是否做过手术", value: "nearOperationFlag" },
            { title: "手术恢复情况", value: "recoveryeSituation" },
            { title: "是否长期服用药物", value: "longEatDrugFlag" },
            { title: "长期服用的药物", value: "longEatDrugClassify" },
            { title: "是否出现过过敏症状", value: "allergyFlag" },
            { title: "过敏症状", value: "allergySituation" },
            { title: "过敏源", value: "allergen" },
            { title: "忌口过敏食物", value: "dishesIngredient" },
          ],
        },
        {
          title: "体检报告",
          content: [
            { title: "体检报告（1）", value: "medicalReport_one" },
            { title: "体检报告（2）", value: "medicalReport_two" },
            { title: "体检报告（3）", value: "medicalReport_three" },
          ],
        },
      ],
    };
  },
  methods: {
    handleOnRemark() {
      this.open = true;
    },
    onClosed() {
      this.open = false;
    },
    submit() {
      const { id, remark } = this.data;
      updateHealthy({ id, remark }).then((res) => {
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.open = false;
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.health_view_wrapper {
  .remark_btn {
    position: absolute;
    top: 36px;
    right: 16px;
  }

  .msg-info {
    display: flex;
    margin-bottom: 8px;

    .text-info-extra {
      margin-bottom: 2px;
    }
  }
}
</style>
