<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      
      <el-form-item label="任务名称" prop="rwmc">
        <el-input
          v-model="queryParams.rwmc"
          placeholder="请输入任务名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属流程" prop="lcmc">
        <el-input
          v-model="queryParams.lcmc"
          placeholder="请输入所属流程"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--
      <el-form-item label="创建人" prop="createuser">
        <el-input
          v-model="queryParams.createuser"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createtime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.createtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="标准数量" prop="standardCount">
        <el-input
          v-model="queryParams.standardCount"
          placeholder="请输入标准数量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新者" prop="updateuser">
        <el-input
          v-model="queryParams.updateuser"
          placeholder="请输入更新者"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" prop="updatetime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.updatetime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="备用字段" prop="beiyong">
        <el-input
          v-model="queryParams.beiyong"
          placeholder="请输入备用字段"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      -->
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
          v-hasPermi="['benyi:dayflowtask:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:dayflowtask:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:dayflowtask:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['benyi:dayflowtask:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dayflowtaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="任务名称" align="center" prop="rwmc" />
      <el-table-column label="所属流程" align="center" prop="lcmc" />
      <el-table-column label="标准数量" align="center" prop="standardCount" />
      <el-table-column label="创建人" align="center" prop="createuser" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updateuser" />
      <el-table-column label="更新时间" align="center" prop="updatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime) }}</span>
        </template>
      </el-table-column>
      <!--
      <el-table-column label="流程id" align="center" prop="lcId" />
      <el-table-column label="任务目的" align="center" prop="rwmd" />
      <el-table-column label="任务解读" align="center" prop="rwjd" />
      <el-table-column label="备用字段" align="center" prop="beiyong" />
      -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:dayflowtask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:dayflowtask:remove']"
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

    <!-- 添加或修改一日流程任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="rwmc">
          <el-input v-model="form.rwmc" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="所属流程" prop="lcmc">
          <el-input v-model="form.lcmc" placeholder="请输入所属流程" />
        </el-form-item>
        <el-form-item label="任务目的" prop="rwmd">
          <el-input v-model="form.rwmd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="任务解读" prop="rwjd">
          <el-input v-model="form.rwjd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!--
        <el-form-item label="流程id" prop="lcId">
          <el-input v-model="form.lcId" placeholder="请输入流程id" />
        </el-form-item>
        <el-form-item label="更新者" prop="updateuser">
          <el-input v-model="form.updateuser" placeholder="请输入更新者" />
        </el-form-item>
        <el-form-item label="备用字段" prop="beiyong">
          <el-input v-model="form.beiyong" placeholder="请输入备用字段" />
        </el-form-item>
        -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDayflowtask, getDayflowtask, delDayflowtask, addDayflowtask, updateDayflowtask, exportDayflowtask } from "@/api/benyi/dayflowtask";

export default {
  name: "Dayflowtask",
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
      // 一日流程任务表格数据
      dayflowtaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        lcId: undefined,
        rwmc: undefined,
        lcmc: undefined,
        rwmd: undefined,
        rwjd: undefined,
        createuser: undefined,
        createtime: undefined,
        standardCount: undefined,
        updateuser: undefined,
        updatetime: undefined,
        beiyong: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rwmc: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询一日流程任务列表 */
    getList() {
      this.loading = true;
      listDayflowtask(this.queryParams).then(response => {
        this.dayflowtaskList = response.rows;
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
        id: undefined,
        lcId: undefined,
        rwmc: undefined,
        lcmc: undefined,
        rwmd: undefined,
        rwjd: undefined,
        createuser: undefined,
        createtime: undefined,
        standardCount: undefined,
        updateuser: undefined,
        updatetime: undefined,
        beiyong: undefined
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
      this.title = "添加一日流程任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDayflowtask(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改一日流程任务";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDayflowtask(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addDayflowtask(this.form).then(response => {
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
      this.$confirm('是否确认删除一日流程任务编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDayflowtask(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有一日流程任务数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDayflowtask(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>