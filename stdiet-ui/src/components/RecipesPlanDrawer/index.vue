<template>
  <el-drawer
    :title="title"
    :close-on-press-escape="false"
    :visible.sync="visible"
    @closed="handleOnClosed"
    size="45%"
  >
    <div class="app-container recipes_plan_drawer_wrapper">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            v-if="cusOutId"
            type="primary"
            icon="el-icon-share"
            size="mini"
            class="copyBtn"
            :data-clipboard-text="copyValue"
            @click="handleOnRecipesLinkClick"
            >食谱链接
          </el-button>
          <el-popover
            placement="top"
            trigger="click"
            v-if="cusOutId"
            style="margin: 0 12px"
          >
            <VueQr :text="copyValue" :logoSrc="logo" size="256" />
            <el-button
              slot="reference"
              size="mini"
              icon="el-icon-picture-outline"
              type="primary"
              @click="handleCopy(scope.row.path)"
              >二维码</el-button
            >
          </el-popover>
          <el-button icon="el-icon-view" size="mini" @click="handleInnerOpen"
            >查看暂停记录
          </el-button>
        </el-col>
      </el-row>

      <el-table :data="planList" v-loading="planLoading" height="80%">
        <el-table-column label="审核状态" align="center" width="80">
          <template slot-scope="scope">
            <el-tag
              :type="
                scope.row.reviewStatus === 0
                  ? 'info'
                  : scope.row.reviewStatus === 2
                  ? 'success'
                  : 'danger'
              "
              >{{
                `${
                  scope.row.reviewStatus === 0
                    ? "未制作"
                    : scope.row.reviewStatus === 2
                    ? "已审核"
                    : "未审核"
                }`
              }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="计划" align="center">
          <template slot-scope="scope">
            {{ `第${scope.row.startNumDay} 至 ${scope.row.endNumDay}天` }}
          </template>
        </el-table-column>
        <el-table-column label="日期" align="center">
          <template slot-scope="scope">
            {{ `${scope.row.startDate} 至 ${scope.row.endDate}` }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="120">
          <template slot-scope="scope">
            <el-button
              type="text"
              :icon="
                scope.row.recipesId ? 'el-icon-edit' : 'el-icon-edit-outline'
              "
              @click="handleOnRecipesEditClick(scope.row)"
            >
              {{ `${scope.row.recipesId ? "编辑" : "制作"}` }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 暂停记录抽屉 -->
      <PlanPauseDrawer ref="planPauseRef" />
    </div>
  </el-drawer>
</template>
<script>
import Clipboard from "clipboard";
import { listRecipesPlanByCusId } from "@/api/custom/recipesPlan";
import PlanPauseDrawer from "./PlanPauseDrawer";
import VueQr from "vue-qr";
const logo = require("@/assets/logo/logo_b.png");
export default {
  name: "RecipesPlanDrawer",
  components: {
    PlanPauseDrawer,
    VueQr,
  },
  data() {
    return {
      logo,
      visible: false,
      title: "",
      cusOutId: "",
      copyValue: "",
      planLoading: false,
      planList: [],
    };
  },
  methods: {
    showDrawer(data) {
      // console.log(data);
      this.data = data;
      if (!this.data) {
        return;
      }

      this.visible = true;
      this.title = `「${this.data.name}」食谱计划`;
      this.planLoading = true;
      listRecipesPlanByCusId(data.id).then((response) => {
        this.planList = response.data;
        this.cusOutId = response.data.reduce((str, cur) => {
          if (!str && cur.recipesId && cur.reviewStatus === 2) {
            str = cur.outId;
          }
          return str;
        }, "");
        this.copyValue =
          window.location.origin.replace("manage", "sign") +
          "/recipes/detail/" +
          this.cusOutId;
        // console.log(this.planList);
        this.planLoading = false;
      });
    },
    handleOnClosed() {
      this.data = undefined;
      this.cusOutId = "";
    },
    handleInnerOpen() {
      this.$refs["planPauseRef"].showDrawer(this.data);
      this.innerVisible = true;
      this.innerTitle = `「${this.data.name}」暂停记录`;
    },
    handleOnRecipesLinkClick() {
      new Clipboard(".copyBtn");
      this.$message({
        message: "拷贝成功",
        type: "success",
      });
    },
    handleOnRecipesEditClick(data) {
      // console.log(data);
      // const { id, name } = this.data;
      window.open("/recipes/build/" + this.data.name + "/" + data.id, "_blank");
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}

.recipes_plan_drawer_wrapper {
  height: calc(100vh - 77px);
}
</style>
