<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择订单状态" clearable>
          <el-option
            v-for="dict in dict.type.consult_order"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应付金额" class="amount">
        <el-input
          v-model="queryParams.lowAmount"
          placeholder="请输入最低应付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
        <div style="margin: 0 10px">~</div>
        <el-input
          v-model="queryParams.highAmount"
          placeholder="请输入最高应付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['psychology:order:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['psychology:order:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['psychology:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['psychology:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单号" align="center" prop="orderNo" />
      <el-table-column label="咨询师" align="center" prop="consultName" />
      <el-table-column label="服务" align="center" prop="serveName" />
      <el-table-column label="客户" align="center" prop="userName" />
      <el-table-column label="班次" align="center" prop="type" />
      <el-table-column label="咨询时段开始" align="center" prop="timeStart" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.timeStart, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="咨询时段结束" align="center" prop="timeEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.timeEnd, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="咨询时长" align="center" prop="time" />
      <el-table-column label="可预约" align="center" prop="num" />
      <el-table-column label="已预约" align="center" prop="buyNum" />
      <el-table-column label="应付费用" align="center" prop="amount" />
      <el-table-column label="订单状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="130">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="finish(scope.row)"
            v-hasPermi="['psychology:order:edit']"
          >已完成</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['psychology:order:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['psychology:order:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改咨询订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="咨询师" prop="consultId">
          <el-input v-model="form.consultId" placeholder="请输入咨询师" />
        </el-form-item>
        <el-form-item label="咨询师" prop="consultName">
          <el-input v-model="form.consultName" placeholder="请输入咨询师" />
        </el-form-item>
        <el-form-item label="服务" prop="serveId">
          <el-input v-model="form.serveId" placeholder="请输入服务" />
        </el-form-item>
        <el-form-item label="服务" prop="serveName">
          <el-input v-model="form.serveName" placeholder="请输入服务" />
        </el-form-item>
        <el-form-item label="客户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入客户id" />
        </el-form-item>
        <el-form-item label="排班" prop="workId">
          <el-input v-model="form.workId" placeholder="请输入排班" />
        </el-form-item>
        <el-form-item label="咨询时段开始" prop="timeStart">
          <el-date-picker clearable
            v-model="form.timeStart"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择咨询时段开始">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="咨询时段结束" prop="timeEnd">
          <el-date-picker clearable
            v-model="form.timeEnd"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择咨询时段结束">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="应付费用" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入应付费用" />
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/psychology/order";

export default {
  name: "Order",
  dicts: ['consult_order'],
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
      // 咨询订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        consultName: null,
        serveName: null,
        userName: null,
        amount: null,
        lowAmount: null,
        highAmount: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        consultId: [
          { required: true, message: "咨询师不能为空", trigger: "blur" }
        ],
        consultName: [
          { required: true, message: "咨询师不能为空", trigger: "blur" }
        ],
        serveId: [
          { required: true, message: "服务不能为空", trigger: "blur" }
        ],
        serveName: [
          { required: true, message: "服务不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "客户id不能为空", trigger: "blur" }
        ],
        workId: [
          { required: true, message: "排班不能为空", trigger: "blur" }
        ],
        num: [
          { required: true, message: "可预约数量不能为空", trigger: "blur" }
        ],
        buyNum: [
          { required: true, message: "已预约数量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询咨询订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        orderNo: null,
        consultId: null,
        consultName: null,
        serveId: null,
        serveName: null,
        userId: null,
        workId: null,
        type: null,
        timeStart: null,
        timeEnd: null,
        time: null,
        num: null,
        buyNum: null,
        amount: null,
        delFlag: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加咨询订单";
    },
    /** 删除按钮操作 */
    finish(row) {
      const ids = row.id
      this.$modal.confirm('是否确认完成咨询订单编号为"' + row.orderNo + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改咨询订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除咨询订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('psychology/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style lang="scss" scoped>
.amount {
  ::v-deep {
    .el-form-item__content {
      display: inline-flex;
      align-items: center;
    }
    .el-input__inner {
      width: 150px;
    }
  }
}
</style>
