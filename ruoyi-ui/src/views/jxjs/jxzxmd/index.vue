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
          <el-option
            v-for="dict in faOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="基地校" prop="jdxid">
        <el-select
          v-model="queryParams.jdxid"
          filterable
          placeholder="请选择基地校"
        >
          <el-option
            v-for="item in jdxOptions"
            :key="item.id"
            :label="item.jdxmc"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择教师" prop="jsid">
        <el-select
          v-model="queryParams.jsid"
          filterable
          placeholder="请选择教师"
        >
          <el-option
            v-for="item in jsOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
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

    <el-table
      v-loading="loading"
      :data="jxzxmdList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="评选方案名称" align="center" prop="pxfamc" />
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <el-table-column
        label="聘任校"
        align="center"
        prop="tsbzJxjsjbxx.prdwmc"
      />
      <el-table-column label="姓名" align="center" prop="tsbzJxjsjbxx.name" />
      <el-table-column
        label="任教学科"
        align="center"
        prop="tsbzJxjsjbxx.rjxk"
        :formatter="rjxkFormat"
      />
      <el-table-column
        label="任教学段"
        align="center"
        prop="tsbzJxjsjbxx.rjxd"
        :formatter="rjxdFormat"
      />
      <el-table-column label="录取年份" align="center" prop="nf" />
      <!-- <el-table-column label="创建人" align="center" prop="createuserid" /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="180px"
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

    <!-- 查看见习之星名单详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="880px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="教师姓名" prop="name">
              <el-input v-model="form.name" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进修编号" prop="jxbh">
              <el-input v-model="form.jxbh" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="xb">
              <el-select v-model="form.xb" :disabled="true">
                <el-option
                  v-for="dict in xbOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="csrq">
              <el-date-picker
                clearable
                size="small"
                style="width: 200px"
                v-model="form.csrq"
                type="date"
                value-format="yyyy-MM-dd"
                :disabled="true"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电子邮件" prop="email">
              <el-input v-model="form.email" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" :disabled="true" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编" prop="yzbm">
              <el-input v-model="form.yzbm" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="政治面貌" prop="zzmm">
              <el-select v-model="form.zzmm" :disabled="true">
                <el-option
                  v-for="dict in zzmmOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="民族" prop="mz">
              <el-select v-model="form.mz" :disabled="true">
                <el-option
                  v-for="dict in mzOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="聘任单位" prop="prdwid">
              <el-input v-model="form.prdwid" placeholder="请输入聘任单位" />
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="聘任单位" prop="prdwmc">
              <el-input v-model="form.prdwmc" :disabled="true" />
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="基地校" prop="jdxid">
              <el-input v-model="form.jdxid" disabled="true" />
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="任教学段" prop="rjxd">
              <el-select v-model="form.rjxd" :disabled="true">
                <el-option
                  v-for="dict in rjxdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任教学科" prop="rjxk">
              <el-select v-model="form.rjxk" :disabled="true">
                <el-option
                  v-for="dict in rjxkOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任教年级" prop="rjnj">
              <el-select v-model="form.rjnj" :disabled="true">
                <el-option
                  v-for="dict in rjnjOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="毕业院校" prop="byyx">
              <el-input v-model="form.byyx" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学历" prop="xl">
              <el-select v-model="form.xl" :disabled="true">
                <el-option
                  v-for="dict in xlOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学位" prop="xw">
              <el-select v-model="form.xw" :disabled="true">
                <el-option
                  v-for="dict in xwOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否师范生" prop="sfsfs">
              <el-select v-model="form.sfsfs" :disabled="true">
                <el-option
                  v-for="dict in sfsfsOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="录取年份" prop="lqnf">
              <el-select v-model="form.lqnf" :disabled="true">
                <el-option
                  v-for="dict in lqnfOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      xbOptions: [],
      // 政治面貌字典
      zzmmOptions: [],
      // 民族字典
      mzOptions: [],
      // 任教学段字典
      rjxdOptions: [],
      // 任教学科字典
      rjxkOptions: [],
      // 任教年级字典
      rjnjOptions: [],
      // 学历字典
      xlOptions: [],
      // 学位字典
      xwOptions: [],
      // 是否师范生字典
      sfsfsOptions: [],
      // 录取年份字典
      lqnfOptions: [],
      //方案
      faOptions: [],
      //教师
      jsOptions: [],
      //基地校
      jdxOptions: [],
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

    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_zzmm").then((response) => {
      this.zzmmOptions = response.data;
    });
    this.getDicts("sys_dm_mz").then((response) => {
      this.mzOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.rjxdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.rjxkOptions = response.data;
    });
    this.getDicts("sys_dm_rjnj").then((response) => {
      this.rjnjOptions = response.data;
    });
    this.getDicts("sys_dm_xl").then((response) => {
      this.xlOptions = response.data;
    });
    this.getDicts("sys_dm_xw").then((response) => {
      this.xwOptions = response.data;
    });
    this.getDicts("sys_dm_sfsfs").then((response) => {
      this.sfsfsOptions = response.data;
    });
    this.getDicts("sys_dm_rxnf").then((response) => {
      this.lqnfOptions = response.data;
    });
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
    // 任教学段字典翻译
    rjxdFormat(row, column) {
      return this.selectDictLabel(this.rjxdOptions, row.tsbzJxjsjbxx.rjxd);
    },
    // 任教学科字典翻译
    rjxkFormat(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.tsbzJxjsjbxx.rjxk);
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
      const id = row.jsid;
      getJxjsjbxx(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "见习教师信息详情";
      });
    },
    /** 证书按钮操作 */
    handlePostcard(row) {
      // console.log(row.id);
      const id = row.id;
      this.$router.push({ path: "/zm/jxjs/jxzxzm/" + id });
    },
  },
};
</script>
