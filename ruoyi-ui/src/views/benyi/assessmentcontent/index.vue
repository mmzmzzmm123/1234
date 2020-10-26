<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="评估名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:assessmentcontent:add']"
          >新增</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      :data="assessmentcontentList"
      @selection-change="handleSelectionChange"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column label="评估名称" align="center" prop="name" />
      <el-table-column
        label="是否元素"
        align="center"
        prop="iselement"
        :formatter="iselementFormat"
      />
      <el-table-column
        label="适用范围"
        align="center"
        prop="scope"
        :formatter="scopeFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.parentId !== 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:assessmentcontent:edit']"
            >修改</el-button
          >
          <el-button
            v-if="scope.row.parentId !== 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:assessmentcontent:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改评估内容对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属模块" prop="parentId">
          <treeselect
            v-model="form.parentId"
            :options="parentidOptions"
            :normalizer="normalizer"
            placeholder="选择上级内容"
          />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input
            v-model="form.name"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="是否元素" prop="iselement">
          <el-select v-model="form.iselement" placeholder="请选择是否元素">
            <el-option
              v-for="dict in iselementOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="适用范围" prop="scope">
          <el-select v-model="form.scope" placeholder="请选择元素适用范围">
            <el-option
              v-for="dict in scopeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="序号" prop="sort">
          <el-input-number
            v-model="form.sort"
            :min="0"
            placeholder="请输入序号"
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
  listAssessmentcontent,
  getAssessmentcontent,
  delAssessmentcontent,
  addAssessmentcontent,
  updateAssessmentcontent,
} from "@/api/benyi/assessmentcontent";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Assessmentcontent",
  components: { Treeselect },
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
      // 评估内容表格数据
      assessmentcontentList: [],
      // 范围选项
      scopeOptions: [],
      // 是否元素选项
      iselementOptions: [],
      // 父类id
      parentidOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        parentId: undefined,
        name: undefined,
        iselement: undefined,
        scope: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "所属模块不能为空", trigger: "blur" },
        ],
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        iselement: [
          { required: true, message: "是否元素不能为空", trigger: "blur" },
        ],
        scope: [{ required: true, message: "范围不能为空", trigger: "blur" }],
        sort: [{ required: true, message: "序号不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_pgyssyfw").then((response) => {
      this.scopeOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.iselementOptions = response.data;
    });
  },
  methods: {
    // 性别字典翻译
    scopeFormat(row, column) {
      return this.selectDictLabel(this.scopeOptions, row.scope);
    },
    // 是否元素字典翻译
    iselementFormat(row, column) {
      return this.selectDictLabel(this.iselementOptions, row.iselement);
    },
    /** 查询评估内容列表 */
    getList() {
      this.loading = true;
      listAssessmentcontent(this.queryParams).then((response) => {
        this.assessmentcontentList = this.handleTree(response.rows, "id");
        this.loading = false;
      });
    },

    /** 查询部门下拉树结构 */
    getTreeselect() {
      listAssessmentcontent(null).then((response) => {
        this.parentidOptions = this.handleTree(response.rows, "id");
      });
    },

    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children,
      };
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
        parentId: undefined,
        name: undefined,
        iselement: undefined,
        scope: undefined,
        createTime: undefined,
        sort: 0,
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
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != undefined) {
        this.form.parentId = row.id;
      }
      this.open = true;
      this.title = "添加评估内容";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getAssessmentcontent(id).then((response) => {
        this.form = response.data;
        this.form.parentId = response.data.parentId;
        this.open = true;
        this.title = "修改评估内容";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateAssessmentcontent(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAssessmentcontent(this.form).then((response) => {
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
      this.$confirm('是否确认删除名称为"' + row.name + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delAssessmentcontent(row.id);
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