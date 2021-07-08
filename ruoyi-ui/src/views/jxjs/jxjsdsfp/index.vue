<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="见习教师" prop="jxjsid">
        <el-select
          v-model="queryParams.jxjsid"
          placeholder="请选择见习教师"
          filterable
          size="small"
        >
          <el-option
            v-for="dict in jxjsOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="见习导师" prop="dsid">
        <el-select
          v-model="queryParams.dsid"
          placeholder="请选择见习导师"
          filterable
          size="small"
        >
          <el-option
            v-for="dict in dsjbxxList"
            :key="dict.id"
            :label="dict.jsxm"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任教学段" prop="rjxd">
        <el-select v-model="queryParams.rjxd" placeholder="请选择任教学段">
          <el-option
            v-for="dict in rjxdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任教学科" prop="rjxk">
        <el-select v-model="queryParams.rjxk" placeholder="请选择任教学段">
          <el-option
            v-for="dict in rjxkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jxjs:jxjsdsfp:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jxjsdsfpList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <el-table-column
        label="见习教师"
        align="center"
        prop="tsbzJxjsjbxx.name"
      />
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
      <el-table-column
        label="见习导师"
        align="center"
        prop="dsid"
        :formatter="dsFormat"
      />
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
            v-hasPermi="['jxjs:jxjsdsfp:edit']"
            >分配</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jxjsdsfp:remove']"
            >清空</el-button
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

    <!-- 分配老师对话框 -->
    <el-dialog
      title="分配导师"
      :visible.sync="open_dsfp"
      width="900px"
      append-to-body
    >
      <el-form
        :model="queryParams_ds"
        ref="queryForm_ds"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item label="见习导师" prop="jsxm">
          <el-input
            v-model="queryParams_ds.jsxm"
            placeholder="请输入教师姓名"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item label="任教学段" prop="xd">
          <el-select
            v-model="queryParams_ds.xd"
            placeholder="请选择任教学段"
            clearable
            size="small"
            :disabled="true"
          >
            <el-option
              v-for="dict in rjxdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="任教学科" prop="xk">
          <el-select
            v-model="queryParams_ds.xk"
            placeholder="请选择任教学科"
            clearable
            size="small"
            :disabled="true"
          >
            <el-option
              v-for="dict in rjxkOptions"
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
            @click="handleQuery_dsfp"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery_dsfp"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
      <el-table
        :data="dsjbxxList"
        @selection-change="handleSelectionChangeDsfp"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="导师姓名" align="center" prop="jsxm" />
        <el-table-column
          label="任教学段"
          align="center"
          prop="xd"
          :formatter="rjxdFormatDs"
        />
        <el-table-column
          label="任教学科"
          align="center"
          prop="xk"
          :formatter="rjxkFormatDs"
        />
        <el-table-column
          label="已分配学员数量（人）"
          align="center"
          prop="dsxscount"
        />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm_dsfp">确 定</el-button>
        <el-button @click="cancel_dsfp">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listJxjsdsfp,
  getJxjsdsfp,
  delJxjsdsfp,
  addJxjsdsfp,
  updateJxjsdsfp,
  exportJxjsdsfp,
} from "@/api/jxjs/jxjsdsfp";
import { listDsjbxx, getDsjbxx } from "@/api/jxzxkhgl/dsjbxx";
import { listJxjsjbxx, getJxjsjbxx } from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jxjsdsfp",
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
      // 见习教师分配表格数据
      jxjsdsfpList: [],
      // 任教学段字典
      rjxdOptions: [],
      // 任教学科字典
      rjxkOptions: [],
      // 导师
      dsjbxxList: [],
      // 全部导师列表
      dsAllOptions: [],
      // 见习教师
      jxjsOptions: [],
      // 选择的导师id
      dsidChose: "",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      open_dsfp: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jxjsid: null,
        dsid: null,
        jdxmc: null,
        rjxd: null,
        rjxk: null,
      },
      // 查询导师
      queryParams_ds: {
        xd: null,
        xk: null,
        jsxm: null,
        dsxscount: null,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.rjxdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.rjxkOptions = response.data;
    });
    this.getJxjsList();
    this.getDsList();
    this.getList();
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
    // 导师任教学段字典翻译
    rjxdFormatDs(row, column) {
      return this.selectDictLabel(this.rjxdOptions, row.xd);
    },
    // 导师任教学科字典翻译
    rjxkFormatDs(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.xk);
    },
    // 导师字典翻译
    dsFormat(row, column) {
      var actions = [];
      var datas = this.dsAllOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.dsid) {
          actions.push(datas[key].jsxm);
          return false;
        }
      });
      return actions.join("");
    },
    // 新页面导师字典翻译
    dsFormatNew(row, column) {
      var actions = [];
      var datas = this.dsAllOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.id) {
          actions.push(datas[key].jsxm);
          return false;
        }
      });
      return actions.join("");
    },
    /** 查询见习之星白名单信息列表 */
    getJxjsList() {
      listJxjsjbxx(null).then((response) => {
        //console.log(response);
        this.jxjsOptions = response.rows;
      });
    },
    /** 查询导师基本信息列表 */
    getDsList() {
      listDsjbxx(this.queryParams_ds).then((response) => {
        //console.log(response);
        this.dsjbxxList = response.rows;
      });
    },
    getDsjbxxFa() {
      getDsjbxx(null).then((response) => {
        this.dsAllOptions = response.rows;
      });
    },
    /** 查询见习导师分配列表 */
    getList() {
      this.loading = true;
      listJxjsdsfp(this.queryParams).then((response) => {
        console.log(response);
        this.jxjsdsfpList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel_dsfp() {
      this.open_dsfp = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        jxjsid: null,
        dsid: null,
        createTime: null,
        jdxmc: null,
        rjxd: null,
        rjxk: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 搜索按钮操作 */
    handleQuery_dsfp() {
      this.queryParams.pageNum = 1;
      this.getDsList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 重置按钮操作 */
    resetQuery_dsfp() {
      this.resetForm("queryForm_ds");
      this.handleQuery_dsfp();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 导师选择框
    // 多选框选中数据
    handleSelectionChangeDsfp(selection) {
      this.dsidChose = selection.map((item) => item.id);
      // console.log(this.dsidChose[0]);
    },
    /** 分配教师提交按钮 */
    submitForm_dsfp() {
      if (this.dsidChose == null || this.dsidChose == "") {
        this.msgError("请选择导师");
      } else if (this.dsidChose.length > 1) {
        this.msgError("只能选择一名导师");
      } else {
        //console.log(this.form);
        this.form.dsid = this.dsidChose[0];
        if (this.form.id != null) {
          updateJxjsdsfp(this.form).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("修改分配成功");
              this.open_dsfp = false;
              this.getList();
            }
          });
        } else {
          addJxjsdsfp(this.form).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("新增分配成功");
              this.open_dsfp = false;
              this.getList();
            }
          });
        }
      }
    },
    /** 分配按钮操作 */
    handleUpdate(row) {
      this.reset();
      // 给请求导师的query赋值
      //console.log(row.rjxk);
      if (row.rjxk == null) {
        this.msgError("当前学段学科没有相匹配的导师");
        return;
      }

      this.queryParams_ds.xd = row.rjxd;
      this.queryParams_ds.xk = row.rjxk;
      listDsjbxx(this.queryParams_ds).then((response) => {
        this.dsjbxxList = response.rows;
        if (this.dsjbxxList.length < 1) {
          this.msgError("当前学段学科没有相匹配的导师");
        } else {
          const id = row.id || this.ids;
          if (id.length > 1) {
            this.msgError("只支持单选");
          } else {
            this.reset();
            if (id == "" || id == null) {
              this.getDsList();
              this.form.jxjsid = row.jxjsid;
              this.open_dsfp = true;
            } else {
              getJxjsdsfp(id).then((response) => {
                this.getDsList();
                this.form = response.data;
                this.open_dsfp = true;
              });
            }
          }
        }
      });
    },
    /** 清空按钮操作 */
    handleDelete(row) {
      const ids = row.id;
      if (ids == null || ids == "") {
        this.msgError("未分配导师，不可清空");
        return;
      }
      this.$confirm('是否确认清空编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJxjsdsfp(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("清除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有见习教师导师分配数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportJxjsdsfp(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
