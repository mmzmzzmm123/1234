<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="公司简称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入公司简称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
        <el-form-item label="状态" prop="enabled">
        <el-select v-model="queryParams.enabled" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in enabledOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入备注"
          clearable
          size="small"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['base:company:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['base:company:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['base:company:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['base:company:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" stripe border fit highlight-current-row :data="companyList" @selection-change="handleSelectionChange" @sort-change='tableSortChange'>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" width="80" align="center">
              <template slot-scope="scope">{{scope.$index + 1}}</template>
      </el-table-column>
      <el-table-column label="UUID" align="center" prop="id" v-if = "show" />
      <el-table-column label="排序" align="center" prop="sort" sortable='custom' :sort-orders="['ascending', 'descending']" />
      <el-table-column label="公司代码" align="center" prop="codeid" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="公司简称" align="center" prop="name" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="状态" align="center">
                  <template slot-scope="scope">
                    <el-switch
                      v-model="scope.row.enabled"
                      active-value="0"
                      inactive-value="1"
                      @change="handleStatusChange(scope.row)"
                    ></el-switch>
                  </template>
       </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['base:company:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['base:company:remove']"
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

    <!-- 添加或修改公司管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="公司代码" prop="codeid">
          <el-input v-model="form.codeid" placeholder="请输入公司代码" />
        </el-form-item>
        <el-form-item label="公司简称" prop="name">
          <el-input v-model="form.name" placeholder="请输入公司简称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.enabled" placeholder="请选择状态">
            <el-option
              v-for="dict in enabledOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
import { listCompany, getCompany, delCompany, addCompany, updateCompany,changeCompanyEnabled, exportCompany } from "@/api/base/company";

export default {
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
      // 总条数
      total: 0,
      // 公司管理表格数据
      companyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      enabledOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        enabled: undefined,
        createTime: undefined,
        remark: undefined,
        orderByColumn:'sort',
        isAsc:'desc',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sort: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],        codeid: [
          { required: true, message: "公司代码不能为空", trigger: "blur" }
        ],        name: [
          { required: true, message: "公司简称不能为空", trigger: "blur" }
        ],        enabled: [
          { required: true, message: "状态（0正常 1停用）不能为空", trigger: "blur" }
        ],        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" }
        ],        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_enable").then(response => {
      this.enabledOptions = response.data;
    });
  },
  methods: {
    /** 公司管理排序 */
    tableSortChange(column, prop, order) {
          this.queryParams.pageNum = 1;
          if (column.order === 'descending') {
            this.queryParams.orderByColumn = column.prop;
            this.queryParams.isAsc = 'desc';
          } else {
            this.queryParams.orderByColumn = column.prop;
            this.queryParams.isAsc = 'asc';
          }
          this.getList();
     },
    /** 查询公司管理列表 */
    getList() {
      this.loading = true;
      listCompany(this.queryParams).then(response => {
        this.companyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    enabledFormat(row, column) {
      return this.selectDictLabel(this.enabledOptions, row.enabled);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 用户状态修改
     handleStatusChange(row) {
       let text = row.enabled === "0" ? "启用" : "停用";
       this.$confirm('确认要"' + text + '""' + row.name + '"公司吗?', "警告", {
           confirmButtonText: "确定",
           cancelButtonText: "取消",
           type: "warning"
         }).then(function() {
           return changeCompanyEnabled(row.id, row.enabled);
         }).then(() => {
           this.msgSuccess(text + "成功");
         }).catch(function() {
           row.enabled = row.enabled === "0" ? "1" : "0";
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
        id: undefined,
        sort: undefined,
        codeid: undefined,
        name: undefined,
        enabled: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
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
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加公司管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCompany(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改公司管理";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCompany(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addCompany(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除公司管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCompany(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有公司管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCompany(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
