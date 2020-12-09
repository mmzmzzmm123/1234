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
        <el-select v-model="queryParams.faid" placeholder="请选择方案">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jdcx:edit']"
          >审核</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxjs:jdcx:remove']"
          >退回</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jdcxList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案名称" align="center" prop="faname" />
      <el-table-column label="基地校名称" align="center" prop="jdxmc" />
      <el-table-column label="教师姓名" align="center" prop="jsname" />
      <el-table-column
        label="当前状态"
        align="center"
        prop="dqzt"
        :formatter="dqztFormat"
      />
      <el-table-column
        label="基地校审核意见"
        align="center"
        prop="jdxshzt"
        :formatter="jdxshztFormat"
      />
      <el-table-column
        label="区级审核状态"
        align="center"
        prop="qjshzt"
        :formatter="qjshztFormat"
      />
      <el-table-column label="区级审核建议" align="center" prop="qjshyj" />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jdcx:edit']"
            >审核</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jdcx:remove']"
            >退回</el-button
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

    <!-- 添加或修改基地区级审核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="方案编号" prop="faid">
          <el-select
            v-model="form.faid"
            placeholder="请选择方案"
            :disabled="true"
          >
            <el-option
              v-for="dict in faOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教师名称" prop="jsid">
          <el-select
            v-model="form.jsid"
            placeholder="请选择教师"
            :disabled="true"
          >
            <el-option
              v-for="dict in jsOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区级审核意见" prop="qjshzt">
          <el-select v-model="form.qjshzt" placeholder="请选择区级审核意见">
            <el-option
              v-for="dict in qjshztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区级审核建议" prop="qjshyj">
          <el-input
            v-model="form.qjshyj"
            type="textarea"
            placeholder="请输入审核建议"
          />
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
import {
  listJdcx,
  getJdcx,
  delJdcx,
  addJdcx,
  updateJdcx,
  exportJdcx,
} from "@/api/jxjs/jdcx";

import { listJxzxpxfa } from "@/api/jxjs/jxzxpxfa";
import { listJxjsjbxx, getJxjsjbxx } from "@/api/jxjs/jxjsjbxx";
import { listJdx } from "@/api/jxjs/jdx";

export default {
  name: "Jdcx",
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
      // 基地区级审核表格数据
      jdcxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前状态字典
      dqztOptions: [],
      // 基地校审核意见字典
      jdxshztOptions: [],
      //基地校
      jdxOptions: [],
      // 区级审核状态字典
      qjshztOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      // 教师列表
      jsOptions: [],
      //方案
      faOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        dqzt: null,
        createuserid: null,
        jdxshr: null,
        jdxshzt: null,
        sbly: null,
        qjshr: null,
        qjshzt: null,
        qjshyj: null,
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        cjdrcreateTime: null,
        msqr: null,
        msqrcreateTime: null,
        msjgmnktjxdf: null,
        yjdf: null,
        zhdf2: null,
      },
      // 查询参数
      queryParams_fa: {
        fazt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        faid: [
          { required: true, message: "方案编号不能为空", trigger: "blur" },
        ],
        jsid: [
          { required: true, message: "教师编号不能为空", trigger: "blur" },
        ],
        dqzt: [
          { required: true, message: "当前状态不能为空", trigger: "blur" },
        ],
        jdxshzt: [
          {
            required: true,
            message: "基地校审核状态不能为空",
            trigger: "blur",
          },
        ],
        qjshzt: [
          { required: true, message: "区级审核状态不能为空", trigger: "blur" },
        ],
        qjshyj: [
          { required: true, message: "区级审核意见不能为空", trigger: "blur" },
        ],
        zhdf: [
          { required: true, message: "综合得分不能为空", trigger: "blur" },
        ],
        zhdf2: [
          { required: true, message: "综合得分2不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getFaList();
    this.getJdxList();
    this.getJsList();
    this.getDicts("sys_dm_shzt").then((response) => {
      this.dqztOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.jdxshztOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.qjshztOptions = response.data;
    });
    // this.getDicts("sys_dm_shyj").then((response) => {
    //   this.qjshyjOptions = response.data;
    // });
  },
  methods: {
    getJdxList() {
      listJdx(null).then((response) => {
        this.jdxOptions = response.rows;
      });
    },
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.faOptions;
      Object.keys(datas).map((key) => {
        console.log(row.faid);
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    async getFaList() {
      this.queryParams_fa.fazt = "1";
      await listJxzxpxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
        this.defaultFaId = response.rows[0].id;
        this.queryParams.faid = this.defaultFaId;

        this.getList();
      });
    },
    /** 查询基地区级审核列表 */
    getList() {
      this.loading = true;
      listJdcx(this.queryParams).then((response) => {
        this.jdcxList = response.rows;
        // 过滤未审核和未同意的人员
        this.jdcxList = this.jdcxList.filter(function (item) {
          return item.dqzt == 2 && item.jdxshzt == 1;
        });
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取教师信息
    getJsList() {
      listJxjsjbxx(null).then((response) => {
        this.jsOptions = response.rows;
        //console.log(this.jsOptions);
      });
    },
    // 基地校名称字典翻译
    jdxmcFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.jsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.jsid) {
          actions.push(datas[key].jdxid);
          return false;
        }
      });
      return actions.join("");
    },
    // 当前状态字典翻译
    dqztFormat(row, column) {
      return this.selectDictLabel(this.dqztOptions, row.dqzt);
    },
    // 基地校审核状态字典翻译
    jdxshztFormat(row, column) {
      return this.selectDictLabel(this.jdxshztOptions, row.jdxshzt);
    },
    // 区级审核状态字典翻译
    qjshztFormat(row, column) {
      return this.selectDictLabel(this.qjshztOptions, row.qjshzt);
    },
    // 区级审核意见字典翻译
    qjshyjFormat(row, column) {
      return this.selectDictLabel(this.qjshyjOptions, row.qjshyj);
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
        dqzt: null,
        createuserid: null,
        createTime: null,
        jdxshr: null,
        jdxshzt: null,
        sbly: null,
        qjshr: 2,
        qjshzt: null,
        qjshyj: null,
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        cjdrcreateTime: null,
        msqr: null,
        msqrcreateTime: null,
        msjgmnktjxdf: null,
        yjdf: null,
        zhdf2: null,
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
      this.queryParams.faid = this.defaultFaId;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJdcx(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "见习之星评选区级审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            if (this.form.qjshzt == "0") {
              this.form.dqzt = "8";
            } else if (this.form.qjshzt == "1") {
              this.form.dqzt = "9";
            }
            updateJdcx(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("审核成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            if (this.form.qjshzt == "0") {
              this.form.dqzt = "8";
            } else if (this.form.qjshzt == "1") {
              this.form.dqzt = "9";
            }
            addJdcx(this.form).then((response) => {
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
      this.$confirm("是否确认回退基地区级审核数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJdcx(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("回退成功");
        })
        .catch(function () {});
    },
  },
};
</script>
