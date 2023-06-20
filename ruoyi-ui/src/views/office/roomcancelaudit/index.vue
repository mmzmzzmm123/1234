<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="原订单编号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入原订单编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="退款申请编号" prop="refundOrderNo">
        <el-input v-model="queryParams.refundOrderNo" placeholder="请输入退款申请编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['office:roomcancelaudit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['office:roomcancelaudit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['office:roomcancelaudit:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['office:roomcancelaudit:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roomcancelauditList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="预定订单号" align="center" prop="orderNo" />
      <el-table-column label="所在门店" align="center" prop="storeName" />
      <el-table-column label="预定房间" align="center" prop="roomName" />
      <el-table-column label="开始时间" align="center" prop="startTime" />
      <el-table-column label="结束时间" align="center" prop="endTime" />
      <!-- <el-table-column label="原订单编号" align="center" prop="unionId" /> -->
      <el-table-column label="用户名称" align="center" prop="nickName" />
      <el-table-column label="退款申请wx编号" align="center" prop="refundOrderNo" />
      <el-table-column label="退单金额" align="center" prop="refundAmount" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormatter" />
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleApprove(scope.row)"
            v-hasPermi="['office:roomcancelaudit:edit']">通过</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleAuditBack(scope.row)"
            v-hasPermi="['office:roomcancelaudit:remove']">退回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改预约取消审核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入原订单编号" />
        </el-form-item>
        <el-form-item label="退款申请wx编号" prop="refundOrderNo">
          <el-input v-model="form.refundOrderNo" placeholder="请输入退款申请wx编号" />
        </el-form-item>
        <el-form-item label="退单金额" prop="refundAmount">
          <el-input v-model="form.refundAmount" placeholder="请输入退单金额" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
  import {
    listRoomcancelaudit,
    getRoomcancelaudit,
    delRoomcancelaudit,
    addRoomcancelaudit,
    updateRoomcancelaudit,
    approve,
    auditBack
  } from "@/api/office/roomcancelaudit";

  export default {
    name: "Roomcancelaudit",
    dicts: ["refund_status"],
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
        // 预约取消审核表格数据
        roomcancelauditList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderNo: null,
          refundOrderNo: null,
          refundAmount: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          orderNo: [{
            required: true,
            message: "原订单编号不能为空",
            trigger: "blur"
          }],
          refundAmount: [{
            required: true,
            message: "退单金额不能为空",
            trigger: "blur"
          }],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询预约取消审核列表 */
      getList() {
        this.loading = true;
        listRoomcancelaudit(this.queryParams).then(response => {
          this.roomcancelauditList = response.rows;
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
          refundOrderNo: null,
          refundAmount: null,
          status: null,
          remark: null,
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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加预约取消审核";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getRoomcancelaudit(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改预约取消审核";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateRoomcancelaudit(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addRoomcancelaudit(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除预约取消审核编号为"' + ids + '"的数据项？').then(function() {
          return delRoomcancelaudit(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('office/roomcancelaudit/export', {
          ...this.queryParams
        }, `roomcancelaudit_${new Date().getTime()}.xlsx`)
      },
      statusFormatter(row, index) {
        return this.selectDictLabel(this.dict.type.refund_status, row.status);
      },
      /** 通过按钮操作 */
      handleApprove(row) {
        var form = {};
        form.id = row.id;
        form.status = 1;
        this.$modal.confirm('是否确认通过?').then(function() {
          return approve(form);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("操作成功");
        }).catch(() => {});
      },
      /** 退回按钮操作 */
      handleAuditBack(row) {
        var form = {};
        form.id = row.id;
        form.status = 2;
        this.$modal.confirm('是否确认退回?').then(function() {
          return auditBack(form);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("操作成功");
        }).catch(() => {});
      },
    }
  };
</script>
