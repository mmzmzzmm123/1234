<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="任务名称" prop="rwmc">
        <el-select v-model="queryParams.rwmc" placeholder="请选择任务名称">
          <el-option
            v-for="dict in rwmcOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课程名称" prop="kcmc">
        <el-input
          v-model="queryParams.kcmc"
          placeholder="请输入课程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主讲人" prop="zjr">
        <el-input
          v-model="queryParams.zjr"
          placeholder="请输入主讲人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上课时间" prop="sktime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.sktime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择上课时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jdgl:jdgljxsj:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jdgl:jdgljxsj:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jdgl:jdgljxsj:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jdgljxsjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务名称" align="center" prop="rwmc" />
      <el-table-column label="课程名称" align="center" prop="kcmc" />
      <el-table-column label="主讲人" align="center" prop="zjr" />
      <el-table-column
        label="上课时间"
        align="center"
        prop="sktime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sktime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="科目"
        align="center"
        prop="km"
        :formatter="kmFormat"
      />
      <el-table-column
        label="年级"
        align="center"
        prop="nj"
        :formatter="njFormat"
      />
      <el-table-column label="学校名称" align="center" prop="xxmc" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jdgl:jdgljxsj:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jdgl:jdgljxsj:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改基地管理教学实践对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="rwmc">
          <el-select v-model="form.rwmc" placeholder="请选择任务名称">
            <el-option
              v-for="dict in rwmcOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程名称" prop="kcmc">
          <el-input v-model="form.kcmc" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="主讲人" prop="zjr">
          <el-input v-model="form.zjr" placeholder="请输入主讲人" />
        </el-form-item>
        <el-form-item label="上课时间" prop="sktime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.sktime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择上课时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="科目" prop="km">
          <el-select v-model="form.km" placeholder="请选择科目">
            <el-option
              v-for="dict in kmOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="nj">
          <el-select v-model="form.nj" placeholder="请选择年级">
            <el-option
              v-for="dict in njOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学校名称" prop="xxmc">
          <el-input v-model="form.xxmc" placeholder="请输入学校名称" />
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
  listJdgljxsj,
  getJdgljxsj,
  delJdgljxsj,
  addJdgljxsj,
  updateJdgljxsj,
  exportJdgljxsj,
} from "@/api/jdgl/jdgljxsj";

export default {
  name: "Jdgljxsj",
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
      // 基地管理教学实践表格数据
      jdgljxsjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 科目字典
      kmOptions: [],
      // 年级字典
      njOptions: [],
      // 任务名称字典
      rwmcOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rwmc: null,
        kcmc: null,
        zjr: null,
        sktime: null,
        km: null,
        nj: null,
        xxmc: null,
        createUser: null,
        jdid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.kmOptions = response.data;
    });
    this.getDicts("sys_dm_rjnj").then((response) => {
      this.njOptions = response.data;
    });
    this.getDicts("sys_dm_jdgljxsjrwmc").then((response) => {
      this.rwmcOptions = response.data;
    });
  },
  methods: {
    /** 查询基地管理教学实践列表 */
    getList() {
      this.loading = true;
      listJdgljxsj(this.queryParams).then((response) => {
        this.jdgljxsjList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 科目字典翻译
    kmFormat(row, column) {
      return this.selectDictLabel(this.kmOptions, row.km);
    },
    // 年级字典翻译
    njFormat(row, column) {
      return this.selectDictLabel(this.njOptions, row.nj);
    },
    // 任务名称字典翻译
    rwmcFormat(row, column) {
      return this.selectDictLabel(this.rwmcOptions, row.rwmc);
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
        rwmc: null,
        kcmc: null,
        zjr: null,
        sktime: null,
        km: null,
        nj: null,
        xxmc: null,
        createUser: null,
        createTime: null,
        jdid: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加基地管理教学实践";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJdgljxsj(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地管理教学实践";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJdgljxsj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJdgljxsj(this.form).then((response) => {
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
        '是否确认删除基地管理教学实践编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJdgljxsj(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有基地管理教学实践数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportJdgljxsj(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
