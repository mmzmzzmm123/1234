<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会员id" prop="memberId">
        <el-input
          v-model="queryParams.memberId"
          placeholder="请输入会员id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="菜地id" prop="gardenId">
        <el-input
          v-model="queryParams.gardenId"
          placeholder="请输入菜地id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="对应年份" prop="yearNo">
        <el-input
          v-model="queryParams.yearNo"
          placeholder="请输入对应年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人id" prop="doerId">
        <el-input
          v-model="queryParams.doerId"
          placeholder="请输入操作人id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理时间" prop="doTime">
        <el-date-picker clearable
          v-model="queryParams.doTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择处理时间">
        </el-date-picker>
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
          v-hasPermi="['csa:archedrecord:add']"
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
          v-hasPermi="['csa:archedrecord:edit']"
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
          v-hasPermi="['csa:archedrecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['csa:archedrecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="archedrecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="拱棚使用记录id" align="center" prop="id" />
      <el-table-column label="对应年份" align="center" prop="yearNo" />
      <el-table-column label="剩余年份" align="center" prop="remainingYears" />
      <el-table-column label="操作人id" align="center" prop="doerId" />
      <el-table-column label="处理时间" align="center" prop="doTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.doTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="有效状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:archedrecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['csa:archedrecord:remove']"
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

    <!-- 添加或修改拱棚使用记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员id" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入会员id" />
        </el-form-item>
        <el-form-item label="菜地id" prop="gardenId">
          <el-input v-model="form.gardenId" placeholder="请输入菜地id" />
        </el-form-item>
        <el-form-item label="对应年份" prop="yearNo">
          <el-input v-model="form.yearNo" placeholder="请输入对应年份" />
        </el-form-item>
        <el-form-item label="消耗年份" prop="consumYear">
          <el-input v-model="form.consumYear" placeholder="请输入消耗年份" />
        </el-form-item>
        <el-form-item label="剩余年份" prop="remainingYears">
          <el-input v-model="form.remainingYears" placeholder="请输入剩余年份" />
        </el-form-item>
        <el-form-item label="操作人id" prop="doerId">
          <el-input v-model="form.doerId" placeholder="请输入操作人id" />
        </el-form-item>
        <el-form-item label="处理时间" prop="doTime">
          <el-date-picker clearable
            v-model="form.doTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择处理时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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
import { listArchedrecord, getArchedrecord, delArchedrecord, addArchedrecord, updateArchedrecord } from "@/api/csa/archedrecord";

export default {
  name: "Archedrecord",
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
      // 拱棚使用记录表格数据
      archedrecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        memberId: null,
        gardenId: null,
        yearNo: null,
        doerId: null,
        doTime: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        memberId: [
          { required: true, message: "会员id不能为空", trigger: "blur" }
        ],
        gardenId: [
          { required: true, message: "菜地id不能为空", trigger: "blur" }
        ],
        yearNo: [
          { required: true, message: "对应年份不能为空", trigger: "blur" }
        ],
        consumYear: [
          { required: true, message: "消耗年份不能为空", trigger: "blur" }
        ],
        remainingYears: [
          { required: true, message: "剩余年份不能为空", trigger: "blur" }
        ],
        doerId: [
          { required: true, message: "操作人id不能为空", trigger: "blur" }
        ],
        doTime: [
          { required: true, message: "处理时间不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "有效状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询拱棚使用记录列表 */
    getList() {
      this.loading = true;
      listArchedrecord(this.queryParams).then(response => {
        this.archedrecordList = response.rows;
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
        memberId: null,
        gardenId: null,
        yearNo: null,
        consumYear: null,
        remainingYears: null,
        doerId: null,
        doTime: null,
        status: "0",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.title = "添加拱棚使用记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getArchedrecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改拱棚使用记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateArchedrecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArchedrecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除拱棚使用记录编号为"' + ids + '"的数据项？').then(function() {
        return delArchedrecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('csa/archedrecord/export', {
        ...this.queryParams
      }, `archedrecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
