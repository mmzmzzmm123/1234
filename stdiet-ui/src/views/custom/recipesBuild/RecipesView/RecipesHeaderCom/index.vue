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
        <el-button
          size="mini"
          type="primary"
          style="margin-left: 12px"
          icon="el-icon-download"
          @click="handleOnExportStartNumConfig"
          v-loading="downloading"
          :disabled="downloading"
        >
          导出图片
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
          v-if="!!recipesId"
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
import html2canvans from "html2canvas";
export default {
  name: "RecipesHeaderCom",
  components: {
    TemplateDialog,
  },
  data() {
    return {
      loading: false,
      downloading: false,
      nFontSize: 0,
      fontSizeOpts: [
        { value: 8, label: "8" },
        { value: 10, label: "10" },
        { value: 12, label: "12" },
        { value: 14, label: "14" },
        { value: 16, label: "16" },
        { value: 18, label: "18" },
      ],
      oriStartNum: 0,
    };
  },
  updated() {},
  computed: {
    ...mapState([
      "recipesId",
      "reviewStatus",
      "fontSize",
      "leftShow",
      "healthyData",
      "recipesData",
    ]),
  },
  watch: {},
  methods: {
    handleOnSizeChange(fontSize) {
      this.updateFontSize({ fontSize });
    },
    handleOnSave() {
      this.saveRecipes({
        reviewStatus: 3, // 制作中
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
            cusId: 0, // 模板特有
            planId,
            reviewStatus: 2, // 已审核
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
    handleOnExportStartNumConfig() {
      this.oriStartNum = this.recipesData[0].numDay;
      this.$prompt("食谱开始天数", "导出图片", {
        confirmButtonText: "确定",
        inputValue: this.oriStartNum,
        inputPattern: /^[1-9]\d*$/,
        inputErrorMessage: "请输入正整数",
      }).then(({ value }) => {
        this.handleOnExportImg(parseInt(value));
      });
    },
    handleOnExportImg(startNum) {
      this.downloading = true;
      this.$nextTick(() => {
        const centerContentDom = document.getElementById("center_content");
        if (!centerContentDom) {
          this.downloading = false;
          return;
        }
        centerContentDom.style.overflow = "visible";
        const recipesDom = document.getElementById("recipes_content");
        if (!recipesDom) {
          this.downloading = false;
          return;
        }
        Array.from({ length: this.recipesData.length }).forEach((_, idx) => {
          const tmpElm = document.getElementById(`cus_name_${idx}`);
          if (tmpElm) {
            tmpElm.classList = [];
          }
          const tmpNum = document.getElementById(`day_num_${idx}`);
          if (tmpNum) {
            tmpNum.innerText = `第${startNum + idx}天`;
          }
        });
        recipesDom.style.overflow = "visible";
        html2canvans(recipesDom, {
          scale: 1.5,
          height: recipesDom.scrollHeight,
        }).then((canvas) => {
          const { name } = this.healthyData;
          // const startNum = this.recipesData[0].numDay;
          // const endNum = this.recipesData[this.recipesData.length - 1].numDay;
          const endNum = startNum + this.recipesData.length - 1;
          const link = document.createElement("a");
          link.download = `${name || "匿名"}_第${startNum}至${endNum}天.jpeg`;
          link.href = canvas.toDataURL();
          link.click();

          centerContentDom.style.overflow = "auto";
          recipesDom.style.overflow = "auto";

          Array.from({ length: this.recipesData.length }).forEach((_, idx) => {
            const tmpElm = document.getElementById(`cus_name_${idx}`);
            if (tmpElm) {
              tmpElm.classList = ["cus_name_hide"];
            }
            const tmpNum = document.getElementById(`day_num_${idx}`);
            if (tmpNum) {
              tmpNum.innerText = `第${this.oriStartNum + idx}天`;
            }
          });

          this.downloading = false;
          this.$message.success("食谱导出成功");
        });
      });
    },
    ...mapActions(["saveRecipes", "updateReviewStatus"]),
    ...mapMutations(["updateStateData", "updateFontSize", "toggleLeftShow"]),
  },
};
</script>
<style  lang="scss" scoped>
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

  /deep/ .el-loading-spinner {
    margin-top: -10px;
  }

  /deep/.el-loading-spinner .circular {
    width: 20px;
    height: 20px;
  }
}
</style>
