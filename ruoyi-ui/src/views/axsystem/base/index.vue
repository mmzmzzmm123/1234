<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="类型" prop="userRole">
        <el-input
          v-model="queryParams.userRole"
          placeholder="请输入2正常用户 3禁言用户 4虚拟用户 5运营"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册来源" prop="registerSource">
        <el-input
          v-model="queryParams.registerSource"
          placeholder="请输入注册：1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="queryParams.gender" placeholder="请选择性别 0男1女" clearable size="small">
          <el-option
            v-for="dict in genderOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-input
          v-model="queryParams.birthday"
          placeholder="请输入生日"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入邮箱"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户设备" prop="pushToken">
        <el-input
          v-model="queryParams.pushToken"
          placeholder="请输入用户设备"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['axsystem:base:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['axsystem:base:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['axsystem:base:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['axsystem:base:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="baseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="用户ID" align="center" prop="uid" /> -->
      <!-- <el-table-column label="类型" align="center" prop="userRole" /> -->
      <!-- <el-table-column label="注册来源" align="center" prop="registerSource" /> -->
      <el-table-column label="账号" align="center" prop="userName" />
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column label="性别" align="center" prop="gender" :formatter="genderFormat" />
      <el-table-column label="生日" align="center" prop="birthday" />
      <el-table-column label="个人签名" align="center" prop="signature" />
      <el-table-column label="手机号码" align="center" prop="mobile" />
      <!-- <el-table-column label="创建时间" align="center" prop="createTime" /> -->
      <el-table-column label="邮箱" align="center" prop="email" />
      <!-- <el-table-column label="绑定时间" align="center" prop="emailBindTime" /> -->
      <el-table-column label="头像" align="center" prop="face" />
      <el-table-column label="200x200x80" align="center" prop="face200" />
      <el-table-column label="原图头像" align="center" prop="srcface" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="用户设备" align="center" prop="pushToken" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['axsystem:base:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['axsystem:base:remove']"
          >删除</el-button>
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

    <!-- 添加或修改用户基础信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型" prop="userRole">
          <el-input v-model="form.userRole" placeholder="请输入2正常用户 3禁言用户 4虚拟用户 5运营" />
        </el-form-item>
        <el-form-item label="注册来源" prop="registerSource">
          <el-input v-model="form.registerSource" placeholder="请输入注册：1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博" />
        </el-form-item>
        <el-form-item label="账号" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" placeholder="请选择性别 0男1女">
            <el-option
              v-for="dict in genderOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-input v-model="form.birthday" placeholder="请输入生日" />
        </el-form-item>
        <el-form-item label="个人签名" prop="signature">
          <el-input v-model="form.signature" placeholder="请输入个人签名" />
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="绑定时间" prop="mobileBindTime">
          <el-input v-model="form.mobileBindTime" placeholder="请输入手机号码绑定时间" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="绑定时间" prop="emailBindTime">
          <el-input v-model="form.emailBindTime" placeholder="请输入邮箱绑定时间" />
        </el-form-item>
        <el-form-item label="头像" prop="face">
          <el-input v-model="form.face" placeholder="请输入头像" />
        </el-form-item>
        <el-form-item label="200x200x80" prop="face200">
          <el-input v-model="form.face200" placeholder="请输入头像 200x200x80" />
        </el-form-item>
        <el-form-item label="原图头像" prop="srcface">
          <el-input v-model="form.srcface" placeholder="请输入原图头像" />
        </el-form-item>
        <el-form-item label="用户设备" prop="pushToken">
          <el-input v-model="form.pushToken" placeholder="请输入用户设备" />
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
import { listBase, getBase, delBase, addBase, updateBase, exportBase } from "@/api/axsystem/base";

export default {
  name: "Base",
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
      // 总条数
      total: 0,
      // 用户基础信息表格数据
      baseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别 0男1女字典
      genderOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userRole: undefined,
        registerSource: undefined,
        userName: undefined,
        nickName: undefined,
        gender: undefined,
        birthday: undefined,
        mobile: undefined,
        email: undefined,
        pushToken: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userRole: [
          { required: true, message: "2正常用户 3禁言用户 4虚拟用户 5运营不能为空", trigger: "blur" }
        ],
        registerSource: [
          { required: true, message: "注册：1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        nickName: [
          { required: true, message: "昵称不能为空", trigger: "blur" }
        ],
        gender: [
          { required: true, message: "性别 0男1女不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.genderOptions = response.data;
    });
  },
  methods: {
    /** 查询用户基础信息列表 */
    getList() {
      this.loading = true;
      listBase(this.queryParams).then(response => {
        this.baseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 性别 0男1女字典翻译
    genderFormat(row, column) {
      return this.selectDictLabel(this.genderOptions, row.gender);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        uid: undefined,
        userRole: undefined,
        registerSource: undefined,
        userName: undefined,
        nickName: undefined,
        gender: undefined,
        birthday: undefined,
        signature: undefined,
        mobile: undefined,
        mobileBindTime: undefined,
        email: undefined,
        emailBindTime: undefined,
        face: undefined,
        face200: undefined,
        srcface: undefined,
        createTime: undefined,
        updateTime: undefined,
        pushToken: undefined
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
      this.ids = selection.map(item => item.uid)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户基础信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uid = row.uid || this.ids
      getBase(uid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户基础信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uid != undefined) {
            updateBase(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addBase(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uids = row.uid || this.ids;
      this.$confirm('是否确认删除用户基础信息编号为"' + uids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBase(uids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有用户基础信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportBase(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
