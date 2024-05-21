<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类名称" prop="classificationName">
        <el-input
          v-model="queryParams.classificationName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类标签" prop="classificationLabels">
        <el-input
          v-model="queryParams.classificationLabels"
          placeholder="请输入分类标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="isEnabled">
        <el-select v-model="queryParams.isEnabled" placeholder="请选择是否启用" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="父节点" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父节点"
          clearable
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:classification:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="classificationList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="分类名称" align="center" prop="classificationName" />
      <el-table-column label="是否启用" align="center" prop="isEnabled">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isEnabled"/>
        </template>
      </el-table-column>
      <el-table-column label="图标" align="center" prop="imageIcon" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imageIcon" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="description" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:classification:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:classification:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:classification:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改案例分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="classificationName">
          <el-input v-model="form.classificationName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类标签" prop="classificationLabels">
          <el-input v-model="form.classificationLabels" placeholder="请输入分类标签" />
        </el-form-item>
        <el-form-item label="是否启用" prop="isEnabled">
          <el-radio-group v-model="form.isEnabled">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="父节点" prop="parentId">
          <treeselect v-model="form.parentId" :options="classificationOptions" :normalizer="normalizer" placeholder="请选择父节点" />
        </el-form-item>
        <el-form-item label="图标" prop="imageIcon">
          <image-upload v-model="form.imageIcon"/>
        </el-form-item>
        <el-form-item label="说明">
          <editor v-model="form.description" :min-height="192"/>
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
import { listClassification, getClassification, delClassification, addClassification, updateClassification } from "@/api/system/classification";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Classification",
  dicts: ['sys_yes_no'],
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 案例分类表格数据
      classificationList: [],
      // 案例分类树选项
      classificationOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        classificationName: null,
        classificationLabels: null,
        isEnabled: null,
        parentId: null,
        imageIcon: null,
        description: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        classificationName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        isEnabled: [
          { required: true, message: "是否启用不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询案例分类列表 */
    getList() {
      this.loading = true;
      listClassification(this.queryParams).then(response => {
        this.classificationList = this.handleTree(response.data, "id", "parentId");
        this.loading = false;
      });
    },
    /** 转换案例分类数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.classificationName,
        children: node.children
      };
    },
	/** 查询案例分类下拉树结构 */
    getTreeselect() {
      listClassification().then(response => {
        this.classificationOptions = [];
        const data = { id: 0, classificationName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "id", "parentId");
        this.classificationOptions.push(data);
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
        classificationNo: null,
        classificationName: null,
        classificationLabels: null,
        isEnabled: null,
        parentId: null,
        imageIcon: null,
        description: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.parentId = row.id;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加案例分类";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.parentId;
      }
      getClassification(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改案例分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateClassification(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClassification(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除案例分类编号为"' + row.id + '"的数据项？').then(function() {
        return delClassification(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
