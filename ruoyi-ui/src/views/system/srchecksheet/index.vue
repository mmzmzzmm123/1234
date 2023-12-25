<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供货单位" prop="supplier">
        <el-input
          v-model="queryParams.supplier"
          placeholder="请输入供货单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="票据号" prop="billnumber">
        <el-input
          v-model="queryParams.billnumber"
          placeholder="请输入票据号"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:srchecksheet:add']"
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
          v-hasPermi="['system:srchecksheet:edit']"
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
          v-hasPermi="['system:srchecksheet:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:srchecksheet:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="srchecksheetList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="报销单编号" align="center" prop="id"/>
      <el-table-column label="供货单位" align="center" prop="supplier" />
      <el-table-column label="票据号" align="center" prop="billnumber" />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="人民币" align="center" prop="rmb" />
      <el-table-column label="付款方式" align="center" prop="paymentmethod" :formatter="formatPaymentmethod"/></el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
		  <el-table-column label="分管经理审批人" align="center" prop="examine" width="120" :formatter="formatExamine"></el-table-column>
      <el-table-column label="部门审批人" align="center" prop="section" width="120" :formatter="formatSection"></el-table-column>
      <el-table-column label="报账部门" align="center" prop="reimbursement" :formatter="formatReimbursement"></el-table-column>
      <el-table-column label="报账人" align="center" prop="reimburser"/>
      <el-table-column label="创建时间" align="center" prop="createtime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center" prop="updatetime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:srchecksheet:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:srchecksheet:remove']"
          >删除</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
            <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handlePay" icon="el-icon-circle-check"
                v-hasPermi="['system:user:edit']">付款记录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- 添加或修改报销单数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供货单位" prop="supplier">
          <el-input v-model="form.supplier" placeholder="请输入供货单位" />
        </el-form-item>
        <el-form-item label="票据号" prop="billnumber">
          <el-input v-model="form.billnumber" placeholder="请输入票据号" />
        </el-form-item>
        <el-form-item label="报账内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="人民币" prop="rmb">
          <el-input v-model="form.rmb" placeholder="请输入人民币" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>

        <el-form-item label="分管经理">
          <el-select v-model="form.examine" placeholder="请选择分管经理审批人">
            <el-option
              v-for="dict in dict.type.sys_normal_examine"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="报账部门">
          <el-select v-model="form.reimbursement" placeholder="请选择报账部门">
            <el-option
              v-for="dict in dict.type.sys_normal_reimbursement"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="部门审批">
          <el-select v-model="form.section" placeholder="请选择部门审批人">
            <el-option
              v-for="dict in dict.type.sys_normal_section"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="付款方式">
          <el-select v-model="form.paymentmethod" placeholder="请选择付款方式">
            <el-option
              v-for="dict in dict.type.sys_payment_method"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="报账人" prop="reimburser">
          <el-input v-model="form.reimburser" placeholder="请输入报账人" />
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
import { listSrchecksheet, getSrchecksheet, delSrchecksheet, addSrchecksheet, updateSrchecksheet } from "@/api/system/srchecksheet";

export default {
  name: "Srchecksheet",
  dicts: ['sys_payment_method','sys_normal_section','sys_normal_reimbursement','sys_normal_examine'],
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
      // 报销单数据表格数据
      srchecksheetList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplier: null,
        billnumber: null,
        content: null,
        rmb: null,
        paymentmethod: null,
        examine: null,
        section: null,
        reimbursement: null,
        reimburser: null,
        createtime: null,
        status: null,
        updatetime: null,
        reserve1: null,
        reserve2: null,
        reserve3: null,
        reserve4: null
      },
      // 列信息
      columns: [
        { key: 0, label: `付款方式`, visible: true }
      ],
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
    formatExamine: function (row, column, cellValue, index) {
			return row.examine == 1 ? "李涛" : row.examine == 2 ? "梁美丽" : "未知";
		},
    formatSection: function (row, column, cellValue, index) {
			return row.section == 1 ? "李涛" : row.section == 2 ? "梁美丽" : "未知";
		},
    formatReimbursement: function (row, column, cellValue, index) {
			return row.reimbursement == 1 ? "技术部" : row.reimbursement == 2 ? "供应部" : "未知";
		},
    formatPaymentmethod(row, column) {
            switch(row.paymentmethod){
                case 1:
	                return '现金';
	                break;
                case 2:
	                return '挂账';
	                break;
                case 3:
	                return '电汇';
	                break;
                case 4:
	                return '充电汇';
	                break;
            }
      },
    /** 查询报销单数据列表 */
    getList() {
      this.loading = true;
      listSrchecksheet(this.queryParams).then(response => {
        this.srchecksheetList = response.rows;
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
        supplier: null,
        billnumber: null,
        content: null,
        rmb: null,
        paymentmethod: null,
        remark: null,
        examine: null,
        section: null,
        reimbursement: null,
        reimburser: null,
        createtime: null,
        status: null,
        updatetime: null,
        reserve1: null,
        reserve2: null,
        reserve3: null,
        reserve4: null
      };
      this.resetForm("form");
    },
    /** 付款记录操作 */
    handlePay: function(row) {
      const id = row.id;
      this.$router.push("/system/pay-auth/role/" + id);
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
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handlePay":
          this.handlePay(row);
          break;
        default:
          break;
      }
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
      this.title = "添加报销单数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSrchecksheet(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报销单数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSrchecksheet(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSrchecksheet(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除报销单数据编号为"' + ids + '"的数据项？').then(function() {
        return delSrchecksheet(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/srchecksheet/export', {
        ...this.queryParams
      }, `srchecksheet_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
