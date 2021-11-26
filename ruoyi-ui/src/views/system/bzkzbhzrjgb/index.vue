<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="信息入库时间戳" prop="xxrksjTmsp">
        <el-input
          v-model="queryParams.xxrksjTmsp"
          placeholder="请输入信息入库时间戳"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="首次采集时间" prop="firstCollectTime">
        <el-input
          v-model="queryParams.firstCollectTime"
          placeholder="请输入首次采集时间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最近采集时间" prop="lastCollectTime">
        <el-input
          v-model="queryParams.lastCollectTime"
          placeholder="请输入最近采集时间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发现次数" prop="foundTimes">
        <el-input
          v-model="queryParams.foundTimes"
          placeholder="请输入发现次数"
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
          v-hasPermi="['system:bzkzbhzrjgb:add']"
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
          v-hasPermi="['system:bzkzbhzrjgb:edit']"
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
          v-hasPermi="['system:bzkzbhzrjgb:remove']"
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
          v-hasPermi="['system:bzkzbhzrjgb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bzkzbhzrjgbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标准库主记录ID" align="center" prop="zjlid" />
      <el-table-column label="来源应用系统编号" align="center" prop="lyyyxtid" />
      <el-table-column label="来源表" align="center" prop="dataFrom" />
      <el-table-column label="信息入库时间" align="center" prop="xxrksj" />
      <el-table-column label="信息入库时间戳" align="center" prop="xxrksjTmsp" />
      <el-table-column label="标准库_备注" align="center" prop="bz" />
      <el-table-column label="来源表信息入库时间" align="center" prop="sourceXxrksj" />
      <el-table-column label="来源表主记录ID" align="center" prop="sourceZjlid" />
      <el-table-column label="业务标签ID" align="center" prop="businessFlagId" />
      <el-table-column label="业务标签值" align="center" prop="businessFlagValue" />
      <el-table-column label="首次采集时间" align="center" prop="firstCollectTime" />
      <el-table-column label="最近采集时间" align="center" prop="lastCollectTime" />
      <el-table-column label="发现次数" align="center" prop="foundTimes" />
      <el-table-column label="错误数据标识" align="center" prop="errorDataFlag" />
      <el-table-column label="错误数据" align="center" prop="errorData" />
      <el-table-column label="异常字段编码" align="center" prop="errorElement" />
      <el-table-column label="数据批次标识" align="center" prop="batchDataFlag" />
      <el-table-column label="数据安全级别代码" align="center" prop="sjaqjbdm" />
      <el-table-column label="采集地" align="center" prop="cjd" />
      <el-table-column label="统一社会信用代码" align="center" prop="tyshxydm" />
      <el-table-column label="申报个体工商户姓名" align="center" prop="sbgtgshxm" />
      <el-table-column label="证件类型" align="center" prop="zjlx" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="注册日期" align="center" prop="zcrq" />
      <el-table-column label="注册地址" align="center" prop="zcdz" />
      <el-table-column label="注册地址_行政区划" align="center" prop="zcdzXzqh" />
      <el-table-column label="登记状态名称" align="center" prop="regstate" />
      <el-table-column label="重大税收违法案件_主要违法事实" align="center" prop="zywfss" />
      <el-table-column label="失信被执行人案号" align="center" prop="sxbzxrah" />
      <el-table-column label="失信黑名单信息名称" align="center" prop="sxhmdxxmc" />
      <el-table-column label="工商异常信息" align="center" prop="gsycxx" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:bzkzbhzrjgb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:bzkzbhzrjgb:remove']"
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

    <!-- 添加或修改个体工商户信贷_标准库_准入监管对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="来源应用系统编号" prop="lyyyxtid">
          <el-input v-model="form.lyyyxtid" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="来源表" prop="dataFrom">
          <el-input v-model="form.dataFrom" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="信息入库时间" prop="xxrksj">
          <el-input v-model="form.xxrksj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="信息入库时间戳" prop="xxrksjTmsp">
          <el-input v-model="form.xxrksjTmsp" placeholder="请输入信息入库时间戳" />
        </el-form-item>
        <el-form-item label="标准库_备注" prop="bz">
          <el-input v-model="form.bz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="来源表信息入库时间" prop="sourceXxrksj">
          <el-input v-model="form.sourceXxrksj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="来源表主记录ID" prop="sourceZjlid">
          <el-input v-model="form.sourceZjlid" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="业务标签ID" prop="businessFlagId">
          <el-input v-model="form.businessFlagId" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="业务标签值" prop="businessFlagValue">
          <el-input v-model="form.businessFlagValue" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="首次采集时间" prop="firstCollectTime">
          <el-input v-model="form.firstCollectTime" placeholder="请输入首次采集时间" />
        </el-form-item>
        <el-form-item label="最近采集时间" prop="lastCollectTime">
          <el-input v-model="form.lastCollectTime" placeholder="请输入最近采集时间" />
        </el-form-item>
        <el-form-item label="发现次数" prop="foundTimes">
          <el-input v-model="form.foundTimes" placeholder="请输入发现次数" />
        </el-form-item>
        <el-form-item label="错误数据标识" prop="errorDataFlag">
          <el-input v-model="form.errorDataFlag" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="错误数据" prop="errorData">
          <el-input v-model="form.errorData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="异常字段编码" prop="errorElement">
          <el-input v-model="form.errorElement" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数据批次标识" prop="batchDataFlag">
          <el-input v-model="form.batchDataFlag" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数据安全级别代码" prop="sjaqjbdm">
          <el-input v-model="form.sjaqjbdm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="采集地" prop="cjd">
          <el-input v-model="form.cjd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="tyshxydm">
          <el-input v-model="form.tyshxydm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
          <el-input v-model="form.sbgtgshxm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证件类型" prop="zjlx">
          <el-input v-model="form.zjlx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="注册日期" prop="zcrq">
          <el-input v-model="form.zcrq" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="注册地址" prop="zcdz">
          <el-input v-model="form.zcdz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="注册地址_行政区划" prop="zcdzXzqh">
          <el-input v-model="form.zcdzXzqh" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="登记状态名称" prop="regstate">
          <el-input v-model="form.regstate" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="重大税收违法案件_主要违法事实" prop="zywfss">
          <el-input v-model="form.zywfss" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="失信被执行人案号" prop="sxbzxrah">
          <el-input v-model="form.sxbzxrah" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="失信黑名单信息名称" prop="sxhmdxxmc">
          <el-input v-model="form.sxhmdxxmc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="工商异常信息" prop="gsycxx">
          <el-input v-model="form.gsycxx" type="textarea" placeholder="请输入内容" />
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
import { listBzkzbhzrjgb, getBzkzbhzrjgb, delBzkzbhzrjgb, addBzkzbhzrjgb, updateBzkzbhzrjgb, exportBzkzbhzrjgb } from "@/api/system/bzkzbhzrjgb";

