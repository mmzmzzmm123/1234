<template>
  <div class="recipes_header_com_wrapper">
    <div class="header_btns" v-loading="loading">
      <section style="display: flex; align-items: center">
        <em
          :class="`${
            leftShow ? 'el-icon-s-fold' : 'el-icon-s-unfold'
          } collapse_btn`"
          @click="toggleLeftShow"
        />
        <div>食谱制作</div>
        <el-button
          size="mini"
          v-if="!!recipesId"
          type="primary"
          style="margin-left: 12px"
          icon="el-icon-document-copy"
          @click="handleOnTemplateClick"
        >
          另存为模板
        </el-button>
      </section>
      <section>
        <span class="font_size_style">
          字体大小：
          <el-select
            v-model="fontSize"
            size="mini"
            style="width: 80px"
            @change="handleOnSizeChange"
          >
            <el-option
              v-for="size in fontSizeOpts"
              :key="size.value"
              :label="size.label"
              :value="size.value"
            />
          </el-select>
        </span>
        <el-button size="mini" v-if="!recipesId" @click="handleOnBack">
          返回
        </el-button>
        <el-popover
          placement="bottom"
          trigger="click"
          title="修改食谱状态"
          style="margin-right: 12px"
        >
          <div>
            <el-button
              size="mini"
              type="success"
              v-hasPermi="['recipes:plan:review']"
              @click="hanldeOnReveiwChange(2)"
              >已审核</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="hanldeOnReveiwChange(1)"
              >未审核</el-button
            >
            <el-button
              size="mini"
              type="primary"
              @click="hanldeOnReveiwChange(3)"
              >制作中</el-button
            >
          </div>
          <el-button
            slot="reference"
            size="mini"
            v-if="!!recipesId"
            :type="getReviewType(reviewStatus)"
          >
            {{ getReviewStatusName(reviewStatus) }}
          </el-button>
        </el-popover>
        <el-button
          v-if="!recipesId"
          size="mini"
          type="primary"
          @click="handleOnSave"
          >生成食谱</el-button
        >
      </section>
    </div>
    <!-- 模板 -->
    <TemplateDialog ref="templateRef" @onConfirm="handleOnCopy" />
  </div>
</template>
<script>
import { addRecipesTemplate } from "@/api/custom/recipesTemplate";
import { createNamespacedHelpers } from "vuex";
const { mapActions, mapState, mapMutations } = createNamespacedHelpers(
  "recipes"
);
import TemplateDialog from "@/components/TemplateDialog";
export default {
  name: "RecipesHeaderCom",
  components: {
    TemplateDialog,
  },
  data() {
    return {
      loading: false,
      nFontSize: 0,
      fontSizeOpts: [
        { value: 8, label: "8" },
        { value: 10, label: "10" },
        { value: 12, label: "12" },
        { value: 14, label: "14" },
        { value: 16, label: "16" },
        { value: 18, label: "18" },
      ],
    };
  },
  updated() {},
  computed: {
    ...mapState(["recipesId", "reviewStatus", "fontSize", "leftShow"]),
  },
  watch: {},
  methods: {
    handleOnSizeChange(fontSize) {
      this.updateFontSize({ fontSize });
    },
    handleOnSave() {
      this.saveRecipes({
        callback: (query) => {
          // console.log(query);
          let path = "/recipes/build/" + query.name + "/" + query.planId;
          if (this.$route.query.temId) {
            path += "?temId=" + this.$route.query.temId;
          }
          this.$router.replace({
            path,
          });
        },
      });
    },
    hanldeOnReveiwChange(reviewStatus) {
      this.updateReviewStatus({ reviewStatus });
    },
    handleOnBack() {
      this.updateStateData({ recipesData: [] });
    },
    handleOnTemplateClick() {
      this.$refs.templateRef.showDialog();
    },
    handleOnCopy(form) {
      this.loading = true;
      addRecipesTemplate(form).then((response) => {
        if (response.code === 200) {
          const { planId, id } = response.data;
          this.saveRecipes({
            cusId: 0,
            planId,
            callback: () => {
              this.$message.success(`另存为模板「${form.name}」成功`);
              this.loading = false;
              // window.open(
              //   "/recipes/build/" + form.name + "/" + planId + "?temId=" + id,
              //   "_blank"
              // );
            },
          });
        }
      });
    },
    getReviewStatusName(status) {
      switch (status) {
        case 1:
          return "未审核";
        case 2:
          return "已审核";
        case 3:
          return "制作中";
        case 0:
        default:
          return "未制作";
      }
    },
    getReviewType(status) {
      switch (status) {
        case 1:
          return "danger";
        case 2:
          return "success";
        case 3:
          return "primary";
        case 0:
        default:
          return "info";
      }
    },
    ...mapActions(["saveRecipes", "updateReviewStatus"]),
    ...mapMutations(["updateStateData", "updateFontSize", "toggleLeftShow"]),
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scope>
.recipes_header_com_wrapper {
  .header_btns {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-bottom: 8px;

    .collapse_btn {
      font-size: 18px;
      padding: 4px;
      cursor: pointer;
    }
  }

  .font_size_style {
    display: inline-flex;
    align-items: center;
    font-size: 12px;
    margin-right: 12px;
  }
}
</style>
