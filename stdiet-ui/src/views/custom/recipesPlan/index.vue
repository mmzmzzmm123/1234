<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="客户信息" prop="customer">
        <el-input
          v-model="queryParams.customer"
          placeholder="请输入客户姓名或手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="日期" prop="planStartDateScope">
        <el-date-picker
          v-model="planStartDateScope"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="营养师" prop="nutritionistId">
        <el-select
          v-model="queryParams.nutritionistId"
          placeholder="请选择营养师"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in nutritionistIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="营养师助理" prop="nutritionistAssisId">
        <el-select
          v-model="queryParams.nutritionistAssisId"
          placeholder="请选择营养师助理"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in nutriAssisIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="reviewStatus">
        <el-select
          v-model="queryParams.reviewStatus"
          placeholder="请选审核状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in reviewStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <div>
      <span style="color: #e6a23c; font-family: PingFang SC">
        注意事项：
        <br />1、2021年1月开始的订单才会自动生成食谱计划</span
      >
    </div>
    <el-row :gutter="10" class="mb8" style="margin-top: 10px">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['recipes:plan:export']"
          >导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recipesPlanList">
      <el-table-column label="审核状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag :type="getReviewType(scope.row.reviewStatus)">
            {{ getReviewStatusName(scope.row.reviewStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="客户姓名" align="center" prop="customer" />
      <el-table-column
        label="客户手机号"
        align="center"
        prop="hidePhone"
        width="180"
      />
      <el-table-column
        label="计划"
        align="center"
        prop="scopeDay"
        width="200"
      />
      <el-table-column
        label="日期"
        align="center"
        prop="scopeDate"
        width="200"
      />
      <el-table-column label="营养师" align="center" prop="nutritionist" />
      <el-table-column
        label="营养师助理"
        align="center"
        prop="nutritionistAssis"
        width="180"
      />
      <el-table-column label="订阅情况" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.subscribed ? 'success' : 'danger'">
            {{ scope.row.subscribed ? "已订阅" : "未订阅" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发送" align="center" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="!!scope.row.sendFlag"
            @change="(val) => handleOnSendChange(val, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="300"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            v-hasPermi="['recipes:plan:edit']"
            :icon="`${
              scope.row.recipesId ? 'el-icon-edit' : 'el-icon-edit-outline'
            }`"
            @click="handleBuild(scope.row)"
            >{{ `${scope.row.recipesId ? "编辑" : "制作"}` }}</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  exportRecipesPlan,
  listRecipesPlan,
  updateRecipesPlan,
} from "@/api/custom/recipesPlan";
import dayjs from "dayjs";
import store from "@/store";
import { mapState } from "vuex";

const nextDate = dayjs().add(1, "day").format("YYYY-MM-DD");
const weekDate = dayjs().add(6, "day").format("YYYY-MM-DD");
const userId = store.getters && store.getters.userId;
export default {
  name: "recipesPlan",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 食谱计划表格数据
      recipesPlanList: [],
      orderDialog: undefined,
      reviewStatusOptions: [
        { dictValue: 0, dictLabel: "未制作" },
        { dictValue: 3, dictLabel: "制作中" },
        { dictValue: 1, dictLabel: "未审核" },
        { dictValue: 2, dictLabel: "已审核" },
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customer: null,
        startDate: null,
        endDate: null,
        nutritionistId: null,
        nutritionistAssisId: null,
        reviewStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //食谱开始时间范围
      planStartDateScope: [nextDate, nextDate],
      //订单对于所有计划安排数据
      allRecipesPlanList: [],
      //订单弹窗状态
      allRecipesPlanOpen: false,
      allRecipesPlanTitle: "",
      //订单弹窗中查询参数
      allRecipesPlanQueryParam: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        sendFlag: 0,
      },
      //订单弹窗中列表数据的总条数
      allRecipesPlanTotal: 0,
    };
  },
  components: {
    // "order-dialog": OrderDetail,
    // body_sign_dialog: BodySignDetail,
  },
  computed: {
    ...mapState({
      //营养师
      nutritionistIdOptions: (state) =>
        state.global.nutritionistIdOptions.slice(1),
      //营养师助理
      nutriAssisIdOptions: (state) => state.global.nutriAssisIdOptions.slice(1),
    }),
  },
  watch: {
    nutritionistIdOptions: function (val, oldVal) {
      if (val.length && !oldVal.length) {
        const tarObj = val.find((opt) => opt.dictValue == userId);
        if (tarObj) {
          this.queryParams.nutritionistId = userId;
        }
      }
    },
    nutriAssisIdOptions: function (val, oldVal) {
      if (val.length && !oldVal.length) {
        const tarObj = val.find((opt) => opt.dictValue == userId);
        if (tarObj) {
          this.queryParams.nutritionistAssisId = userId;
        }
      }
    },
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询食谱计划列表 */
    getList() {
      this.loading = true;
      if (
        this.planStartDateScope != null &&
        this.planStartDateScope.length > 0
      ) {
        this.queryParams.startDate = dayjs(this.planStartDateScope[0]).format(
          "YYYY-MM-DD"
        );
        this.queryParams.endDate = dayjs(this.planStartDateScope[1]).format(
          "YYYY-MM-DD"
        );
      } else {
        this.queryParams.startDate = null;
        this.queryParams.endDate = null;
      }
      listRecipesPlan(this.queryParams).then((response) => {
        this.recipesPlanList = response.rows;
        this.recipesPlanList.forEach(function (item, index) {
          item.scopeDate =
            dayjs(item.startDate).format("YYYY-MM-DD") +
            " 至 " +
            dayjs(item.endDate).format("YYYY-MM-DD");
          item.scopeDay = `第${item.startNumDay} 至 ${item.endNumDay}天`;
        });
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.reset();
    },
    handleOnSendChange(val, data) {
      const { id } = data;
      if (data.reviewStatus === 2) {
        updateRecipesPlan({
          id,
          sendFlag: val ? 1 : 0,
        }).then((res) => {
          if (res.code === 200) {
            this.$message.success(res.msg);
            const tarPlan = this.recipesPlanList.find((obj) => obj.id === id);
            if (tarPlan) {
              tarPlan.sendFlag = val ? 1 : 0;
            }
          }
        });
      } else {
        this.$message.error("未审核的食谱不能发送");
      }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderId: null,
        startDate: null,
        endDate: null,
        recipesId: null,
        sendFlag: null,
        sendTime: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        delFlag: null,
      };
      this.resetForm("form");
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
          return "";
        case 0:
        default:
          return "info";
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.planStartDateScope = [nextDate, nextDate];
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有食谱计划数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportRecipesPlan(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    handleBuild(data) {
      // console.log(data);
      const { id, customer } = data;
      window.open("/recipes/build/" + customer + "/" + id, "_blank");
    },
  },
};
</script>



