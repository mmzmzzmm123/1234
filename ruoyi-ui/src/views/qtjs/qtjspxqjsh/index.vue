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
      <el-form-item label="教师姓名" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入教师姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="区级意见" prop="qjshyj">
        <el-select
          v-model="queryParams.qjshyj"
          placeholder="请选择区级审核意见"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in qjshyjOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
          v-hasPermi="['qtjs:qtjspxsh:edit']"
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
          v-hasPermi="['qtjs:qtjspxsh:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="qtjspxshList"
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
      <el-table-column label="教师" align="center" prop="tsbzJsjbxx.jsxm">
        <template slot-scope="scope">
          <span>{{ scope.row.tsbzJsjbxx.jsxm }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="状态"
        align="center"
        prop="status"
        :formatter="statusFormat"
      />
      <!-- <el-table-column label="校级审核人" align="center" prop="xjshr" /> -->
      <el-table-column
        label="校级审核意见"
        align="center"
        prop="xjshyj"
        :formatter="xjshyjFormat"
      />
      <el-table-column label="校级审核建议" align="center" prop="xjshjy" />
      <!-- <el-table-column label="区级审核人" align="center" prop="qjshr" /> -->
      <el-table-column label="区级审核意见" align="center" prop="qjshyj"  :formatter="qjshyjFormat" />
      <el-table-column label="区级审核建议" align="center" prop="qjshjy" /> 
      <!-- <el-table-column label="创建人" align="center" prop="createuseird" />-->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjs:qtjspxsh:edit']"
            >审核</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjs:qtjspxsh:remove']"
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

    <!-- 添加或修改群体教师评选审核过程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="考核方案" prop="faid">
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
        <el-form-item label="教师" prop="jsid">
          <el-input v-model="jsxm" :disabled="true" />
          <el-input v-model="form.jsid" v-if="false" />
        </el-form-item>
        <el-form-item label="校级审核意见" prop="xjshyj">
          <el-select v-model="form.xjshyj" placeholder="请选择校级审核意见">
            <el-option
              v-for="dict in xjshyjOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="校级审核建议" prop="xjshjy">
          <el-input
            v-model="form.xjshjy"
            type="textarea"
            placeholder="请输入内容"
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
  listQtjspxsh,
  getQtjspxsh,
  delQtjspxsh,
  updateQtjspxsh,
} from "@/api/qtjs/qtjspxsh";
import { listQtjspxfa, getQtjspxfa } from "@/api/qtjs/qtjspxfa";

export default {
  name: "Qtjspxshxx",
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
      // 群体教师评选审核过程表格数据
      qtjspxshList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //方案
      faOptions: [],
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
      rules: {},
    };
  },
  created() {
    this.getPxFaList();
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
    // 获取方案信息
    async getPxFaList() {
      await listQtjspxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;
      });
      this.getList();
    },
    /** 查询群体教师评选审核过程列表 */
    getList() {
      this.loading = true;
      listQtjspxsh(this.queryParams).then((response) => {
        this.qtjspxshList = response.rows;
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
        status: "0",
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
      if (id == null || id == "") {
        return this.msgError("当前教师未提交数据，不允许审核！");
      }
      getQtjspxsh(id).then((response) => {
        this.jsxm = response.data.tsbzJsjbxx.jsxm;
        this.form = response.data;
        this.open = true;
        this.title = "群体教师评选审核过程";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjspxsh(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
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
      this.$confirm("是否确认退回群体教师评选审核过程数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delQtjspxsh(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("退回成功");
        })
        .catch(function () {});
    },
  },
};
</script>