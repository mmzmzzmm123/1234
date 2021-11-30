<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="统一社会信用代码" prop="tyshxydm">
        <el-input
          v-model="queryParams.tyshxydm"
          placeholder="请输入统一社会信用代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
        <el-input
          v-model="queryParams.sbgtgshxm"
          placeholder="请输入申报个体工商户姓名"
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
      <el-form-item label="性别名称" prop="xb">
        <el-input
          v-model="queryParams.xb"
          placeholder="请输入性别名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年龄" prop="nl">
        <el-input
          v-model="queryParams.nl"
          placeholder="请输入年龄"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册日期" prop="zcrq">
        <el-input
          v-model="queryParams.zcrq"
          placeholder="请输入注册日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册地址" prop="zcdz">
        <el-input
          v-model="queryParams.zcdz"
          placeholder="请输入注册地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册地址_行政区划" prop="zcdzXzqh">
        <el-input
          v-model="queryParams.zcdzXzqh"
          placeholder="请输入注册地址_行政区划"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登记状态名称" prop="regstate">
        <el-input
          v-model="queryParams.regstate"
          placeholder="请输入登记状态名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重大税收违法案件_主要违法事实" prop="zywfss">
        <el-input
          v-model="queryParams.zywfss"
          placeholder="请输入重大税收违法案件_主要违法事实"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="失信被执行人案号" prop="sxbzxrah">
        <el-input
          v-model="queryParams.sxbzxrah"
          placeholder="请输入失信被执行人案号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="失信黑名单信息名称" prop="sxhmdxxmc">
        <el-input
          v-model="queryParams.sxhmdxxmc"
          placeholder="请输入失信黑名单信息名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工商异常信息" prop="gsycxx">
        <el-input
          v-model="queryParams.gsycxx"
          placeholder="请输入工商异常信息"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不动产查封" prop="bdccf">
        <el-input
          v-model="queryParams.bdccf"
          placeholder="请输入不动产查封"
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
          v-hasPermi="['system:zrjgmxb:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:zrjgmxb:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:zrjgmxb:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:zrjgmxb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zrjgmxbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="统一社会信用代码" align="center" prop="tyshxydm" />
      <el-table-column label="申报个体工商户姓名" align="center" prop="sbgtgshxm" />
      <el-table-column label="证件类型" align="center" prop="zjlx" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="性别名称" align="center" prop="xb" />
      <el-table-column label="年龄" align="center" prop="nl" />
      <el-table-column label="注册日期" align="center" prop="zcrq" />
      <el-table-column label="注册地址" align="center" prop="zcdz" />
      <el-table-column label="注册地址_行政区划" align="center" prop="zcdzXzqh" />
      <el-table-column label="登记状态名称" align="center" prop="regstate" />
      <el-table-column label="重大税收违法案件_主要违法事实" align="center" prop="zywfss" />
      <el-table-column label="失信被执行人案号" align="center" prop="sxbzxrah" />
      <el-table-column label="失信黑名单信息名称" align="center" prop="sxhmdxxmc" />
      <el-table-column label="工商异常信息" align="center" prop="gsycxx" />
      <el-table-column label="不动产查封" align="center" prop="bdccf" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:zrjgmxb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:zrjgmxb:remove']"
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

    <!-- 添加或修改个体工商户信贷_标准库_准入监管明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="统一社会信用代码" prop="tyshxydm">
          <el-input v-model="form.tyshxydm" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
          <el-input v-model="form.sbgtgshxm" placeholder="请输入申报个体工商户姓名" />
        </el-form-item>
        <el-form-item label="证件类型" prop="zjlx">
          <el-input v-model="form.zjlx" placeholder="请输入证件类型" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="性别名称" prop="xb">
          <el-input v-model="form.xb" placeholder="请输入性别名称" />
        </el-form-item>
        <el-form-item label="年龄" prop="nl">
          <el-input v-model="form.nl" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="注册日期" prop="zcrq">
          <el-input v-model="form.zcrq" placeholder="请输入注册日期" />
        </el-form-item>
        <el-form-item label="注册地址" prop="zcdz">
          <el-input v-model="form.zcdz" placeholder="请输入注册地址" />
        </el-form-item>
        <el-form-item label="注册地址_行政区划" prop="zcdzXzqh">
          <el-input v-model="form.zcdzXzqh" placeholder="请输入注册地址_行政区划" />
        </el-form-item>
        <el-form-item label="登记状态名称" prop="regstate">
          <el-input v-model="form.regstate" placeholder="请输入登记状态名称" />
        </el-form-item>
        <el-form-item label="重大税收违法案件_主要违法事实" prop="zywfss">
          <el-input v-model="form.zywfss" placeholder="请输入重大税收违法案件_主要违法事实" />
        </el-form-item>
        <el-form-item label="失信被执行人案号" prop="sxbzxrah">
          <el-input v-model="form.sxbzxrah" placeholder="请输入失信被执行人案号" />
        </el-form-item>
        <el-form-item label="失信黑名单信息名称" prop="sxhmdxxmc">
          <el-input v-model="form.sxhmdxxmc" placeholder="请输入失信黑名单信息名称" />
        </el-form-item>
        <el-form-item label="工商异常信息" prop="gsycxx">
          <el-input v-model="form.gsycxx" placeholder="请输入工商异常信息" />
        </el-form-item>
        <el-form-item label="不动产查封" prop="bdccf">
          <el-input v-model="form.bdccf" placeholder="请输入不动产查封" />
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
import { listZrjgmxb, getZrjgmxb, delZrjgmxb, addZrjgmxb, updateZrjgmxb, exportZrjgmxb } from "@/api/system/zrjgmxb";

export default {
  name: "Zrjgmxb",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
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
      // 个体工商户信贷_标准库_准入监管明细表格数据
      zrjgmxbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tyshxydm: null,
        sbgtgshxm: null,
        zjlx: null,
        zjhm: null,
        xb: null,
        nl: null,
        zcrq: null,
        zcdz: null,
        zcdzXzqh: null,
        regstate: null,
        zywfss: null,
        sxbzxrah: null,
        sxhmdxxmc: null,
        gsycxx: null,
        bdccf: null
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
    /** 查询个体工商户信贷_标准库_准入监管明细列表 */
    getList() {
      this.loading = true;
      listZrjgmxb(this.queryParams).then(response => {
        this.zrjgmxbList = response.rows;
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
        tyshxydm: null,
        sbgtgshxm: null,
        zjlx: null,
        zjhm: null,
        xb: null,
        nl: null,
        zcrq: null,
        zcdz: null,
        zcdzXzqh: null,
        regstate: null,
        zywfss: null,
        sxbzxrah: null,
        sxhmdxxmc: null,
        gsycxx: null,
        bdccf: null
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
      this.ids = selection.map(item => item.tyshxydm)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加个体工商户信贷_标准库_准入监管明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tyshxydm = row.tyshxydm || this.ids
      getZrjgmxb(tyshxydm).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改个体工商户信贷_标准库_准入监管明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tyshxydm != null) {
            updateZrjgmxb(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addZrjgmxb(this.form).then(response => {
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
      const tyshxydms = row.tyshxydm || this.ids;
      this.$confirm('是否确认删除个体工商户信贷_标准库_准入监管明细编号为"' + tyshxydms + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delZrjgmxb(tyshxydms);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有个体工商户信贷_标准库_准入监管明细数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportZrjgmxb(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
