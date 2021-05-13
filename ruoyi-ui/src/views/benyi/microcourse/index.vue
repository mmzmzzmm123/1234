<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
      <el-form-item label="课程标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入课程标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程类型" prop="type">
        <el-select
          v-model="queryParams.type"
          clearable
          placeholder="请选择课程类型"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="适用班级" prop="scpoe">
        <el-select
          v-model="queryParams.scpoe"
          clearable
          placeholder="请选择适用班级"
        >
          <el-option
            v-for="dict in bjtypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
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
          v-hasPermi="['benyi:microcourse:add']"
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
          v-hasPermi="['benyi:microcourse:edit']"
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
          v-hasPermi="['benyi:microcourse:remove']"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="microcourseList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="课程标题" align="center" prop="title" />
      <el-table-column label="作者" align="center" prop="author" />
      <!-- <el-table-column label="内容" align="center" prop="contents" /> -->
      <el-table-column label="课程类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="适用班级" align="center" prop="scpoe" :formatter="bjtypeFormat" />
      <el-table-column label="上下册" align="center" prop="upanddown" :formatter="upanddownFormat" />
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
            v-hasPermi="['benyi:microcourse:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:microcourse:remove']"
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

    <!-- 添加或修改微型课程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入课程标题" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="内容" prop="contents">
          <Editor v-model="form.contents" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="课程类型">
          <el-select v-model="form.type" placeholder="请选择课程类型">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="适用班级" prop="scpoe">
          <el-select v-model="form.scpoe" placeholder="请选择适用班级">
            <el-option
              v-for="dict in bjtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="上下册" prop="upanddown">
          <el-select v-model="form.upanddown" placeholder="请选择上下册">
            <el-option
              v-for="dict in upanddownOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="序号" prop="sort">
          <el-input-number v-model="form.sort" placeholder="请输入序号" />
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
  listMicrocourse,
  getMicrocourse,
  delMicrocourse,
  addMicrocourse,
  updateMicrocourse,
} from "@/api/benyi/microcourse";
import Editor from "@/components/Editor";

export default {
  name: "Microcourse",
  components: {
    Editor,
  },
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
      // 微型课程表格数据
      microcourseList: [],
      //微型课程类型
      typeOptions: [],
      //微型课程上下册
      upanddownOptions: [],
      //班级类型 字典
      bjtypeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        author: undefined,
        contents: undefined,
        type: undefined,
        scpoe: undefined,
        upanddown: undefined,
        sort: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_wxkclx").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_dm_wxkcsxc").then((response) => {
      this.upanddownOptions = response.data;
    });
    this.getDicts("sys_yebjlx").then((response) => {
      this.bjtypeOptions = response.data;
    });
  },
  methods: {
    // 班级类型--字典状态字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 班级类型--字典状态字典翻译
    upanddownFormat(row, column) {
      return this.selectDictLabel(this.upanddownOptions, row.upanddown);
    },
    // 班级类型--字典状态字典翻译
    bjtypeFormat(row, column) {
      return this.selectDictLabel(this.bjtypeOptions, row.scpoe);
    },
    /** 查询微型课程列表 */
    getList() {
      this.loading = true;
      listMicrocourse(this.queryParams).then((response) => {
        this.microcourseList = response.rows;
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
        title: undefined,
        author: undefined,
        contents: undefined,
        type: undefined,
        scpoe: undefined,
        upanddown: undefined,
        sort: undefined,
        createTime: undefined,
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
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加微型课程";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMicrocourse(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微型课程";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateMicrocourse(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addMicrocourse(this.form).then((response) => {
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
        '是否确认删除微型课程编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delMicrocourse(ids);
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