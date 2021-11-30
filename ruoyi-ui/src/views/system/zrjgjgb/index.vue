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
      <el-form-item label="经营年限大于1年(0否1是)" prop="jynx">
        <el-input
          v-model="queryParams.jynx"
          placeholder="请输入经营年限大于1年(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="准入年龄_18至60男_55女(0否1是)" prop="zrnl">
        <el-input
          v-model="queryParams.zrnl"
          placeholder="请输入准入年龄_18至60男_55女(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="非异地贷款_泉州市内(0否1是)" prop="fyddk">
        <el-input
          v-model="queryParams.fyddk"
          placeholder="请输入非异地贷款_泉州市内(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="营业执照状态非吊销_停业_清算_注销(0否1是)" prop="regstate">
        <el-input
          v-model="queryParams.regstate"
          placeholder="请输入营业执照状态非吊销_停业_清算_注销(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不存在重大税收违法案件(0否1是)" prop="zdsffwaj">
        <el-input
          v-model="queryParams.zdsffwaj"
          placeholder="请输入不存在重大税收违法案件(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="非失信被执行人(0否1是)" prop="sxbzxr">
        <el-input
          v-model="queryParams.sxbzxr"
          placeholder="请输入非失信被执行人(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不存在失信黑名单(0否1是)" prop="sxhmd">
        <el-input
          v-model="queryParams.sxhmd"
          placeholder="请输入不存在失信黑名单(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不存在国家下发工商异常名录(0否1是)" prop="gjxfgsycml">
        <el-input
          v-model="queryParams.gjxfgsycml"
          placeholder="请输入不存在国家下发工商异常名录(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不存在不动产查封(0否1是)" prop="bdccf">
        <el-input
          v-model="queryParams.bdccf"
          placeholder="请输入不存在不动产查封(0否1是)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否准入(0否1是)" prop="sfzr">
        <el-input
          v-model="queryParams.sfzr"
          placeholder="请输入是否准入(0否1是)"
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
          v-hasPermi="['system:zrjgjgb:add']"
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
          v-hasPermi="['system:zrjgjgb:edit']"
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
          v-hasPermi="['system:zrjgjgb:remove']"
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
          v-hasPermi="['system:zrjgjgb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zrjgjgbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="统一社会信用代码" align="center" prop="tyshxydm" />
      <el-table-column label="申报个体工商户姓名" align="center" prop="sbgtgshxm" />
      <el-table-column label="证件类型" align="center" prop="zjlx" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="经营年限大于1年(0否1是)" align="center" prop="jynx" />
      <el-table-column label="准入年龄_18至60男_55女(0否1是)" align="center" prop="zrnl" />
      <el-table-column label="非异地贷款_泉州市内(0否1是)" align="center" prop="fyddk" />
      <el-table-column label="营业执照状态非吊销_停业_清算_注销(0否1是)" align="center" prop="regstate" />
      <el-table-column label="不存在重大税收违法案件(0否1是)" align="center" prop="zdsffwaj" />
      <el-table-column label="非失信被执行人(0否1是)" align="center" prop="sxbzxr" />
      <el-table-column label="不存在失信黑名单(0否1是)" align="center" prop="sxhmd" />
      <el-table-column label="不存在国家下发工商异常名录(0否1是)" align="center" prop="gjxfgsycml" />
      <el-table-column label="不存在不动产查封(0否1是)" align="center" prop="bdccf" />
      <el-table-column label="是否准入(0否1是)" align="center" prop="sfzr" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:zrjgjgb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:zrjgjgb:remove']"
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

    <!-- 添加或修改个体工商户信贷_主题库_准入监管结果对话框 -->
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
        <el-form-item label="经营年限大于1年(0否1是)" prop="jynx">
          <el-input v-model="form.jynx" placeholder="请输入经营年限大于1年(0否1是)" />
        </el-form-item>
        <el-form-item label="准入年龄_18至60男_55女(0否1是)" prop="zrnl">
          <el-input v-model="form.zrnl" placeholder="请输入准入年龄_18至60男_55女(0否1是)" />
        </el-form-item>
        <el-form-item label="非异地贷款_泉州市内(0否1是)" prop="fyddk">
          <el-input v-model="form.fyddk" placeholder="请输入非异地贷款_泉州市内(0否1是)" />
        </el-form-item>
        <el-form-item label="营业执照状态非吊销_停业_清算_注销(0否1是)" prop="regstate">
          <el-input v-model="form.regstate" placeholder="请输入营业执照状态非吊销_停业_清算_注销(0否1是)" />
        </el-form-item>
        <el-form-item label="不存在重大税收违法案件(0否1是)" prop="zdsffwaj">
          <el-input v-model="form.zdsffwaj" placeholder="请输入不存在重大税收违法案件(0否1是)" />
        </el-form-item>
        <el-form-item label="非失信被执行人(0否1是)" prop="sxbzxr">
          <el-input v-model="form.sxbzxr" placeholder="请输入非失信被执行人(0否1是)" />
        </el-form-item>
        <el-form-item label="不存在失信黑名单(0否1是)" prop="sxhmd">
          <el-input v-model="form.sxhmd" placeholder="请输入不存在失信黑名单(0否1是)" />
        </el-form-item>
        <el-form-item label="不存在国家下发工商异常名录(0否1是)" prop="gjxfgsycml">
          <el-input v-model="form.gjxfgsycml" placeholder="请输入不存在国家下发工商异常名录(0否1是)" />
        </el-form-item>
        <el-form-item label="不存在不动产查封(0否1是)" prop="bdccf">
          <el-input v-model="form.bdccf" placeholder="请输入不存在不动产查封(0否1是)" />
        </el-form-item>
        <el-form-item label="是否准入(0否1是)" prop="sfzr">
          <el-input v-model="form.sfzr" placeholder="请输入是否准入(0否1是)" />
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
import { listZrjgjgb, getZrjgjgb, delZrjgjgb, addZrjgjgb, updateZrjgjgb, exportZrjgjgb } from "@/api/system/zrjgjgb";

export default {
  name: "Zrjgjgb",
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
      // 个体工商户信贷_主题库_准入监管结果表格数据
      zrjgjgbList: [],
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
        jynx: null,
        zrnl: null,
        fyddk: null,
        regstate: null,
        zdsffwaj: null,
        sxbzxr: null,
        sxhmd: null,
        gjxfgsycml: null,
        bdccf: null,
        sfzr: null
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
    /** 查询个体工商户信贷_主题库_准入监管结果列表 */
    getList() {
      this.loading = true;
      listZrjgjgb(this.queryParams).then(response => {
        this.zrjgjgbList = response.rows;
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
        jynx: null,
        zrnl: null,
        fyddk: null,
        regstate: null,
        zdsffwaj: null,
        sxbzxr: null,
        sxhmd: null,
        gjxfgsycml: null,
        bdccf: null,
        sfzr: null
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
      this.title = "添加个体工商户信贷_主题库_准入监管结果";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tyshxydm = row.tyshxydm || this.ids
      getZrjgjgb(tyshxydm).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改个体工商户信贷_主题库_准入监管结果";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tyshxydm != null) {
            updateZrjgjgb(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addZrjgjgb(this.form).then(response => {
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
      this.$confirm('是否确认删除个体工商户信贷_主题库_准入监管结果编号为"' + tyshxydms + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delZrjgjgb(tyshxydms);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有个体工商户信贷_主题库_准入监管结果数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportZrjgjgb(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
