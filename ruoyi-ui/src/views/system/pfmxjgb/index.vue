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
      <el-form-item label="营业店名" prop="yydm">
        <el-input
          v-model="queryParams.yydm"
          placeholder="请输入营业店名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经营场所_地址" prop="jycsDz">
        <el-input
          v-model="queryParams.jycsDz"
          placeholder="请输入经营场所_地址"
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
      <el-form-item label="经营年限_评分" prop="jynx">
        <el-input
          v-model="queryParams.jynx"
          placeholder="请输入经营年限_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报人固定资产情况_评分" prop="sbrgdzcqkpf">
        <el-input
          v-model="queryParams.sbrgdzcqkpf"
          placeholder="请输入申报人固定资产情况_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报人最高学历_评分" prop="sbrzgxlpf">
        <el-input
          v-model="queryParams.sbrzgxlpf"
          placeholder="请输入申报人最高学历_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报人婚姻状况_评分" prop="sbrhyzkpf">
        <el-input
          v-model="queryParams.sbrhyzkpf"
          placeholder="请输入申报人婚姻状况_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报人欠税情况_评分" prop="sbrqsqkpf">
        <el-input
          v-model="queryParams.sbrqsqkpf"
          placeholder="请输入申报人欠税情况_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="个体工商户欠税情况_评分" prop="gtgshqsqkpf">
        <el-input
          v-model="queryParams.gtgshqsqkpf"
          placeholder="请输入个体工商户欠税情况_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="个体工商户激励情况_评分" prop="gtgshjlqkpf">
        <el-input
          v-model="queryParams.gtgshjlqkpf"
          placeholder="请输入个体工商户激励情况_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="个体工商户享受扶持情况_评分" prop="gtgshxsfcqkpf">
        <el-input
          v-model="queryParams.gtgshxsfcqkpf"
          placeholder="请输入个体工商户享受扶持情况_评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总评分" prop="zpf">
        <el-input
          v-model="queryParams.zpf"
          placeholder="请输入总评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="风险等级" prop="fxdj">
        <el-input
          v-model="queryParams.fxdj"
          placeholder="请输入风险等级"
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
          v-hasPermi="['system:pfmxjgb:add']"
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
          v-hasPermi="['system:pfmxjgb:edit']"
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
          v-hasPermi="['system:pfmxjgb:remove']"
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
          v-hasPermi="['system:pfmxjgb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pfmxjgbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="统一社会信用代码" align="center" prop="tyshxydm" />
      <el-table-column label="申报个体工商户姓名" align="center" prop="sbgtgshxm" />
      <el-table-column label="证件类型" align="center" prop="zjlx" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="营业店名" align="center" prop="yydm" />
      <el-table-column label="经营场所_地址" align="center" prop="jycsDz" />
      <el-table-column label="注册日期" align="center" prop="zcrq" />
      <el-table-column label="经营年限_评分" align="center" prop="jynx" />
      <el-table-column label="申报人固定资产情况_评分" align="center" prop="sbrgdzcqkpf" />
      <el-table-column label="申报人最高学历_评分" align="center" prop="sbrzgxlpf" />
      <el-table-column label="申报人婚姻状况_评分" align="center" prop="sbrhyzkpf" />
      <el-table-column label="申报人欠税情况_评分" align="center" prop="sbrqsqkpf" />
      <el-table-column label="个体工商户欠税情况_评分" align="center" prop="gtgshqsqkpf" />
      <el-table-column label="个体工商户激励情况_评分" align="center" prop="gtgshjlqkpf" />
      <el-table-column label="个体工商户享受扶持情况_评分" align="center" prop="gtgshxsfcqkpf" />
      <el-table-column label="总评分" align="center" prop="zpf" />
      <el-table-column label="风险等级" align="center" prop="fxdj" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:pfmxjgb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:pfmxjgb:remove']"
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

    <!-- 添加或修改个体工商户信贷_主题库_评分模型结果对话框 -->
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
        <el-form-item label="营业店名" prop="yydm">
          <el-input v-model="form.yydm" placeholder="请输入营业店名" />
        </el-form-item>
        <el-form-item label="经营场所_地址" prop="jycsDz">
          <el-input v-model="form.jycsDz" placeholder="请输入经营场所_地址" />
        </el-form-item>
        <el-form-item label="注册日期" prop="zcrq">
          <el-input v-model="form.zcrq" placeholder="请输入注册日期" />
        </el-form-item>
        <el-form-item label="经营年限_评分" prop="jynx">
          <el-input v-model="form.jynx" placeholder="请输入经营年限_评分" />
        </el-form-item>
        <el-form-item label="申报人固定资产情况_评分" prop="sbrgdzcqkpf">
          <el-input v-model="form.sbrgdzcqkpf" placeholder="请输入申报人固定资产情况_评分" />
        </el-form-item>
        <el-form-item label="申报人最高学历_评分" prop="sbrzgxlpf">
          <el-input v-model="form.sbrzgxlpf" placeholder="请输入申报人最高学历_评分" />
        </el-form-item>
        <el-form-item label="申报人婚姻状况_评分" prop="sbrhyzkpf">
          <el-input v-model="form.sbrhyzkpf" placeholder="请输入申报人婚姻状况_评分" />
        </el-form-item>
        <el-form-item label="申报人欠税情况_评分" prop="sbrqsqkpf">
          <el-input v-model="form.sbrqsqkpf" placeholder="请输入申报人欠税情况_评分" />
        </el-form-item>
        <el-form-item label="个体工商户欠税情况_评分" prop="gtgshqsqkpf">
          <el-input v-model="form.gtgshqsqkpf" placeholder="请输入个体工商户欠税情况_评分" />
        </el-form-item>
        <el-form-item label="个体工商户激励情况_评分" prop="gtgshjlqkpf">
          <el-input v-model="form.gtgshjlqkpf" placeholder="请输入个体工商户激励情况_评分" />
        </el-form-item>
        <el-form-item label="个体工商户享受扶持情况_评分" prop="gtgshxsfcqkpf">
          <el-input v-model="form.gtgshxsfcqkpf" placeholder="请输入个体工商户享受扶持情况_评分" />
        </el-form-item>
        <el-form-item label="总评分" prop="zpf">
          <el-input v-model="form.zpf" placeholder="请输入总评分" />
        </el-form-item>
        <el-form-item label="风险等级" prop="fxdj">
          <el-input v-model="form.fxdj" placeholder="请输入风险等级" />
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
import { listPfmxjgb, getPfmxjgb, delPfmxjgb, addPfmxjgb, updatePfmxjgb, exportPfmxjgb } from "@/api/system/pfmxjgb";

