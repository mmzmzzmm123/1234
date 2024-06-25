<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="账户ID" prop="consultantId">
        <el-input
          v-model="queryParams.consultantId"
          placeholder="请输入账户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="账户ID" align="center" prop="consultantId" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
            {{ payStatusName(scope.row.status) }}
         </template>
      </el-table-column> 
      <el-table-column label="提现类型" align="center" prop="payType">
        <template slot-scope="scope">
            {{ payTypeName(scope.row.payType) }}
        </template>
      </el-table-column>  
      <el-table-column label="订单ID" align="center" prop="orderId" />
      <el-table-column label="支付前账户余额" align="center" prop="accountAmount" />
      <el-table-column label="支付金额" align="center" prop="payAmount" />
      <el-table-column label="支付消息结果" align="center" prop="payMessage" />
      <el-table-column label="更新时间" align="center" prop="updateTime" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改账户明细流水对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账户ID" prop="consultantId">
          <el-input v-model="form.consultantId" placeholder="请输入账户ID" />
        </el-form-item>
        <el-form-item label="订单ID" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单ID" />
        </el-form-item>
        <el-form-item label="支付前账户余额" prop="accountAmount">
          <el-input v-model="form.accountAmount" placeholder="请输入支付前账户余额" />
        </el-form-item>
        <el-form-item label="支付金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="支付消息结果" prop="payMessage">
          <el-input v-model="form.payMessage" type="textarea" placeholder="请输入内容" />
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
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/psychology/record";

export default {
  name: "Record",
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
      // 账户明细流水表格数据
      recordList: [],
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
      payTypeDic :[
        {
          id: "0",
          name: '分成'
        },
        {
          id: "1",
          name: '提现'
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
        payType: null,
        orderId: null,
        accountAmount: null,
        payAmount: null,
        payMessage: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        consultantId: [
          { required: true, message: "账户ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    payTypeName(type) {
      const list = this.payTypeDic.filter(item => item.id === type)
      return list.length > 0 ? list[0].name : undefined
    },
    payStatusName(type) {
      const list = this.payStatusDic.filter(item => item.id === type)
      return list.length > 0 ? list[0].name : undefined
    },
    /** 查询账户明细流水列表 */
    getList() {
      this.loading = true;
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
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
        recordId: null,
        consultantId: null,
        status: "0",
        payType: null,
        orderId: null,
        accountAmount: null,
        payAmount: null,
        payMessage: null,
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
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加账户明细流水";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getRecord(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改账户明细流水";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecord(this.form).then(response => {
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
      const recordIds = row.recordId || this.ids;
      this.$modal.confirm('是否确认删除账户明细流水编号为"' + recordIds + '"的数据项？').then(function() {
        return delRecord(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
