<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="微信昵称" prop="wxNickName">
        <el-input
          v-model="queryParams.wxNickName"
          placeholder="请输入微信昵称"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="微信号" prop="wxAccount">
        <el-input
          v-model="queryParams.wxAccount"
          placeholder="请输入微信号"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="wxPhone">
        <el-input
          v-model="queryParams.wxPhone"
          placeholder="请输入手机号"
          clearable
          size="small"
        />
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
          v-hasPermi="['custom:wxAccount:add']"
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
          v-hasPermi="['custom:wxAccount:edit']"
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
          v-hasPermi="['custom:wxAccount:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:wxAccount:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxAccountList" stripe>
      <el-table-column label="微信昵称" align="center" prop="wxNickName" />
      <el-table-column label="微信号" align="center" prop="wxAccount" />
      <el-table-column label="手机号" align="center" prop="wxPhone" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
            active-text="可接粉"
            inactive-text="不可接粉"
            :value="!scope.row.status"
            @change="handleOnChange($event, scope.row)"
          />
        </template>
      </el-table-column>
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
            v-hasPermi="['custom:wxAccount:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:wxAccount:remove']"
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

    <!-- 添加或修改微信账号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="微信昵称" prop="wxNickName">
          <el-input
            v-model="form.wxNickName"
            maxlength="100"
            placeholder="请输入微信昵称"
          />
        </el-form-item>
        <el-form-item label="微信号" prop="wxAccount">
          <el-input
            v-model="form.wxAccount"
            maxlength="100"
            placeholder="请输入微信号"
          />
        </el-form-item>
        <el-form-item label="手机号" prop="wxPhone">
          <el-input
            v-model="form.wxPhone"
            maxlength="20"
            placeholder="请输入手机号"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            type="textarea"
            :rows="3"
            show-word-limit
            maxlength="100"
            placeholder="请输入备注"
            v-model="form.remark"
          >
          </el-input>
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
  listWxAccount,
  getWxAccount,
  delWxAccount,
  addWxAccount,
  updateWxAccount,
  exportWxAccount,
} from "@/api/custom/wxAccount";

export default {
  name: "WxAccount",
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
      // 微信账号表格数据
      wxAccountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wxNickName: null,
        wxAccount: null,
        wxPhone: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wxNickName: [
          { required: true, trigger: "blur", message: "请输入微信昵称" },
        ],
        wxAccount: [
          { required: true, trigger: "blur", message: "请输入微信号" },
        ],
        wxPhone: [
          {
            required: false,
            trigger: "blur",
            pattern: /^[0-9]{5,11}$/,
            message: "手机号格式不正确",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询微信账号列表 */
    getList() {
      this.loading = true;
      listWxAccount(this.queryParams).then((response) => {
        this.wxAccountList = response.rows;
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
        wxNickName: null,
        wxAccount: null,
        wxPhone: null,
        wxCodeUrl: null,
        remark: null,
        wxType: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        delFlag: null,
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
      this.title = "添加微信账号";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWxAccount(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信账号";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWxAccount(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addWxAccount(this.form).then((response) => {
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
        '是否确认删除微信账号编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delWxAccount(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有微信账号数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportWxAccount(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    handleOnChange(enable, data) {
      updateWxAccount({
        ...data,
        status: enable ? 0 : 1,
      }).then((response) => {
        if (response.code === 200) {
          this.msgSuccess("修改成功");
          this.open = false;
          this.getList();
        }
      });
    },
  },
};
</script>
