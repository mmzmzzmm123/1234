<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属任务" prop="rwid">
        <el-input
          v-model="queryParams.rwid"
          placeholder="请输入所属任务"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务内容" prop="rwnrlx">
        <el-input
          v-model="queryParams.rwnrlx"
          placeholder="请输入任务内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务时间" prop="fwsj">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.fwsj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择服务时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建人" prop="createUserid">
        <el-input
          v-model="queryParams.createUserid"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门编号" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['jyykhgl:jyyfwjc:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jyykhgl:jyyfwjc:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jyykhgl:jyyfwjc:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jyykhgl:jyyfwjc:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jyyfwjcList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="所属任务" align="center" prop="rwid" />
      <el-table-column label="任务内容" align="center" prop="rwnrlx" />
      <el-table-column label="主题" align="center" prop="zt" />
      <el-table-column label="服务学校" align="center" prop="fwxx" />
      <el-table-column label="服务时间" align="center" prop="fwsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fwsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="bz" />
      <el-table-column label="创建人" align="center" prop="createUserid" />
      <el-table-column label="部门编号" align="center" prop="deptId" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
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
            v-hasPermi="['jyykhgl:jyyfwjc:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jyykhgl:jyyfwjc:remove']"
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

    <!-- 添加或修改服务基层（教研员）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属任务" prop="rwid">
          <el-input v-model="form.rwid" placeholder="请输入所属任务" />
        </el-form-item>
        <el-form-item label="任务内容" prop="rwnrlx">
          <el-input v-model="form.rwnrlx" placeholder="请输入任务内容" />
        </el-form-item>
        <el-form-item label="主题" prop="zt">
          <el-input v-model="form.zt" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="服务学校" prop="fwxx">
          <el-input v-model="form.fwxx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="服务时间" prop="fwsj">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.fwsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择服务时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="创建人" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="部门编号" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门编号" />
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
import { listJyyfwjc, getJyyfwjc, delJyyfwjc, addJyyfwjc, updateJyyfwjc, exportJyyfwjc } from "@/api/jyykhgl/jyyfwjc";

export default {
  name: "Jyyfwjc",
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
      // 服务基层（教研员）表格数据
      jyyfwjcList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rwid: null,
        rwnrlx: null,
        zt: null,
        fwxx: null,
        fwsj: null,
        bz: null,
        createUserid: null,
        deptId: null,
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
    /** 查询服务基层（教研员）列表 */
    getList() {
      this.loading = true;
      listJyyfwjc(this.queryParams).then(response => {
        this.jyyfwjcList = response.rows;
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
        rwid: null,
        rwnrlx: null,
        zt: null,
        fwxx: null,
        fwsj: null,
        bz: null,
        createUserid: null,
        deptId: null,
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
      this.title = "添加服务基层（教研员）";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getJyyfwjc(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改服务基层（教研员）";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJyyfwjc(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJyyfwjc(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除服务基层（教研员）编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJyyfwjc(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有服务基层（教研员）数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportJyyfwjc(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
