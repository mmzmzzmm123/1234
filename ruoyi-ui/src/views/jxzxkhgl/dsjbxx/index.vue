<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="其他系统教师编号" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入其他系统教师编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师姓名" prop="jsxm">
        <el-input
          v-model="queryParams.jsxm"
          placeholder="请输入教师姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-input
          v-model="queryParams.xb"
          placeholder="请输入性别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门id" prop="deptid">
        <el-input
          v-model="queryParams.deptid"
          placeholder="请输入部门id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师档案号" prop="dabh">
        <el-input
          v-model="queryParams.dabh"
          placeholder="请输入教师档案号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件类型" prop="zjlx">
        <el-input
          v-model="queryParams.zjlx"
          placeholder="请输入证件类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编制状态" prop="bzzt">
        <el-input
          v-model="queryParams.bzzt"
          placeholder="请输入编制状态"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位状态" prop="gwzt">
        <el-input
          v-model="queryParams.gwzt"
          placeholder="请输入岗位状态"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位类型" prop="gwlx">
        <el-input
          v-model="queryParams.gwlx"
          placeholder="请输入岗位类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出生日期" prop="csrq">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.csrq"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择出生日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="任教年限" prop="rjnx">
        <el-input
          v-model="queryParams.rjnx"
          placeholder="请输入任教年限"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职称" prop="zc">
        <el-input
          v-model="queryParams.zc"
          placeholder="请输入职称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专业荣誉" prop="zyry">
        <el-input
          v-model="queryParams.zyry"
          placeholder="请输入专业荣誉"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="同职比" prop="tzb">
        <el-input
          v-model="queryParams.tzb"
          placeholder="请输入同职比"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="周课时" prop="zks">
        <el-input
          v-model="queryParams.zks"
          placeholder="请输入周课时"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学段" prop="xd">
        <el-input
          v-model="queryParams.xd"
          placeholder="请输入学段"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学科" prop="xk">
        <el-input
          v-model="queryParams.xk"
          placeholder="请输入学科"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="导师类型" prop="dslx">
        <el-input
          v-model="queryParams.dslx"
          placeholder="请输入导师类型"
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
          v-hasPermi="['jxzxkhgl:dsjbxx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxzxkhgl:dsjbxx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxzxkhgl:dsjbxx:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jxzxkhgl:dsjbxx:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dsjbxxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="其他系统教师编号" align="center" prop="jsid" />
      <el-table-column label="教师姓名" align="center" prop="jsxm" />
      <el-table-column label="性别" align="center" prop="xb" />
      <el-table-column label="部门id" align="center" prop="deptid" />
      <el-table-column label="教师档案号" align="center" prop="dabh" />
      <el-table-column label="证件类型" align="center" prop="zjlx" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="编制状态" align="center" prop="bzzt" />
      <el-table-column label="岗位状态" align="center" prop="gwzt" />
      <el-table-column label="岗位类型" align="center" prop="gwlx" />
      <el-table-column label="出生日期" align="center" prop="csrq" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.csrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任教年限" align="center" prop="rjnx" />
      <el-table-column label="职称" align="center" prop="zc" />
      <el-table-column label="专业荣誉" align="center" prop="zyry" />
      <el-table-column label="同职比" align="center" prop="tzb" />
      <el-table-column label="周课时" align="center" prop="zks" />
      <el-table-column label="学段" align="center" prop="xd" />
      <el-table-column label="学科" align="center" prop="xk" />
      <el-table-column label="导师类型" align="center" prop="dslx" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxzxkhgl:dsjbxx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxzxkhgl:dsjbxx:remove']"
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

    <!-- 添加或修改导师基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="其他系统教师编号" prop="jsid">
          <el-input v-model="form.jsid" placeholder="请输入其他系统教师编号" />
        </el-form-item>
        <el-form-item label="教师姓名" prop="jsxm">
          <el-input v-model="form.jsxm" placeholder="请输入教师姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-input v-model="form.xb" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="部门id" prop="deptid">
          <el-input v-model="form.deptid" placeholder="请输入部门id" />
        </el-form-item>
        <el-form-item label="教师档案号" prop="dabh">
          <el-input v-model="form.dabh" placeholder="请输入教师档案号" />
        </el-form-item>
        <el-form-item label="证件类型" prop="zjlx">
          <el-input v-model="form.zjlx" placeholder="请输入证件类型" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="编制状态" prop="bzzt">
          <el-input v-model="form.bzzt" placeholder="请输入编制状态" />
        </el-form-item>
        <el-form-item label="岗位状态" prop="gwzt">
          <el-input v-model="form.gwzt" placeholder="请输入岗位状态" />
        </el-form-item>
        <el-form-item label="岗位类型" prop="gwlx">
          <el-input v-model="form.gwlx" placeholder="请输入岗位类型" />
        </el-form-item>
        <el-form-item label="出生日期" prop="csrq">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.csrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="任教年限" prop="rjnx">
          <el-input v-model="form.rjnx" placeholder="请输入任教年限" />
        </el-form-item>
        <el-form-item label="职称" prop="zc">
          <el-input v-model="form.zc" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item label="专业荣誉" prop="zyry">
          <el-input v-model="form.zyry" placeholder="请输入专业荣誉" />
        </el-form-item>
        <el-form-item label="同职比" prop="tzb">
          <el-input v-model="form.tzb" placeholder="请输入同职比" />
        </el-form-item>
        <el-form-item label="周课时" prop="zks">
          <el-input v-model="form.zks" placeholder="请输入周课时" />
        </el-form-item>
        <el-form-item label="学段" prop="xd">
          <el-input v-model="form.xd" placeholder="请输入学段" />
        </el-form-item>
        <el-form-item label="学科" prop="xk">
          <el-input v-model="form.xk" placeholder="请输入学科" />
        </el-form-item>
        <el-form-item label="导师类型" prop="dslx">
          <el-input v-model="form.dslx" placeholder="请输入导师类型" />
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
import { listDsjbxx, getDsjbxx, delDsjbxx, addDsjbxx, updateDsjbxx, exportDsjbxx } from "@/api/jxzxkhgl/dsjbxx";

export default {
  name: "Dsjbxx",
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
      // 导师基本信息表格数据
      dsjbxxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jsid: null,
        jsxm: null,
        xb: null,
        deptid: null,
        dabh: null,
        zjlx: null,
        zjhm: null,
        bzzt: null,
        gwzt: null,
        gwlx: null,
        csrq: null,
        rjnx: null,
        zc: null,
        zyry: null,
        tzb: null,
        zks: null,
        xd: null,
        xk: null,
        dslx: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询导师基本信息列表 */
    getList() {
      this.loading = true;
      listDsjbxx(this.queryParams).then(response => {
        this.dsjbxxList = response.rows;
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
        jsid: null,
        jsxm: null,
        xb: null,
        deptid: null,
        dabh: null,
        zjlx: null,
        zjhm: null,
        bzzt: null,
        gwzt: null,
        gwlx: null,
        csrq: null,
        rjnx: null,
        zc: null,
        zyry: null,
        tzb: null,
        zks: null,
        xd: null,
        xk: null,
        dslx: null,
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
      this.title = "添加导师基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDsjbxx(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改导师基本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDsjbxx(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDsjbxx(this.form).then(response => {
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
      this.$confirm('是否确认删除导师基本信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDsjbxx(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有导师基本信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDsjbxx(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
