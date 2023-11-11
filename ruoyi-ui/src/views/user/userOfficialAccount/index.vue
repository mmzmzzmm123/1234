<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="openId" prop="openId">
        <el-input
          v-model="queryParams.openId"
          placeholder="请输入openId"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="unionId" prop="unionId">
        <el-input
          v-model="queryParams.unionId"
          placeholder="请输入unionId"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订阅场景" prop="subscribeScene">
        <el-select v-model="queryParams.subscribeScene" placeholder="请选择订阅场景" clearable>
          <el-option
            v-for="dict in dict.type.subscribe_scene"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['user:userOfficialAccount:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['user:userOfficialAccount:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-position"
          size="mini"
          @click="pullNewUserListCli"
          v-hasPermi="['user:userOfficialAccount:add']"
        >拉取最新用户列表
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-magic-stick"
          size="mini"
          @click="updateNewUserInfoCli"
          v-hasPermi="['user:userOfficialAccount:edit']"
        >批量更新用户信息
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userOfficialAccountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id" :show-overflow-tooltip="true"/>
      <el-table-column label="组标识" align="center" prop="groupId" :show-overflow-tooltip="true"/>
      <el-table-column label="openId" align="center" prop="openId" :show-overflow-tooltip="true"/>
      <el-table-column label="unionId" align="center" prop="unionId" :show-overflow-tooltip="true"/>
      <el-table-column label="昵称" align="center" prop="nickName" :show-overflow-tooltip="true"/>
      <el-table-column label="头像" align="center" prop="headImgUrl" :show-overflow-tooltip="true"/>
      <el-table-column label="语言" align="center" prop="language" :show-overflow-tooltip="true"/>
      <el-table-column label="订阅场景" align="center" prop="subscribeScene" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.subscribe_scene" :value="scope.row.subscribeScene"/>
        </template>
      </el-table-column>
      <el-table-column label="订阅时间" align="center" prop="subscribeTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.subscribeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="二维码扫码场景" align="center" prop="qrScene" :show-overflow-tooltip="true"/>
      <el-table-column label="二维码扫码场景字符串" align="center" prop="qrSceneStr" :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['user:userOfficialAccount:edit']"
          >修改
          </el-button>
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

    <!-- 添加或修改微信公众号用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组标识" prop="groupId">
          <el-input v-model="form.groupId" placeholder="请输入组标识"/>
        </el-form-item>
        <el-form-item label="openId" prop="openId">
          <el-input v-model="form.openId" placeholder="请输入openId"/>
        </el-form-item>
        <el-form-item label="unionId" prop="unionId">
          <el-input v-model="form.unionId" placeholder="请输入unionId"/>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称"/>
        </el-form-item>
        <el-form-item label="头像" prop="headImgUrl">
          <el-input v-model="form.headImgUrl" placeholder="请输入头像"/>
        </el-form-item>
        <el-form-item label="语言" prop="language">
          <el-input v-model="form.language" placeholder="请输入语言"/>
        </el-form-item>
        <el-form-item label="订阅场景" prop="subscribeScene">
          <el-select v-model="form.subscribeScene" placeholder="请选择订阅场景">
            <el-option
              v-for="dict in dict.type.subscribe_scene"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订阅时间" prop="subscribeTime">
          <el-date-picker clearable
                          v-model="form.subscribeTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择订阅时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="二维码扫码场景" prop="qrScene">
          <el-input v-model="form.qrScene" placeholder="请输入二维码扫码场景"/>
        </el-form-item>
        <el-form-item label="二维码扫码场景字符串" prop="qrSceneStr">
          <el-input v-model="form.qrSceneStr" placeholder="请输入二维码扫码场景字符串"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
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
  listUserOfficialAccount,
  getUserOfficialAccount,
  delUserOfficialAccount,
  addUserOfficialAccount,
  updateUserOfficialAccount,
  pullNewUserList,
  updateNewUserInfo
} from "@/api/user/userOfficialAccount";

export default {
  name: "UserOfficialAccount",
  dicts: ['subscribe_scene'],
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
      // 微信公众号用户表格数据
      userOfficialAccountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        openId: null,
        unionId: null,
        nickName: null,
        subscribeScene: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询微信公众号用户列表 */
    getList() {
      this.loading = true;
      listUserOfficialAccount(this.queryParams).then(response => {
        this.userOfficialAccountList = response.rows;
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
        groupId: null,
        openId: null,
        unionId: null,
        nickName: null,
        headImgUrl: null,
        language: null,
        subscribeScene: null,
        subscribeTime: null,
        qrScene: null,
        qrSceneStr: null,
        remark: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加微信公众号用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUserOfficialAccount(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信公众号用户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateUserOfficialAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUserOfficialAccount(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除微信公众号用户编号为"' + ids + '"的数据项？').then(function () {
        return delUserOfficialAccount(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('user/userOfficialAccount/export', {
        ...this.queryParams
      }, `userOfficialAccount_${new Date().getTime()}.xlsx`)
    },
    /** 拉取公众号最新用户列表 */
    pullNewUserListCli() {
      this.$modal.loading("正在拉取");
      pullNewUserList().then(res => {
        this.getList();
        this.$modal.notifySuccess("成功拉取：" + res.msg + "条");
      }).finally(res => {
        this.$modal.closeLoading();
      })
    },
    /** 批量更新新用户基本信息 */
    updateNewUserInfoCli() {
      this.$modal.loading("正在更新");
      updateNewUserInfo().then(res => {
        this.getList();
        this.$modal.notifySuccess("成功更新：" + res.msg + "条");
      }).finally(res => {
        this.$modal.closeLoading();
      })
    }
  }
};
</script>
