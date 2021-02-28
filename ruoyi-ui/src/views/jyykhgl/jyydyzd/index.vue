<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="所属任务" prop="rwid">
         <el-select
          v-model="queryParams.rwid"
          size="small"
          clearable
          placeholder="请选择所属任务"
        >
          <el-option
            v-for="dict in jyykhrwList"
            :key="dict.id"
            :label="dict.rwmc"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务内容" prop="rwnrlx">
        <el-select
          v-model="queryParams.rwnrlx"
          size="small"
          clearable
          placeholder="请选择任务内容"
        >
          <el-option
            v-for="dict in rwnrOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="调研时间" prop="dysj">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.dysj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择调研时间"
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
          v-hasPermi="['jyykhgl:jyydyzd:add']"
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
          v-hasPermi="['jyykhgl:jyydyzd:edit']"
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
          v-hasPermi="['jyykhgl:jyydyzd:remove']"
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
      :data="jyydyzdList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="所属任务" align="center" prop="tsbzJyykhrw.rwmc" />
      <el-table-column label="任务内容" align="center" prop="rwnrlx" :formatter="rwnrFormat"/>
      <el-table-column label="调研名称" align="center" prop="dymc" />
      <el-table-column label="调研主题" align="center" prop="dyzt" />
      <el-table-column label="调研时间" align="center" prop="dysj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dysj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="调研学校" align="center" prop="sysDept.deptName" />
      <el-table-column label="调研对象" align="center" prop="dydx" />
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
            v-hasPermi="['jyykhgl:jyydyzd:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jyykhgl:jyydyzd:remove']"
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

    <!-- 添加或修改调研指导对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属任务" prop="rwid">
           <el-select v-model="form.rwid" placeholder="请选择所属任务">
            <el-option
              v-for="dict in jyykhrwList"
              :key="dict.id"
              :label="dict.rwmc"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务内容" prop="rwnrlx">
          <el-select v-model="form.rwnrlx" placeholder="请选择任务内容">
            <el-option
              v-for="dict in rwnrOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调研名称" prop="dymc">
          <el-input
            v-model="form.dymc"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="调研主题" prop="dyzt">
          <el-input
            v-model="form.dyzt"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="调研时间" prop="dysj">
          <el-date-picker
            clearable
           class="my-date-picker"
            v-model="form.dysj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择调研时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="调研学校" prop="dyxx">
          <el-select v-model="form.dyxx" filterable placeholder="请选择调研学校">
                <el-option
                  v-for="dict in deptOptions"
                  :key="dict.deptId"
                  :label="dict.deptName"
                  :value="dict.deptId"
                ></el-option>
              </el-select>
        </el-form-item>
        <el-form-item label="调研对象" prop="dydx">
          <el-input
            v-model="form.dydx"
            type="textarea"
            placeholder="请输入内容"
          />
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
  listJyydyzd,
  getJyydyzd,
  delJyydyzd,
  addJyydyzd,
  updateJyydyzd,
} from "@/api/jyykhgl/jyydyzd";
import { listJyykhrw } from "@/api/jyykhgl/jyykhrw";
import { listDept, getDept } from "@/api/system/dept";

export default {
  name: "Jyydyzd",
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
      // 调研指导表格数据
      jyydyzdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //考核任务
      jyykhrwList: [],
      //任务内容
      rwnrOptions: [],
            // 部门选项
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rwid: null,
        rwnrlx: null,
        dymc: null,
        dyzt: null,
        dysj: null,
        dyxx: null,
        dydx: null,
        deptId: null,
        createUserid: null,
      },
      dept_queryParams: {
        parentId: 300,
      },
      // 查询参数
      queryParams_khrw: {
        rwlx: "04",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rwid: [
          { required: true, message: "考核任务不能为空", trigger: "blur" },
        ],
        rwnrlx: [
          { required: true, message: "任务内容不能为空", trigger: "blur" },
        ],
        dyxx: [
          { required: true, message: "调研学校不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDeptList();
    this.getKhrwList();
    this.getDicts("sys_dm_dyzdrwnr").then((response) => {
      this.rwnrOptions = response.data;
    });
  },
  methods: {
        // 查询部门
    getDeptList() {
      listDept(this.dept_queryParams).then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 任务类型字典翻译
    rwnrFormat(row, column) {
      return this.selectDictLabel(this.rwnrOptions, row.rwnrlx);
    },
    /** 查询教研员考核任务列表 */
    getKhrwList() {
      listJyykhrw(this.queryParams_khrw).then((response) => {
        this.jyykhrwList = response.rows;
      });
    },
    /** 查询调研指导列表 */
    getList() {
      this.loading = true;
      listJyydyzd(this.queryParams).then((response) => {
        this.jyydyzdList = response.rows;
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
        dymc: null,
        dyzt: null,
        dysj: null,
        dyxx: null,
        dydx: null,
        deptId: null,
        createUserid: null,
        createTime: null,
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
      this.title = "添加调研指导（教研员）";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJyydyzd(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改调研指导（教研员）";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJyydyzd(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJyydyzd(this.form).then((response) => {
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
        '是否确认删除调研指导编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJyydyzd(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
</style>