<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="销售" prop="userId">
        <el-select
          v-model="queryParams.userId"
          placeholder="请选择销售"
          filterable
          clearable
          size="small"
        >
          <el-option
            v-for="dict in preSaleIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售组别" prop="saleGroupId">
        <el-select
          v-model="queryParams.saleGroupId"
          placeholder="请选择组别"
          filterable
          clearable
          size="small"
        >
          <el-option
            v-for="dict in saleGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="进粉渠道" prop="accountId">
        <el-select
          v-model="queryParams.accountId"
          filterable
          placeholder="请选择渠道"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in accountIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <!--<el-form-item label="微信号" prop="wechatAccount">
        <el-input
          v-model="queryParams.wechatAccount"
          placeholder="请输入微信号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
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
          v-hasPermi="['custom:wxDistribution:add']"
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
          v-hasPermi="['custom:wxDistribution:edit']"
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
          v-hasPermi="['custom:wxDistribution:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:wxDistribution:export']"
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
      :data="wxDistributionList"
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="销售姓名" align="center" prop="userName" />
      <el-table-column label="微信昵称" align="center" prop="wxNickName" />
      <el-table-column label="微信号" align="center" prop="wxAccount" />
      <el-table-column label="进粉渠道" align="center" prop="account" />
      <el-table-column label="销售组别" align="center" prop="saleGroup" />
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
            v-hasPermi="['custom:wxDistribution:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:wxDistribution:remove']"
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

    <!-- 添加或修改微信分配管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="销售" prop="userId">
          <el-select
            v-model="form.userId"
            placeholder="请选择销售"
            filterable
            clearable
            size="small"
          >
            <el-option
              v-for="dict in preSaleIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="销售组别" prop="saleGroupId">
          <el-select
            v-model="form.saleGroupId"
            placeholder="请选择组别"
            filterable
            clearable
            size="small"
          >
            <el-option
              v-for="dict in saleGroupOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="进粉渠道" prop="accountId">
          <el-select
            v-model="form.accountId"
            filterable
            placeholder="请选择渠道"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in accountIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="微信号" prop="wechatAccount">
          <el-select
            v-model="form.wechatAccount"
            filterable
            placeholder="请选择微信号"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in wxAccountList"
              :key="dict.id"
              :label="dict.wxAccount"
              :value="parseInt(dict.id)"
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
  listWxDistribution,
  getWxDistribution,
  delWxDistribution,
  addWxDistribution,
  updateWxDistribution,
  exportWxDistribution,
} from "@/api/custom/wxDistribution";
import { listWxAccount } from "@/api/custom/wxAccount";
import { mapState } from "vuex";
export default {
  name: "WxDistribution",
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
      // 微信分配管理表格数据
      wxDistributionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        saleGroupId: null,
        accountId: null,
        wechatAccount: null,
        pageFlag: false
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [{ required: true, trigger: "blur", message: "请选择销售" }],
        saleGroupId: [
          { required: true, trigger: "blur", message: "请选择组别" },
        ],
        accountId: [
          { required: true, trigger: "blur", message: "请选择进粉账号" },
        ],
        wechatAccount: [
          { required: true, trigger: "blur", message: "请选择微信号" },
        ],
      },
      //进粉账号列表
      accountIdOptions: [],
      //销售组别列表
      saleGroupOptions: [],
      //微信号列表
      wxAccountList: [],
    };
  },
  created() {
    this.getList();
    this.getDicts("fan_channel").then((response) => {
      this.accountIdOptions = response.data;
    });
    this.getDicts("sale_group").then((response) => {
      this.saleGroupOptions = response.data;
    });
    this.getListWxAccount();
  },
  computed: {
    ...mapState({
      //售前
      preSaleIdOptions: (state) => state.global.preSaleIdOptions.slice(1),
    }),
  },
  methods: {
    /** 查询微信分配管理列表 */
    getList() {
      this.loading = true;
      listWxDistribution(this.queryParams).then((response) => {
        this.wxDistributionList = response.rows;
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
        userId: null,
        saleGroupId: null,
        accountId: null,
        wechatAccount: null,
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
      this.title = "添加微信分配";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWxDistribution(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信分配";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWxDistribution(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addWxDistribution(this.form).then((response) => {
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
        '是否确认删除微信号为"' + row.wxNickName + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delWxDistribution(ids);
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
      this.$confirm("是否确认导出所有微信分配数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportWxDistribution(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    getListWxAccount() {
      listWxAccount(this.queryParams).then((response) => {
        this.wxAccountList = response.rows;
      });
    },
  },
};
</script>