export default {
  name: "Bzkzbhzrjgb",
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
      // 个体工商户信贷_标准库_准入监管表格数据
      bzkzbhzrjgbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        lyyyxtid: null,
        dataFrom: null,
        xxrksj: null,
        xxrksjTmsp: null,
        bz: null,
        sourceXxrksj: null,
        sourceZjlid: null,
        businessFlagId: null,
        businessFlagValue: null,
        firstCollectTime: null,
        lastCollectTime: null,
        foundTimes: null,
        errorDataFlag: null,
        errorData: null,
        errorElement: null,
        batchDataFlag: null,
        sjaqjbdm: null,
        cjd: null,
        tyshxydm: null,
        sbgtgshxm: null,
        zjlx: null,
        zjhm: null,
        zcrq: null,
        zcdz: null,
        zcdzXzqh: null,
        regstate: null,
        zywfss: null,
        sxbzxrah: null,
        sxhmdxxmc: null,
        gsycxx: null
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
    /** 查询个体工商户信贷_标准库_准入监管列表 */
    getList() {
      this.loading = true;
      listBzkzbhzrjgb(this.queryParams).then(response => {
        this.bzkzbhzrjgbList = response.rows;
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
        zjlid: null,
        lyyyxtid: null,
        dataFrom: null,
        xxrksj: null,
        xxrksjTmsp: null,
        bz: null,
        sourceXxrksj: null,
        sourceZjlid: null,
        businessFlagId: null,
        businessFlagValue: null,
        firstCollectTime: null,
        lastCollectTime: null,
        foundTimes: null,
        errorDataFlag: null,
        errorData: null,
        errorElement: null,
        batchDataFlag: null,
        sjaqjbdm: null,
        cjd: null,
        tyshxydm: null,
        sbgtgshxm: null,
        zjlx: null,
        zjhm: null,
        zcrq: null,
        zcdz: null,
        zcdzXzqh: null,
        regstate: null,
        zywfss: null,
        sxbzxrah: null,
        sxhmdxxmc: null,
        gsycxx: null
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
      this.ids = selection.map(item => item.zjlid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加个体工商户信贷_标准库_准入监管";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const zjlid = row.zjlid || this.ids
      getBzkzbhzrjgb(zjlid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改个体工商户信贷_标准库_准入监管";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.zjlid != null) {
            updateBzkzbhzrjgb(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBzkzbhzrjgb(this.form).then(response => {
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
      const zjlids = row.zjlid || this.ids;
      this.$confirm('是否确认删除个体工商户信贷_标准库_准入监管编号为"' + zjlids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBzkzbhzrjgb(zjlids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有个体工商户信贷_标准库_准入监管数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportBzkzbhzrjgb(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
