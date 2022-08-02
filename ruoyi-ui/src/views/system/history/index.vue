<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="版本" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="脚本类型" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入脚本类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="校验编码" prop="checksum">
        <el-input
          v-model="queryParams.checksum"
          placeholder="请输入校验编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="执行人" prop="installedBy">
        <el-input
          v-model="queryParams.installedBy"
          placeholder="请输入执行人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作日期" prop="installedOn">
        <el-date-picker clearable
          v-model="queryParams.installedOn"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择操作日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="执行时长" prop="executionTime">
        <el-input
          v-model="queryParams.executionTime"
          placeholder="请输入执行时长"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否成功" prop="success">
        <el-select v-model="queryParams.success" placeholder="请选择是否成功" clearable>
          <el-option
            v-for="dict in dict.type.yes_or_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:history:add']"
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
          v-hasPermi="['system:history:edit']"
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
          v-hasPermi="['system:history:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:history:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="historyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="installedRank" />
      <el-table-column label="版本" align="center" prop="version" />
      <el-table-column label="脚本类型" align="center" prop="description" />
      <el-table-column label="脚本后缀" align="center" prop="type" />
      <el-table-column label="脚本名称" align="center" prop="script" />
      <el-table-column label="校验编码" align="center" prop="checksum" />
      <el-table-column label="执行人" align="center" prop="installedBy" />
      <el-table-column label="操作日期" align="center" prop="installedOn" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installedOn, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行时长" align="center" prop="executionTime" />
      <el-table-column label="是否成功" align="center" prop="success">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_or_no" :value="scope.row.success"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:history:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:history:remove']"
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

    <!-- 添加或修改flyway管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="版本" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本" />
        </el-form-item>
        <el-form-item label="脚本类型" prop="description">
          <el-input v-model="form.description" placeholder="请输入脚本类型" />
        </el-form-item>
        <el-form-item label="脚本名称" prop="script">
          <el-input v-model="form.script" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="校验编码" prop="checksum">
          <el-input v-model="form.checksum" placeholder="请输入校验编码" />
        </el-form-item>
        <el-form-item label="执行人" prop="installedBy">
          <el-input v-model="form.installedBy" placeholder="请输入执行人" />
        </el-form-item>
        <el-form-item label="操作日期" prop="installedOn">
          <el-date-picker clearable
            v-model="form.installedOn"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择操作日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="执行时长" prop="executionTime">
          <el-input v-model="form.executionTime" placeholder="请输入执行时长" />
        </el-form-item>
        <el-form-item label="是否成功" prop="success">
          <el-select v-model="form.success" placeholder="请选择是否成功">
            <el-option
              v-for="dict in dict.type.yes_or_no"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
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
import { listHistory, getHistory, delHistory, addHistory, updateHistory } from "@/api/system/history";

export default {
  name: "History",
  dicts: ['yes_or_no'],
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
      // flyway管理表格数据
      historyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        version: null,
        description: null,
        type: null,
        script: null,
        checksum: null,
        installedBy: null,
        installedOn: null,
        executionTime: null,
        success: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        description: [
          { required: true, message: "脚本类型不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "脚本后缀不能为空", trigger: "change" }
        ],
        script: [
          { required: true, message: "脚本名称不能为空", trigger: "blur" }
        ],
        success: [
          { required: true, message: "是否成功不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询flyway管理列表 */
    getList() {
      this.loading = true;
      listHistory(this.queryParams).then(response => {
        this.historyList = response.rows;
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
        installedRank: null,
        version: null,
        description: null,
        type: null,
        script: null,
        checksum: null,
        installedBy: null,
        installedOn: null,
        executionTime: null,
        success: null
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
      this.ids = selection.map(item => item.installedRank)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加flyway管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const installedRank = row.installedRank || this.ids
      getHistory(installedRank).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改flyway管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.installedRank != null) {
            updateHistory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHistory(this.form).then(response => {
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
      const installedRanks = row.installedRank || this.ids;
      this.$modal.confirm('是否确认删除flyway管理编号为"' + installedRanks + '"的数据项？').then(function() {
        return delHistory(installedRanks);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/history/export', {
        ...this.queryParams
      }, `history_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
