<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="见习之星" prop="jxjsid">
        <el-select
          v-model="queryParams.jxjsid"
          placeholder="请选择见习之星"
          filterable
          size="small"
        >
          <el-option
            v-for="dict in jxzxOptions"
            :key="dict.jsid"
            :label="dict.tsbzJxjsjbxx.name"
            :value="dict.jsid"
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
            v-for="dict in dsOptions"
            :key="dict.id"
            :label="dict.jsxm"
            :value="dict.id"
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jxzxkhgl:jxzxdsfp:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxzxkhgl:jxzxdsfp:edit']"
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
          v-hasPermi="['jxzxkhgl:jxzxdsfp:remove']"
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
      :data="jxzxdsfpList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column
        label="见习之星"
        align="center"
        prop="jxjsid"
        :formatter="jxzxFormat"
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
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxdsfp:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxdsfp:remove']"
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

    <!-- 添加或修改见习导师分配对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="见习之星" prop="jxjsid">
          <el-select
            v-model="form.jxjsid"
            placeholder="请选择见习之星"
            filterable
            size="small"
          >
            <el-option
              v-for="dict in jxzxOptions"
              :key="dict.jsid"
              :label="dict.tsbzJxjsjbxx.name"
              :value="dict.jsid"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="见习导师" prop="dsid">
          <el-select
            v-model="form.dsid"
            placeholder="请选择见习导师"
            filterable
            size="small"
          >
            <el-option
              v-for="dict in dsOptions"
              :key="dict.id"
              :label="dict.jsxm"
              :value="dict.id"
            />
          </el-select>
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
  listJxzxdsfp,
  getJxzxdsfp,
  delJxzxdsfp,
  addJxzxdsfp,
  updateJxzxdsfp,
} from "@/api/jxzxkhgl/jxzxdsfp";
import { listDsjbxx } from "@/api/jxzxkhgl/dsjbxx";
import { listJxzxmd, getJxzxmd } from "@/api/jxjs/jxzxmd";

export default {
  name: "Jxzxdsfp",
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
      // 见习导师分配表格数据
      jxzxdsfpList: [],
      //导师
      dsOptions: [],
      //导师
      jxzxOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jxjsid: null,
        dsid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jxjsid: [
          { required: true, message: "见习之星不能为空", trigger: "blur" },
        ],
        dsid: [{ required: true, message: "导师不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getJxzxList();
    this.getDsList();
    this.getList();
  },
  methods: {
    // 见习教师字典翻译
    jxzxFormat(row, column) {
      var actions = [];
      var datas = this.jxzxOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].jsid == "" + row.jxjsid) {
          actions.push(datas[key].tsbzJxjsjbxx.name);
          return false;
        }
      });
      return actions.join("");
    },
    // 导师字典翻译
    dsFormat(row, column) {
      var actions = [];
      var datas = this.dsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.dsid) {
          actions.push(datas[key].jsxm);
          return false;
        }
      });
      return actions.join("");
    },
    /** 查询导师基本信息列表 */
    getJxzxList() {
      listJxzxmd(null).then((response) => {
        // console.log(response.rows);
        this.jxzxOptions = response.rows;
      });
    },
    /** 查询导师基本信息列表 */
    getDsList() {
      listDsjbxx(null).then((response) => {
        this.dsOptions = response.rows;
      });
    },
    /** 查询见习导师分配列表 */
    getList() {
      this.loading = true;
      listJxzxdsfp(this.queryParams).then((response) => {
        this.jxzxdsfpList = response.rows;
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
        jxjsid: null,
        dsid: null,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加见习导师分配";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJxzxdsfp(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改见习导师分配";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJxzxdsfp(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxzxdsfp(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除见习导师分配编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJxzxdsfp(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
