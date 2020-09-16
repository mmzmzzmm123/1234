<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属考核方案" prop="faid">
        <el-select v-model="queryParams.faid" placeholder="请选择考核模块" clearable size="small">
          <el-option
            v-for="dict in faOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="评选条件" prop="pxtj">
        <el-input
          v-model="queryParams.pxtj"
          placeholder="请输入评选条件"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属类型" prop="sslx">
        <el-input
          v-model="queryParams.sslx"
          placeholder="请输入所属类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createuserid">
        <el-input
          v-model="queryParams.createuserid"
          placeholder="请输入创建人"
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
          v-hasPermi="['qtjs:qtjspxfazbx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjs:qtjspxfazbx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjs:qtjspxfazbx:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['qtjs:qtjspxfazbx:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qtjspxfazbxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案编号" align="center" prop="faid" :formatter="khfaFormat"/>
      <el-table-column label="评选条件" align="center" prop="pxtj" />
      <el-table-column label="所属类型" align="center" prop="sslx" />
      <el-table-column label="指标值" align="center" prop="zbb" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="创建人" align="center" prop="createuserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjs:qtjspxfazbx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjs:qtjspxfazbx:remove']"
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

    <!-- 添加或修改群体教师评选方案指标对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核方案" prop="faid">
          <el-select
            v-model="queryParams.faid"
            placeholder="请选择考核模块"
            clearable
            size="small"
            :disabled="true"
          >
            <el-option
              v-for="dict in faOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评选条件" prop="pxtj">
          <el-input v-model="form.pxtj" placeholder="请输入评选条件" />
        </el-form-item>
        <el-form-item label="所属类型" prop="sslx">
          <el-input v-model="form.sslx" placeholder="请输入所属类型" />
        </el-form-item>
        <el-form-item label="指标值" prop="zbb">
          <el-input v-model="form.zbb" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
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
import { listQtjspxfazbx, getQtjspxfazbx, delQtjspxfazbx, addQtjspxfazbx, updateQtjspxfazbx, exportQtjspxfazbx } from "@/api/qtjs/qtjspxfazbx";
import {
  listQtjspxfa,
  getQtjspxfa,
} from "@/api/qtjs/qtjspxfa";

export default {
  name: "Qtjspxfazbx",
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
      // 群体教师评选方案指标表格数据
      qtjspxfazbxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 方案选项
      faOptions: [],
      // 默认方案
      defaultFa: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        pxtj: null,
        sslx: null,
        zbb: null,
        remarks: null,
        createuserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    const faid = this.$route.params && this.$route.params.id;
    this.getfa2(faid);
    this.getList();
    this.getfaList();
  },
  methods: {

    /** 查询群体教师评选方案指标列表 */
    getList() {
      this.loading = true;
      listQtjspxfazbx(this.queryParams).then(response => {
        this.qtjspxfazbxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 考核方案列表
    getfa2(faid) {
      getQtjspxfa(faid).then((response) => {
        this.queryParams.faid = response.data.id;
        this.defaultFa = response.data.id;
        this.getList();
      });
    },
    // 查询考核方案选项
    getfaList() {
      listQtjspxfa().then((response) => {
        this.faOptions = response.rows;
      });
    },
    // 考核方案字典翻译
    khfaFormat(row, column) {
      var actions = [];
      var datas = this.faOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
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
        faid: null,
        pxtj: null,
        sslx: null,
        zbb: null,
        remarks: null,
        createuserid: null,
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
      this.queryParams.faid = this.defaultFa;
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
      this.title = "添加群体教师评选方案指标";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getQtjspxfazbx(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改群体教师评选方案指标";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjspxfazbx(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjspxfazbx(this.form).then(response => {
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
      this.$confirm('是否确认删除群体教师评选方案指标编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delQtjspxfazbx(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有群体教师评选方案指标数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportQtjspxfazbx(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
