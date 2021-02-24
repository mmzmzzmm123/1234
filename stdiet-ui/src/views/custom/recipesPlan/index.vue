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
        />
      </el-form-item>
      <el-form-item
        label="食谱开始日期范围"
        prop="planStartDateScope"
        label-width="130px"
      >
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['recipes:recipesPlan:edit']"
          >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['recipes:recipesPlan:export']"
          >导出
        </el-button>
      </el-col>
      <!--<div><span style="margin-left:10px;font-size:16px;color:#E6A23C;font-family:PingFang SC">备注：2021年1月开始的订单才会自动生成食谱计划</span></div>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="recipesPlanList"
      @selection-change="handleSelectionChange"
    >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="客户姓名" align="center" prop="customer" />
      <el-table-column
        label="客户手机号"
        align="center"
        prop="hidePhone"
        width="180"
      />
      <el-table-column
        label="食谱日期范围"
        align="center"
        prop="scopeDate"
        width="200"
      />
      <el-table-column
        label="食谱天数范围"
        align="center"
        prop="scopeDay"
        width="200"
      />
      <el-table-column label="营养师" align="center" prop="nutritionist" />
      <el-table-column
        label="营养师助理"
        align="center"
        prop="nutritionistAssis"
        width="180"
      />
      <el-table-column label="是否发送" align="center" prop="sendFlag">
        <!--<template slot-scope="scope">
          <span>{{ scope.row.sendFlag == 1 ? "已发送" : "未发送"}}</span>
        </template>(.sendFlag == 1) ? 'success' : 'warning'-->
        <template slot-scope="scope">
          <el-tag :type="getTagType(scope.row)" disable-transitions>
            {{ scope.row.sendFlag == 1 ? "已发送" : "未发送" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="食谱发送时间"
        align="center"
        prop="sendTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendTime, "{y}-{m}-{d}") }}</span>
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['recipes:recipesPlan:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="
              allRecipesPlanQueryParam.orderId = scope.row.orderId;
              getAllPlanByOrderId();
            "
            >查看完整计划
          </el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getOrderDetail(scope.row)"
            v-hasPermi="['custom:order:query']"
            >查看订单
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getCustomerSign(scope.row)"
            v-hasPermi="['custom:customer:query']"
            >查看体征
          </el-button> -->
          <el-button
            size="mini"
            type="text"
            :icon="`${
              scope.row.recipesId ? 'el-icon-edit' : 'el-icon-edit-outline'
            }`"
            @click="handleBuild(scope.row)"
            >{{ `${scope.row.recipesId ? "编辑" : "制作"}食谱` }}</el-button
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

    <!-- 添加或修改食谱计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item
          label="食谱是否已发送"
          prop="sendFlag"
          label-width="120px"
        >
          <el-select v-model="form.sendFlag" placeholder="请选择">
            <el-option label="否" :value="parseInt('0')" />
            <el-option label="是" :value="parseInt('1')" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看完整计划 -->
    <el-dialog
      :title="allRecipesPlanTitle"
      :visible.sync="allRecipesPlanOpen"
      width="800px"
      append-to-body
    >
      <el-form
        :model="allRecipesPlanQueryParam"
        ref="allPlanQueryFrom"
        :inline="true"
      >
        <el-form-item label="发送状态" prop="sendFlag">
          <el-select
            v-model="allRecipesPlanQueryParam.sendFlag"
            placeholder="请选择"
          >
            <el-option label="全部" :value="null" />
            <el-option label="未发送" :value="parseInt('0')" />
            <el-option label="已发送" :value="parseInt('1')" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="cyan"
            icon="el-icon-search"
            size="mini"
            @click="getAllPlanByOrderId()"
            >搜索</el-button
          >
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="allRecipesPlanList" width="700px">
        <!-- <el-table-column label="客户姓名" align="center" prop="customer" /> -->
        <!--<el-table-column label="营养师名称" align="center" prop="nutritionist" />
        <el-table-column label="营养师助理名称" align="center" prop="nutritionistAssis" />-->

        <el-table-column
          label="食谱时间范围"
          align="center"
          prop="scopeDate"
          width="250"
        />
        <el-table-column label="食谱是否发送" align="center" prop="sendFlag">
          <!--<template slot-scope="scope">
            <span>{{ scope.row.sendFlag == 1 ? "已发送" : "未发送"}}</span>
          </template>(.sendFlag == 1) ? 'success' : 'warning'-->
          <template slot-scope="scope">
            <el-tag :type="getTagType(scope.row)" disable-transitions>
              {{ scope.row.sendFlag == 1 ? "已发送" : "未发送" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="食谱发送时间"
          align="center"
          prop="sendTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.sendTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="allRecipesPlanTotal > 0"
        :total="allRecipesPlanTotal"
        :page.sync="allRecipesPlanQueryParam.pageNum"
        :limit.sync="allRecipesPlanQueryParam.pageSize"
        @pagination="getAllPlanByOrderId"
      />
      <!--<div slot="footer" class="dialog-footer">
        <el-button @click="allRecipesPlanOpen = false">关 闭</el-button>
      </div>-->
    </el-dialog>

    <!-- 查看订单 -->
    <!-- <order-dialog ref="orderDialog" /> -->
    <!-- 查看体征 -->
    <!-- <body_sign_dialog ref="bodySignDialog" /> -->
  </div>
</template>

<script>
import {
  exportRecipesPlan,
  getRecipesPlan,
  listRecipesPlan,
  updateRecipesPlan,
} from "@/api/custom/recipesPlan";
import { getOptions } from "@/api/custom/order";
// import OrderDetail from "@/components/OrderDetail";
// import BodySignDetail from "@/components/BodySignDetail";
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      orderDialog: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customer: null,
        startDate: null,
        endDate: null,
        nutritionistId: null,
        nutritionistAssisId: null,
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
    /** 查询客户体征 */
    getCustomerSign(row) {
      this.$refs.bodySignDialog.showDialog(row.phone);
    },
    getAllPlanByOrderId() {
      if (this.allRecipesPlanQueryParam.sendFlag === "") {
        this.allRecipesPlanQueryParam.sendFlag = null;
      }
      listRecipesPlan(this.allRecipesPlanQueryParam).then((response) => {
        this.allRecipesPlanList = response.rows;
        this.allRecipesPlanList.forEach(function (item, index) {
          item.scopeDate =
            dayjs(item.startDate).format("YYYY-MM-DD") +
            " 到 " +
            dayjs(item.endDate).format("YYYY-MM-DD");
        });
        this.allRecipesPlanOpen = true;
        this.allRecipesPlanTitle = `「${this.allRecipesPlanList[0].customer}」食谱计划表`;
        this.allRecipesPlanTotal = response.total;
      });
    },
    getOrderDetail(row) {
      this.$refs.orderDialog.showDialog(row.orderId);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRecipesPlan(id).then((response) => {
        this.form.id = response.data.id;
        this.form.sendFlag = response.data.sendFlag;
        this.open = true;
        this.title = "修改食谱计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateRecipesPlan(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
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
    getTagType(row) {
      if (row.sendFlag == 1) {
        return "success";
      }
      return "danger";
      /* if(dayjs(row.startDate+"").diff(dayjs(),'day') <= 1){
              return "danger";
         }
         return '';*/
    },
    // 自定义列背景色
    columnStyle({ row, column, rowIndex, columnIndex }) {
      if (
        columnIndex == 0 ||
        columnIndex == 2 ||
        columnIndex == 4 ||
        columnIndex == 6
      ) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
    // 和并列
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1,
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          };
        }
      }
    },
    handleBuild(data) {
      // console.log(data);
      const { id, cusId, recipesId, customer, startNumDay, endNumDay } = data;

      const queryParam = {
        planId: id,
        cusId,
        name: customer,
      };
      if (recipesId) {
        queryParam.recipesId = recipesId;
      } else {
        queryParam.startNum = startNumDay;
        queryParam.endNum = endNumDay;
      }
      const routeUrl = this.$router.resolve({
        path: "/recipes/build",
        query: queryParam,
      });
      window.open(routeUrl.href, "_blank");
      // this.$router.push({ path: "/recipes/build", query: queryParam });
    },
  },
};
</script>



