<template>
  <div class="app-container">
    <el-form :model="form" ref="form" :inline="true" label-width="102px">
      <el-form-item label="教师姓名" prop="jsid">
        <el-input v-model="jsxm" :disabled="true" />
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="qtjskhgcsjList">
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="指标模块"
        align="center"
        prop="tsbzQtjskhzbx.khmk"
        :formatter="khmkFormat"
      />
      <el-table-column
        label="指标项"
        align="center"
        prop="tsbzQtjskhzbx.khnr"
      />
      <el-table-column
        label="提交文件数"
        align="center"
        prop="tsbzQtjskhzbx.tjsl"
      />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="附件" align="center" prop="filename">
        <template slot-scope="scope">
          <router-link
            :to="uploadFileUrl + scope.row.filepath"
            class="link-type"
          >
            <span>{{ scope.row.filename }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column
        label="当前状态"
        align="center"
        prop="substatus"
        :formatter="substatusFormat"
      />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  listQtjskhsh,
  getQtjskhsh,
  delQtjskhsh,
  addQtjskhsh,
  updateQtjskhsh,
  exportQtjskhsh,
} from "@/api/qtjskhgl/qtjskhsh";
import { listQtjskhgcsj } from "@/api/qtjskhgl/qtjskhgcsj";
import { listQtjskhfa } from "@/api/qtjskhgl/qtjskhfa";
import { listXxjbxx, getXxjbxx } from "@/api/qtjs/xxjbxx";

export default {
  name: "Jzxzkhsh",
  data() {
    return {
      //教师姓名
      jsxm: "",
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 群体教师考核过程数据表格数据
      qtjskhgcsjList: [],
      // 提交状态
      substatusOptions: [],
      //指标模块
      khmkOptions: [],
      uploadFileUrl: process.env.VUE_APP_BASE_API,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        zbid: null,
        content: null,
        createuserid: null,
        substatus: null,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    const dqshid = this.$route.params && this.$route.params.id;
    console.log(dqshid);
    if (dqshid != null || dqshid != "") {
      this.getDetail(dqshid);
    } else {
      alert("当前教师未提交数据，无法查看！");
    }
    this.getDicts("sys_dm_khmk").then((response) => {
      this.khmkOptions = response.data;
    });
    this.getDicts("sys_dm_qtjsshgctjzt").then((response) => {
      this.substatusOptions = response.data;
    });
  },
  methods: {
    // 查详情
    async getDetail(val) {
      await getQtjskhsh(val).then((response) => {
        console.log(response);
        this.jsxm = response.data.tsbzJsjbxx.jsxm;
        this.queryParams.faid = response.data.faid;
        this.queryParams.createuserid = response.data.jsid;

        this.getList();
      });
    },
    /** 查询群体教师考核过程数据列表 */
    getList() {
      this.loading = true;
      console.log(this.queryParams);
      listQtjskhgcsj(this.queryParams).then((response) => {
        this.qtjskhgcsjList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 考核模块字典项字典翻译
    khmkFormat(row, column) {
      return this.selectDictLabel(this.khmkOptions, row.tsbzQtjskhzbx.khmk);
    },
    // 提交状态字典项字典翻译
    substatusFormat(row, column) {
      return this.selectDictLabel(this.substatusOptions, row.substatus);
    },
  },
};
</script>
