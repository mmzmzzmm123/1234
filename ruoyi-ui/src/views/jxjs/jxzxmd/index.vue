<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="评选方案" prop="pxfaid">
        <el-select v-model="queryParams.pxfaid" placeholder="请选择方案">
          <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="基地校" prop="jdxid">
        <el-select v-model="queryParams.jdxid" filterable placeholder="请选择基地校">
          <el-option v-for="item in jdxOptions" :key="item.id" :label="item.jdxmc" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择教师" prop="jsid">
        <el-select v-model="queryParams.jsid" filterable placeholder="请选择教师">
          <el-option v-for="item in jsOptions" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="jxzxmdList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
       <el-table-column label="评选方案名称" align="center" prop="pxfamc" />
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <el-table-column label="聘任校" align="center" prop="tsbzJxjsjbxx.prdwmc" />
      <el-table-column label="姓名" align="center" prop="tsbzJxjsjbxx.name" />
      <el-table-column label="任教学科" align="center" prop="tsbzJxjsjbxx.rjxk" />
      <el-table-column label="任教学段" align="center" prop="tsbzJxjsjbxx.rjxd" />
      <el-table-column label="录取年份" align="center" prop="nf" />
      <!-- <el-table-column label="创建人" align="center" prop="createuserid" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['jxjs:jxzxmd:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-postcard"
            @click="handlePostcard(scope.row)"
            v-hasPermi="['jxjs:jxzxmd:query']"
          >证书查看</el-button>
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

    <!-- 添加或修改见习之星名单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body></el-dialog>
  </div>
</template>

<script>
import { listJxzxmd, getJxzxmd } from "@/api/jxjs/jxzxmd";
import { listJxzxpxfa } from "@/api/jxjs/jxzxpxfa";
import { listJxjsjbxx, getJxjsjbxx } from "@/api/jxjs/jxjsjbxx";
import { listJdx } from "@/api/jxjs/jdx";

export default {
  name: "Jxzxmd",
  data() {
    return {
      //默认选中方案id
      defaultFaId: "",
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
      // 见习之星名单表格数据
      jxzxmdList: [],
      //方案
      faOptions: [],
      //教师
      jsOptions: [],
      //基地校
      jdxOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jsid: null,
        nf: null,
        pxfaid: null,
        jdxid: null,
        createuserid: null,
      },
      // 查询参数
      queryParams_fa: {
        fazt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getFaList();
    this.getJdxList();
    this.getJsList();
  },
  methods: {
    async getFaList() {
      this.queryParams_fa.fazt = "1";
      await listJxzxpxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
        this.defaultFaId = response.rows[0].id;
        this.queryParams.pxfaid = this.defaultFaId;

        this.getList();
      });
    },
    getJdxList() {
      listJdx(null).then((response) => {
        this.jdxOptions = response.rows;
      });
    },
    getJsList() {
      listJxjsjbxx(null).then((response) => {
        this.jsOptions = response.rows;
      });
    },
    /** 查询见习之星名单列表 */
    getList() {
      this.loading = true;
      listJxzxmd(this.queryParams).then((response) => {
        this.jxzxmdList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.pxfaid = this.defaultFaId;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },

    /** 详情按钮操作 */
    handleView(row) {
      this.msgSuccess("等待完善中...");
    },
    /** 证书按钮操作 */
    handlePostcard() {
      this.msgSuccess("等待完善中...");
    },
  },
};
</script>
