<template>
  <div class="body_sign_view_wrapper">
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
          v-for="con in info"
          :title="con.title"
          :key="con.title"
          :value="data[con.value]"
          extraclass="text-info-extra"
        />
      </div>
    </div>

    <!-- 备注弹窗 -->
    <el-dialog title="修改备注" :visible.sync="open" width="480px">
      <el-input
        type="textarea"
        v-model="data.remarks"
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
  },
  components: {
    "text-info": TextInfo,
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
      basicInfo.splice(basicInfo.length, 0, [
        { title: "备注", value: "remarks" },
      ]);
    }
    return {
      basicInfo,
      open: false,
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
      const { id, remarks } = this.data;
      editPhysicalSigns({ id, remarks }).then((res) => {
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
.body_sign_view_wrapper {
  position: relative;

  .remark_btn {
    position: absolute;
    top: 0;
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
