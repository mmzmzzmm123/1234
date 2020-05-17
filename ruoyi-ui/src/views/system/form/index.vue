<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="表单名称" prop="formName">
        <el-input
          v-model="queryParams.formName"
          placeholder="请输入表单名称"
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
          v-hasPermi="['system:form:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:form:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:form:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:form:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="formList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="formId" />
      <el-table-column label="表单名称" align="center" prop="formName">
        <template slot-scope="scope">
          <el-link type="primary" @click="handleDesign(scope.row)">{{scope.row.formName}}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="表单描述" align="center" prop="formComment" />
      <!-- <el-table-column label="实体类名称" align="center" prop="tableName" />
      <el-table-column label="表单配置json" align="center" prop="options" />
      <el-table-column label="版本号" align="center" prop="versionName" />
      <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <!-- 获取当前单元格的值 slot-scope="scope"-->
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDesign(scope.row)"
            v-hasPermi="['system:form:edit']"
          >设计表单</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:form:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:form:remove']"
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

    <!-- 添加或修改数据模型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="表单名称" prop="formName">
          <el-input v-model="form.formName" placeholder="请输入表单名称" />
        </el-form-item>
        <el-form-item label="表单描述" prop="formComment">
          <el-input v-model="form.formComment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!-- <el-form-item label="实体类名称" prop="tableName">
          <el-input v-model="form.tableName" placeholder="请输入实体类名称" />
        </el-form-item>
        <el-form-item label="表单配置json" prop="options">
          <el-input v-model="form.options" placeholder="请输入表单配置json" />
        </el-form-item>
        <el-form-item label="版本号" prop="versionName">
          <el-input v-model="form.versionName" placeholder="请输入版本号" />
        </el-form-item> -->
      </el-form>
      
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 表单设计器 -->
    <el-dialog :title="title" :visible.sync="designOpen" :fullscreen="true" append-to-body>
      <!-- 动态销毁 -->
      <form-design :form="currentRow" v-if="designOpen"></form-design>
    </el-dialog>
  </div>
</template>

<script>
import { listForm, getForm, delForm, addForm, updateForm, exportForm } from "@/api/system/form";
export default {
  name: "Form",
  components:{
    //动态组件
    'FormDesign': () => import('@/views/tool/build/index')
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
      // 数据模型表格数据
      formList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      designOpen:false,
      currentRow:{},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        formName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        formName: [
          { required: true, message: "表单名称不能为空", trigger: "blur" }
        ],
        tableName: [
          { required: true, message: "实体类名称不能为空", trigger: "blur" }
        ],
        versionName: [
          { required: true, message: "版本号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询数据模型列表 */
    getList() {
      this.loading = true;
      listForm(this.queryParams).then(response => {
        this.formList = response.rows;
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
        formId: undefined,
        formName: undefined,
        formComment: undefined,
        tableName: undefined,
        options: undefined,
        versionName: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
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
      this.ids = selection.map(item => item.formId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据模型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const formId = row.formId || this.ids
      getForm(formId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据模型";
      });
    },
    /** 设计表单操作 */
    handleDesign(row){
        console.log(row)
        this.designOpen=true
        this.title = "设计表单模型";
        this.currentRow = row
        
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.formId != undefined) {
            updateForm(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addForm(this.form).then(response => {
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
      const formIds = row.formId || this.ids;
      this.$confirm('是否确认删除数据模型编号为"' + formIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delForm(formIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有数据模型数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportForm(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>