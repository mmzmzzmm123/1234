<template>
  <div>
    <div>
      <h2>{{ this.data.name }}</h2>
      <div class="msg-info" v-for="(info, idx) in basicInfo" :key="idx">
        <text-info
          v-for="con in info"
          :title="con.title"
          :key="con.title"
          :value="data[con.value]"
          extraclass="text-info-extra"
        />
      </div>
    </div>
  </div>
</template>
<script>
import TextInfo from "./TextInfo.vue";

export default {
  name: "BodySignView",
  props: ["data"],
  components: {
    "text-info": TextInfo,
  },
  data() {
    return {
      basicInfo: [
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
        [
          { title: "湿气数据", value: "moistureDate" },
          { title: "气血数据", value: "bloodData" },
        ],
        [{ title: "病史", value: "signStr" }],
        [{ title: "忌口或过敏源", value: "dishesIngredientId" }],
        [{ title: "方便沟通时间", value: "connectTime" }],
        [{ title: "备注", value: "remark" }],
      ],
    };
  },
};
</script>
<style rel="stylesheet/scss" lang="scss">
.msg-info {
  display: flex;
  margin-bottom: 8px;

  .text-info-extra {
    margin-bottom: 2px;
  }
}
</style>
