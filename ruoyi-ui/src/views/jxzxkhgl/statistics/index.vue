<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="考核方案" prop="faid">
        <el-select v-model="queryParams.faid" size="small">
          <el-option
            v-for="item in jxzxkhfaOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="基地学校" prop="jdxid">
        <el-select v-model="queryParams.jdxid" size="small">
          <el-option
            v-for="item in jdxOptions"
            :key="item.id"
            :label="item.jdxmc"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="见习教师" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入教师"
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

    <el-table v-loading="loading" :data="jzxzkhjdList">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="基地校" align="center" prop="jdxmc"/>
      <el-table-column label="聘任校" align="center" prop="tsbzJxjsjbxx.prdwmc" />
      <el-table-column label="姓名" align="center" prop="tsbzJxjsjbxx.name" />
      <el-table-column label="任教学科" align="center" prop="tsbzJxjsjbxx.rjxk" />
      <el-table-column label="任教学段" align="center" prop="tsbzJxjsjbxx.rjxd" />
      <el-table-column prop="bfb" label="完成进度">
        <template slot-scope="scope">
          <el-progress
            :text-inside="true"
            :stroke-width="24"
            :percentage="scope.row.bfb*100"
            status="success"
          ></el-progress>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['jxzxkhgl:statistics:query']"
          >详情</el-button>
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

    <!-- 添加或修改考核审核过程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJxzxKhjd, getJxzxKhjd } from "@/api/jxzxkhgl/statistics";
import { listJxzxkhfa, getJxzxkhfa } from "@/api/jxzxkhgl/jxzxkhfa";
import { listJdx } from "@/api/jxjs/jdx";

export default {
  name: "Jzxzkhjd",
  data() {
    return {
      //默认方案id
      defaultFaid: "",
      //默认方案年份
      defaultNf: "",
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
      // 考核进度表格数据
      jzxzkhjdList: [],
      //考核方案
      jxzxkhfaOptions: [],
      //基地校列表
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
        faid: null,
        nf: null,
        name: null,
      },
      // 查询参数
      queryParams_fa: {
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getJdxList();
    this.getKhfa();
  },
  methods: {
    //获取基地校列表
    getJdxList() {
      listJdx(null).then((response) => {
        this.jdxOptions = response.rows;
      });
    },
    /** 查询考核审核过程列表 */
    getList() {
      this.loading = true;
      listJxzxKhjd(this.queryParams).then((response) => {
        console.log(response.rows);
        this.jzxzkhjdList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //考核方案
    async getKhfa() {
      await listJxzxkhfa(this.queryParams_fa).then((response) => {
        this.jxzxkhfaOptions = response.rows;
        console.log(response.rows);
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;
        this.defaultNf = response.rows[0].khnf;
        this.queryParams.nf = this.defaultNf;

        this.getList();
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
      this.queryParams.faid = this.defaultFaid;
      this.queryParams.nf = this.defaultNf;
      this.handleQuery();
    },
  },
};
</script>
