<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="咨询师ID" prop="consultantId">
        <el-input
          v-model="queryParams.consultantId"
          placeholder="请输入咨询师ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="咨询师ID" align="center" prop="consultantId" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
                {{ payStatusName(scope.row.status) }}
              </template>
      </el-table-column> 
      <el-table-column label="提现金额" align="center" prop="withdrawalAmount" />
      <el-table-column label="提现日期" align="center" prop="updateTime" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改提现订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="咨询师ID" prop="consultantId">
          <el-input v-model="form.consultantId" placeholder="请输入咨询师ID" />
        </el-form-item>
        <el-form-item label="提现金额" prop="withdrawalAmount">
          <el-input v-model="form.withdrawalAmount" placeholder="请输入提现金额" />
        </el-form-item>
        <el-form-item label="0 未删除 1 删除" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入0 未删除 1 删除" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/psychology/withdrawal";

export default {
  name: "Order",
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
      // 提现订单表格数据
      orderList: [],
      payStatusDic :[
        {
          id: "0",
          name: '失败'
        },
        {
          id: "1",
          name: '成功'
        },
      ],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        consultantId: null,
        status: null,
        withdrawalAmount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        consultantId: [
          { required: true, message: "咨询师ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    payStatusName(type) {
      const list = this.payStatusDic.filter(item => item.id === type)
      return list.length > 0 ? list[0].name : undefined
    },
    /** 查询提现订单列表 */
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
        withdrawalNo: null,
        consultantId: null,
        status: "0",
        withdrawalAmount: null,
        delFlag: null,
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
      this.ids = selection.map(item => item.withdrawalNo)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加提现订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const withdrawalNo = row.withdrawalNo || this.ids
      getOrder(withdrawalNo).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改提现订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.withdrawalNo != null) {
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
      const withdrawalNos = row.withdrawalNo || this.ids;
      this.$modal.confirm('是否确认删除提现订单编号为"' + withdrawalNos + '"的数据项？').then(function() {
        return delOrder(withdrawalNos);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
