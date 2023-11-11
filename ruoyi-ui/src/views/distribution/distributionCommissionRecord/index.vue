<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户标识" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单标识" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="记录类型" prop="commissionRecordType">
        <el-select v-model="queryParams.commissionRecordType" placeholder="请选择记录类型" clearable>
          <el-option
            v-for="dict in dict.type.commission_record_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="分销用户" prop="distributionUserId">
        <el-input
          v-model="queryParams.distributionUserId"
          placeholder="请输入分销用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分销层级" prop="distributionLevel">
        <el-select v-model="queryParams.distributionLevel" placeholder="请选择分销层级" clearable>
          <el-option
            v-for="dict in dict.type.distribution_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['distribution:distributionCommissionRecord:add']"
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
          v-hasPermi="['distribution:distributionCommissionRecord:edit']"
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
          v-hasPermi="['distribution:distributionCommissionRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['distribution:distributionCommissionRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="distributionCommissionRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" :show-overflow-tooltip="true" />
      <el-table-column label="用户标识" align="center" prop="userId" :show-overflow-tooltip="true"/>
      <el-table-column label="记录类型" align="center" prop="commissionRecordType" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.commission_record_type" :value="scope.row.commissionRecordType"/>
        </template>
      </el-table-column>
      <el-table-column label="订单标识" align="center" prop="orderId" :show-overflow-tooltip="true"/>
      <el-table-column label="订单金额" align="center" prop="orderAmount" :show-overflow-tooltip="true"/>
      <el-table-column label="分销用户" align="center" prop="distributionUserId" :show-overflow-tooltip="true"/>
      <el-table-column label="分销层级" align="center" prop="distributionLevel" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.distribution_level" :value="scope.row.distributionLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="分销比例" align="center" prop="distributionRate" :show-overflow-tooltip="true"/>
      <el-table-column label="分销佣金" align="center" prop="distributionCommission" :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['distribution:distributionCommissionRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['distribution:distributionCommissionRecord:remove']"
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

    <!-- 添加或修改分销佣金记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户标识" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户标识" />
        </el-form-item>
        <el-form-item label="记录类型" prop="commissionRecordType">
          <el-select v-model="form.commissionRecordType" placeholder="请选择记录类型">
            <el-option
              v-for="dict in dict.type.commission_record_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单标识" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单标识" />
        </el-form-item>
        <el-form-item label="订单金额" prop="orderAmount">
          <el-input v-model="form.orderAmount" placeholder="请输入订单金额" />
        </el-form-item>
        <el-form-item label="分销用户" prop="distributionUserId">
          <el-input v-model="form.distributionUserId" placeholder="请输入分销用户" />
        </el-form-item>
        <el-form-item label="分销层级" prop="distributionLevel">
          <el-select v-model="form.distributionLevel" placeholder="请选择分销层级">
            <el-option
              v-for="dict in dict.type.distribution_level"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分销比例" prop="distributionRate">
          <el-input v-model="form.distributionRate" placeholder="请输入分销比例" />
        </el-form-item>
        <el-form-item label="分销佣金" prop="distributionCommission">
          <el-input v-model="form.distributionCommission" placeholder="请输入分销佣金" />
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
import { listDistributionCommissionRecord, getDistributionCommissionRecord, delDistributionCommissionRecord, addDistributionCommissionRecord, updateDistributionCommissionRecord } from "@/api/distribution/distributionCommissionRecord";

export default {
  name: "DistributionCommissionRecord",
  dicts: ['distribution_level', "commission_record_type"],
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
      // 分销佣金记录表格数据
      distributionCommissionRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 分销佣金时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        orderId: null,
        distributionUserId: null,
        distributionLevel: null,
        commissionRecordType: null,
        createTime: null
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
    /** 查询分销佣金记录列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listDistributionCommissionRecord(this.queryParams).then(response => {
        this.distributionCommissionRecordList = response.rows;
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
        userId: null,
        orderId: null,
        orderAmount: null,
        distributionUserId: null,
        distributionLevel: null,
        commissionRecordType: null,
        distributionRate: null,
        distributionCommission: null,
        createTime: null
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
      this.daterangeCreateTime = [];
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
      this.title = "添加分销佣金记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDistributionCommissionRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分销佣金记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDistributionCommissionRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDistributionCommissionRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除分销佣金记录编号为"' + ids + '"的数据项？').then(function() {
        return delDistributionCommissionRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('distribution/distributionCommissionRecord/export', {
        ...this.queryParams
      }, `distributionCommissionRecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