export default {
  name: "Pfmxjgb",
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
      // 个体工商户信贷_主题库_评分模型结果表格数据
      pfmxjgbList: [],
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
        yydm: null,
        jycsDz: null,
        zcrq: null,
        jynx: null,
        sbrgdzcqkpf: null,
        sbrzgxlpf: null,
        sbrhyzkpf: null,
        sbrqsqkpf: null,
        gtgshqsqkpf: null,
        gtgshjlqkpf: null,
        gtgshxsfcqkpf: null,
        zpf: null,
        fxdj: null
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
    /** 查询个体工商户信贷_主题库_评分模型结果列表 */
    getList() {
      this.loading = true;
      listPfmxjgb(this.queryParams).then(response => {
        this.pfmxjgbList = response.rows;
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
        yydm: null,
        jycsDz: null,
        zcrq: null,
        jynx: null,
        sbrgdzcqkpf: null,
        sbrzgxlpf: null,
        sbrhyzkpf: null,
        sbrqsqkpf: null,
        gtgshqsqkpf: null,
        gtgshjlqkpf: null,
        gtgshxsfcqkpf: null,
        zpf: null,
        fxdj: null
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
      this.title = "添加个体工商户信贷_主题库_评分模型结果";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tyshxydm = row.tyshxydm || this.ids
      getPfmxjgb(tyshxydm).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改个体工商户信贷_主题库_评分模型结果";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tyshxydm != null) {
            updatePfmxjgb(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPfmxjgb(this.form).then(response => {
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
      this.$confirm('是否确认删除个体工商户信贷_主题库_评分模型结果编号为"' + tyshxydms + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPfmxjgb(tyshxydms);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有个体工商户信贷_主题库_评分模型结果数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportPfmxjgb(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
