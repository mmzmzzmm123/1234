<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="评选方案" prop="faid">
        <el-select v-model="queryParams.faid" size="small">
          <el-option
            v-for="dict in faOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择教师" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入教师编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="评选类型" prop="pxlx">
        <el-input
          v-model="queryParams.pxlx"
          placeholder="请输入评选类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="年份" prop="nf">
        <el-input
          v-model="queryParams.nf"
          placeholder="请输入年份"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['qtjs:qtjspxjg:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjs:qtjspxjg:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjs:qtjspxjg:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['qtjs:qtjspxjg:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table
      v-loading="loading"
      :data="qtjspxjgList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="评选方案"
        align="center"
        prop="faid"
        :formatter="faFormat"
      />
      <el-table-column label="教师姓名" align="center" prop="tsbzJsjbxx.jsxm" />
      <el-table-column label="评选类型" align="center" prop="pxlx" />
      <el-table-column label="年份" align="center" prop="nf" />
      <!-- <el-table-column label="创建人" align="center" prop="createuserid" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['jxjs:jxzxmd:query']"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-postcard"
            @click="handlePostcard(scope.row)"
            v-hasPermi="['jxjs:jxzxmd:query']"
            >证书查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改群体教师评选结果对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listQtjspxjg,
  getQtjspxjg,
  delQtjspxjg,
  addQtjspxjg,
  updateQtjspxjg,
  exportQtjspxjg,
} from "@/api/qtjs/qtjspxjg";
import { listQtjspxfa, getQtjspxfa } from "@/api/qtjs/qtjspxfa";

export default {
  name: "Qtjspxjg",
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
      // 群体教师评选结果表格数据
      qtjspxjgList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //方案
      faOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        pxlx: null,
        nf: null,
        createuserid: null,
      },
      // 查询参数
      queryParams_fa: {
        status: "1",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getPxFaList();
  },
  methods: {
    // 方案字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
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
    // 获取方案信息
    async getPxFaList() {
      await listQtjspxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;
      });
      this.getList();
    },
    /** 查询群体教师评选结果列表 */
    getList() {
      this.loading = true;
      listQtjspxjg(this.queryParams).then((response) => {
        this.qtjspxjgList = response.rows;
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
        faid: null,
        jsid: null,
        pxlx: null,
        nf: null,
        createuserid: null,
        createTime: null,
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
      this.queryParams.faid = this.defaultFaid;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有群体教师评选结果数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportQtjspxjg(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>