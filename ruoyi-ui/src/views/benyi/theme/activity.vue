<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="所属主题" prop="themeid">
        <el-input
          v-model="queryParams.themeid"
          placeholder="请输入所属主题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入活动名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动形式" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择活动形式" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>
      <el-form-item label="活动领域" prop="field">
        <el-input
          v-model="queryParams.field"
          placeholder="请输入活动领域"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动目标" prop="target">
        <el-input
          v-model="queryParams.target"
          placeholder="请输入活动目标"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动材料" prop="data">
        <el-input
          v-model="queryParams.data"
          placeholder="请输入活动材料"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动过程" prop="process">
        <el-input
          v-model="queryParams.process"
          placeholder="请输入活动过程"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动建议" prop="proposal">
        <el-input
          v-model="queryParams.proposal"
          placeholder="请输入活动建议"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动反思" prop="reflect">
        <el-input
          v-model="queryParams.reflect"
          placeholder="请输入活动反思"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动附录" prop="appendix">
        <el-input
          v-model="queryParams.appendix"
          placeholder="请输入活动附录"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="序号" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入序号"
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
          v-hasPermi="['system:activity:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:activity:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:activity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:activity:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="所属主题" align="center" prop="themeid" />
      <el-table-column label="活动名称" align="center" prop="name" />
      <el-table-column label="活动形式" align="center" prop="type" />
      <el-table-column label="活动领域" align="center" prop="field" />
      <el-table-column label="活动目标" align="center" prop="target" />
      <el-table-column label="活动材料" align="center" prop="data" />
      <el-table-column label="活动过程" align="center" prop="process" />
      <el-table-column label="活动建议" align="center" prop="proposal" />
      <el-table-column label="活动反思" align="center" prop="reflect" />
      <el-table-column label="活动附录" align="center" prop="appendix" />
      <el-table-column label="序号" align="center" prop="sort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:activity:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:activity:remove']"
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

    <!-- 添加或修改主题整合活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属主题" prop="themeid">
          <el-input v-model="form.themeid" placeholder="请输入所属主题" />
        </el-form-item>
        <el-form-item label="活动名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动形式">
          <el-select v-model="form.type" placeholder="请选择活动形式">
            <el-option label="请选择字典生成" value />
          </el-select>
        </el-form-item>
        <el-form-item label="活动领域" prop="field">
          <el-input v-model="form.field" placeholder="请输入活动领域" />
        </el-form-item>
        <el-form-item label="活动目标" prop="target">
          <el-input v-model="form.target" placeholder="请输入活动目标" />
        </el-form-item>
        <el-form-item label="活动材料" prop="data">
          <el-input v-model="form.data" placeholder="请输入活动材料" />
        </el-form-item>
        <el-form-item label="活动过程" prop="process">
          <el-input v-model="form.process" placeholder="请输入活动过程" />
        </el-form-item>
        <el-form-item label="活动建议" prop="proposal">
          <el-input v-model="form.proposal" placeholder="请输入活动建议" />
        </el-form-item>
        <el-form-item label="活动反思" prop="reflect">
          <el-input v-model="form.reflect" placeholder="请输入活动反思" />
        </el-form-item>
        <el-form-item label="活动附录" prop="appendix">
          <el-input v-model="form.appendix" placeholder="请输入活动附录" />
        </el-form-item>
        <el-form-item label="序号" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入序号" />
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
  listActivity,
  getActivity,
  delActivity,
  addActivity,
  updateActivity,
  exportActivity
} from "@/api/benyi/activity";

export default {
  name: "Activity",
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
      // 主题整合活动表格数据
      activityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        themeid: undefined,
        name: undefined,
        type: undefined,
        field: undefined,
        target: undefined,
        data: undefined,
        process: undefined,
        proposal: undefined,
        reflect: undefined,
        appendix: undefined,
        sort: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询主题整合活动列表 */
    getList() {
      this.loading = true;
      listActivity(this.queryParams).then(response => {
        this.activityList = response.rows;
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
        themeid: undefined,
        name: undefined,
        type: undefined,
        field: undefined,
        target: undefined,
        data: undefined,
        process: undefined,
        proposal: undefined,
        reflect: undefined,
        appendix: undefined,
        sort: undefined,
        createTime: undefined
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加主题整合活动";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getActivity(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改主题整合活动";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateActivity(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addActivity(this.form).then(response => {
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
      this.$confirm(
        '是否确认删除主题整合活动编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delActivity(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有主题整合活动数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportActivity(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>