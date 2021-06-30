<template>
  <div class="body_sign_view_wrapper">
    <h2>{{ data.name }}</h2>
    <div class="msg-info" v-for="(info, idx) in basicInfo" :key="idx">
      <text-info
        v-for="con in info"
        :title="con.title"
        :key="con.title"
        :value="data[con.value]"
        extraclass="text-info-extra"
      />
    </div>
    <RemarkCom
      v-if="dev"
      :value.sync="data.remark"
      @onConfirm="handleOnConfirm"
    />
    <ACFCom
      v-if="dev"
      :value.sync="data.avoidFood"
      @onConfirm="handleOnConfirm"
    />
    <RemarkCom
      v-if="dev && showRemark"
      title="营养师点评"
      :value.sync="data.recipesPlanRemark"
      @onConfirm="handleOnRemarkConfirm"
    />
  </div>
</template>
<script>
import TextInfo from "@/components/TextInfo";
import ACFCom from "@/components/HealthyView/ACFCom";
import RemarkCom from "@/components/HealthyView/RemarkCom";
import { editPhysicalSigns } from "@/api/custom/healthy";

export default {
  name: "BodySignView",
  props: {
    data: {
      type: Object,
      default: {},
    },
    dev: {
      type: Boolean,
      default: false,
    },
    showRemark: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    "text-info": TextInfo,
    ACFCom,
    RemarkCom,
  },
  data() {
    const basicInfo = [
      [
        { title: "性别", value: "sex" },
        { title: "年龄", value: "age" },
      ],
      [
        { title: "电话", value: "phone" },
        { title: "地域", value: "position" },
      ],
      [
        { title: "身高", value: "tall" },
        { title: "体重", value: "weight" },
      ],
      [
        { title: "工作职业", value: "vocation" },
        { title: "上夜班", value: "night" },
      ],
      [
        { title: "熬夜失眠", value: "staylate" },
        { title: "运动情况", value: "walk" },
      ],

      [
        { title: "浑身乏力", value: "weakness" },
        { title: "经常运动", value: "motion" },
      ],
      [
        { title: "睡觉时间", value: "sleepTime" },
        { title: "起床时间", value: "getupTime" },
      ],
      [
        { title: "饮食方式", value: "makeFoodType" },
        { title: "饮食特点", value: "makeFoodTaste" },
      ],
      [{ title: "便秘", value: "constipation" }],
      [{ title: "饮食备注", value: "remarks" }],
      [{ title: "减脂反弹", value: "rebound" }],
      [{ title: "意识到生活习惯是减脂关键", value: "crux" }],
      [{ title: "减脂遇到的困难", value: "difficulty" }],
      [{ title: "湿气数据", value: "moistureDate" }],
      [{ title: "气血数据", value: "bloodData" }],
      [{ title: "病史", value: "signStr" }],
      [{ title: "忌口或过敏源", value: "dishesIngredientId" }],
      [{ title: "方便沟通时间", value: "connectTime" }],
    ];
    if (this.dev) {
      basicInfo.splice(3, 0, [{ title: "BMI", value: "bmi" }]);
      basicInfo.splice(4, 0, [{ title: "蛋白范围", value: "recProtein" }]);
      basicInfo.splice(5, 0, [{ title: "基础代谢BMR", value: "basicBMR" }]);
      basicInfo.splice(6, 0, [
        { title: "不运动总热量", value: "notSportHeat" },
      ]);
      // basicInfo.splice(basicInfo.length, 0, [
      //   { title: "备注", value: "remark" },
      // ]);
    }
    return {
      basicInfo,
    };
  },
  methods: {
    handleOnConfirm(data) {
      editPhysicalSigns({ id: this.data.id, ...data }).then((res) => {
        if (res.code === 200) {
          this.$message.success("修改成功");
        }
      });
    },
  },
  watch: {
    data(val, oldVal) {
      if (
        val &&
        val.dietitianName &&
        !this.basicInfo[3].some((obj) => obj.value === "dietitianName")
      ) {
        this.basicInfo.splice(3, 0, [
          { title: "主营养师", value: "dietitianName" },
          { title: "营养师助理", value: "assDietitianName" },
          { title: "售后营养师", value: "afterDietitianName" },
        ]);
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.body_sign_view_wrapper {
  .msg-info {
    display: flex;
    margin-bottom: 8px;

    .text-info-extra {
      margin-bottom: 2px;
    }
  }
}
</style>
