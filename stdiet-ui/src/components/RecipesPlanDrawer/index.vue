<template>
  <el-drawer
    :title="title"
    :close-on-press-escape="false"
    :visible.sync="visible"
    @closed="handleOnClosed"
    size="45%"
  >
    <div class="app-container recipes_plan_drawer_wrapper">
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
          <el-button
            size="mini"
            type="primary"
            v-if="fansChannel === 1"
            @click="createOneDay"
          >
            生成7天体验计划
          </el-button>
        </section>
        <section>
          <el-button
            icon="el-icon-refresh"
            size="mini"
            @click="getList"
            circle
          />
        </section>
      </div>

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
        <el-table-column label="操作" align="center" width="160">
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
            <el-button
              v-if="scope.row.reviewStatus === 1"
              type="text"
              icon="el-icon-delete"
              @click="handleOnDelete(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 暂停记录抽屉 -->
      <PlanPauseDrawer ref="planPauseRef" />
      <!-- 创建计划 -->
      <el-dialog
        title="创建1天体验计划"
        :visible.sync="open"
        width="480px"
        append-to-body
      >
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="100px"
          v-loading="createLoading"
        >
          <el-form-item label="开始时间" prop="startDate">
            <el-date-picker
              v-model="form.startDate"
              type="date"
              placeholder="选择开始时间"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :picker-options="fanPickerOptions"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleOnCreateConfirm"
            >确 定
          </el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </el-drawer>
</template>
<script>
import Clipboard from "clipboard";
import { listRecipesPlanByCusId } from "@/api/custom/recipesPlan";
import { addRecipesPlan, updateRecipesPlan } from "@/api/custom/recipesPlan";
import PlanPauseDrawer from "./PlanPauseDrawer";
import VueQr from "vue-qr";
import dayjs from "dayjs";
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
      open: false,
      visible: false,
      createLoading: false,
      title: "",
      cusOutId: "",
      copyValue: "",
      planLoading: false,
      planList: [],
      fansChannel: 0,
      form: {},
      rules: {
        startDate: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
      },
      fanPickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        },
      },
    };
  },
  methods: {
    showDrawer(data) {
      console.log(data);
      this.data = data;
      if (!this.data) {
        return;
      }

      this.fansChannel = data.fansChannel;
      this.visible = true;
      this.title = `「${this.data.name}」食谱计划`;

      this.getList();
    },
    getList() {
      this.planLoading = true;
      listRecipesPlanByCusId(this.data.id).then((response) => {
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
    reset() {
      this.form = {
        startDate: null,
      };
      this.resetForm("form");
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
    createOneDay() {
      this.open = true;
    },
    handleOnCreateConfirm() {
      const { id } = this.data;
      this.createLoading = true;
      addRecipesPlan({
        cusId: id,
        startNumDay: 1,
        endNumDay: 7,
        startDate: this.form.startDate,
        endDate: dayjs(this.form.startDate).add(6, "day").format("YYYY-MM-DD"),
      })
        .then((res) => {
          if (res.code === 200) {
            this.$message.success("创建成功");
            this.getList();
          }
          this.createLoading = false;
          this.open = false;
        })
        .catch(() => {
          this.createLoading = false;
        });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleOnDelete(data) {
      console.log(data);

      this.$confirm("此操作将删除对计划的食谱, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          updateRecipesPlan({
            id: data.id,
            recipesId: 0,
            reviewStatus: 0,
          }).then((res) => {
            if (res.code === 200) {
              this.$message.success("删除成功");
              this.getList();
            }
          });
        })
        .catch(() => {
          // this.$message({
          //   type: "info",
          //   message: "已取消删除",
          // });
        });
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

  .header {
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
