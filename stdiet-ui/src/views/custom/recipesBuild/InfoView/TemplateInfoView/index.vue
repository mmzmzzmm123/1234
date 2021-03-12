<template>
  <div class="template_info_wrapper">
    <h2>{{ data.name }}</h2>
    <TextInfo title="营养师" :value="data.nutritionist" />
    <TextInfo
      title="营养师助理"
      :value="data.nutriAssis"
      style="margin-top: 24px"
    />
    <div class="remark_info">
      <span class="info_title">备注：</span>
      <el-input
        type="textarea"
        :rows="8"
        placeholder="请输入内容"
        v-model="data.remark"
      >
      </el-input>
    </div>
    <div style="margin-top: 12px; text-align: right">
      <el-button v-if="change" type="primary" size="mini" @click="handleSubmit"
        >提交</el-button
      >
    </div>
  </div>
</template>
<script>
import TextInfo from "@/components/TextInfo";
import { updateRecipesTemplate } from "@/api/custom/recipesTemplate";
export default {
  name: "TemplateInfoView",
  props: {
    data: {
      type: Object,
      default: () => ({
        id: "",
        name: "",
        nutritionist: "",
        nutriAssis: "",
        remark: "",
      }),
    },
  },
  components: {
    TextInfo,
  },
  data() {
    return {
      oriRemark: "",
      change: false,
    };
  },
  watch: {
    "data.remark": function (val, oldVal) {
      if (!oldVal && val) {
        this.oriRemark = val;
      }
      this.change = this.oriRemark !== val;
    },
  },
  methods: {
    handleSubmit() {
      const { remark, id } = this.data;
      updateRecipesTemplate({ id, remark }).then((res) => {
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.change = false;
          this.oriRemark = remark;
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.template_info_wrapper {
  .remark_info {
    font-size: 14px;
    margin-top: 24px;
    display: flex;

    .info_title {
      color: #8c8c8c;
      flex: 0 0 48px;
      width: auto;
    }
  }
}
</style>
