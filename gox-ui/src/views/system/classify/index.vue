<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入分类名"
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:classify:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table
      v-loading="loading"
      :data="classifyList"
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
      <el-table-column label="分类名" align="center" prop="name" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="父节点id" align="center" prop="parentId" />
      <el-table-column label="父节点名字" align="center" prop="parentName" />
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:classify:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:classify:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改分类管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级节点">
              <treeselect
                v-model="form.parentId"
                :options="classifyOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级节点"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="节点类型" prop="menuType">
              <el-radio-group v-model="form.type">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="节点图标">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected"/>
                <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
                  <svg-icon
                    v-if="form.icon"
                    slot="prefix"
                    :icon-class="form.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="节点名称" prop="menuName">
              <el-input v-model="form.name" placeholder="请输入节点名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.sort" controls-position="right" :min="0"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="权限标识">
              <el-input v-model="form.perms" placeholder="请权限标识" maxlength="50"/>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="所属数据库">
              <el-input v-model="form.path" placeholder="请输入路径" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="节点文档类型">
              <el-radio-group v-model="form.archivalType">
                <el-radio :label="true">文件</el-radio>
                <el-radio :label="false">案卷</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listClassify, getClassify, delClassify, addClassify, updateClassify, exportClassify } from "@/api/system/classify";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Treeselect from "@riophae/vue-treeselect";
import IconSelect from "@/components/IconSelect";
export default {
  name: "Classify",
  components: {
    Treeselect,IconSelect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: false,
      // 分类管理表格数据
      classifyList: [],
      data:[],
      classifyOptions:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        name: null,
        parentId: null,
        parentName: null
      },
      // 表单参数
      form: {

      },
      // 表单校验
      rules: {
      },
    }
  },
  created() {
    this.getList();
  },
  methods: {
    // 选择图标
    selected(name) {
      this.$set(this.form,"icon",name);
    },
    /** 查询分类管理列表 */
    getList() {
      this.loading = true;
      listClassify(this.queryParams).then(response => {
        if (response.rows){
          this.classifyOptions = [];
          this.data = response.rows
          const classify = {id: 0, name: '主类目', children: []};
          this.classifyList = this.handleTree(response.rows, "id");
          classify.children = this.classifyList
          this.classifyOptions.push(classify);
        }
        this.loading = false;
      });
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children
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
        id: null,
        name: null,
        sort: null,
        parentId: null,
        parentName: null,

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
    /** 查询菜单下拉树结构 */
    getTreeselect() {
      listClassify(this.queryParams).then(response => {
        if (response.rows){
          this.data = response.rows
          this.classifyOptions = [];
          const classify = {id: 0, name: '主类目', children: []};
          classify.children = this.handleTree(response.rows, "id");
          this.classifyOptions.push(classify);
        }
      });
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
      this.title = "添加分类管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getClassify(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分类管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let a = this.data.filter(i=>{return i.id===this.form.parentId})
          if (a.length===0){
            this.form.parentName = null
          }
          else{
            this.form.parentName =a[0].name
          }
          if (this.form.id != null) {
            updateClassify(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClassify(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除分类管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delClassify(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有分类管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportClassify(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
