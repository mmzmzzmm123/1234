<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="用户ID" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入平台唯一ID" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="用户名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入用户名" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入电话" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="所在城市" prop="city">
        <el-input v-model="queryParams.city" placeholder="请输入所在城市" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="类型：" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择用户类型：" clearable size="small">
          <el-option v-for="dict in dict.type.carpool_user_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['carpool:blacklist:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['carpool:blacklist:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="blacklistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--      <el-table-column label="autoID" align="center" prop="id" />-->
      <el-table-column label="平台唯一ID" align="center" prop="userId" />
      <el-table-column label="用户名" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="年龄" align="center" prop="age" />
      <!-- <el-table-column label="所在城市" align="center" prop="city" /> -->
      <el-table-column label="用户类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.carpool_user_type" :value="scope.row.type" />
        </template>
      </el-table-column>
      <!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['carpool:blacklist:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改黑名单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="所在城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入所在城市" />
        </el-form-item>
        <el-form-item label="用户类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择用户类型：">
            <el-option v-for="dict in dict.type.carpool_user_type" :key="dict.value" :label="dict.label" :value="dict.value" />
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
  listBlacklist,
  getBlacklist,
  delBlacklist,
  addBlacklist,
  updateBlacklist,
} from "@/api/carpool/blacklist";

export default {
  name: "Blacklist",
  dicts: ["carpool_user_type", "sys_user_sex"],
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
      // 黑名单表格数据
      blacklistList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        name: null,
        phone: null,
        age: null,
        city: null,
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询黑名单列表 */
    getList() {
      this.loading = true;
      listBlacklist(this.queryParams).then((response) => {
        this.blacklistList = response.rows;
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
        userId: null,
        name: null,
        phone: null,
        age: null,
        city: null,
        type: null,
        remark: null,
        createTime: null,
        updateTime: null,
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
      this.ids = selection.map((item) => item.userId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加黑名单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getBlacklist(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改黑名单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateBlacklist(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBlacklist(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.userId || this.ids;
      this.$modal
        .confirm('是否确认删除黑名单编号为"' + ids + '"的数据项？')
        .then(function () {
          return delBlacklist(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "carpool/blacklist/export",
        {
          ...this.queryParams,
        },
        `blacklist_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
