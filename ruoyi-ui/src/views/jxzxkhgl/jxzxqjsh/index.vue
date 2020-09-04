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
      <el-form-item label="选择学校" prop="prdwid">
        <el-input
          v-model="queryParams.prdwid"
          placeholder="请选择学校"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="选择教师" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入教师"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="区级意见" prop="qjshyj">
        <el-select v-model="queryParams.qjshyj" placeholder="请选择区级审核意见" clearable size="small">
          <el-option
            v-for="dict in qjshyjOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['jxzxkhgl:jxzxkhsh:edit']"
        >审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxzxkhgl:jxzxkhsh:remove']"
        >退回</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jzxzkhshList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="isShow" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="考核方案" align="center" prop="faid" :formatter="faFormat" />
      <el-table-column label="聘任校" align="center" prop="tsbzJxjsjbxx.prdwmc" />
      <el-table-column label="教师" align="center" prop="tsbzJxjsjbxx.name" >
        <template slot-scope="scope">
          <router-link :to="'/jxzxkhgl/jxzxxxsh/data/' + scope.row.id" class="link-type">
            <span>{{ scope.row.tsbzJxjsjbxx.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="校级审核意见" align="center" prop="xjshyj" :formatter="xjshyjFormat" />
      <el-table-column label="校级审核建议" align="center" prop="xjshjy" :show-overflow-tooltip="true" />
      <el-table-column label="区级审核意见" align="center" prop="qjshyj" :formatter="qjshyjFormat" />
      <el-table-column label="区级审核建议" align="center" prop="qjshjy" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhsh:edit']"
            v-show="isShow(scope.row)"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhsh:remove']"
            v-show="isShow(scope.row)"
          >退回</el-button>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核方案" prop="faid">
          <el-select v-model="form.faid" size="small" :disabled="true">
            <el-option
              v-for="item in jxzxkhfaOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="教师" prop="jsid">
          <el-input v-model="jsxm" :disabled="true" />
          <el-input v-model="form.jsid" v-if="false" />
        </el-form-item>
        <el-form-item label="区级意见" prop="qjshyj">
          <el-select v-model="form.qjshyj" placeholder="请选择区级审核意见">
            <el-option
              v-for="dict in qjshyjOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区级建议" prop="qjshjy">
          <el-input v-model="form.qjshjy" type="textarea" placeholder="请输入区级审核建议" />
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
  listJzxzkhsh,
  getJzxzkhsh,
  backJzxzkhsh,
  addJzxzkhsh,
  updateJzxzkhsh,
} from "@/api/jxzxkhgl/jxzxkhsh";
import { listJxzxkhfa, getJxzxkhfa } from "@/api/jxzxkhgl/jxzxkhfa";

export default {
  name: "Jzxzkhsh",
  data() {
    return {
      //教师姓名
      jsxm: "",
      //默认方案id
      defaultFaid: "",
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
      // 考核审核过程表格数据
      jzxzkhshList: [],
      //考核方案
      jxzxkhfaOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 校级审核意见字典
      xjshyjOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        status: "2",
        xjshr: null,
        xjshyj: null,
        xjshjy: null,
        qjshr: null,
        qjshyj: null,
        qjshjy: null,
        createuseird: null,
      },
      // 查询参数
      queryParams_fa: {
        status: "1",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        qjshyj: [
          { required: true, message: "区级审核意见不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getKhfa();
    this.getList();
    this.getDicts("sys_dm_khshgczt").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.xjshyjOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.qjshyjOptions = response.data;
    });
  },
  methods: {
    //设置是否可用
    isShow(row) {
      if (row.status == "9") {
        return false;
      }
      return true;
    },
    /** 查询考核审核过程列表 */
    getList() {
      this.loading = true;
      listJzxzkhsh(this.queryParams).then((response) => {
        this.jzxzkhshList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //考核方案
    getKhfa() {
      listJxzxkhfa(this.queryParams_fa).then((response) => {
        this.jxzxkhfaOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 校级审核意见字典翻译
    xjshyjFormat(row, column) {
      return this.selectDictLabel(this.xjshyjOptions, row.xjshyj);
    },
    // 区级审核意见字典翻译
    qjshyjFormat(row, column) {
      return this.selectDictLabel(this.qjshyjOptions, row.qjshyj);
    },
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.jxzxkhfaOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
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
        status: null,
        xjshr: null,
        xjshyj: null,
        xjshjy: null,
        qjshr: null,
        qjshyj: null,
        qjshjy: null,
        createuseird: null,
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJzxzkhsh(id).then((response) => {
        this.jsxm = response.data.tsbzJxjsjbxx.name;
        this.form = response.data;
        this.open = true;
        this.title = "区级考核审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJzxzkhsh(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("审核成功");
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
      this.$confirm("是否确认该教师考核数据回退到聘任校?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return backJzxzkhsh(ids, "1");
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
