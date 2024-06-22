<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="accountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="提现密码" align="center" prop="withdrawalPassword">
        <template slot-scope="scope">
          {{ withPayKey(scope.row.withdrawalPassword) }}
        </template>
      </el-table-column>  
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          {{ getStatusName(scope.row.status) }}
        </template>
      </el-table-column>
      <el-table-column label="账户余额" align="center" prop="amount" >
        <template slot-scope="scope">
          {{ amountStr(scope.row.amount) }}
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

    <!-- 添加或修改账户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="数字账号ID" prop="accountNumber">
          <el-input v-model="form.accountNumber" placeholder="请输入数字账号ID" />
        </el-form-item>
        <el-form-item label="提现密码" prop="withdrawalPassword">
          <el-input v-model="form.withdrawalPassword" placeholder="请输入提现密码" />
        </el-form-item>
        <el-form-item label="账户余额" prop="accountAmount">
          <el-input v-model="form.accountAmount" placeholder="请输入账户余额" />
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
import { listAccount, getAccount, delAccount, addAccount, updateAccount } from "@/api/psychology/account";

export default {
  name: "Account",
  props: {
    id: {
      type: String
    },
  },
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
      // 账户表格数据
      accountList: [],
      statusList: [
        {
          id: "1",
          name: '激活'
        },
        {
          id: "0",
          name: '失效'
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
        accountNumber: null,
        withdrawalPassword: null,
        status: null,
        consultantId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    amountStr(value) {
      if (!value) return '0.00';
      return value.toFixed(2);
    },
    withPayKey(name) {
      return name.length > 0 ? name.substring(0, 1) + '*******' : ""
    },
    getStatusName(type) {
      const list = this.statusList.filter(item => item.id === type)
      return list.length > 0 ? list[0].name : undefined
    },
    /** 查询账户列表 */
    getList() {
      console.log("getList   ："+this.id)
      this.loading = true;
      this.queryParams.consultantId = this.id;
      listAccount(this.queryParams).then(response => {
        this.accountList = response.rows;
        console.log(response.rows)
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
        consultantId: null,
        accountNumber: null,
        withdrawalPassword: null,
        status: "0",
        accountAmount: null,
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
      this.ids = selection.map(item => item.consultantId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加账户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const consultantId = row.consultantId || this.ids
      getAccount(consultantId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改账户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.consultantId != null) {
            updateAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAccount(this.form).then(response => {
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
      const consultantIds = row.consultantId || this.ids;
      this.$modal.confirm('是否确认删除账户编号为"' + consultantIds + '"的数据项？').then(function() {
        return delAccount(consultantIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/account/export', {
        ...this.queryParams
      }, `account_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
