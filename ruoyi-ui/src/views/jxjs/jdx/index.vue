<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="入选年份" prop="jdxnf">
        <el-select v-model="queryParams.jdxnf" placeholder="请选择入选年份">
          <el-option
            v-for="dict in nfOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="基地校" prop="jdxmc">
        <el-input
          v-model="queryParams.jdxmc"
          placeholder="请输入基地校名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="学校类别" prop="xxlb">
        <el-input
          v-model="queryParams.xxlb"
          placeholder="请输入学校类别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
      <el-form-item label="学校办别" prop="xxbb">
        <el-select v-model="queryParams.xxbb" placeholder="请选择学校办别">
          <el-option
            v-for="dict in xxbbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="办学类型" prop="bxlx">
        <el-select v-model="queryParams.bxlx" placeholder="请选择办学类型">
          <el-option
            v-for="dict in bxlxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jxjs:jdx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jdx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxjs:jdx:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jdxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="标识" align="center" prop="id" /> -->
      <!-- <el-table-column label="基地校名称" align="center" prop="jdxmc" /> -->
      <el-table-column label="基地校名称" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/jxjs/jdx/jxjsjbxx/data/' + scope.row.id" class="link-type">
            <span>{{ scope.row.jdxmc }}</span>
          </router-link>
        </template>
      </el-table-column>
      <!-- <el-table-column label="学校类别" align="center" prop="xxlb" /> -->
      <el-table-column label="学校办别" align="center" prop="xxbb" :formatter="xxbbFormat" />
      <el-table-column label="办学类型" align="center" prop="bxlx" :formatter="bxlxFormat" />
      <el-table-column label="基地校年份" align="center" prop="jdxnf" :formatter="nfFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jdx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jdx:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-user"
            @click="handleFpjs(scope.row)"
            v-hasPermi="['jxjs:jdx:edit']"
          >分配教师</el-button>
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

    <!-- 添加或修改基地校对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="基地校名称" prop="jdxmc">
          <el-input v-model="form.jdxmc" type="textarea" placeholder="请输入基地校名称" />
        </el-form-item>
        <!-- <el-form-item label="学校类别" prop="xxlb">
          <el-input v-model="form.xxlb" placeholder="请输入学校类别" />
        </el-form-item>-->
        <el-form-item label="学校办别" prop="xxbb">
          <el-select v-model="form.xxbb" placeholder="请选择学校办别">
            <el-option
              v-for="dict in xxbbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="办学类型" prop="bxlx">
          <el-select v-model="form.bxlx" placeholder="请选择办学类型">
            <el-option
              v-for="dict in bxlxOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入选年份" prop="jdxnf">
          <el-select v-model="form.jdxnf" placeholder="请选择入选年份">
            <el-option
              v-for="dict in nfOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改基地校对话框 -->
    <el-dialog title="基地校分配见习教师" :visible.sync="open_fpjs" width="600px" append-to-body>
      <el-table :data="jxjsjbxxList" @selection-change="handleSelectionChangeFpjs">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="录取年份" align="center" prop="lqnf" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm_fpjs">确 定</el-button>
        <el-button @click="cancel_fpjs">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJdx, getJdx, delJdx, addJdx, updateJdx } from "@/api/jxjs/jdx";

import {
  listJxjsjbxxnotjdx,
  getJxjsjbxx,
  updateJxjsJdx,
} from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jdx",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      jdxid: "",
      jsIds: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 基地校表格数据
      jdxList: [],
      jxjsjbxxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      open_fpjs: false,
      //字典
      nfOptions: [],
      xxbbOptions: [],
      bxlxOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        otherid: null,
        jdxmc: null,
        xxlb: null,
        bxlx: null,
        xxbb: null,
        jdxnf: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jdxmc: [
          { required: true, message: "基地校名称不能为空", trigger: "blur" },
        ],
        xxlb: [
          { required: true, message: "学校类别不能为空", trigger: "blur" },
        ],
        bxlx: [
          { required: true, message: "办学类型不能为空", trigger: "blur" },
        ],
        xxbb: [
          { required: true, message: "学校办别不能为空", trigger: "blur" },
        ],
        jdxnf: [
          { required: true, message: "入选年份不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_rxnf").then((response) => {
      this.nfOptions = response.data;
    });
    this.getDicts("sys_dm_xxbb").then((response) => {
      this.xxbbOptions = response.data;
    });
    this.getDicts("sys_dm_bxlx").then((response) => {
      this.bxlxOptions = response.data;
    });
  },
  methods: {
    // 字典翻译
    nfFormat(row, column) {
      return this.selectDictLabel(this.nfOptions, row.jdxnf);
    },
    // 字典翻译
    bxlxFormat(row, column) {
      return this.selectDictLabel(this.bxlxOptions, row.bxlx);
    },
    // 字典翻译
    xxbbFormat(row, column) {
      return this.selectDictLabel(this.xxbbOptions, row.xxbb);
    },
    /** 查询基地校列表 */
    getList() {
      this.loading = true;
      listJdx(this.queryParams).then((response) => {
        this.jdxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询基地校列表 */
    getJxjsList() {
      listJxjsjbxxnotjdx(null).then((response) => {
        this.jxjsjbxxList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel_fpjs() {
      this.open_fpjs = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        otherid: null,
        jdxmc: null,
        xxlb: null,
        bxlx: null,
        xxbb: null,
        jdxnf: null,
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 多选框选中数据
    handleSelectionChangeFpjs(selection) {
      this.jsIds = selection.map((item) => item.id);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加基地校";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJdx(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地校";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJdx(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJdx(this.form).then((response) => {
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
    /** 分配教师提交按钮 */
    submitForm_fpjs() {
      if (this.jsIds == null || this.jsIds.length == 0) {
        this.msgError("请选择要分配的教师");
      } else {
        console.log(this.jsIds[0]);
        updateJxjsJdx(this.jsIds, this.jdxid).then((response) => {
          if (response.code === 200) {
            this.msgSuccess("分配成功");
            this.open_fpjs = false;
          }
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除基地校数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJdx(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 分配教师按钮操作 */
    handleFpjs(row) {
      const ids = row.id || this.ids;
      console.log(ids);
      this.jdxid = ids;
      this.open_fpjs = true;
      this.getJxjsList();
    },
  },
};
</script>
