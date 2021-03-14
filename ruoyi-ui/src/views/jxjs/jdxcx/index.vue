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
          type="primary"
          icon="el-icon-user"
          size="mini"
          @click="handleAddNew"
          v-hasPermi="['jxjs:jdcx:add']"
          >选取名单</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jdcx:edit']"
          >修改</el-button
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
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="handleCheck"
          v-hasPermi="['jxjs:jdcx:edit']"
          >提交</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>
    <!-- //原来代码 -->
    <el-table
      v-loading="loading"
      :data="jdcxList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
        align="center"
        :selectable="isShow"
      />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="方案名称"
        align="center"
        prop="faid"
        :formatter="faFormat"
      >
        <template slot-scope="scope">
          <router-link
            :to="'/jxjs/jxzxpxfa/data/' + scope.row.faid"
            class="link-type"
          >
            <span>{{ faFormat(scope.row.faid) }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column
        label="教师姓名"
        align="center"
        prop="jsid"
        :formatter="jsFormat"
      />
      <el-table-column
        label="当前状态"
        align="center"
        prop="dqzt"
        :formatter="dqztFormat"
      />
      <el-table-column
        label="基地校意见"
        align="center"
        prop="jdxshzt"
        :formatter="jdxshztFormat"
      />
      <el-table-column label="上报理由" align="center" prop="sbly" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="210px"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleCheck(scope.row)"
            v-hasPermi="['jxjs:jdcx:edit']"
            v-show="isShow(scope.row)"
            >提交</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jdcx:edit']"
            v-show="isShow(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jdcx:remove']"
            v-show="isShow(scope.row)"
            >删除</el-button
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

    <!-- //添加代码 -->
    <!-- 添加或修改基地校对话框 -->
    <el-dialog
      title="选取名单"
      :visible.sync="open_jscx"
      width="900px"
      append-to-body
    >
      <el-form
        :model="queryParams_jscx"
        ref="queryForm_jscx"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item label="教师姓名" prop="name">
          <el-input
            v-model="queryParams_jscx.name"
            placeholder="请输入教师姓名"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="cyan"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery_jscx"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery_jscx"
            >重置</el-button
          >
          <!-- <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            >确 定</el-button
          > -->
        </el-form-item>
      </el-form>

      <!-- <el-table v-loading="loading" :data="jdcxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="isShow" /> -->

      <el-table :data="jxjsjbxxList" @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55"
          align="center"
          :selectable="isShow"
        />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column
          label="性别"
          align="center"
          prop="xb"
          :formatter="xbFormat"
        />
        <el-table-column label="聘任单位名称" align="center" prop="prdwmc" />
        <el-table-column
          label="任教学段"
          align="center"
          prop="rjxd"
          :formatter="rjxdFormat"
        />
        <el-table-column
          label="任教学科"
          align="center"
          prop="rjxk"
          :formatter="rjxkFormat"
        />
        <el-table-column label="录取年份" align="center" prop="lqnf" />
      </el-table>
      
      <pagination
        v-show="totalJscx > 0"
        :total="totalJscx"
        :page.sync="queryParams_jscx.pageNum"
        :limit.sync="queryParams_jscx.pageSize"
        @pagination="getJxjsList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleCheck">确 定</el-button>
        <el-button @click="cancel_jscx">取 消</el-button>
      </div>
    </el-dialog>

    

    <!-- 添加或修改基地区级审核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="选择教师" prop="dqzt">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
            :disabled="isable"
            >全选</el-checkbox
          >
          <div style="margin: 15px 0"></div>
          <el-checkbox-group
            v-model="checkedJss"
            @change="handlecheckedJssChange"
          >
            <el-checkbox
              v-for="js in jss"
              :label="js.id"
              :key="js.id"
              :disabled="isable"
              >{{ js.name }}</el-checkbox
            >
          </el-checkbox-group>
          <el-input v-model="form.dqzt" v-if="false" />
        </el-form-item>
        <el-form-item label="上报理由" prop="sbly">
          <el-input
            v-model="form.sbly"
            type="textarea"
            placeholder="请输入上报理由"
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
  checkJdcx,
} from "@/api/jxjs/jdcx";

import { listJxzxpxfa } from "@/api/jxjs/jxzxpxfa";

