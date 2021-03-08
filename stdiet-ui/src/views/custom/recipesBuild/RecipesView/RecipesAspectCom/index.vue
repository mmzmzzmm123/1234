<template>
  <div
    class="recipes_aspect_wrapper"
    :style="`height: ${collapse ? 30 : 200}px`"
  >
    <div class="header" v-loading="loading">
      <div class="header_btns">
        <span>
          <el-button
            size="mini"
            v-if="!!recipesId"
            type="primary"
            icon="el-icon-document-copy"
            @click="handleOnTemplateClick"
          >
            另存为模板
          </el-button>
        </span>
        <span>
          <span class="font_size_style">
            字体大小：
            <el-select
              v-model="mFontSize"
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
          <el-button size="mini" v-if="!recipesId" @click="handleOnBack"
            >返回</el-button
          >
          <el-popover
            placement="bottom"
            trigger="click"
            title="修改审核状态"
            style="margin-right: 12px"
            v-hasPermi="['recipes:plan:review']"
          >
            <div>
              <el-button
                size="mini"
                type="success"
                @click="hanldeOnReveiwChange(2)"
                >审核通过</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="hanldeOnReveiwChange(1)"
                >未审核通过</el-button
              >
            </div>
            <el-button
              slot="reference"
              size="mini"
              v-if="reviewStatus"
              :type="reviewStatus === 1 ? 'danger' : 'success'"
            >
              {{ reviewStatus === 1 ? "未审核" : "已审核" }}
            </el-button>
          </el-popover>
          <el-button
            v-if="!recipesId"
            size="mini"
            type="primary"
            @click="handleOnSave"
            >生成食谱</el-button
          >
        </span>
      </div>
      <el-button
        size="mini"
        type="text"
        @click="handleCollapseClick"
        class="collapse_btn"
      >
        {{ `${collapse ? "展开" : "收起"}` }}
        <em
          class="el-icon-arrow-down arrow_icon"
          :style="
            collapse ? 'transform: rotate(-180deg);' : 'transform: unset;'
          "
        />
      </el-button>
    </div>
    <div
      class="content"
      :style="`visibility: ${collapse ? 'hidden' : 'visible'};`"
    >
      <BarChart
        v-if="data.length > 1"
        :data="data"
        height="170px"
        width="500px"
        :max="
          healthyData.basicBMR
            ? parseFloat(
                healthyData.basicBMR.substring(
                  0,
                  healthyData.basicBMR.indexOf('千卡')
                )
              )
            : 0
        "
      />
      <PieChart
        v-if="data.length === 1"
        :data="data"
        height="170px"
        width="500px"
      />
    </div>
    <!-- 模板 -->
    <TemplateDialog ref="templateRef" @onConfirm="handleOnCopy" />
  </div>
</template>
<script>
import BarChart from "./BarChart";
import PieChart from "./PieChart";
import { addRecipesTemplate } from "@/api/custom/recipesTemplate";
import { createNamespacedHelpers } from "vuex";
const { mapActions, mapState, mapMutations } = createNamespacedHelpers(
  "recipes"
);
import TemplateDialog from "@/components/TemplateDialog";
export default {
  name: "RecipesAspectCom",
  components: {
    BarChart,
    PieChart,
    TemplateDialog,
  },
  data() {
    return {
      loading: false,
      mFontSize: 12,
      fontSizeOpts: [
        { value: 12, label: "12" },
        { value: 14, label: "14" },
        { value: 16, label: "16" },
        { value: 18, label: "18" },
      ],
    };
  },
  updated() {
    // console.log(this.data);
  },
  props: ["collapse", "data"],
  computed: {
    ...mapState(["recipesId", "reviewStatus", "healthyData", "fontSize"]),
  },
  watch: {
    fontSize(val) {
      this.mFontSize = val;
    },
  },
  methods: {
    handleOnSizeChange(fontSize) {
      this.updateFontSize({ fontSize });
    },
    handleCollapseClick() {
      this.$emit("update:collapse", !this.collapse);
    },
    handleOnSave() {
      this.saveRecipes({
        callback: (query) => {
          // console.log(query);
          this.$router.replace({
            path: "/recipes/build/" + query.name + "/" + query.planId,
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
              window.open(
                "/recipes/build/" + form.name + "/" + planId + "?temId=" + id,
                "_blank"
              );
            },
          });
        }
      });
    },
    ...mapActions(["saveRecipes", "updateReviewStatus"]),
    ...mapMutations(["updateStateData", "updateFontSize"]),
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scope>
.recipes_aspect_wrapper {
  transition: all 0.3s;
  padding-bottom: 12px;

  .header {
    text-align: right;
    height: 30px;
    display: flex;
    align-items: center;

    .header_btns {
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex: 1;
    }

    .collapse_btn {
      width: 42px;
    }

    .arrow_icon {
      transition: all 0.3s;
      transform-origin: center center;
    }

    .font_size_style {
      display: inline-flex;
      align-items: center;
      font-size: 12px;
      margin-right: 12px;
    }
  }

  .content {
  }
}
</style>
