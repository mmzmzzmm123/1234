<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="培训名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入培训名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['jxjs:jxzxjdpxxxfb:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jxzxjdpxxxfb:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxjs:jxzxjdpxxxfb:remove']"
        >删除</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jxzxjdpxxxfbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="培训信息名称" align="center" prop="name" />
      <el-table-column label="基地校名称" align="center" prop="jdxid" :formatter="jdxFormat" />      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleCheck(scope.row)"
            v-hasPermi="['jxjs:jxzxjdpxxxfb:edit']"
            >查看</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jxzxjdpxxxfb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jxzxjdpxxxfb:remove']"
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

    <!-- 添加或修改基地培训信息发布对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="培训名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入培训信息名称" />
        </el-form-item>
        <el-form-item label="培训计划" prop="pxjh">
          <Editor v-model="form.pxjh" placeholder="请输入培训计划" />
        </el-form-item>
        <el-form-item label="培训方案" prop="pxfa">
          <Editor v-model="form.pxfa" placeholder="请输入培训方案" />
        </el-form-item>
        <el-form-item label="培训总结" prop="pxzj">
          <Editor v-model="form.pxzj" placeholder="请输入培训总结" />
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
import { listJxzxjdpxxxfb, getJxzxjdpxxxfb, delJxzxjdpxxxfb, addJxzxjdpxxxfb, updateJxzxjdpxxxfb, exportJxzxjdpxxxfb } from "@/api/jxjs/jxzxjdpxxxfb";
import Editor from "@/components/Editor";
import { listJdx, getJdx, } from "@/api/jxjs/jdx";

export default {
  name: "Jxzxjdpxxxfb",
  components: {
    Editor
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 基地培训信息发布表格数据
      jxzxjdpxxxfbList: [],
      // 基地校选项
      jdxOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jdxid: null,
        name: null,
        pxjh: null,
        pxfa: null,
        pxzj: null,
        pxBeiyong: null,
        createUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" },
        ],
        pxjh: [
          { required: true, message: "培训计划不能为空", trigger: "blur" },
        ],
        pxfa: [
          { required: true, message: "培训方案不能为空", trigger: "blur" },
        ],
        pxzj: [
          { required: true, message: "培训总结不能为空", trigger: "blur" },
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getJdxList();
  },
  methods: {
    /** 查询基地培训信息发布列表 */
    getList() {
      this.loading = true;
      listJxzxjdpxxxfb(this.queryParams).then(response => {
        this.jxzxjdpxxxfbList = response.rows;
        //console.log(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取基地校信息
    getJdxList() {
      listJdx(null).then((response) => {
        this.jdxOptions = response.rows;
      });
    },
    // 字典翻译
    jdxFormat(row, column) {
      var actions = [];
      var datas = this.jdxOptions;
      console.log(this.jdxOptions);
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.jdxid) {
          actions.push(datas[key].jdxmc);
          return false;
        }
      });
      return actions.join("");
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
        jdxid: null,
        name: null,
        pxjh: null,
        pxfa: null,
        pxzj: null,
        pxBeiyong: null,
        createUserid: null,
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
      this.title = "添加基地培训信息发布";
    },
    /** 查看按钮操作 */
    handleCheck(row) {
      this.reset();
      const id = row.id || this.ids
      getJxzxjdpxxxfb(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地培训信息发布";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getJxzxjdpxxxfb(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地培训信息发布";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJxzxjdpxxxfb(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxzxjdpxxxfb(this.form).then(response => {
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
      this.$confirm('是否确认删除基地培训信息发布编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJxzxjdpxxxfb(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有基地培训信息发布数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportJxzxjdpxxxfb(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