import {
  listJxjsjbxx,
  listJxjsjbxxnotjdcx,
  listJxjsjbxxnotjdx,
  getJxjsjbxx,
} from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jdxcx",
  data() {
    return {
      //默认选中方案id
      defaultFaId: "",
      isable: false,
      isCheck: true,
      checkAll: false,
      checkedJss: [],
      jss: [],
      isIndeterminate: false,
      // 是否显示弹出层
      open_jscx: false,
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
      totalJscx: 0,
      // 基地区级审核表格数据
      jdcxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前状态字典
      dqztOptions: [],
      // 基地校审核状态字典
      jdxshztOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      //方案
      faOptions: [],
      //教师
      jsOptions: [],
      // 查询教师列表
      jxjsjbxxList: [],
      // 性别字典
      xbOptions: [],
      // 任教学年学科
      rjxdOptions: [],
      rjxkOptions: [],
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
      // 教师查询参数
      queryParams_jscx: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        xb: null,
        prdwid: null,
        prdwmc: null,
        jdxid: null,
        rjxd: null,
        rjxk: null,
      },
      // 查询参数
      queryParams_fa: {
        fazt: "1",
      },
      // 查询参数
      queryParams_js: {
        faid: null,
        nf: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        faid: [{ required: true, message: "方案不能为空", trigger: "blur" }],
        dqzt: [
          { required: true, message: "请至少选择一个教师", trigger: "blur" },
        ],
        sbly: [
          { required: true, message: "上报理由不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getFaList();
    this.getJsList();
    this.getJxjsList();
    this.getDicts("sys_dm_shzt").then((response) => {
      this.dqztOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.jdxshztOptions = response.data;
    });
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.rjxdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.rjxkOptions = response.data;
    });
  },
  methods: {
    // 任教学段字典翻译
    rjxdFormat(row, column) {
      return this.selectDictLabel(this.rjxdOptions, row.rjxd);
    },
    // 任教学科字典翻译
    rjxkFormat(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.rjxk);
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    /** 重置按钮操作 */
    resetQuery_jscx() {
      this.resetForm("queryForm_jscx");
      this.handleQuery_jscx();
    },
    // 取消按钮
    cancel_jscx() {
      this.open_jscx = false;
    },
    isShow(row) {
      //console.log(row.dqzt);
      if (row.dqzt == "2" || row.dqzt == "9" || row.dqzt == "8") {
        return false;
      } else {
        return true;
      }
    },
    // 字典翻译
    faFormat(val) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.faOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + val) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 字典翻译
    jsFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.jsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.jsid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    async getFaList() {
      await listJxzxpxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
        this.defaultFaId = response.rows[0].id;
        this.queryParams.faid = this.defaultFaId;

        this.queryParams_js.faid = this.defaultFaId;
        this.queryParams_js.nf = response.rows[0].nf;
        this.getList();
      });
    },
    getJsList() {
      listJxjsjbxx(null).then((response) => {
        this.jsOptions = response.rows;
      });
    },
    // /** 查询基地校列表 */
    // getJxjsList() {
    //   listJxjsjbxxnotjdx(this.queryParams_jscx).then((response) => {
    //     this.jxjsjbxxList = response.rows;
    //   });
    // },
    /** 搜索按钮操作 */
    handleQuery_jscx() {
      this.queryParams.pageNum = 1;
      // this.getJxjsList();
    },
    /** 查询基地区级审核列表 */
    getList() {
      this.loading = true;
      listJdcx(this.queryParams).then((response) => {
        this.jdcxList = response.rows;
        console.log(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },
    // 当前状态字典翻译
    dqztFormat(row, column) {
      return this.selectDictLabel(this.dqztOptions, row.dqzt);
    },
    // 基地校审核状态字典翻译
    jdxshztFormat(row, column) {
      return this.selectDictLabel(this.jdxshztOptions, row.jdxshzt);
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
      };
      this.resetForm("form");

      this.form.faid = this.defaultFaId;
      this.checkedJss = [];
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
    /** 新增按钮操作 */
    handleAdd(row) {
      const id = row.id;
      this.reset();
      this.isable = false;
      this.open = true;
      this.title = "基地校初选";
      listJxjsjbxxnotjdcx(this.queryParams_js).then((response) => {
        this.jss = response.rows;
      });
    },
    /** 查询基地区级审核列表 */
    getJxjsList() {
      listJxjsjbxxnotjdcx(this.queryParams_js).then((response) => {
        this.loading = true;
        this.jxjsjbxxList = response.rows;
        this.totalJscx = response.total;
        this.loading = false;
      });
    },
    /** 新增选择s教师按钮 */
    handleAddNew() {
      this.reset();
      this.loading = true;
      this.isable = false;
      this.open_jscx = true;
      this.title = "基地校初选";
      this.getJxjsList();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isable = true;
      const id = row.id || this.ids;
      getJdcx(id).then((response) => {
        this.form = response.data;
        listJxjsjbxx(null).then((response) => {
          this.jss = response.rows;
        });
        this.checkedJss.push(response.data.jsid);
        this.open = true;
        this.title = "修改基地校初选信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJdcx(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
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
      this.$confirm("是否确认删除基地校初级审核数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJdcx(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 提交按钮操作 */
    handleCheck(row) {
      const ids = row.id || this.ids;
      this.$confirm("确认提交基地校初级审核数据项?提交后数据不能维护", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return checkJdcx(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("提交成功");
        })
        .catch(function () {});
    },
    handleCheckAllChange(val) {
      // this.checkedJss = val ? this.jss : [];
      // this.isIndeterminate = false;
      this.checkedJss = [];
      this.jss.forEach((item) => {
        //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
        this.checkedJss.push(item.id);
      });
      this.checkedJss = val ? this.checkedJss : []; //三元表达式，如果val的值为true，那么就把当前默认选中的值赋值给自身，这样页面页面上所有的元素就都选中了。如果为false，就是取消全选
      this.isIndeterminate = false; //官网说这是个样式控制，是来控制，什么时候半选的，要不要都无所谓，看你需求

      //console.log(this.checkedJss);
      var cids = "";
      this.checkedJss.forEach((item) => {
        //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
        cids = cids + item + ",";
      });
      //console.log(cids);
      this.form.dqzt = cids;
    },
    handlecheckedJssChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.jss.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.jss.length;

      console.log(this.isIndeterminate);
      var cids = "";
      this.checkedJss.forEach((item) => {
        //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
        cids = cids + item + ",";
      });
      //console.log(cids);
      this.form.dqzt = cids;
    },
  },
};
</script>
