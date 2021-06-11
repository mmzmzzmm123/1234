<template>
  <div class="recipes_plan_wrapper">
    <div class="header">
      <section>
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
          <VueQr :text="copyValue" :logoSrc="logo" :size="256" />
          <el-button
            slot="reference"
            size="mini"
            icon="el-icon-picture-outline"
            type="primary"
            @click="handleCopy(scope.row.path)"
            >二维码</el-button
          >
        </el-popover>
        <!-- <el-button icon="el-icon-view" size="mini" @click="handleInnerOpen"
          >查看暂停记录
        </el-button> -->
      </section>
      <section>
        <el-button
          icon="el-icon-refresh"
          size="mini"
          @click="getRecipesPlanActions({ cusId })"
          circle
        />
      </section>
    </div>

    <el-table :data="planList" v-loading="planListLoading">
      <el-table-column label="审核状态" align="center">
        <template slot-scope="scope">
          <el-tag :type="getReviewType(scope.row.reviewStatus)">
            {{ getReviewStatusName(scope.row.reviewStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="计划" align="center" width="130">
        <template slot-scope="scope">
          {{ `第${scope.row.startNumDay} 至 ${scope.row.endNumDay}天` }}
        </template>
      </el-table-column>
      <el-table-column label="日期" align="center" width="200">
        <template slot-scope="scope">
          {{ `${scope.row.startDate} 至 ${scope.row.endDate}` }}
        </template>
      </el-table-column>
      <el-table-column label="订阅情况" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.subscribed ? 'success' : 'danger'">
            {{ scope.row.subscribed ? "已订阅" : "未订阅" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发送" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="!!scope.row.sendFlag"
            @change="(val) => handleOnSendChange(val, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
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
    <!-- <PlanPauseDrawer ref="planPauseRef" /> -->
  </div>
</template>
<script>
import Clipboard from "clipboard";
import { createNamespacedHelpers } from "vuex";
const { mapActions, mapState, mapMutations, mapGetters } =
  createNamespacedHelpers("message");
// import PlanPauseDrawer from "./PlanPauseDrawer";
import VueQr from "vue-qr";
const logo = require("@/assets/logo/logo_b.png");
export default {
  name: "RecipesPlanDrawer",
  components: {
    // PlanPauseDrawer,
    VueQr,
  },
  data() {
    return {
      logo,
      title: "",
      cusOutId: "",
      copyValue: "",
      cusId: "",
    };
  },
  watch: {
    planList(val, newVal) {
      // console.log({ val, newVal });
      this.cusOutId = val.reduce((str, cur) => {
        if (!str && cur.recipesId && cur.reviewStatus === 2) {
          str = cur.outId;
          this.cusId = cur.cusId;
        }
        return str;
      }, "");
      if (this.cusOutId) {
        this.copyValue =
          window.location.origin.replace("manage", "sign") +
          "/recipes/detail/" +
          this.cusOutId;
      }
    },
  },
  computed: {
    ...mapState(["planList", "planListLoading", "healthyData"]),
  },
  methods: {
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
          return "";
        case 0:
        default:
          return "info";
      }
    },
    // handleInnerOpen() {
    //   this.$refs["planPauseRef"].showDrawer(this.data);
    //   this.innerVisible = true;
    //   this.innerTitle = `「${this.data.name}」暂停记录`;
    // },
    handleOnRecipesLinkClick() {
      new Clipboard(".copyBtn");
      this.$message({
        message: "拷贝成功",
        type: "success",
      });
    },
    handleOnRecipesEditClick(data) {
      window.open(
        "/recipes/build/" + this.healthyData.name + "/" + data.id,
        "_blank"
      );
    },
    handleOnSendChange(val, data) {
      // console.log({ val, data });
      const { id } = data;
      if (data.reviewStatus === 2) {
        this.updateRecipesPlanActions({
          id,
          sendFlag: val ? 1 : 0,
          callback: (type, msg) => {
            this.$message[type](msg);
          },
        });
      } else {
        this.$message.error("未审核的食谱不能发送");
      }
    },
    ...mapActions(["getRecipesPlanActions", "updateRecipesPlanActions"]),
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}

.recipes_plan_wrapper {
  // height: calc(100vh - 77px);

  .header {
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
